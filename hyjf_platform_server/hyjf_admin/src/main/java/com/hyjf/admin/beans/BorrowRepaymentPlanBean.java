package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRecoverBean, v0.1 2018/7/2 14:54
 */
public class BorrowRepaymentPlanBean {

    private List<DropDownVO> hjhInstConfigList;
    private BorrowRepaymentPlanCustomizeVO sumObject;
    private List<BorrowRepaymentPlanCustomizeVO> recordList;
    private Integer total;


    public List<DropDownVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<DropDownVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public BorrowRepaymentPlanCustomizeVO getSumObject() {
        return sumObject;
    }

    public void setSumObject(BorrowRepaymentPlanCustomizeVO sumObject) {
        this.sumObject = sumObject;
    }

    public List<BorrowRepaymentPlanCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepaymentPlanCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
