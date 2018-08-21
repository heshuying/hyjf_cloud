/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.bean;

import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.TenderCreditAssignedStatisticCustomizeVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author wangjun
 * @version MyCreditDetailBean, v0.1 2018/8/17 14:49
 */
public class MyCreditDetailBean {
    @ApiModelProperty("散标转让记录列表")
    private List<TenderCreditAssignedCustomizeVO> recordList;

    @ApiModelProperty("散标转让记录详情")
    private TenderCreditAssignedStatisticCustomizeVO assignedStatistic;

    @ApiModelProperty("转让标的信息")
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
