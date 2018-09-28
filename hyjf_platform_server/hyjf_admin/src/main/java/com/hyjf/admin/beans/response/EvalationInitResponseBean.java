/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author nxl
 * @version EvalationInitResponseBean, v0.1 2018/8/06 18:15
 */
public class EvalationInitResponseBean {
    // 用户角色
    @ApiModelProperty(value = "测评等级")
    private List<DropDownVO> evaluationStatus;
    // 用户属性
    @ApiModelProperty(value = "测评状态")
    private List<DropDownVO> evaluationType;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    private List<DropDownVO> accountStatus;

    public List<DropDownVO> getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(List<DropDownVO> evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public List<DropDownVO> getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(List<DropDownVO> evaluationType) {
        this.evaluationType = evaluationType;
    }

    public List<DropDownVO> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(List<DropDownVO> accountStatus) {
        this.accountStatus = accountStatus;
    }
}
