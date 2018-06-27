package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterAccountHuifuResponse;
import com.hyjf.am.response.callcenter.CallcenterBankConfigResponse;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterAccountHuifuVO;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.callcenter.client.AccountHuifuClient;

/**
 * @author libin
 * @version AccountHuifuClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class AccountHuifuClientImpl implements AccountHuifuClient {
	@Autowired
	private RestTemplate restTemplate;
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
	@Override
	public List<CallcenterBankConfigVO> getBankConfigList(CallcenterAccountHuifuRequest callcenterAccountHuifuRequest) {
		
		CallcenterBankConfigResponse callcenterBankConfigResponse = restTemplate
				.postForEntity("http://AM-CONFIG//am-config/callcenter/getBankConfigList/",callcenterAccountHuifuRequest, CallcenterBankConfigResponse.class)
				.getBody();
        if (callcenterBankConfigResponse != null) {
            return callcenterBankConfigResponse.getResultList();
        }
		return null;
	}
}
