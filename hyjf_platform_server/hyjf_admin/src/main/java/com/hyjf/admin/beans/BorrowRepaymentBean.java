package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRepaymentBean {

    private List<BorrowStyleVO> repayTypeList;
    private List<HjhInstConfigVO> hjhInstConfigList;
    private BorrowRepaymentCustomizeVO sumObject;
    private List<BorrowRepaymentCustomizeVO> recordList;
    private Integer total;

    public List<BorrowStyleVO> getRepayTypeList() {
        return repayTypeList;
    }

    public void setRepayTypeList(List<BorrowStyleVO> repayTypeList) {
        this.repayTypeList = repayTypeList;
    }

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public BorrowRepaymentCustomizeVO getSumObject() {
        return sumObject;
    }

    public void setSumObject(BorrowRepaymentCustomizeVO sumObject) {
        this.sumObject = sumObject;
    }

    public List<BorrowRepaymentCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepaymentCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
