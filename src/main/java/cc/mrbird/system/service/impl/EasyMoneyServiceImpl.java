package cc.mrbird.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.EasyMoneyMapper;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.NewsInfo;
import cc.mrbird.system.domain.UserWithRole;
import cc.mrbird.system.service.EasyMoneyService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("EasyMoneyService")
public class EasyMoneyServiceImpl extends BaseService<EasyMoney> implements EasyMoneyService{
	
	@Autowired
	private EasyMoneyMapper easyMoneyMapper;
	
	@Override
	@Transactional
	public void deleteEasyMoney(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(EasyMoney.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		EasyMoney easyMoney=new EasyMoney();
		easyMoney.setFlag(1);
		
		this.easyMoneyMapper.updateByExample(easyMoney, example);
	}
	
}
