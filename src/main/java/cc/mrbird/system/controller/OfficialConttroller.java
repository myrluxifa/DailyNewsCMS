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
import cc.mrbird.system.domain.Official;
import cc.mrbird.system.service.OfficialService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class OfficialConttroller extends BaseController {

	@Autowired
	private OfficialService officialService;
	
	@Log("文案设置")
	@RequestMapping("official")
	@RequiresPermissions("official:list")
	public String index() {
		return "system/official/official";
	}
	
	@RequestMapping("official/list")
	@ResponseBody
	public Map<String, Object> officialList(QueryRequest request) {
		Example example = new Example(Official.class);
		Criteria criteria = example.createCriteria();
		List<Official> list = this.officialService.selectByExample(example);
		
		PageInfo<Official> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("official/getofficial")
	@RequiresPermissions("official:edit")
	@ResponseBody
	public ResponseBo getGoldRewards(String id) {
		try 
		{
			
			Official official=officialService.selectByKey(id);
			return ResponseBo.ok(official);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	
	
	
	@Log("修改文案")
	@RequestMapping("official/update")
	@RequiresPermissions("official:edit")
	@ResponseBody
	public ResponseBo update(Official official) {
		try {
			
			officialService.updateAll(official);
			return ResponseBo.ok("修改文案成功");
		}catch(Exception e) {
			return ResponseBo.error("修改文案失败");
		}
	}
	
}
