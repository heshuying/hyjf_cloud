package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.trade.borrow.ApplyAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;

import java.io.Serializable;
import java.util.List;

/**
 * @version ApplyAgreementRequestBean, v0.1 2018/8/10 14:08
 * @Author: Zha Daojian
 */
public class ApplyAgreementRequestBean implements Serializable {

    private List<ApplyAgreementVO> recordList;

    private Integer total;

    public List<ApplyAgreementVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<ApplyAgreementVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
