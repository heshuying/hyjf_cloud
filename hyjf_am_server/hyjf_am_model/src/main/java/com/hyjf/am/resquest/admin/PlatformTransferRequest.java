/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: sunpeikai
 * @version: PlatformTransferRequest, v0.1 2018/7/9 15:35
 */
@ApiModel(value = "发起平台转账请求参数")
public class PlatformTransferRequest extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "交易密码")
    private String password;
    @ApiModelProperty(value = "转账金额")
    private BigDecimal money;
    @ApiModelProperty(value = "备注说明")
    private String remark;
    @ApiModelProperty(value = "后台内部用参数")
    private BankCallBeanVO bankCallBeanVO;
    @ApiModelProperty(value = "后台内部用参数")
    private String accountId;
    @ApiModelProperty(value = "后台内部用参数")
    private String loginUserName;
    @ApiModelProperty(value = "后台内部用参数")
    private UserInfoCustomizeVO userInfo;
    @ApiModelProperty(value = "后台内部用参数")
    private UserInfoCustomizeVO userInfoCustomizeVO;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BankCallBeanVO getBankCallBeanVO() {
        return bankCallBeanVO;
    }

    public void setBankCallBeanVO(BankCallBeanVO bankCallBeanVO) {
        this.bankCallBeanVO = bankCallBeanVO;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public UserInfoCustomizeVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoCustomizeVO userInfo) {
        this.userInfo = userInfo;
    }

    public UserInfoCustomizeVO getUserInfoCustomizeVO() {
        return userInfoCustomizeVO;
    }

    public void setUserInfoCustomizeVO(UserInfoCustomizeVO userInfoCustomizeVO) {
        this.userInfoCustomizeVO = userInfoCustomizeVO;
    }
}
