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
import cc.mrbird.system.domain.Dict;
import cc.mrbird.system.domain.NewsInfo;
import cc.mrbird.system.service.NewsInfoService;

@Controller
public class NewsController extends BaseController {
	
	@Autowired
	private NewsInfoService newsInfoService;
	
	@Log("获得新闻列表")
	@RequestMapping("news")
	@RequiresPermissions("news:list")
	public String index() {
		return "system/news/news";
	}
	
	
	@RequestMapping("news/list")
	@ResponseBody
	public Map<String, Object> newsList(QueryRequest request, NewsInfo newsInfo) {
		PageHelper.startPage(request.getPageNum(), request.getPageSize());
		List<NewsInfo> list = this.newsInfoService.findNewsInfo(newsInfo);
		PageInfo<NewsInfo> pageInfo = new PageInfo<>(list);
		return getDataTable(pageInfo);
	}

	@Log("删除新闻")
	@RequiresPermissions("news:delete")
	@RequestMapping("news/delete")
	@ResponseBody
	public ResponseBo deleteNews(String ids) {
		try {
			this.newsInfoService.deleteNews(ids);
			return ResponseBo.ok("删除新闻成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("删除新闻失败，请联系网站管理员！");
		}
	}
	
	@Log("推送新闻")
	@RequiresPermissions("news:Jpush")
	@RequestMapping("news/jpush")
	@ResponseBody
	public ResponseBo jpush(String ids) {
		try {
			this.newsInfoService.jpush(ids);
			return ResponseBo.ok("推送新闻成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseBo.error("推送新闻失败，请联系网站管理员！");
		}
	}
}

