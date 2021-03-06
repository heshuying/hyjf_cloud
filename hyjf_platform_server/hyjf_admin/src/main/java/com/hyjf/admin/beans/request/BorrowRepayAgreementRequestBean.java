package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.admin.BorrowRepayAgreementCustomizeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @version ApplyAgreementRequestBean, v0.1 2018/8/10 14:08
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementRequestBean implements Serializable {

    private List<BorrowRepayAgreementCustomizeVO> recordList;

    private Integer total;

    public List<BorrowRepayAgreementCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepayAgreementCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
