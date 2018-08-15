package com.hyjf.admin.beans;

import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListBean, v0.1 2018/7/10 10:12
 */
public class BorrowRepaymentInfoListBean {

    private List<DropDownVO> hjhInstConfigList;
    private List<BorrowRepaymentInfoListCustomizeVO> recordList;

    private BorrowRepaymentInfoListCustomizeVO sumObject;

    private Integer total;

    public List<DropDownVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<DropDownVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<BorrowRepaymentInfoListCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepaymentInfoListCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public BorrowRepaymentInfoListCustomizeVO getSumObject() {
        return sumObject;
    }

    public void setSumObject(BorrowRepaymentInfoListCustomizeVO sumObject) {
        this.sumObject = sumObject;
    }
}
