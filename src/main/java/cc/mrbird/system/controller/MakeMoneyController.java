package cc.mrbird.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import cc.mrbird.system.domain.MakeMoney;
import cc.mrbird.system.service.MakeMoneyService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class MakeMoneyController extends BaseController {

	@Autowired
	private MakeMoneyService makeMoneyService;
	
	
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
		List<MakeMoney> list = this.makeMoneyService.selectByExample(example);
		PageInfo<MakeMoney> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	@Log("删除新闻")
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
}
