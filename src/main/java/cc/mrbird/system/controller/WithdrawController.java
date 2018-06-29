package cc.mrbird.system.controller;

import java.util.Arrays;
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
import cc.mrbird.system.dao.WithdrawMapper;
import cc.mrbird.system.domain.NewsInfo;
import cc.mrbird.system.domain.WithdrawLog;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class WithdrawController extends BaseController {

	@Autowired
	private WithdrawMapper withdrawMapper;
	
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
	public ResponseBo pass(String ids) {
		try {
			List<String> list = Arrays.asList(ids.split(","));
			withdrawMapper.updateState(list, "1", String.valueOf(getCurrentUser().getUserId()));
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
			withdrawMapper.updateState(list, "2", String.valueOf(getCurrentUser().getUserId()));
			return ResponseBo.ok("操作成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("操作失败，请联系网站管理员！");
		}
	}
}
