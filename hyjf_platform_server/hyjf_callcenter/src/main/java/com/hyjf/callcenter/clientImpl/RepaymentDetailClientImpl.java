/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.am.response.callcenter.CallCenterHtjRepaymentResponse;
import com.hyjf.am.response.callcenter.CallCenterHztRepaymentResponse;
import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterHtjRepaymentDetailVO;
import com.hyjf.am.vo.callcenter.CallCenterHztRepaymentDetailVO;
import com.hyjf.callcenter.client.RepaymentDetailClient;

/**
 * @author wangjun
 * @version AccountBankClientImpl, v0.1 2018/6/6 13:53
 */
@Service
public class RepaymentDetailClientImpl implements RepaymentDetailClient {
    private static final Logger logger = LoggerFactory.getLogger(RepaymentDetailClientImpl.class);
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public List<CallCenterHztRepaymentDetailVO> getHztRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest) {
        CallCenterHztRepaymentResponse callCenterHztRepaymentResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getHztRepaymentDetailList/", callCenterBaseRequest, CallCenterHztRepaymentResponse.class)
                .getBody();
        if (callCenterHztRepaymentResponse != null) {
            return callCenterHztRepaymentResponse.getResultList();
        }
        return null;
    }

    @Override
    public List<CallCenterHtjRepaymentDetailVO> getHtjRepaymentDetailList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterHtjRepaymentResponse callCenterHtjRepaymentResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getHtjRepaymentDetailList/", callCenterBaseRequest, CallCenterHtjRepaymentResponse.class)
                .getBody();
        if (callCenterHtjRepaymentResponse != null) {
            return callCenterHtjRepaymentResponse.getResultList();
        }
        return null;
    }
}
