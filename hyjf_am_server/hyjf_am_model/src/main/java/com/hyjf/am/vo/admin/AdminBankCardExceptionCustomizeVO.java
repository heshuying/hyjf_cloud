/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: AdminBankCardExceptionCustomizeVO, v0.1 2018/8/14 14:43
 */
@ApiModel(value = "银行卡异常列表返回参数")
public class AdminBankCardExceptionCustomizeVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户ID")
    private String userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "真实姓名")
    private String truename;
    @ApiModelProperty(value = "身份证号")
    private String idcard;
    @ApiModelProperty(value = "银行账号")
    private String account;
    @ApiModelProperty(value = "所属银行代码")
    private String bankcode;
    @ApiModelProperty(value = "所属银行")
    private String bank;
    @ApiModelProperty(value = "卡类型")
    private String cardType;
    @ApiModelProperty(value = "默认卡，cardType=1,2显示是,否则显示否")
    private String isdefault;
    @ApiModelProperty(value = "银行卡属性,cardType=0普通提现卡,1默认卡,2快捷支付卡")
    private String bankShuxing;
    @ApiModelProperty(value = "添加时间")
    private String addtime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBankcode() {
        return bankcode;
    }

    public void setBankcode(String bankcode) {
        this.bankcode = bankcode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getIsdefault() {
        return isdefault;
    }

    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    public String getBankShuxing() {
        return bankShuxing;
    }

    public void setBankShuxing(String bankShuxing) {
        this.bankShuxing = bankShuxing;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }
}
