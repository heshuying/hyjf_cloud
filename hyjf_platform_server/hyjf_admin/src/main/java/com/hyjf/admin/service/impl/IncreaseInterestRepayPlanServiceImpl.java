/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.IncreaseInterestRepayPlanService;
import com.hyjf.am.response.admin.IncreaseInterestRepayPlanResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayPlanServiceImpl.java, v0.1 2018年8月30日
 */
@Service
public class IncreaseInterestRepayPlanServiceImpl implements IncreaseInterestRepayPlanService {
    @Autowired
    private AmTradeClient increaseInterestRepayPlanClient;

    /**
     * 查询列表
     * @param request
     * @return
     */
    @Override
    public IncreaseInterestRepayPlanResponse searchPage(IncreaseInterestRepayPlanRequest request){
        IncreaseInterestRepayPlanResponse response = new IncreaseInterestRepayPlanResponse();
            List<IncreaseInterestRepayDetailVO> voList = increaseInterestRepayPlanClient.getIncreaseInterestRepayPlanList(request);
            response.setResultList(voList);
        return response;
    }

    /**
     * 融通宝加息还款信息导出
     *
     * @Title selectRecordList
     * @param form
     * @return
     */
    @Override
    public List<IncreaseInterestRepayDetailVO> selectRecordList(IncreaseInterestRepayPlanRequest form) {
        return increaseInterestRepayPlanClient.getIncreaseInterestRepayPlanList(form);
    }
}
