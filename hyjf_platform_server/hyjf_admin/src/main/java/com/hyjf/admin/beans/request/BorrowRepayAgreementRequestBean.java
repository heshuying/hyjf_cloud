package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.trade.BorrowRepayAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;

import java.io.Serializable;
import java.util.List;

/**
 * @version ApplyAgreementRequestBean, v0.1 2018/8/10 14:08
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementRequestBean implements Serializable {

    private List<BorrowRepayAgreementVO> recordList;

    private Integer total;

    public List<BorrowRepayAgreementVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepayAgreementVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
