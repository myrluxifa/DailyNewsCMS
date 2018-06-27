package cc.mrbird.system.service;

import java.util.List;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.MakeMoney;
import cc.mrbird.system.domain.MakeMoneyLog;

public interface MakeMoneyLogService extends IService<MakeMoneyLog> {

	MakeMoneyLog findById(MakeMoneyLog makeMoneyLog);
	
	List<MakeMoneyLog> findAll(MakeMoneyLog makeMoneyLog);
}
