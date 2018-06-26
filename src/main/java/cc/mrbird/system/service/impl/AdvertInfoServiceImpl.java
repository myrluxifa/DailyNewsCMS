package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.AdvertInfoMapper;
import cc.mrbird.system.dao.EasyMoneyMapper;
import cc.mrbird.system.domain.AdvertInfo;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.service.AdvertInfoService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("advertInfoService")
public class AdvertInfoServiceImpl extends BaseService<AdvertInfo> implements AdvertInfoService {
	@Autowired
	private AdvertInfoMapper advertInfoMapper;
	
	@Override
	@Transactional
	public void deleteAdvertInfo(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(EasyMoney.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		AdvertInfo advert=new AdvertInfo();
		advert.setFlag(1);
		
		this.advertInfoMapper.updateByExample(advert, example);
	}
}
