package com.hyjf.am.market.service.impl;

import com.hyjf.am.market.dao.mapper.customize.market.ActivityUserGuessCustomizeMapper;
import com.hyjf.am.market.service.ActivityUserGuessService;
import com.hyjf.am.resquest.admin.ActivityUserGuessRequest;
import com.hyjf.am.vo.admin.ActivityUserGuessVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version ActivityUserGuessServiceImpl, v0.1 2019-04-17 17:20
 */
@Service
public class ActivityUserGuessServiceImpl implements ActivityUserGuessService {

	@Autowired
	private ActivityUserGuessCustomizeMapper customizeMapper;

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
