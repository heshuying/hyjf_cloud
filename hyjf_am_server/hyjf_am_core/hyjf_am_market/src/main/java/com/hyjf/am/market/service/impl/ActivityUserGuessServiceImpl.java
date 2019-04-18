package com.hyjf.am.market.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hyjf.am.market.dao.mapper.customize.market.ActivityUserGuessCustomizeMapper;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.vo.admin.ActivityUserGuessVO;
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
	@Autowired
	private ActivityUserGuessCustomizeMapper customizeMapper;

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

    @Override
    public int getGuessListCount(ActivityUserGuessRequest request) {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("userName", request.getUserName());
		requestMap.put("trueName", request.getTrueName());
		requestMap.put("grade", request.getGrade());
		int count = customizeMapper.countGuessList(requestMap);
        return count;
    }

	@Override
	public List<ActivityUserGuessVO> getGuessList(ActivityUserGuessRequest request, int limitStart, int limitEnd) {
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("userName", request.getUserName());
		requestMap.put("trueName", request.getTrueName());
		requestMap.put("grade", request.getGrade());
		if (limitStart != -1) {
			requestMap.put("limitStart", limitStart);
			requestMap.put("limitEnd", limitEnd);
		}
		List<ActivityUserGuessVO> userGuessVOList = customizeMapper.selectGuessUserList(requestMap);
		return userGuessVOList;
	}
}
