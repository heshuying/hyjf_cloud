package com.hyjf.callcenter.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallcenterHtjInvestResponse;
import com.hyjf.am.resquest.callcenter.CallcenterHtjInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHtjInvestVO;
import com.hyjf.callcenter.client.HtjInvestClient;

/**
 * @author libin
 * @version HtjInvestClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class HtjInvestClientImpl implements HtjInvestClient  {
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public List<CallcenterHtjInvestVO> selectBorrowInvestList(CallcenterHtjInvestRequest callcenterHtjInvestRequest) {
		CallcenterHtjInvestResponse callcenterHtjInvestResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryHtjBorrowInvestList", callcenterHtjInvestRequest, CallcenterHtjInvestResponse.class)
                .getBody();
        if (callcenterHtjInvestResponse != null) {
            return callcenterHtjInvestResponse.getResultList();
        }
		return null;
	}

}
 