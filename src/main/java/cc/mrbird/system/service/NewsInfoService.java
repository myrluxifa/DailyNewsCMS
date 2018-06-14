package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.Dict;
import cc.mrbird.system.domain.NewsInfo;

public interface NewsInfoService extends IService<NewsInfo>  {

	List<NewsInfo> findAllNews(NewsInfo newsInfo);

	List<NewsInfo> findNewsInfo(NewsInfo newsInfo);

	void deleteNews(String ids);
	
	void jpush(String ids);
}
