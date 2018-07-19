package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.SysMessage;

public interface SysMessageService extends IService<SysMessage> {

	void deleteSysMessageByIds(String ids);

	void pushMessageByIds(String ids);

}
