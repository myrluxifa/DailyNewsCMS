package cc.mrbird.system.dao;

import java.util.List;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.MakeMoneyLog;

public interface MakeMoneyLogMapper extends MyMapper<MakeMoneyLog>  {

	MakeMoneyLog findById(MakeMoneyLog makeMoneyLog);
	
	List<MakeMoneyLog> findAll(MakeMoneyLog makeMoneyLog);
}
