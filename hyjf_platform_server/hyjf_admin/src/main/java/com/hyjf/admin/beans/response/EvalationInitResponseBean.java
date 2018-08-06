/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.coupon.ParamName;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version EvalationInitResponseBean, v0.1 2018/8/06 18:15
 */
public class EvalationInitResponseBean {
    // 用户角色
    @ApiModelProperty(value = "测评等级")
    private List<ParamName> evaluationStatus;
    // 用户属性
    @ApiModelProperty(value = "测评状态")
    private List<ParamName> evaluationType;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    private Map<String, String> accountStatus;

    public List<ParamName> getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(List<ParamName> evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public List<ParamName> getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(List<ParamName> evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Map<String, String> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Map<String, String> accountStatus) {
        this.accountStatus = accountStatus;
    }
}
