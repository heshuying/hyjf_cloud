package com.hyjf.am.resquest.trade;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.SynBalanceVO;
import com.hyjf.am.vo.user.BankCardVO;

/**
 * @author pangchengchao
 * @version BankWithdrawBeanRequest, v0.1 2018/6/13 15:00
 */
public class SynBalanceBeanRequest extends Request {

    private AccountVO accountUser;
    private SynBalanceVO synBalanceBean;
    private BankCardVO bankCardVO;
    private String username;
    private String ipAddr;

    public AccountVO getAccountUser() {
        return accountUser;
    }

    public void setAccountUser(AccountVO accountUser) {
        this.accountUser = accountUser;
    }

    public SynBalanceVO getSynBalanceBean() {
        return synBalanceBean;
    }

    public void setSynBalanceBean(SynBalanceVO synBalanceBean) {
        this.synBalanceBean = synBalanceBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public BankCardVO getBankCardVO() {
        return bankCardVO;
    }

    public void setBankCardVO(BankCardVO bankCardVO) {
        this.bankCardVO = bankCardVO;
    }
}
