package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.GoldRewards;
import cc.mrbird.system.domain.ReadRewards;

public interface ReadRewardsService  extends IService<ReadRewards>  {
	List<ReadRewards> findRewards();

	int update(ReadRewards readRewards);
}
