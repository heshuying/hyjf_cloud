package com.hyjf.callcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.HztInvestClient;
import com.hyjf.callcenter.service.HztInvestService;

/**
 * @author libin
 * @version HztInvestServiceImpl, v0.1 2018/6/5
 */
@Service
public class HztInvestServiceImpl implements HztInvestService {
	
	@Autowired
	private HztInvestClient hztInvestBeanClient;

	@Override
	public List<CallcenterHztInvestVO> getRecordList(UserVO user, Integer limitStart, Integer limitEnd) {
		CallcenterHztInvestRequest callcenterHztInvestRequest = new CallcenterHztInvestRequest();
		callcenterHztInvestRequest.setLimitStart(limitStart);
		callcenterHztInvestRequest.setLimitSize(limitEnd);
		callcenterHztInvestRequest.setUserId(user.getUserId()+"");
		List<CallcenterHztInvestVO> list = null;
		list = hztInvestBeanClient.selectBorrowInvestList(callcenterHztInvestRequest);
		return list;
	}

}
