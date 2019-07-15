package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.ApplyBorrowAgreementVO;

import java.io.Serializable;
import java.util.List;

/**
 * @version ApplyAgreementRequestBean, v0.1 2018/8/10 14:08
 * @Author: Zha Daojian
 */
public class ApplyBorrowAgreementRequestBean implements Serializable {

    private List<ApplyBorrowAgreementVO> recordList;

    private Integer total;

    public List<ApplyBorrowAgreementVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ApplyBorrowAgreementVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
