package cc.mrbird.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.mrbird.common.service.impl.BaseService;
import cc.mrbird.system.dao.AdvertImgMapper;
import cc.mrbird.system.domain.AdvertImg;
import cc.mrbird.system.service.AdvertImgService;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("advertImgService")
public class AdvertImgServiceImpl extends BaseService<AdvertImg> implements AdvertImgService  {

	@Autowired
	private AdvertImgMapper advertImgMapper;
	
	public int deleteByAdvertId(String id) {
		Example e=new Example(AdvertImg.class);
		Criteria criteria = e.createCriteria();
		criteria.andCondition("advert_id=",id);
		return  advertImgMapper.deleteByExample(e);
	}
}

