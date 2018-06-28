package cc.mrbird.system.service;

import cc.mrbird.common.service.IService;
import cc.mrbird.system.domain.InviteImgBanner;

public interface InviteImgBannerService extends IService<InviteImgBanner> {

	void deleteInviteImgBanner(String ids);

}
