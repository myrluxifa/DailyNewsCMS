package cc.mrbird.system.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.ReadRewards;

public interface ReadRewardsMapper extends MyMapper<ReadRewards>  {

	List<ReadRewards> findRewards();

	int update(ReadRewards readRewards);
}
