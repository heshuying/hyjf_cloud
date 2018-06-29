/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhDebtCreditResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.HjhDebtCreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClientImpl, v0.1 2018/6/26 11:59
 */
@Service
public class HjhDebtCreditClientImpl implements HjhDebtCreditClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhDebtCreditListByAccedeOrderId/",
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 判断剩余待清算金额是否全部债转
     * 查询条件：PlanOrderIdEqualTo(accedeOrderId)
     *           BorrowNidEqualTo(borrowNid)
     *           CreditStatusNotEqualTo(3)
     *
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId, String borrowNid) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhDebtCreditListByOrderIdNid/" + accedeOrderId + "/" + borrowNid,
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 汇计划债转协议下载
     * @return
     */
    @Override
    public List<HjhDebtCreditTenderVO> selectHjhCreditTenderListByAssignOrderId(String assignOrderId) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhCreditTenderListByAssignOrderId/"+assignOrderId;
        HjhDebtCreditTenderResponse response = restTemplate.getForEntity(url,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }
}
