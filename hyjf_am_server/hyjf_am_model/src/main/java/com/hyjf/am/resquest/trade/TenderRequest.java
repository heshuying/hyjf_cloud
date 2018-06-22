/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.WebViewUserVO;

/**
 * @Description 投资请求参数
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:37
 */
public class TenderRequest extends BaseVO {
    // 债转编号
    private String creditNid ;
    // 优惠券投资
    private Integer couponGrantId ;
    // 项目编号
    private String borrowNid ;
    // 防止重复加入计划
    private String tenderToken;
    // 投资Ip
    private String ip;
    // 投资平台
    private String platform;
    // 登录的token
    private String token;
    // 登录的用户对象
    private WebViewUserVO user;
    // 投资金额
    private String account;

    // 内部操作用
    private BankOpenAccountVO bankOpenAccount;

    private AccountVO tenderAccount ;

    private String planOrderId;

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public Integer getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Integer couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTenderToken() {
        return tenderToken;
    }

    public void setTenderToken(String tenderToken) {
        this.tenderToken = tenderToken;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public WebViewUserVO getUser() {
        return user;
    }

    public void setUser(WebViewUserVO user) {
        this.user = user;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BankOpenAccountVO getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(BankOpenAccountVO bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public AccountVO getTenderAccount() {
        return tenderAccount;
    }

    public void setTenderAccount(AccountVO tenderAccount) {
        this.tenderAccount = tenderAccount;
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }
}
