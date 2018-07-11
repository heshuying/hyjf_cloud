package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoClient, v0.1 2018/7/9 9:23
 */
public interface BorrowRepaymentInfoClient {
    Integer countBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);

    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request);

    BorrowRepaymentInfoCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);

    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset copyForm);
}
