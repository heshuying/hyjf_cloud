/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhDebtDetailResponse;
import com.hyjf.am.vo.trade.hjh.HjhDebtDetailVO;
import com.hyjf.cs.trade.client.HjhDebtDetailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtDetailClientImpl, v0.1 2018/6/26 13:44
 */
@Service
public class HjhDebtDetailClientImpl implements HjhDebtDetailClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhDebtDetailVO> selectHjhDebtDetailListByAccedeOrderId(String accedeOrderId) {
        HjhDebtDetailResponse response = restTemplate
                .getForEntity("http://AM-USER/am-trade/hjhDebtDetail/selectHjhDebtDetailListByAccedeOrderId/" + accedeOrderId , HjhDebtDetailResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
