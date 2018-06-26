package cc.mrbird.system.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.GoldRewards;
import cc.mrbird.system.domain.SysLog;

public interface GoldRewardsMapper extends MyMapper<GoldRewards> {

	List<GoldRewards> findGoldRewards(GoldRewards goldRewards);

	int updateGold(GoldRewards goldRewards);
	
	int updateBalance(GoldRewards goldRewards);

	GoldRewards findById(GoldRewards goldRewards);
}
