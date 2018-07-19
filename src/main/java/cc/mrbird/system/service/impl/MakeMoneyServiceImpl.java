package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.MakeMoneyMapper;
import cc.mrbird.system.domain.MakeMoney;
import cc.mrbird.system.domain.NewsInfo;
import cc.mrbird.system.service.MakeMoneyService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("makeMoneySerivce")
public class MakeMoneyServiceImpl extends BaseService<MakeMoney> implements MakeMoneyService {

	@Autowired
	private MakeMoneyMapper MakeMoneyMapper;
	
	@Override
	@Transactional
	public void deleteMk(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(MakeMoney.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		MakeMoney mk=new MakeMoney();
		mk.setFlag(1);
		
		this.MakeMoneyMapper.updateByExampleSelective(mk, example);
	}
}
