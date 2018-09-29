package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRepaymentBean {

    private List<DropDownVO> repayTypeList;
    private List<DropDownVO> hjhInstConfigList;
    private BorrowRepaymentCustomizeVO sumObject;
    private List<BorrowRepaymentCustomizeVO> recordList;
    private Integer total;

    public List<DropDownVO> getRepayTypeList() {
        return repayTypeList;
    }

    public void setRepayTypeList(List<DropDownVO> repayTypeList) {
        this.repayTypeList = repayTypeList;
    }

    public List<DropDownVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<DropDownVO> hjhInstConfigList) {
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
