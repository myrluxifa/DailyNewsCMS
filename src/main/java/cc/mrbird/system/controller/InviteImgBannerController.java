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
import cc.mrbird.common.util.ImgUtil;
import cc.mrbird.system.domain.AdvertInfo;
import cc.mrbird.system.domain.InviteImgBanner;
import cc.mrbird.system.service.InviteImgBannerService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class InviteImgBannerController extends BaseController  {

	@Autowired
	private InviteImgBannerService inviteImgBannerService;
	
	
	@Log("banner")
	@RequestMapping("inviteImgBanner")
	@RequiresPermissions("inviteImgBanner:list")
	public String index() {
		return "system/inviteImgBanner/inviteImgBanner";
	}
	
	@RequestMapping("inviteImgBanner/list")
	@ResponseBody
	public Map<String, Object> advertList(QueryRequest request, InviteImgBanner ib) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		Example example = new Example(AdvertInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag=",0);
		example.setOrderByClause(" sort ASC");
		List<InviteImgBanner> list = this.inviteImgBannerService.selectByExample(example);
		PageInfo<InviteImgBanner> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	@Log("删除广告")
	@RequiresPermissions("inviteImgBanner:delete")
	@RequestMapping("inviteImgBanner/delete")
	@ResponseBody
	public ResponseBo deleteAdvert(String ids) {
		try {
			this.inviteImgBannerService.deleteInviteImgBanner(ids);
			return ResponseBo.ok("删除banner成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除banner失败，请联系网站管理员！");
		}
	}
	
	@Log("添加banner")
	@RequestMapping("inviteImgBanner/add")
	@RequiresPermissions("inviteImgBanner:add")
	@ResponseBody
	public ResponseBo add(InviteImgBanner inviteImgBanner) {
		try {
			String uuid=ImgUtil.getUUID();
			inviteImgBanner.setId(uuid);
			inviteImgBannerService.save(inviteImgBanner);
			return ResponseBo.ok("新增banner成功");
		}catch(Exception e) {
			return ResponseBo.error("新增banner失败");
		}
	}
	
	
	@RequestMapping("inviteImgBanner/getInviteImgBanner")
	@RequiresPermissions("inviteImgBanner:edit")
	@ResponseBody
	public ResponseBo getInviteImgBanner(String id) {
		try 
		{
			InviteImgBanner inviteImgBanner=inviteImgBannerService.selectByKey(id);
			
			return ResponseBo.ok(inviteImgBanner);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	@Log("修改")
	@RequestMapping("inviteImgBanner/update")
	@RequiresPermissions("inviteImgBanner:edit")
	@ResponseBody
	public ResponseBo update(InviteImgBanner inviteImgBanner) {
		try {
			inviteImgBannerService.deleteInviteImgBanner(inviteImgBanner.getId());
			inviteImgBannerService.updateAll(inviteImgBanner);
			return ResponseBo.ok("修改成功");
		}catch(Exception e) {
			return ResponseBo.error("修改失败");
		}
	}
}
