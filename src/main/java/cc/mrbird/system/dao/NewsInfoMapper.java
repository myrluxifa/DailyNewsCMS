package cc.mrbird.system.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.NewsInfo;

public interface NewsInfoMapper extends MyMapper<NewsInfo> {
	List<NewsInfo> findNewsInfo(NewsInfo newsInfo);
}
