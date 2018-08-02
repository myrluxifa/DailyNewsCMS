package cc.mrbird.system.controller;

import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.BeanTools;
import cc.mrbird.common.util.NumberUtils;
import cc.mrbird.system.controller.bean.PayToUser;
import cc.mrbird.system.controller.bean.PayToUserRes;
import cc.mrbird.system.dao.UserLoginMapper;
import cc.mrbird.system.dao.WithdrawMapper;
import cc.mrbird.system.domain.UserLogin;
import cc.mrbird.system.domain.WithdrawLog;

@Controller
public class WithdrawController extends BaseController {

	private static KeyStore keyStore;
	private static SSLContext sslcontext;
	private static SSLConnectionSocketFactory sslsf;
	private static CloseableHttpClient httpClient;
	
	@Autowired
	public void initWeixinPayCert() {
		try {
			keyStore = KeyStore.getInstance("PKCS12");
			InputStream fis = getClass().getResourceAsStream("/certs/apiclient_cert.p12");//FIXME 需要修改证书
			keyStore.load(fis, "1508334291".toCharArray());
			fis.close();
			sslcontext = SSLContexts  
	                .custom()  
	                .loadKeyMaterial(keyStore,  
	                		"1508334291".toCharArray()).build();  
	        sslsf = new SSLConnectionSocketFactory(  
	                sslcontext, new String[] { "TLSv1" }, null,  
	                new DefaultHostnameVerifier());  
	        
	        httpClient = HttpClients.custom()  
	                .setSSLSocketFactory(sslsf).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	@Autowired
	private WithdrawMapper withdrawMapper;
	
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	@RequestMapping("withdraw")
	@RequiresPermissions("withdraw:list")
	public String index() {
		return "system/withdraw/index";
	}
	
	
	@RequestMapping("withdraw/list")
	@RequiresPermissions("withdraw:list")
	@ResponseBody
	public Map<String, Object> newsList(QueryRequest request, WithdrawLog withdrawLog) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<WithdrawLog> list = withdrawMapper.findWithdrawLog(withdrawLog);
		PageInfo<WithdrawLog> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	@Log("审批通过")
	@RequiresPermissions("withdraw:pass")
	@RequestMapping("withdraw/pass")
	@ResponseBody
	public synchronized ResponseBo pass(String ids) {
		try {
			
			for (String id : ids.split(",")) {
				
				WithdrawLog wlog = withdrawMapper.findById(id);
				
				if(!wlog.getState().equals("0")) {
					return ResponseBo.ok("不能重复审核");
				}
				
				HttpPost post = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
		        PayToUser req = new PayToUser("wx70fd7691b93c23d6", "1508334291", null, String.valueOf(RandomUtils.nextInt(99999999)), 
		        		String.valueOf(RandomUtils.nextInt(99999999)), wlog.getOpenid(), "NO_CHECK", null, String.valueOf(Math.round(Double.valueOf(wlog.getFee()) * 100)), "提现", "47.104.73.127");
		        req.setSign(weixinSign(req));
		        log.info(new Gson().toJson(req));
		        post.setEntity(new StringEntity(BeanTools.toXml(req), "UTF-8"));
		        CloseableHttpResponse response = httpClient.execute(post);
		        
		        HttpEntity entity = response.getEntity();
		        
		        PayToUserRes res = new PayToUserRes();
		        String xml = EntityUtils.toString(entity, "utf8");
		        log.info(xml);
		        res = PayToUserRes.fromXml(xml);
				
		        log.info(String.valueOf(StringUtils.isBlank(res.getErr_code_des())));
		        
		        if(StringUtils.isBlank(res.getErr_code_des())) {
		        	withdrawMapper.updateState(Arrays.asList(id), "1", String.valueOf(getCurrentUser().getUserId()), null);		        	
		        } else {
		        	log.info("else->" + res.getErr_code_des());
		        	withdrawMapper.updateState(Arrays.asList(id), "0", String.valueOf(getCurrentUser().getUserId()), res.getErr_code_des());       	
		        }
		        
			}
			
			return ResponseBo.ok("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("操作失败，请联系网站管理员！");
		}
	}
	
	@Log("审批不通过")
	@RequiresPermissions("withdraw:reject")
	@RequestMapping("withdraw/reject")
	@ResponseBody
	public ResponseBo reject(String ids) {
		try {
			List<String> list = Arrays.asList(ids.split(","));
			
			for (String id : list) {
				WithdrawLog wl = withdrawMapper.findById(id);
				if("0".equals(wl.getState())) {
					//恢复余额
					UserLogin ul = userLoginMapper.selectByPrimaryKey(wl.getUserId());
					ul.setBalance(NumberUtils.format(Double.valueOf(ul.getBalance()) + Double.valueOf(wl.getFee())));
					userLoginMapper.updateByPrimaryKeySelective(ul);
					withdrawMapper.updateState(Arrays.asList(id.split(",")), "2", String.valueOf(getCurrentUser().getUserId()), null);
				}
			}
			
			return ResponseBo.ok("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("操作失败，请联系网站管理员！");
		}
	}
	
	/**
	 * @title weixinSign 
	 * @description 获取微信签名
	 * @author Easy
	 * @date 2017年11月6日 下午9:01:22
	 * @param weixinreq
	 * @return String
	 */
	public String weixinSign(Object weixinreq) {
		//签名
		String content = BeanTools.getContent(weixinreq);
		String strSign = content + "&key=" + "009b927906b97d1507451f45d795d55e";
		return md5(strSign).toUpperCase();
	}
	
	/**
	 * md5签名
	 * 
	 * @param value
	 * @return
	 */
	public String md5(String value) {
		Md5Hash md5 = new Md5Hash(value);
		return md5.toHex();
	}
}
