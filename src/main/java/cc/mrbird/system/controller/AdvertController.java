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
import cc.mrbird.common.util.ArrayUtil;
import cc.mrbird.common.util.ImgUtil;
import cc.mrbird.system.domain.AdvertImg;
import cc.mrbird.system.domain.AdvertInfo;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.service.AdvertImgService;
import cc.mrbird.system.service.AdvertInfoService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Controller
public class AdvertController extends BaseController {

	@Autowired
	private AdvertInfoService advertInfoService;
	
	@Autowired
	private AdvertImgService advertImgService;
	
	@Log("广告")
	@RequestMapping("advert")
	@RequiresPermissions("advert:list")
	public String index() {
		return "system/advert/advert";
	}
	
	@RequestMapping("advert/list")
	@ResponseBody
	public Map<String, Object> advertList(QueryRequest request, AdvertInfo ad) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		Example example = new Example(AdvertInfo.class);
		Criteria criteria = example.createCriteria();
		criteria.andCondition("flag=",0);
		if(StringUtils.isNotBlank(ad.getTitle())) {
			criteria.andLike("title", "%"+ad.getTitle()+"%");
		}
		List<AdvertInfo> list = this.advertInfoService.selectByExample(example);
		PageInfo<AdvertInfo> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}
	
	
	@Log("删除广告")
	@RequiresPermissions("advert:delete")
	@RequestMapping("advert/delete")
	@ResponseBody
	public ResponseBo deleteAdvert(String ids) {
		try {
			this.advertInfoService.deleteAdvertInfo(ids);
			return ResponseBo.ok("删除广告成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除广告失败，请联系网站管理员！");
		}
	}
	
	@Log("添加广告")
	@RequestMapping("advert/add")
	@RequiresPermissions("advert:add")
	@ResponseBody
	public ResponseBo add(AdvertInfo advertInfo) {
		try {
			String uuid=ImgUtil.getUUID();
			advertInfo.setId(uuid);
			advertInfoService.save(advertInfo);
			List<String> imgs=ArrayUtil.stringToList(advertInfo.getImgs());
			for(String s:imgs) {
				advertImgService.save(new AdvertImg(s,uuid));
			}
			return ResponseBo.ok("新增广告成功");
		}catch(Exception e) {
			return ResponseBo.error("新增广告失败");
		}
	}
	
	
	@RequestMapping("advert/getAdvert")
	@RequiresPermissions("advert:edit")
	@ResponseBody
	public ResponseBo getAdvert(String id) {
		try 
		{
			AdvertInfo advertInfo=advertInfoService.selectByKey(id);
			Example example = new Example(AdvertInfo.class);
			Criteria criteria = example.createCriteria();
			criteria.andCondition("advert_id=",id);
			List<AdvertImg> advertImgs=advertImgService.selectByExample(example);
			String imgs="";
			for(AdvertImg ai:advertImgs) {
				if(imgs.length()==0) {
					imgs=ai.getImg();
				}else{ 
					imgs=imgs+ArrayUtil.separator+ai.getImg();
				}
			}
			advertInfo.setImgs(imgs);
			return ResponseBo.ok(advertInfo);
		}catch (Exception e) {
			// TODO: handle exception
			return ResponseBo.error("获取信息失败");
		}
	}
	
	@Log("修改轻松赚钱任务")
	@RequestMapping("advert/update")
	@RequiresPermissions("advert:edit")
	@ResponseBody
	public ResponseBo update(AdvertInfo advertInfo) {
		try {
			advertImgService.deleteByAdvertId(advertInfo.getId());
			advertInfoService.updateAll(advertInfo);
			List<String> imgs=ArrayUtil.stringToList(advertInfo.getImgs());
			for(String s:imgs) {
				advertImgService.save(new AdvertImg(s,advertInfo.getId()));
			}
			return ResponseBo.ok("修改成功");
		}catch(Exception e) {
			return ResponseBo.error("修改失败");
		}
	}
}
