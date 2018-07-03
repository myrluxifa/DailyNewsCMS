package cc.mrbird.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cc.mrbird.common.config.MyMapper;
import cc.mrbird.system.domain.WithdrawLog;

public interface WithdrawMapper extends MyMapper<WithdrawLog> {
	List<WithdrawLog> findWithdrawLog(WithdrawLog withdraw);

	int updateState(@Param("ids")List<String> list, @Param("state")String state, @Param("auditingUser")String auditingUser, @Param("remark") String remark);
	
	WithdrawLog findById(@Param("id") String id);
}
