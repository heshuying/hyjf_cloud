package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;

import java.io.Serializable;
import java.util.List;

public class BorrowCreditListResultBean implements Serializable {

    private List<BorrowCreditVO> recordList;

    private BorrowCreditSumVO sumCredit;

    private Integer total;

    public List<BorrowCreditVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditVO> recordList) {
        this.recordList = recordList;
    }

    public BorrowCreditSumVO getSumCredit() {
        return sumCredit;
    }

    public void setSumCredit(BorrowCreditSumVO sumCredit) {
        this.sumCredit = sumCredit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
