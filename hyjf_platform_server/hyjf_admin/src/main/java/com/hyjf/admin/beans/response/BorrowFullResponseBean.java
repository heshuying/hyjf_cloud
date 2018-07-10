/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * 借款复审列表返回类
 *
 * @author wangjun
 * @version BorrowFullResponseBean, v0.1 2018/7/6 9:52
 */
public class BorrowFullResponseBean {
    private List<BorrowFullCustomizeVO> recordList;

    private Map<String, String> sumAccount;

    private Integer total;

    public List<BorrowFullCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowFullCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(Map<String, String> sumAccount) {
        this.sumAccount = sumAccount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
