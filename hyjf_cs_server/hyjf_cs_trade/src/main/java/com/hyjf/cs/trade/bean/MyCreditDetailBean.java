/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedStatisticCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version MyCreditDetailBean, v0.1 2018/8/17 14:49
 */
public class MyCreditDetailBean {
    private List<TenderCreditAssignedCustomizeVO> recordList;

    private TenderCreditAssignedStatisticCustomizeVO assignedStatistic;

    private BorrowCreditVO borrowCredit;

    public List<TenderCreditAssignedCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<TenderCreditAssignedCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public TenderCreditAssignedStatisticCustomizeVO getAssignedStatistic() {
        return assignedStatistic;
    }

    public void setAssignedStatistic(TenderCreditAssignedStatisticCustomizeVO assignedStatistic) {
        this.assignedStatistic = assignedStatistic;
    }

    public BorrowCreditVO getBorrowCredit() {
        return borrowCredit;
    }

    public void setBorrowCredit(BorrowCreditVO borrowCredit) {
        this.borrowCredit = borrowCredit;
    }
}
