package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.GoldRewards;

public interface GoldRewardsService  extends IService<GoldRewards> {
	
	List<GoldRewards> findGoldRewards(GoldRewards goldRewards);
	
    GoldRewards findById(GoldRewards goldRewards);
	
    int updateGold(GoldRewards goldRewards);
    
    int updateBalance(GoldRewards goldRewards);

}
