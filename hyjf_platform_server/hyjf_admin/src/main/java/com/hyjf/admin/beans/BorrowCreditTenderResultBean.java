package com.hyjf.admin.beans;

import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditTenderSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import com.hyjf.am.vo.trade.borrow.BorrowCreditRepayVO;

import java.io.Serializable;
import java.util.List;

/**
 * admin: 产品中心-汇转让-还款信息返回结果实体bean
 * @author zhangyk
 * @date 2018/7/11 19:25
 */
public class BorrowCreditTenderResultBean implements Serializable {

    private List<BorrowCreditRepayVO> recordList;

    private BorrowCreditTenderSumVO sumData;

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

    public BorrowCreditTenderSumVO getSumData() {
        return sumData;
    }

    public void setSumData(BorrowCreditTenderSumVO sumData) {
        this.sumData = sumData;
    }
}
