package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.AdvertInfo;

public interface AdvertInfoService extends IService<AdvertInfo>  {

	void deleteAdvertInfo(String ids);
	
}
