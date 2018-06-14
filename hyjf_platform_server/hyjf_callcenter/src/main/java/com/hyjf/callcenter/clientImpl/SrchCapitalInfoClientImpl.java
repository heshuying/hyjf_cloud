/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import com.hyjf.am.response.callcenter.CallCenterAccountDetailResponse;
import com.hyjf.am.resquest.callcenter.CallCenterAccountDetailRequest;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.callcenter.client.SrchCapitalInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version AmCallcenterBaseClientImpl, v0.1 2018/6/6 10:03
 */
@Service
public class SrchCapitalInfoClientImpl implements SrchCapitalInfoClient {
    private static final Logger logger = LoggerFactory.getLogger(SrchCapitalInfoClientImpl.class);
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();
    @Override
    public List<CallCenterAccountDetailVO> queryAccountDetails(CallCenterAccountDetailRequest callCenterAccountDetailRequest) {
        CallCenterAccountDetailResponse callCenterAccountDetailResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryAccountDetails/",
                        callCenterAccountDetailRequest, CallCenterAccountDetailResponse.class).getBody();
        if (callCenterAccountDetailResponse != null) {
            return callCenterAccountDetailResponse.getResultList();
        }
        return null;
    }
}
