package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallcenterHztInvestResponse;
import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;
import com.hyjf.callcenter.client.HztInvestClient;

/**
 * @author libin
 * @version HztInvestClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class HztInvestClientImpl implements HztInvestClient{
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public List<CallcenterHztInvestVO> selectBorrowInvestList(CallcenterHztInvestRequest callcenterHztInvestRequest) {
		CallcenterHztInvestResponse callcenterHztInvestResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryBorrowInvestList", callcenterHztInvestRequest, CallcenterHztInvestResponse.class)
                .getBody();
        if (callcenterHztInvestResponse != null) {
            return callcenterHztInvestResponse.getResultList();
        }
		return null;
	}
}