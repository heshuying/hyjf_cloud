package com.hyjf.callcenter.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterBankAccountManageResponse;
import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;
import com.hyjf.callcenter.client.SrchBalanceInfoClient;


/**
 * @author libin
 * @version SrchBalanceInfoClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class SrchBalanceInfoClientImpl implements SrchBalanceInfoClient {
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public List<CallCenterBankAccountManageVO> queryAccountInfos(
			CallCenterBankAccountManageRequest callCenterBankAccountManageRequest) {
		CallCenterBankAccountManageResponse manageResponse = restTemplate
        .postForEntity("http://AM-TRADE/am-trade/callcenter/queryAccountInfos/", callCenterBankAccountManageRequest, CallCenterBankAccountManageResponse.class)
        .getBody();
        if (manageResponse != null) {
            return manageResponse.getResultList();
        }
		return null;
	}
}
