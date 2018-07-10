/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;


import com.hyjf.am.vo.admin.BorrowFullCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * 复审详细信息返回类
 * @author wangjun
 * @version BorrowFullInfoResponseBean, v0.1 2018/7/3 18:50
 */
public class BorrowFullInfoResponseBean {
    private BorrowFullCustomizeVO borrowFullInfo;

    private List<BorrowFullCustomizeVO> recordList;

    private Map<String,String> sumAmount;

    private Integer total;

    public BorrowFullCustomizeVO getBorrowFullInfo() {
        return borrowFullInfo;
    }

    public void setBorrowFullInfo(BorrowFullCustomizeVO borrowFullInfo) {
        this.borrowFullInfo = borrowFullInfo;
    }

    public List<BorrowFullCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowFullCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(Map<String, String> sumAmount) {
        this.sumAmount = sumAmount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
