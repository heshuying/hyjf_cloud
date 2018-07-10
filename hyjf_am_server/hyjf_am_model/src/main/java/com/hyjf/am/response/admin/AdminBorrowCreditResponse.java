package com.hyjf.am.response.admin;

import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.vo.admin.BorrowCreditSumVO;
import com.hyjf.am.vo.admin.BorrowCreditVO;


public class AdminBorrowCreditResponse extends AdminResponse<BorrowCreditVO> {

    private BorrowCreditSumVO sumVO;

    public BorrowCreditSumVO getSumVO() {
        return sumVO;
    }

    public void setSumVO(BorrowCreditSumVO sumVO) {
        this.sumVO = sumVO;
    }
}
