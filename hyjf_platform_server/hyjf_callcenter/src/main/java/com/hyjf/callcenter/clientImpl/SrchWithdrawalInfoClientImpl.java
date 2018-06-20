/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.clientImpl;

import java.util.List;

import com.hyjf.am.response.callcenter.CallCenterWithdrawResponse;
import com.hyjf.am.vo.callcenter.CallCenterWithdrawVO;
import com.hyjf.callcenter.client.SrchWithdrawalInfoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hyjf.am.resquest.callcenter.CallCenterBaseRequest;
import com.hyjf.ribbon.EurekaInvokeClient;

/**
 * @author wangjun
 * @version SrchWithdrawalInfoClientImpl, v0.1 2018/6/15 11:30
 */
@Service
public class SrchWithdrawalInfoClientImpl implements SrchWithdrawalInfoClient {
    private static final Logger logger = LoggerFactory.getLogger(SrchWithdrawalInfoClientImpl.class);
    private RestTemplate restTemplate = EurekaInvokeClient.getInstance().buildRestTemplate();

    /**
     * 查询提现明细
     * @param callCenterBaseRequest
     * @return List<CallCenterWithdrawVO>
     * @author wangjun
     */
    public List<CallCenterWithdrawVO> getWithdrawRecordList(CallCenterBaseRequest callCenterBaseRequest){
        CallCenterWithdrawResponse callCenterWithdrawResponse = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/callcenter/getWithdrawRecordList", callCenterBaseRequest, CallCenterWithdrawResponse.class)
                .getBody();
        if (callCenterWithdrawResponse != null) {
            return callCenterWithdrawResponse.getResultList();
        }
        return null;
    }
}
