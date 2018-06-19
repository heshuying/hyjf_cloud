package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterAccountHuifuResponse;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.callcenter.client.AccountHuifuClient;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author libin
 * @version AccountHuifuClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class AccountHuifuClientImpl implements AccountHuifuClient {
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
	@Override
	public List<CallcenterAccountHuifuVO> selectBankCardList(
			CallcenterAccountHuifuRequest callcenterAccountHuifuRequest) {
		CallCenterAccountHuifuResponse callCenterAccountHuifuResponse = restTemplate
				.postForEntity("http://AM-USER//am-user/callcenter/getHuifuTiedcardInfo/",callcenterAccountHuifuRequest, CallCenterAccountHuifuResponse.class)
				.getBody();
        if (callCenterAccountHuifuResponse != null) {
            return callCenterAccountHuifuResponse.getResultList();
        }
		return null;
	}
}
