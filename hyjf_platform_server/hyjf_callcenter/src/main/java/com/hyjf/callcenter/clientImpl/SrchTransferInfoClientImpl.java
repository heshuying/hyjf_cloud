package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.SrchTransferInfoResponse;
import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;
import com.hyjf.callcenter.client.SrchTransferInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author libin
 * @version SrchTransferInfoClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class SrchTransferInfoClientImpl implements SrchTransferInfoClient{
	private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
	@Override
	public List<CallCenterBorrowCreditVO> selectBorrowInvestList(SrchTransferInfoRequest srchTransferInfoRequest) {

		SrchTransferInfoResponse srchTransferInfoResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/selectBorrowCreditList", srchTransferInfoRequest, SrchTransferInfoResponse.class)
                .getBody();
        if (srchTransferInfoResponse != null) {
            return srchTransferInfoResponse.getResultList();
        }
		return null;
	}
}
