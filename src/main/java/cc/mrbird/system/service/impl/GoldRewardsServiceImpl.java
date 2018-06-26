package cc.mrbird.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.GoldRewardsMapper;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.GoldRewards;
import cc.mrbird.system.service.EasyMoneyService;
import cc.mrbird.system.service.GoldRewardsService;

@Service("GoldRewardsService")
public class GoldRewardsServiceImpl  extends BaseService<GoldRewards> implements GoldRewardsService {

	@Autowired
	private GoldRewardsMapper goldRewardsMapper;
	
	public List<GoldRewards> findGoldRewards(GoldRewards goldRewards){
		return goldRewardsMapper.findGoldRewards(goldRewards);
	}
	
	public GoldRewards findById(GoldRewards goldRewards) {
		return goldRewardsMapper.findById(goldRewards);
	}
	
	public int updateGold(GoldRewards goldRewards) {
		return goldRewardsMapper.updateGold(goldRewards);
	}
	
	public int updateBalance(GoldRewards goldRewards) {
		return goldRewardsMapper.updateBalance(goldRewards);
	}
}
