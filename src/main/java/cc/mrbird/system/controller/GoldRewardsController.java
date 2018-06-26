package cc.mrbird.system.controller;

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
import cc.mrbird.system.domain.GoldRewards;
import cc.mrbird.system.domain.MakeMoney;
import cc.mrbird.system.service.GoldRewardsService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class GoldRewardsController extends BaseController{

	@Autowired
	private GoldRewardsService goldRewardsService;
	
	@Log("金币设置")
	@RequestMapping("goldSetting")
	@RequiresPermissions("goldSetting:list")
	public String index() {
		return "system/goldSetting/goldSetting";
	}
	
	@RequestMapping("goldSetting/list")
	@ResponseBody
	public Map<String, Object> goldSettingList(QueryRequest request) {
		
		GoldRewards goldRewards=new GoldRewards();
		goldRewards.setMoney("0");
		List<GoldRewards> list = this.goldRewardsService.findGoldRewards(goldRewards);
		PageInfo<GoldRewards> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	@RequestMapping("goldSetting/getGoldSetting")
	@RequiresPermissions("goldSetting:edit")
	@ResponseBody
	public ResponseBo getMakeMoney(String id) {
		try 
		{
			GoldRewards g=new GoldRewards();
			g.setId(id);
			GoldRewards goldRewards=goldRewardsService.findById(g);
			return ResponseBo.ok(goldRewards);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	@Log("修改金币参数")
	@RequestMapping("goldSetting/update")
	@RequiresPermissions("goldSetting:edit")
	@ResponseBody
	public ResponseBo update(GoldRewards goldRewards) {
		try {
			
			goldRewardsService.updateGold(goldRewards);
			return ResponseBo.ok("修改参数成功");
		}catch(Exception e) {
			return ResponseBo.error("修改参数失败");
		}
	}
}
