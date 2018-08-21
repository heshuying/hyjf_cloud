/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanDetailsCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanListCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangjun
 * @version RepayPlanInfoBean, v0.1 2018/8/15 17:04
 */
public class RepayPlanInfoBean {
    @ApiModelProperty("散标还款计划列表")
    private List<CurrentHoldRepayMentPlanListCustomizeVO> currentHoldRepayMentPlanList;

    @ApiModelProperty("散标还款计划详情")
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
