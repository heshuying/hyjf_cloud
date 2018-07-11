/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowInvestResponseBean, v0.1 2018/7/10 17:15
 */
public class BorrowInvestResponseBean {
    private List<BorrowInvestCustomizeVO> recordList;

    private String sumAccount;

    private Integer total;

    /**
     * 操作平台
     */
    private Map<String,String> clientList;

    /**
     * 还款方式
     */
    private List<BorrowStyleVO> borrowStyleList;

    /**
     * 投资方式
     */
    private Map<String,String> investTypeList;

    public List<BorrowInvestCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowInvestCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public String getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(String sumAccount) {
        this.sumAccount = sumAccount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Map<String, String> getClientList() {
        return clientList;
    }

    public void setClientList(Map<String, String> clientList) {
        this.clientList = clientList;
    }

    public List<BorrowStyleVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<BorrowStyleVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public Map<String, String> getInvestTypeList() {
        return investTypeList;
    }

    public void setInvestTypeList(Map<String, String> investTypeList) {
        this.investTypeList = investTypeList;
    }
}
