package cc.mrbird.job.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import cc.mrbird.job.util.*;

import cc.mrbird.common.util.JpushClientUtil;
import cc.mrbird.common.util.jpush.Alert;
import cc.mrbird.common.util.jpush.GoldToBalance;
import cc.mrbird.system.dao.GoldRewardsMapper;
import cc.mrbird.system.dao.OfficialMapper;
import cc.mrbird.system.dao.UserLoginMapper;
import cc.mrbird.system.domain.GoldRewards;
import cc.mrbird.system.domain.Official;
import cc.mrbird.system.domain.UserLogin;
import cc.mrbird.system.service.GoldRewardsService;
import cc.mrbird.system.service.UserLoginService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Component("goldToBalanceTask")
public class GoldToBalanceTask {
	
	@Autowired
	private UserLoginMapper userLoginMapper;
	
	@Autowired
	private GoldRewardsMapper goldRewardsMapper;
	
	@Autowired
	private GoldRewardsService goldRewardsService;
	
	@Autowired
	private UserLoginService userLoginService;
	
	@Autowired
	private OfficialMapper officialMapper;
	
	public void pushGoldToBalance() {
		System.out.println("开始执行金币转余额");
		Example example=new Example(UserLogin.class);
		Criteria criteria = example.createCriteria();
		
		List<UserLogin> userLoginArray=userLoginMapper.selectByExample(example);
		
//		Example er=new Example(GoldRewards.class);
//		Criteria cr = er.createCriteria();
//		cr.andCondition(" id=","19");
		GoldRewards g=new GoldRewards();
		g.setId("19");
		GoldRewards goldRewards=(GoldRewards) goldRewardsMapper.findById(g);
		String ratio=goldRewards.getGold();
		
		for(UserLogin u:userLoginArray) {
			
			double bsum=Double.valueOf(u.getBalance());
			double gsum=(double)u.getGold()/Integer.valueOf(ratio);
			String balance=String.valueOf(NumberUtils.feeFormat(bsum+gsum));
			u.setGold(0l);
			u.setBalance(balance);
			
			userLoginService.updateAll(u);
		}
		
		SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		Alert alert=new Alert();
		alert.setType("2");
		Official official=officialMapper.selectByPrimaryKey("6");
		alert.setGoldToBalance(new GoldToBalance(official.getDetails(),df.format(new Date())));
    	
    	String jsonstr=new Gson().toJson(alert);
    	
    	JsonObject returnData = new JsonParser().parse(jsonstr).getAsJsonObject();
    
		JpushClientUtil.sendToAll(returnData, new JsonObject());
		
	}
	
}
