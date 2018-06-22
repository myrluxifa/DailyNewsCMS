package cc.mrbird.system.controller;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.common.util.ImgUtil;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.service.EasyMoneyService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class EasyMoneyController  extends BaseController {

	@Autowired
	private EasyMoneyService easyMoneyService;
	
	
	@Log("赚钱列表页")
	@RequestMapping("easyMoney")
	@RequiresPermissions("easyMoney:list")
	public String index() {
		return "system/easyMoney/easyMoney";
	}
	
	@RequestMapping("easyMoney/list")
	@ResponseBody
	public Map<String, Object> newsList(QueryRequest request, EasyMoney em) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		Example example = new Example(EasyMoney.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag=",0);
		if(StringUtils.isNotBlank(em.getTitle())) {
			criteria.andLike("title", "%"+em.getTitle()+"%");
		}
		List<EasyMoney> list = this.easyMoneyService.selectByExample(example);
		PageInfo<EasyMoney> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	

	@RequestMapping("easyMoney/getEasyMoney")
	@RequiresPermissions("easyMoney:edit")
	@ResponseBody
	public ResponseBo getEasyMoney(String id) {
		try 
		{
			EasyMoney easyMoney=easyMoneyService.selectByKey(id);
			return ResponseBo.ok(easyMoney);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	@Log("添加轻松赚钱任务")
	@RequestMapping("easyMoney/add")
	@RequiresPermissions("easyMoney:add")
	@ResponseBody
	public ResponseBo add(EasyMoney easyMoney) {
		try {
			String imgUrl=ImgUtil.decryptByBase64(easyMoney.getImg());
			easyMoney.setImg(imgUrl);
			easyMoneyService.save(easyMoney);
			
			return ResponseBo.ok("新增任务成功");
		}catch(Exception e) {
			return ResponseBo.error("新增任务失败");
		}
	}
	
	@Log("修改轻松赚钱任务")
	@RequestMapping("easyMoney/update")
	@RequiresPermissions("easyMoney:edit")
	@ResponseBody
	public ResponseBo update(EasyMoney easyMoney) {
		try {
			if(StringUtil.isBlank(easyMoney.getImgOld())) {
				String imgUrl=ImgUtil.decryptByBase64(easyMoney.getImg());
				easyMoney.setImg(imgUrl);
			}
			easyMoneyService.updateAll(easyMoney);
			return ResponseBo.ok("修改任务成功");
		}catch(Exception e) {
			return ResponseBo.error("修改任务失败");
		}
	}
	
	@Log("删除任务")
	@RequiresPermissions("easyMoney:delete")
	@RequestMapping("easyMoney/delete")
	@ResponseBody
	public ResponseBo delete(String ids) {
		try {
			this.easyMoneyService.deleteEasyMoney(ids);
			return ResponseBo.ok("删除任务成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除任务失败，请联系网站管理员！");
		}
	}
	
}
