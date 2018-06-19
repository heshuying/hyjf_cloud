package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallcenterHztInvestResponse;
import com.hyjf.am.resquest.callcenter.CallcenterHztInvestRequest;
import com.hyjf.am.vo.callcenter.CallcenterHztInvestVO;
import com.hyjf.callcenter.client.HztInvestClient;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author libin
 * @version HztInvestClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class HztInvestClientImpl implements HztInvestClient{
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
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