package cc.mrbird.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.UserLogin;
import cc.mrbird.system.service.UserLoginService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class UserLoginController extends BaseController {

	@Autowired
	private UserLoginService userLoginService;
	
	@Log("用户列表")
	@RequestMapping("userLogin")
	@RequiresPermissions("userLogin:list")
	public String index() {
		return "system/userLogin/userLogin";
	}
	
	@RequestMapping("userLogin/list")
	@ResponseBody
	public Map<String, Object> userLoginList(QueryRequest request, UserLogin ul) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		Example example = new Example(UserLogin.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag=",0);
		if(StringUtils.isNotBlank(ul.getUserName())) {
			criteria.andLike("userName","%" +ul.getUserName()+"%");
		}
		List<UserLogin> list = this.userLoginService.selectByExample(example);
		PageInfo<UserLogin> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
}
