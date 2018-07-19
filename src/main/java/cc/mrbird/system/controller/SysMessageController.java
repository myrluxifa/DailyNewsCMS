package cc.mrbird.system.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
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
import cc.mrbird.common.util.ImgUtil;
import cc.mrbird.system.domain.SysMessage;
import cc.mrbird.system.service.SysMessageService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class SysMessageController extends BaseController  {

	@Autowired
	private SysMessageService sysMessageService;
	
	@Log("设置")
	@RequestMapping("sysMessage")
	@RequiresPermissions("sysMessage:list")
	public String index() {
		return "system/sysMessage/sysMessage";
	}
	
	@RequestMapping("sysMessage/list")
	@ResponseBody
	public Map<String, Object> sysMessageList(QueryRequest request,SysMessage sysMessage) {
		Example example = new Example(SysMessage.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag !=" ,1);
		if(StringUtils.isNotBlank(sysMessage.getTitle())) {
			criteria.andLike("title", "%"+sysMessage.getTitle().trim()+"%");
		}
		if(sysMessage.getFlag()!=1) {
			criteria.andCondition("flag=", sysMessage.getFlag());
		}
		example.setOrderByClause("create_time desc");
		List<SysMessage> list = this.sysMessageService.selectByExample(example);
		
		PageInfo<SysMessage> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	
	@RequestMapping("sysMessage/getSysMessage")
	@RequiresPermissions("sysMessage:edit")
	@ResponseBody
	public ResponseBo getSysMessage(String id) {
		try 
		{
			
			SysMessage sysMessage=sysMessageService.selectByKey(id);
			return ResponseBo.ok(sysMessage);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	
	
	@Log("添加")
	@RequestMapping("sysMessage/add")
	@RequiresPermissions("sysMessage:add")
	@ResponseBody
	public ResponseBo add(SysMessage sysMessage) {
		try {
			String uuid=ImgUtil.getUUID();
			sysMessage.setId(uuid);
			sysMessageService.save(sysMessage);
			return ResponseBo.ok("添加成功");
		}catch(Exception e) {
			return ResponseBo.error("添加失败");
		}
	}
	
	
	
	@Log("修改")
	@RequestMapping("sysMessage/update")
	@RequiresPermissions("sysMessage:edit")
	@ResponseBody
	public ResponseBo update(SysMessage sysMessage) {
		try {
			
			sysMessageService.updateAll(sysMessage);
			return ResponseBo.ok("修改成功");
		}catch(Exception e) {
			return ResponseBo.error("修改失败");
		}
	}
	
	
	@Log("删除")
	@RequestMapping("sysMessage/delete")
	@RequiresPermissions("sysMessage:delete")
	@ResponseBody
	public ResponseBo delete(String ids) {
		try {
			
			sysMessageService.deleteSysMessageByIds(ids);
			return ResponseBo.ok("删除成功");
		}catch(Exception e) {
			return ResponseBo.error("删除失败");
		}
	}
	
	@Log("发布")
	@RequestMapping("sysMessage/push")
	@RequiresPermissions("sysMessage:push")
	@ResponseBody
	public ResponseBo push(String ids) {
		try {
			sysMessageService.pushMessageByIds(ids);
			return ResponseBo.ok("发布成功");
		}catch(Exception e) {
			return ResponseBo.error("发布失败");
		}
	}
	
	
}
