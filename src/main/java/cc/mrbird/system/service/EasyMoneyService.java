package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.MakeMoney;

public interface EasyMoneyService extends IService<EasyMoney>  {

	void deleteEasyMoney(String ids);
}
