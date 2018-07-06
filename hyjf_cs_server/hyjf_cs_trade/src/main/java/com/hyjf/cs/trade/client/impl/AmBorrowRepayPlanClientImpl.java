/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowRepayPlanResponse;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.cs.trade.client.AmBorrowRepayPlanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version AmBorrowRepayPlanClientImpl, v0.1 2018/6/22 18:50
 */
@Service
public class AmBorrowRepayPlanClientImpl implements AmBorrowRepayPlanClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 根据标的编号查询还款计划
     *
     * @param borrowNid 标的编号
     * @param repaySmsReminder 还款提示短信发送次数
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder) {

        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/selectBorrowRepayPlan/" + borrowNid + "/" + repaySmsReminder,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/updateBorrowRepayPlan/",borrowRepayPlanVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 根据borrowNid和borrowPeriod查询
     *
     * @param bidNid
     * @param borrowPeriod
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByPeriod(String bidNid, Integer borrowPeriod) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_repay_list_by_period/" + bidNid + "/" + borrowPeriod,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据borrowNid检索
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByBorrowNid(String borrowNid) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_by_borrownid/" + borrowNid,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
