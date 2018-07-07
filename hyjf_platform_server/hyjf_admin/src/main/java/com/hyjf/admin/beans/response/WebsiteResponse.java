/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.statistics.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteResponse, v0.1 2018/7/6 16:24
 */
public class WebsiteResponse {

    private List<AccountTradeVO> tradeList;

    private List<AccountWebListVO> accountWebList;

    private int total;

    private String sumAccount;

    public List<AccountTradeVO> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<AccountTradeVO> tradeList) {
        this.tradeList = tradeList;
    }

    public List<AccountWebListVO> getAccountWebList() {
        return accountWebList;
    }

    public void setAccountWebList(List<AccountWebListVO> accountWebList) {
        this.accountWebList = accountWebList;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }
}
