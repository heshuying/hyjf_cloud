/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanDetailsCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanListCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version RepayPlanResponse, v0.1 2018/8/16 14:46
 */
public class RepayPlanResponse extends Response<BaseVO> {
    //当前持有计划列表
    private List<CurrentHoldRepayMentPlanListCustomizeVO> currentHoldRepayMentPlanList;
    //当前持有计划数量
    private CurrentHoldRepayMentPlanDetailsCustomizeVO currentHoldRepayMentPlanDetails;


    public List<CurrentHoldRepayMentPlanListCustomizeVO> getCurrentHoldRepayMentPlanList() {
        return currentHoldRepayMentPlanList;
    }

    public void setCurrentHoldRepayMentPlanList(List<CurrentHoldRepayMentPlanListCustomizeVO> currentHoldRepayMentPlanList) {
        this.currentHoldRepayMentPlanList = currentHoldRepayMentPlanList;
    }

    public CurrentHoldRepayMentPlanDetailsCustomizeVO getCurrentHoldRepayMentPlanDetails() {
        return currentHoldRepayMentPlanDetails;
    }

    public void setCurrentHoldRepayMentPlanDetails(CurrentHoldRepayMentPlanDetailsCustomizeVO currentHoldRepayMentPlanDetails) {
        this.currentHoldRepayMentPlanDetails = currentHoldRepayMentPlanDetails;
    }
}
