package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.TenderAgreementResponse;
import com.hyjf.am.resquest.trade.TenderAgreementRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.TenderAgreementClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author pangchengchao
 * @version WithdrawClientImpl, v0.1 2018/6/13 11:18
 */
@Service
public class TenderAgreementClientImpl implements TenderAgreementClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TenderAgreementVO getTenderAgreementInfo(@PathVariable String tenderAgreementID) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/getTenderAgreementInfo/"+tenderAgreementID;
        TenderAgreementResponse response = restTemplate.getForEntity(url,TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void updateTenderAgreement(TenderAgreementRequest request) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/updateTenderAgreement";
        restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/selectTenderAgreementByTenderNid/"+tenderNid;
        TenderAgreementResponse response = restTemplate.getForEntity(url,TenderAgreementResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

}
