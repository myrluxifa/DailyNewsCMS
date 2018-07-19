package cc.mrbird.system.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.common.util.JpushClientUtil;
import cc.mrbird.common.util.jpush.Alert;
import cc.mrbird.system.dao.NewsInfoMapper;
import cc.mrbird.system.domain.Dict;
import cc.mrbird.system.domain.NewsInfo;
import cc.mrbird.system.domain.User;
import cc.mrbird.system.service.NewsInfoService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("newsInfoService")
public class NewsInfoServiceImpl extends BaseService<NewsInfo> implements NewsInfoService {
	
	@Autowired
	private NewsInfoMapper newsInfoMapper;
	
	@Override
	public List<NewsInfo> findAllNews(NewsInfo newsInfo) {
		try {
			Example example = new Example(NewsInfo.class);
			Criteria criteria = example.createCriteria();
			if (StringUtils.isNotBlank(newsInfo.getCatId())) {
				criteria.andCondition("cat_id=", Long.valueOf(newsInfo.getCatId()));
			}
			example.setOrderByClause("publish_date");
			return this.selectByExample(example);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
	}

	
	public List<NewsInfo> findNewsInfo(NewsInfo newsInfo){
		List<NewsInfo> newsInfoArray=newsInfoMapper.findNewsInfo(newsInfo);
		return newsInfoArray;
	}
	
	@Override
	@Transactional
	public void deleteNews(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(NewsInfo.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		NewsInfo newsInfo=new NewsInfo();
		newsInfo.setFlag(1);
		
		this.newsInfoMapper.updateByExampleSelective(newsInfo, example);
	}
	
	
	public void jpush(String ids) {
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(NewsInfo.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		List<NewsInfo> newsInfoArray=this.selectByExample(example);
		for(NewsInfo n:newsInfoArray) {
			Alert alert=new Alert();
	    	alert.setNewsId(n.getId());
	    	alert.setBody(n.getContent().substring(0,20));
	    	alert.setCreateTime(df.format(n.getPublishDate()));
	    	alert.setTitle(n.getTitle());
	    	alert.setUrl(n.getUrl());
	    	String jsonstr=new Gson().toJson(alert);
	    	
	    	JsonObject returnData = new JsonParser().parse(jsonstr).getAsJsonObject();
	    
			JpushClientUtil.sendToAll(returnData, new JsonObject());
		}
	}
}
