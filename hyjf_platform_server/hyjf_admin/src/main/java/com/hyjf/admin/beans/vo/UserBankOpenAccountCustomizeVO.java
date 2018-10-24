/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author NXL
 * @version UserBankOpenAccountVO, v0.1 2018/6/21 21:52
 */
public class UserBankOpenAccountCustomizeVO extends BaseVO implements Serializable {

    //用户银行账户
    @ApiModelProperty(value = "用户银行账户")
    private String account;
    //用户开户平台
    @ApiModelProperty(value = "用户开户平台")
    private String openAccountPlat;
    //用户开户时间
    @ApiModelProperty(value = "用户开户时间")
    private String openAccountTime;
    //用户开户类型
    @ApiModelProperty(value = "用户开户类型")
    private String userType;
    //用户预留手机号
    @ApiModelProperty(value = "用户预留手机号")
    private String mobile;

    // add by nxl 合规四期,用户详情添加以下三个字段显示
    @ApiModelProperty(value = "银行卡号")
    private String bankNo;
    @ApiModelProperty(value = "银联号")
    private String payAllianceCode;
    @ApiModelProperty(value = "开户银行")
    private String bankName;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getOpenAccountPlat() {
        return openAccountPlat;
    }

    public void setOpenAccountPlat(String openAccountPlat) {
        this.openAccountPlat = openAccountPlat;
    }

    public String getOpenAccountTime() {
        return openAccountTime;
    }

    public void setOpenAccountTime(String openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
