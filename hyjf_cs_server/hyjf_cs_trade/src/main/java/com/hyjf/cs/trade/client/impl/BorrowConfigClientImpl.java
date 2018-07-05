package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowConfigResponse;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * 标的配置
 * @author hesy
 * @version BorrowConfigClientImpl, v0.1 2018/7/4 14:09
 */
@Component
public class BorrowConfigClientImpl implements com.hyjf.cs.trade.client.BorrowConfigClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowConfigVO getConfigByCode(String code) {
        String url = "http://AM-TRADE/am-trade/borrowconfig/get_by_code/" + code;
        BorrowConfigResponse response = restTemplate.getForEntity(url,BorrowConfigResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }
}
