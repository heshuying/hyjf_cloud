package com.hyjf.am.market.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.market.dao.mapper.auto.ActivityUserRewardMapper;
import com.hyjf.am.market.dao.model.auto.ActivityUserReward;
import com.hyjf.am.market.dao.model.auto.ActivityUserRewardExample;
import com.hyjf.am.market.service.ActivityUserRewardService;

/**
 * @author xiasq
 * @version ActivityUserRewardServiceImpl, v0.1 2019-04-18 16:13
 */
@Service
public class ActivityUserRewardServiceImpl implements ActivityUserRewardService {
    @Autowired
    private ActivityUserRewardMapper mapper;

    @Override
	public ActivityUserReward selectByUserId(Integer userId, Integer activityId, Integer grade) {
		ActivityUserRewardExample example = new ActivityUserRewardExample();
		ActivityUserRewardExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId).andActivityIdEqualTo(activityId).andGradeEqualTo(grade);
		List<ActivityUserReward> list = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

    @Override
    public int insertActivityUserReward(ActivityUserReward record) {
        return mapper.insertSelective(record);
    }
}
