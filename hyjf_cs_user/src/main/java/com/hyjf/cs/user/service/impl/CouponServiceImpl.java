package com.hyjf.cs.user.service.impl;

import org.springframework.stereotype.Service;

import com.hyjf.cs.user.service.CouponService;

/**
 * @author xiasq
 * @version CouponServiceImpl, v0.1 2018/4/12 11:36
 */

@Service
public class CouponServiceImpl implements CouponService {
	@Override
	public boolean checkActivityIfAvailable(String activityId) {
		if (activityId == null) {
			return false;
		}
		// ActivityVO activityVO = activityListMapper.selectByPrimaryKey(new
		// Integer(activityId));
		// if(activityVO==null){
		// return false;
		// }
		// if(activityVO.getTimeStart()> GetDate.getNowTime10()){
		// return false;
		// }
		// if(activityVO.getTimeEnd()<GetDate.getNowTime10()){
		// return false;
		// }

		return true;
	}
}
