package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowLogCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoBean, v0.1 2018/7/7 14:19
 */
public class BorrowLogBean {
    private List<BorrowLogCustomizeVO> recordList;

    private Map<String, String> borrowStatusList;
    private Integer total;

    public List<BorrowLogCustomizeVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowLogCustomizeVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getBorrowStatusList() {
        return borrowStatusList;
    }

    public void setBorrowStatusList(Map<String, String> borrowStatusList) {
        this.borrowStatusList = borrowStatusList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
