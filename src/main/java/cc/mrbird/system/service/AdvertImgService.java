package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.AdvertImg;

public interface AdvertImgService extends IService<AdvertImg>  {

	int deleteByAdvertId(String id);
}
