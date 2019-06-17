package com.hyjf.am.user.service.front.user.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.user.dao.model.auto.UtmPlat;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.dao.model.auto.UtmRegExample;
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
			return utmPlatCustomizeMapper.selectUtmPlatBySourceIds(utmReg.getUtmId());
		} else {
			return null;
		}
	}
}
