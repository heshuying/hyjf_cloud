/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedStatisticCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version MyCreditDetailResponse, v0.1 2018/8/17 10:22
 */
public class MyCreditDetailResponse extends Response<BaseVO> {

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
