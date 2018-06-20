package com.hyjf.callcenter.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallcenterHtjInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.HtjInvestClient;
import com.hyjf.callcenter.service.HtjInvestService;

@Service
public class HtjInvestServiceImpl implements HtjInvestService {
	@Autowired
	private HtjInvestClient htjInvestBeanClient;
	@Override
	public List<CallcenterHtjInvestVO> getRecordList(UserVO user, Integer limitStart, Integer limitEnd) {
		CallcenterHtjInvestRequest callcenterHtjInvestRequest = new CallcenterHtjInvestRequest();
		callcenterHtjInvestRequest.setLimitStart(limitStart);
		callcenterHtjInvestRequest.setLimitSize(limitEnd);
		callcenterHtjInvestRequest.setUserName(user.getUsername());
		List<CallcenterHtjInvestVO> list = htjInvestBeanClient.selectBorrowInvestList(callcenterHtjInvestRequest);
		return list;
	}
}
