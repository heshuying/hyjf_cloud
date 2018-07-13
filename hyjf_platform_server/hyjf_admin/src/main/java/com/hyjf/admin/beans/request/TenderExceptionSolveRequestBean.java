/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author nxl
 * @version TenderExceptionSolveRequestBean, v0.1 2018/7/12 16:09
 * 汇计划自动投资异常 ,异常处理请求bean
 */
public class TenderExceptionSolveRequestBean {
    /**
     * 加入订单号
     */
    @ApiModelProperty(value = "加入订单号")
    private String planOrderId;
    /**
     * 计划编号
     */
    @ApiModelProperty(value = "计划编号")
    private String debtPlanNid;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private String userId;

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public String getDebtPlanNid() {
        return debtPlanNid;
    }

    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
