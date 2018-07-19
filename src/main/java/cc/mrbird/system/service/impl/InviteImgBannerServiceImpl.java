package cc.mrbird.system.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.InviteImgBannerMapper;
import cc.mrbird.system.domain.AdvertInfo;
import cc.mrbird.system.domain.EasyMoney;
import cc.mrbird.system.domain.InviteImgBanner;
import cc.mrbird.system.service.InviteImgBannerService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("InviteImgBannerService")
public class InviteImgBannerServiceImpl extends BaseService<InviteImgBanner> implements InviteImgBannerService {

	@Autowired
	private InviteImgBannerMapper inviteImgBannerMapper;
	
	@Override
	@Transactional
	public void deleteInviteImgBanner(String ids) {
		List<String> list = Arrays.asList(ids.split(","));
		Example example=new Example(InviteImgBanner.class);
		Criteria criteria = example.createCriteria(); 
		criteria.andIn("id", list);
		InviteImgBanner advert=new InviteImgBanner();
		advert.setFlag(1);
		
		this.inviteImgBannerMapper.updateByExampleSelective(advert, example);
	}
}
