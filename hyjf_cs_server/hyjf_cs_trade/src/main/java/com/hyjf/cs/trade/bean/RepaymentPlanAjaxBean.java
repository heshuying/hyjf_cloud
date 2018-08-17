/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanDetailsCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldRepayMentPlanListCustomizeVO;
import com.hyjf.common.paginator.Paginator;

import java.util.List;

/**
 * @author wangjun
 * @version RepaymentPlanAjaxBean, v0.1 2018/8/15 17:04
 */
public class RepaymentPlanAjaxBean {
    //当前持有计划列表
    private List<CurrentHoldRepayMentPlanListCustomizeVO> currentHoldRepayMentPlanList;
    //当前持有计划数量
    private CurrentHoldRepayMentPlanDetailsCustomizeVO currentHoldRepayMentPlanDetails;

    // 请求处理是否成功
    private boolean status = false;

    // 分页信息
    private Paginator paginator;

    // web服务地址
    private String host;

    // 返回信息
    private String message;

    // 错误码
    private String errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void success() {
        this.status = true;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


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
