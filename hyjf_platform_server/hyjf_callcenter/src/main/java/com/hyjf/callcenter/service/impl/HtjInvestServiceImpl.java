package com.hyjf.callcenter.service.impl;

import com.hyjf.am.resquest.callcenter.CallcenterHtjInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.AmTradeClient;
import com.hyjf.callcenter.service.HtjInvestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HtjInvestServiceImpl implements HtjInvestService {
	@Autowired
	private AmTradeClient amTradeClient;
	@Override
	public List<CallcenterHtjInvestVO> getRecordList(UserVO user, Integer limitStart, Integer limitEnd) {
		CallcenterHtjInvestRequest callcenterHtjInvestRequest = new CallcenterHtjInvestRequest();
		callcenterHtjInvestRequest.setLimitStart(limitStart);
		callcenterHtjInvestRequest.setLimitSize(limitEnd);
		callcenterHtjInvestRequest.setUserName(user.getUsername());
		List<CallcenterHtjInvestVO> list = amTradeClient.selectBorrowInvestList(callcenterHtjInvestRequest);
		return list;
	}
}
