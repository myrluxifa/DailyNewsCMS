package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.EasyMoneyMapper;
import cc.mrbird.system.dao.SysMessageMapper;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.SysMessage;
import cc.mrbird.system.service.SysMessageService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("SysMessageService")
public class SysMessageServiceImpl  extends BaseService<SysMessage> implements SysMessageService {

	@Autowired
	private SysMessageMapper sysMessageMapper;
	
	@Override
	@Transactional
	public void deleteSysMessageByIds(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(SysMessage.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		SysMessage sysMessage=new SysMessage();
		sysMessage.setFlag(1);
		
		this.sysMessageMapper.updateByExampleSelective(sysMessage, example);
	}
	
	
	@Override
	@Transactional
	public void pushMessageByIds(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(SysMessage.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		SysMessage sysMessage=new SysMessage();
		sysMessage.setFlag(2);
		
		this.sysMessageMapper.updateByExampleSelective(sysMessage, example);
	}
}
