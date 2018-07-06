/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BorrowFirstCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author wangjun
 * @version BorrowFirstResponseBean, v0.1 2018/7/5 15:36
 */
public class BorrowFirstResponseBean {
    private List<HjhInstConfigVO> hjhInstConfigList;

    private Map<String, String> borrowStatusList;

    private List<BorrowFirstCustomizeVO> recordList;

    private String sumAccount;

    private Integer total;

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public Map<String, String> getBorrowStatusList() {
        return borrowStatusList;
    }

    public void setBorrowStatusList(Map<String, String> borrowStatusList) {
        this.borrowStatusList = borrowStatusList;
    }

    public List<BorrowFirstCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowFirstCustomizeVO> recordList) {
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
