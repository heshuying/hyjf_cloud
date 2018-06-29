package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BorrowTenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BorrowTenderClientImpl implements BorrowTenderClient {



    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {
        BorrowTenderResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrowTender/countUserInvest/" +userId + "/" + borrowNid,BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
        }
        return null;
    }

	@Override
	public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest) {
		String url = "http://AM-TRADE/am-trade/borrowTender/selectBorrowTender";
		BorrowTenderResponse response = restTemplate.postForEntity(url,btRequest,BorrowTenderResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<FddTempletVO> getFddTempletList(Integer protocolType) {
		String url = "http://AM-TRADE/am-trade/borrowTender/getFddTempletList/"+protocolType;
		FddTempletResponse response = restTemplate.getForEntity(url,FddTempletResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}
	
	@Override
	public int saveTenderAgreement(TenderAgreementVO info) {
		String url = "http://AM-TRADE/am-trade/borrowTender/saveTenderAgreement";
		return restTemplate.postForEntity(url,info,Integer.class).getBody();
	}

	@Override
	public int updateTenderAgreement(TenderAgreementVO tenderAgreement) {
		String url = "http://AM-TRADE/am-trade/borrowTender/updateTenderAgreement";
		return restTemplate.postForEntity(url,tenderAgreement,Integer.class).getBody();
	}
}
