package cc.mrbird.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.ReadRewardsMapper;
import cc.mrbird.system.domain.ReadRewards;
import cc.mrbird.system.service.ReadRewardsService;

@Service("ReadRewardsService")
public class ReadRewardsServiceImpl extends BaseService<ReadRewards> implements ReadRewardsService  {

	@Autowired
	private ReadRewardsMapper readRewardsMapper;
	
	@Override
	public List<ReadRewards> findRewards() {
		// TODO Auto-generated method stub
		return readRewardsMapper.findRewards();
	}

	@Override
	public int update(ReadRewards readRewards) {
		// TODO Auto-generated method stub
		return readRewardsMapper.update(readRewards);
	}

}
