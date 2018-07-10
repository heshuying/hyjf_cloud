package com.hyjf.admin.client;

import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListClient, v0.1 2018/7/10 10:45
 */
public interface BorrowRepaymentInfoListClient {
    Integer countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);

    List<BorrowRepaymentInfoListCustomizeVO> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);

    BorrowRepaymentInfoListCustomizeVO sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);

}
