package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoBean, v0.1 2018/7/7 14:19
 */
public class BorrowRepaymentInfoBean {
    private List<HjhInstConfigVO> hjhInstConfigList;

    private List<BorrowRepaymentInfoCustomizeVO> recordList;
    private BorrowRepaymentInfoCustomizeVO sumObject;
    private Integer total;

    public List<HjhInstConfigVO> getHjhInstConfigList() {
        return hjhInstConfigList;
    }

    public void setHjhInstConfigList(List<HjhInstConfigVO> hjhInstConfigList) {
        this.hjhInstConfigList = hjhInstConfigList;
    }

    public List<BorrowRepaymentInfoCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowRepaymentInfoCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public BorrowRepaymentInfoCustomizeVO getSumObject() {
        return sumObject;
    }

    public void setSumObject(BorrowRepaymentInfoCustomizeVO sumObject) {
        this.sumObject = sumObject;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
