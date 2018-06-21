package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterBankAccountManageResponse;
import com.hyjf.am.resquest.callcenter.CallCenterBankAccountManageRequest;
import com.hyjf.am.vo.callcenter.CallCenterBankAccountManageVO;
import com.hyjf.callcenter.client.SrchBalanceInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;


/**
 * @author libin
 * @version SrchBalanceInfoClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class SrchBalanceInfoClientImpl implements SrchBalanceInfoClient {
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
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
