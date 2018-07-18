package cc.mrbird.system.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.Constant;
import cc.mrbird.common.util.ImgUtil;
import cc.mrbird.common.util.Util;
import cc.mrbird.system.domain.BalanceLog;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.MakeMoney;
import cc.mrbird.system.domain.MakeMoneyLog;
import cc.mrbird.system.domain.UserLogin;
import cc.mrbird.system.service.BalanceLogService;
import cc.mrbird.system.service.MakeMoneyLogService;
import cc.mrbird.system.service.MakeMoneyService;
import cc.mrbird.system.service.UserLoginService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class MakeMoneyController extends BaseController {

	@Autowired
	private MakeMoneyService makeMoneyService;
	
	@Autowired
	private MakeMoneyLogService makeMoneyLogService;
	
	@Autowired
	private BalanceLogService balanceLogService;
	
	@Autowired
	private UserLoginService userLoginSerivce;
	
	
	@Log("赚钱列表页")
	@RequestMapping("makeMoney")
	@RequiresPermissions("makeMoney:list")
	public String index() {
		return "system/makeMoney/makeMoney";
	}
	
	@RequestMapping("makeMoney/list")
	@ResponseBody
	public Map<String, Object> newsList(QueryRequest request, MakeMoney mk) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		Example example = new Example(MakeMoney.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag=",0);
		if(StringUtils.isNotBlank(mk.getType())) {
			criteria.andCondition("type=", mk.getType());
		}
		if(StringUtils.isNotBlank(mk.getTitle())) {
			criteria.andLike("title", "%"+mk.getTitle().trim()+"%");
		}
		example.setOrderByClause("create_time desc");
		List<MakeMoney> list = this.makeMoneyService.selectByExample(example);
		PageInfo<MakeMoney> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	@Log("删除任务")
	@RequiresPermissions("makeMoney:delete")
	@RequestMapping("makeMoney/delete")
	@ResponseBody
	public ResponseBo deleteMk(String ids) {
		try {
			this.makeMoneyService.deleteMk(ids);
			return ResponseBo.ok("删除任务成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除任务失败，请联系网站管理员！");
		}
	}
	
	@Log("添加高额返利任务")
	@RequestMapping("makeMoney/add")
	@RequiresPermissions("makeMoney:add")
	@ResponseBody
	public ResponseBo add(MakeMoney makeMoney) {
		try {
			String imgUrl=ImgUtil.decryptByBase64(makeMoney.getLogo());
			makeMoney.setLogo(imgUrl);
			makeMoney.setLineOne(Util.translate(makeMoney.getLineOne()));
			makeMoney.setLineTwo(Util.translate(makeMoney.getLineTwo()));
			makeMoney.setCreateTime(new Date());
			makeMoneyService.save(makeMoney);
			
			return ResponseBo.ok("新增任务成功");
		}catch(Exception e) {
			return ResponseBo.error("新增任务失败");
		}
	}
	
	
	@RequestMapping("makeMoney/getMakeMoney")
	@RequiresPermissions("makeMoney:edit")
	@ResponseBody
	public ResponseBo getMakeMoney(String id) {
		try 
		{
			MakeMoney makeMoney=makeMoneyService.selectByKey(id);
			return ResponseBo.ok(makeMoney);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	@Log("修改轻松赚钱任务")
	@RequestMapping("makeMoney/update")
	@RequiresPermissions("makeMoney:edit")
	@ResponseBody
	public ResponseBo update(MakeMoney makeMoney) {
		try {
			if(StringUtil.isBlank(makeMoney.getLogoOld())) {
				String imgUrl=ImgUtil.decryptByBase64(makeMoney.getLogo());
				makeMoney.setLogo(imgUrl);
			}
			makeMoney.setLineOne(Util.translate(makeMoney.getLineOne()));
			makeMoney.setLineTwo(Util.translate(makeMoney.getLineTwo()));
			makeMoneyService.updateAll(makeMoney);
			return ResponseBo.ok("修改任务成功");
		}catch(Exception e) {
			return ResponseBo.error("修改任务失败");
		}
	}
	
	@RequestMapping("makeMoney/uploadImg")
	@ResponseBody
	public String uploadFile(MultipartFile file) {
		String url=ImgUtil.uploadOneFile(file);
		return url;
	}
	
	
	
	@Log("赚钱报名列表页")
	@RequestMapping("makeMoneyLog")
	@RequiresPermissions("makeMoneyLog:list")
	public String makeMoneyLogIndex() {
		return "system/makeMoneyLog/makeMoneyLog";
	}
	
	@RequestMapping("makeMoneyLog/list")
	@ResponseBody
	public Map<String, Object> makeMoneyLogList(QueryRequest request, MakeMoneyLog mk) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		
		switch (mk.getStatus()) {
		case 1:
			mk.set_status(7);
			break;
		case 2:
			mk.set_status(7);
			break;
		case 3:
			mk.set_status(3);
			break;
		case 4:
			mk.set_status(4);
			break;
		case 5:
			mk.set_status(6);
			break;
		case 6:
			mk.set_status(6);
			break;
		}
		List<MakeMoneyLog> list = this.makeMoneyLogService.findAll(mk);
		PageInfo<MakeMoneyLog> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	
	@RequestMapping("makeMoneyLog/getMakeMoneyLog")
	@RequiresPermissions("makeMoneyLog:check")
	@ResponseBody
	public ResponseBo getMakeMoneyLog(String id) {
		try 
		{
			MakeMoneyLog ml=new MakeMoneyLog();
			ml.setId(id);
			MakeMoneyLog makeMoneyLog=makeMoneyLogService.findById(ml);
			return ResponseBo.ok(makeMoneyLog);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	@Log("修改轻松赚钱任务")
	@RequestMapping("makeMoneyLog/update")
	@RequiresPermissions("makeMoneyLog:check")
	@ResponseBody
	public ResponseBo updateMakeMoneyLog(MakeMoneyLog makeMoneyLog) {
		try {
			MakeMoneyLog m=makeMoneyLogService.findById(makeMoneyLog);
			m.setStatus(makeMoneyLog.getStatus());
			makeMoneyLogService.updateAll(m);
			if(m.getStatus()==3) {
				MakeMoney mk=makeMoneyService.selectByKey(m.getMakeMoneyId());
				
				UserLogin us=userLoginSerivce.selectByKey(m.getUserId());
				
				us.setBalance(String.valueOf(Double.valueOf(m.getBalance())+Double.valueOf(mk.getCash())));
				userLoginSerivce.updateAll(us);
				
				BalanceLog balanceLog=new BalanceLog();
				balanceLog.setCreateTime(new Date());
				balanceLog.setNum(mk.getCash());
				balanceLog.setType(Constant.MAKE_MONEY_REWARDS);
				balanceLog.setOldNum(m.getBalance());
				balanceLog.setNewNum(String.valueOf(Double.valueOf(m.getBalance())+Double.valueOf(mk.getCash())));
				balanceLog.setUserId(m.getUserId());
				balanceLogService.save(balanceLog);
			}
			return ResponseBo.ok("审批成功");
		}catch(Exception e) {
			return ResponseBo.error("审批失败");
		}
	}
	
}
