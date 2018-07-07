package com.hyjf.callcenter.client.impl;

import com.hyjf.am.response.callcenter.CallcenterBankConfigResponse;
import com.hyjf.am.resquest.callcenter.CallcenterAccountHuifuRequest;
import com.hyjf.am.vo.callcenter.CallcenterBankConfigVO;
import com.hyjf.callcenter.client.AmConfigClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author wangjun
 * @version AmConfigClientImpl, v0.1 2018/7/6 17:15
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {
    private static Logger logger = LoggerFactory.getLogger(AmConfigClientImpl.class);

    @Autowired
    private RestTemplate restTemplate;

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
