package com.hyjf.am.user.service.front.user.impl;

import java.util.List;

import com.hyjf.am.user.dao.model.auto.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.user.service.front.user.UtmPlatService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;

/**
 * @author：yinhui
 * @Date: 2018/10/23 16:06
 */
@Service
public class UtmPlatServiceImpl extends BaseServiceImpl implements UtmPlatService {

	@Override
	public UtmPlat getUtmPlat(Integer sourceId) {

		UtmPlat utmPlat = utmPlatCustomizeMapper.selectUtmPlatBySourceIds(sourceId);
		return utmPlat;
	}

	@Override
	public UtmPlat getUtmByUserId(Integer userId) {
		UtmRegExample example = new UtmRegExample();
		example.or().andUserIdEqualTo(userId);
		List<UtmReg> lstReg = utmRegMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(lstReg)) {
			UtmReg utmReg = lstReg.get(0);
			return utmPlatCustomizeMapper.selectUtmPlatByUtmId(utmReg.getUtmId());
		} else {
			return null;
		}
	}

	@Override
	public UtmPlat getUtmByUtmId(Integer utmId) {
		return utmPlatCustomizeMapper.selectUtmPlatByUtmId(utmId);
	}


	@Override
	public Utm getUtmBySourceId(Integer sourceId) {
		UtmExample example=new UtmExample();
		example.or().andSourceIdEqualTo(sourceId);
		List<Utm> lstUtm=utmMapper.selectByExample(example);
		return CollectionUtils.isEmpty(lstUtm)?null:lstUtm.get(0);
	}

	@Override
	public Integer getSourceIdByUtmId(Integer tenderUserUtmId) {
		UtmExample example=new UtmExample();
		example.or().andUtmIdEqualTo(tenderUserUtmId);
		List<Utm> lstUtm=utmMapper.selectByExample(example);
		return CollectionUtils.isEmpty(lstUtm)?null:lstUtm.get(0).getSourceId();
	}
}
