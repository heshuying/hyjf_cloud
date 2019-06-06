/**
 * Description:更改用户基本信息(电话,邮箱,角色等)PO
 */

package com.hyjf.am.resquest.user;
/**
 * @author nxl
 */

public class UserInfosUpdCustomizeRequest {
    private String userId;

    private String username;

    private String mobile;

    private String email;
    //用戶角色
    private String userRole;
    //银行账户
    private String account;
    //更新区分
    private String updFlg;

    //用户状态
    private String status;
    //说明
    private String remark;
    //银行卡号
    private String cardNo;
    //联行号
    private String payAllianceCode;
    //银行名
    private String bank;
    //当前登陆用户名
    private String loginUserName;
    //登陆用户id
    private int loginUserId;

    //银行Id
    private int bankId;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUpdFlg() {
        return updFlg;
    }

    public void setUpdFlg(String updFlg) {
        this.updFlg = updFlg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPayAllianceCode() {
        return payAllianceCode;
    }

    public void setPayAllianceCode(String payAllianceCode) {
        this.payAllianceCode = payAllianceCode;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getLoginUserName() {
        return loginUserName;
    }

    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public int getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        this.loginUserId = loginUserId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }
}

