package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentClient, v0.1 2018/7/4 11:59
 */
public interface BorrowRepaymentClient {
    Integer countBorrowRepayment(BorrowRepaymentRequest request);

    List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request);

    BorrowRepaymentCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentRequest request);

    List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);
}
