package cc.mrbird.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;

import cc.mrbird.common.annotation.Log;
import cc.mrbird.common.controller.BaseController;
import cc.mrbird.common.domain.QueryRequest;
import cc.mrbird.common.domain.ResponseBo;
import cc.mrbird.system.domain.ReadRewards;
import cc.mrbird.system.service.ReadRewardsService;

@Controller
public class ReadRewardsController extends BaseController {

	
	@Autowired
	private ReadRewardsService readRewardsService;
	
	@Log("金币设置")
	@RequestMapping("readSetting")
	@RequiresPermissions("readSetting:list")
	public String index() {
		return "system/readSetting/readSetting";
	}
	
	@RequestMapping("readSetting/list")
	@ResponseBody
	public Map<String, Object> ReadSettingList(QueryRequest request) {
		
		
		List<ReadRewards> list = this.readRewardsService.findRewards();
		PageInfo<ReadRewards> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	
	@RequestMapping("readSetting/getReadSetting")
	@RequiresPermissions("readSetting:edit")
	@ResponseBody
	public ResponseBo getBalanceRewards(String id) {
		try 
		{
			List<ReadRewards> list = this.readRewardsService.findRewards();
			return ResponseBo.ok(list.get(0));
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	@Log("修改参数")
	@RequestMapping("readSetting/update")
	@RequiresPermissions("readSetting:edit")
	@ResponseBody
	public ResponseBo update(ReadRewards readRewards) {
		try {
			
			readRewardsService.update(readRewards);
			return ResponseBo.ok("修改参数成功");
		}catch(Exception e) {
			return ResponseBo.error("修改参数失败");
		}
	}
}
