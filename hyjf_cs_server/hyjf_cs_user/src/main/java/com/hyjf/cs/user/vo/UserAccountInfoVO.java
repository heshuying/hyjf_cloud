/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.vo;

import java.io.Serializable;

/**
 * 用户账户信息
 * @author jun
 * @version UserAccountInfoVO, v0.1 2018/8/8 14:40
 */
public class UserAccountInfoVO implements Serializable {

    //"张先生" "王女士"
    private String trueUserName;

    //用户名
    private String userName;

    //汇付天下用户
    private boolean chinapnrUser;
    //银行托管用户
    private boolean bankUser;
    //是否设置交易密码
    private boolean setPassword;
    //是否绑定邮箱
    private boolean setEmail;
    //是否测评过
    private String evaluated;
    // 是否服务费授权
    private Integer paymentAuthStatus;


    //头像图片URL
    private String iconUrl;

    //分享链接
    private String qrcodeUrl;

    //邀請碼
    private Integer invitationCode;

    //是否绑卡
    private boolean isBindCard;


    public String getTrueUserName() {
        return trueUserName;
    }

    public void setTrueUserName(String trueUserName) {
        this.trueUserName = trueUserName;
    }

    public boolean isChinapnrUser() {
        return chinapnrUser;
    }

    public void setChinapnrUser(boolean chinapnrUser) {
        this.chinapnrUser = chinapnrUser;
    }

    public boolean isBankUser() {
        return bankUser;
    }

    public void setBankUser(boolean bankUser) {
        this.bankUser = bankUser;
    }

    public boolean isSetPassword() {
        return setPassword;
    }

    public void setSetPassword(boolean setPassword) {
        this.setPassword = setPassword;
    }

    public boolean isSetEmail() {
        return setEmail;
    }

    public void setSetEmail(boolean setEmail) {
        this.setEmail = setEmail;
    }

    public String isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getQrcodeUrl() {
        return qrcodeUrl;
    }

    public void setQrcodeUrl(String qrcodeUrl) {
        this.qrcodeUrl = qrcodeUrl;
    }

    public Integer getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(Integer invitationCode) {
        this.invitationCode = invitationCode;
    }

    public boolean getIsBindCard() {
        return isBindCard;
    }

    public void setIsBindCard(boolean isBindCard) {
        this.isBindCard = isBindCard;
    }
}
