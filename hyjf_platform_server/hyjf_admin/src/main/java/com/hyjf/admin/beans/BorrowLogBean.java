package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoBean, v0.1 2018/7/7 14:19
 */
public class BorrowLogBean {
    private List<BorrowLogCustomizeVO> recordList;

    private List<DropDownVO> borrowStatusList;
    private Integer total;

    public List<BorrowLogCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowLogCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public List<DropDownVO> getBorrowStatusList() {
        return borrowStatusList;
    }

    public void setBorrowStatusList(List<DropDownVO> borrowStatusList) {
        this.borrowStatusList = borrowStatusList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
