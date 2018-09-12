/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: sunpeikai
 * @version: UserWithdrawRecordCustomizeVO, v0.1 2018/8/30 17:42
 */
@ApiModel(value = "api第三方用户提现记录")
public class UserWithdrawRecordCustomizeVO extends BaseVO {

    @ApiModelProperty(value = "汇盈金服用户名")
    private String username;

    @ApiModelProperty(value = "用户姓名")
    private String truename;

    @ApiModelProperty(value = "提现金额")
    private String total;

    @ApiModelProperty(value = "实际到账")
    private String credited;

    @ApiModelProperty(value = "手续费")
    private String fee;

    @ApiModelProperty(value = "银行卡号")
    private String cardNo;

    @ApiModelProperty(value = "所属银行")
    private String bank;

    @ApiModelProperty(value = "提现状态")
    private String status;

    @ApiModelProperty(value = "提现时间")
    private String withdrawTime;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getTruename() {
        return truename;
    }
    public void setTruename(String truename) {
        this.truename = truename;
    }
    public String getTotal() {
        return total;
    }
    public void setTotal(String total) {
        this.total = total;
    }
    public String getCredited() {
        return credited;
    }
    public void setCredited(String credited) {
        this.credited = credited;
    }
    public String getFee() {
        return fee;
    }
    public void setFee(String fee) {
        this.fee = fee;
    }
    public String getCardNo() {
        return cardNo;
    }
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getWithdrawTime() {
        return withdrawTime;
    }
    public void setWithdrawTime(String withdrawTime) {
        this.withdrawTime = withdrawTime;
    }
}
