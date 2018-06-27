package cc.mrbird.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.BalanceLogMapper;
import cc.mrbird.system.domain.BalanceLog;
import cc.mrbird.system.service.BalanceLogService;

@Service("BalanceLogService")
public class BalanceLogServiceImpl extends BaseService<BalanceLog> implements BalanceLogService{
	@Autowired
	private BalanceLogMapper balanceLogMapper;
}
