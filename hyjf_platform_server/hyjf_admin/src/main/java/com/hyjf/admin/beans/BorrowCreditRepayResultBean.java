package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditRepaySumVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;

import java.io.Serializable;
import java.util.List;

/**
 * admin: 产品中心-汇转让-还款信息返回结果实体bean
 * @author zhangyk
 * @date 2018/7/11 19:25
 */
public class BorrowCreditRepayResultBean implements Serializable {

    private List<BorrowCreditRepayVO> recordList;

    private BorrowCreditRepaySumVO sumData;

    private Integer total;

    public List<BorrowCreditRepayVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<BorrowCreditRepayVO> recordList) {
        this.recordList = recordList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public BorrowCreditRepaySumVO getSumData() {
        return sumData;
    }

    public void setSumData(BorrowCreditRepaySumVO sumData) {
        this.sumData = sumData;
    }
}
