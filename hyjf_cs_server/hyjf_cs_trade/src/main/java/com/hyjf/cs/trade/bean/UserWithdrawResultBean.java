/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: UserWithdrawResultBean, v0.1 2018/8/30 10:31
 */
@ApiModel(value = "用户提现返回参数")
public class UserWithdrawResultBean extends BaseMapBean {

    @ApiModelProperty(value = "交易金额")
    private String amt;

    @ApiModelProperty(value = "到账金额")
    private String arrivalAmount;

    @ApiModelProperty(value = "提现手续费")
    private String fee;

    @ApiModelProperty(value = "返回调用方的url")
    private String callBackAction;

    @Override
    public String getCallBackAction() {
        return callBackAction;
    }

    @Override
    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getArrivalAmount() {
        return arrivalAmount;
    }

    public void setArrivalAmount(String arrivalAmount) {
        this.arrivalAmount = arrivalAmount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }
}
