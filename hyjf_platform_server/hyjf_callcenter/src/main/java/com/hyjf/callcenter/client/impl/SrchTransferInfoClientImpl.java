package com.hyjf.callcenter.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.SrchTransferInfoResponse;
import com.hyjf.am.resquest.callcenter.SrchTransferInfoRequest;
import com.hyjf.am.vo.callcenter.CallCenterBorrowCreditVO;
import com.hyjf.callcenter.client.SrchTransferInfoClient;

/**
 * @author libin
 * @version SrchTransferInfoClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class SrchTransferInfoClientImpl implements SrchTransferInfoClient{
	@Autowired
	private RestTemplate restTemplate;
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
	
	@Override
	public List<CallCenterBorrowCreditVO> selectBorrowCreditTenderList(
			SrchTransferInfoRequest srchTransferInfoRequest) {
		SrchTransferInfoResponse srchTransferInfoResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/selectBorrowCreditTenderList", srchTransferInfoRequest, SrchTransferInfoResponse.class)
                .getBody();
        if (srchTransferInfoResponse != null) {
            return srchTransferInfoResponse.getResultList();
        }
		return null;
	}
}
