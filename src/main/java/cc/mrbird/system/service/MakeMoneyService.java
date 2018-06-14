package cc.mrbird.system.service;


import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.MakeMoney;

public interface MakeMoneyService extends IService<MakeMoney>  {

	void deleteMk(String ids);
}
