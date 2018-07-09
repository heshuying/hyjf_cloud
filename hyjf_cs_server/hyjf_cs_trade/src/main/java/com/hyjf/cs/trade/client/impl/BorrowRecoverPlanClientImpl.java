package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowRecoverPlanResponse;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BorrowRecoverPlanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 还款计划
 * @author hesy
 * @version BorrowRecoverPlanClientImpl, v0.1 2018/6/26 17:41
 */
@Component
public class BorrowRecoverPlanClientImpl implements BorrowRecoverPlanClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BorrowRecoverPlanVO selectRecoverPlanById(Integer id) {
        BorrowRecoverPlanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/recoverplan/get/"+id,BorrowRecoverPlanResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverPlanVO> selectRecoverPlan(String borrowNid, Integer period) {
        BorrowRecoverPlanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/recoverplan/getby_borrownid_period/"+borrowNid + "/" + period,BorrowRecoverPlanResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

}
