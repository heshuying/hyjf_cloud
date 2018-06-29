package com.hyjf.callcenter.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.callcenter.client.SrchBalanceInfoClient;
import com.hyjf.callcenter.service.SrchBalanceInfoService;

/**
 * @author libin
 * @version SrchBalanceInfoServiceImpl, v0.1 2018/6/6 13:47
 */
@Service
public class SrchBalanceInfoServiceImpl implements SrchBalanceInfoService {

    @Autowired
    private SrchBalanceInfoClient srchBalanceInfoClient;
	
	@Override
	public List<CallCenterBankAccountManageVO> queryBankAccountInfos(UserVO user, Integer limitStart,
			Integer limitSize) {
		CallCenterBankAccountManageRequest callCenterBankAccountManageRequest = new CallCenterBankAccountManageRequest();
		callCenterBankAccountManageRequest.setLimitStart(limitStart);
		callCenterBankAccountManageRequest.setLimitSize(limitSize);
		callCenterBankAccountManageRequest.setUserName(user.getUsername());
		List<CallCenterBankAccountManageVO> list = srchBalanceInfoClient.queryAccountInfos(callCenterBankAccountManageRequest);
		return list;
	}

}
