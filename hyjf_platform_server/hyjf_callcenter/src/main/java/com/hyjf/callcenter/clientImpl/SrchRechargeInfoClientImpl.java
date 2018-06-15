/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import com.hyjf.am.response.callcenter.CallCenterRechargeResponse;
import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.am.vo.callcenter.CallCenterRechargeVO;
import com.hyjf.callcenter.client.SrchRechargeInfoClient;
import com.hyjf.ribbon.EurekaInvokeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version SrchRechargeInfoClientImpl, v0.1 2018/6/14 14:51
 */
@Service
public class SrchRechargeInfoClientImpl implements SrchRechargeInfoClient {
    private static final Logger logger = LoggerFactory.getLogger(SrchRechargeInfoClientImpl.class);
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

    /**
     * 查询充值明细
     * @param callCenterBaseRequest
     * @return List<CallCenterRechargeVO>
     * @author wangjun
     */
    public List<CallCenterRechargeVO> queryRechargeList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterRechargeResponse callCenterRechargeResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/queryRechargeList", callCenterBaseRequest, CallCenterRechargeResponse.class)
                .getBody();
        if (callCenterRechargeResponse != null) {
            return callCenterRechargeResponse.getResultList();
        }
        return null;
    }
}
