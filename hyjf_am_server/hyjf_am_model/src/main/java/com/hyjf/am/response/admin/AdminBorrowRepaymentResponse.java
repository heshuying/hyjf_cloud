package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.BorrowRecoverCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentResponse, v0.1 2018/7/4 14:03
 */
public class AdminBorrowRepaymentResponse extends Response<BorrowRepaymentCustomizeVO> {
    private List<BorrowRepaymentPlanCustomizeVO> borrowRepaymentPlanList;
    private BorrowRepaymentPlanCustomizeVO borrowRepaymentPlanCustomizeVO;
    private Integer total;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<BorrowRepaymentPlanCustomizeVO> getBorrowRepaymentPlanList() {
        return borrowRepaymentPlanList;
    }

    public void setBorrowRepaymentPlanList(List<BorrowRepaymentPlanCustomizeVO> borrowRepaymentPlanList) {
        this.borrowRepaymentPlanList = borrowRepaymentPlanList;
    }

    public BorrowRepaymentPlanCustomizeVO getBorrowRepaymentPlanCustomizeVO() {
        return borrowRepaymentPlanCustomizeVO;
    }

    public void setBorrowRepaymentPlanCustomizeVO(BorrowRepaymentPlanCustomizeVO borrowRepaymentPlanCustomizeVO) {
        this.borrowRepaymentPlanCustomizeVO = borrowRepaymentPlanCustomizeVO;
    }
}
