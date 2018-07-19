package cc.mrbird.job.task;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import cc.mrbird.common.util.JpushClientUtil;
import cc.mrbird.common.util.jpush.Alert;
import cc.mrbird.common.util.jpush.News;
import cc.mrbird.system.dao.NewsInfoMapper;
import cc.mrbird.system.domain.NewsInfo;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component("jpushTask")
public class JPushTask {
	
	@Autowired
	private NewsInfoMapper newsInfoMapper;
	
	public void pushNews() {
		System.out.println("开始执行新闻推送");
		Example example=new Example(NewsInfo.class);
		Criteria criteria = example.createCriteria();
		example.setOrderByClause("publish_date");
		
		List<NewsInfo> newsInfoArray=newsInfoMapper.selectByExample(example);
		NewsInfo n=newsInfoArray.get(0);
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Alert alert=new Alert();
		alert.setType("1");
		News news=new News();
		news.setNewsId(n.getId());
		news.setBody(n.getContent().substring(0,20));
		news.setCreateTime(df.format(n.getPublishDate()));
		news.setTitle(n.getTitle());
		news.setUrl(n.getUrl());
		alert.setNews(news);
    	String jsonstr=new Gson().toJson(alert);
    	
    	JsonObject returnData = new JsonParser().parse(jsonstr).getAsJsonObject();
    
		JpushClientUtil.sendToAll(returnData, new JsonObject());
		
	}
	
	

}
