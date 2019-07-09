package com.hyjf.am.market.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.market.dao.mapper.auto.ActivityUserGuessMapper;
import com.hyjf.am.market.dao.model.auto.ActivityUserGuess;
import com.hyjf.am.market.dao.model.auto.ActivityUserGuessExample;
import com.hyjf.am.market.service.ActivityUserGuessService;

/**
 * @author xiasq
 * @version ActivityUserGuessServiceImpl, v0.1 2019-04-17 17:20
 */
@Service
public class ActivityUserGuessServiceImpl implements ActivityUserGuessService {

	@Autowired
	private ActivityUserGuessMapper mapper;

	@Override
	public int insertActivityUserGuess(int userId, int guess) {
		ActivityUserGuess record = new ActivityUserGuess();
		record.setUserId(userId);
		record.setGrade(guess);
		return mapper.insert(record);
	}

	@Override
	public ActivityUserGuess selectByUserId(int userId) {
		ActivityUserGuessExample example = new ActivityUserGuessExample();
		ActivityUserGuessExample.Criteria criteria = example.createCriteria();
		criteria.andUserIdEqualTo(userId);
		List<ActivityUserGuess> list = mapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
}
