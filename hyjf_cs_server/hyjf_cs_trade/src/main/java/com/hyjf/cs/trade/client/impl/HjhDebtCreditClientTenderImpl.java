/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhDebtCreditResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.HjhDebtCreditClient;
import com.hyjf.cs.trade.client.HjhDebtCreditTenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author jijun
 * @date  20180629
 */
@Service
public class HjhDebtCreditClientTenderImpl implements HjhDebtCreditTenderClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<HjhDebtCreditTenderVO> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCreditTender/getHjhDebtCreditTenderList";
        HjhDebtCreditTenderResponse response =
                restTemplate.postForEntity(url,request,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }
}
