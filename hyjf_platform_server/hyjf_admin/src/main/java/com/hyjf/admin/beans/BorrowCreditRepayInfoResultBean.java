package com.hyjf.admin.beans;

import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayInfoVO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * admin: 汇转让：还款详情返回bean
 * @author zhangyk
 * @date 2018/7/12 13:57
 */
public class BorrowCreditRepayInfoResultBean implements Serializable {

    private Integer total;

    private List<BorrowCreditRepayInfoVO> recordList;

    private Map<String,String> sumData;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<BorrowCreditRepayInfoVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditRepayInfoVO> recordList) {
        this.recordList = recordList;
    }

    public Map<String, String> getSumData() {
        return sumData;
    }

    public void setSumData(Map<String, String> sumData) {
        this.sumData = sumData;
    }
}
