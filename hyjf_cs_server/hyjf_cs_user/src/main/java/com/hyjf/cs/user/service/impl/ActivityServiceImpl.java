package com.hyjf.cs.user.service.impl;

import com.hyjf.am.vo.market.ActivityListVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.user.client.AmMarketClient;
import com.hyjf.cs.user.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version ActivityServiceImpl, v0.1 2018/4/12 11:36
 */

@Service
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private AmMarketClient amMarketClient;

	@Override
	public boolean checkActivityIfAvailable(String activityId) {
		if (activityId == null) {
			return false;
		}

		ActivityListVO activityVO = amMarketClient.findActivityById(new Integer(activityId));

		if (activityVO == null) {
			return false;
		}
		if (activityVO.getTimeStart() > GetDate.getNowTime10()) {
			return false;
		}
		if (activityVO.getTimeEnd() < GetDate.getNowTime10()) {
			return false;
		}

		return true;
	}
}
