package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditInfoSumVO;
import com.hyjf.am.vo.admin.BorrowCreditInfoVO;

import java.io.Serializable;
import java.util.List;

/**
 * 债转详情bean
 * @author zhangyk
 * @date 2018/7/10 19:32
 */
public class BorrowCreditInfoResultBean implements Serializable {

    private List<BorrowCreditInfoVO> recordList;

    private BorrowCreditInfoSumVO sumCreditInfo;

    private Integer total;

    public List<BorrowCreditInfoVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditInfoVO> recordList) {
        this.recordList = recordList;
    }

    public BorrowCreditInfoSumVO getSumCreditInfo() {
        return sumCreditInfo;
    }

    public void setSumCreditInfo(BorrowCreditInfoSumVO sumCreditInfo) {
        this.sumCreditInfo = sumCreditInfo;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
