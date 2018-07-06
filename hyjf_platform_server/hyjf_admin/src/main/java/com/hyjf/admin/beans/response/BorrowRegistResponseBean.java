/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BorrowRegistCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowRegistResponseBean, v0.1 2018/7/5 18:24
 */
public class BorrowRegistResponseBean {
    List<BorrowProjectTypeVO> borrowProjectTypeList;

    List<BorrowStyleVO> borrowStyleList;

    private Map<String, String> borrowRegistStatusList;

    private List<BorrowRegistCustomizeVO> recordList;

    private String sumAccount;

    private Integer total;

    public List<BorrowProjectTypeVO> getBorrowProjectTypeList() {
        return borrowProjectTypeList;
    }

    public void setBorrowProjectTypeList(List<BorrowProjectTypeVO> borrowProjectTypeList) {
        this.borrowProjectTypeList = borrowProjectTypeList;
    }

    public List<BorrowStyleVO> getBorrowStyleList() {
        return borrowStyleList;
    }

    public void setBorrowStyleList(List<BorrowStyleVO> borrowStyleList) {
        this.borrowStyleList = borrowStyleList;
    }

    public Map<String, String> getBorrowRegistStatusList() {
        return borrowRegistStatusList;
    }

    public void setBorrowRegistStatusList(Map<String, String> borrowRegistStatusList) {
        this.borrowRegistStatusList = borrowRegistStatusList;
    }

    public List<BorrowRegistCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRegistCustomizeVO> recordList) {
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
}
