package cc.mrbird.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.MakeMoneyLogMapper;
import cc.mrbird.system.domain.MakeMoneyLog;
import cc.mrbird.system.service.MakeMoneyLogService;

@Service("MakeMoneyLogService")
public class MakeMoneyLogServiceImpl  extends BaseService<MakeMoneyLog> implements MakeMoneyLogService{

	@Autowired
	private MakeMoneyLogMapper makeMoneyLogMapper;
	
	@Override
	public MakeMoneyLog findById(MakeMoneyLog makeMoneyLog) {
		// TODO Auto-generated method stub
		return makeMoneyLogMapper.findById(makeMoneyLog);
	}

	@Override
	public List<MakeMoneyLog> findAll(MakeMoneyLog makeMoneyLog) {
		// TODO Auto-generated method stub
		return makeMoneyLogMapper.findAll(makeMoneyLog);
	}

}
