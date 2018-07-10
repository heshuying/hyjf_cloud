package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentInfoListBean;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoListRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoListService, v0.1 2018/7/10 10:22
 */
public interface BorrowRepaymentInfoListService {
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);

    BorrowRepaymentInfoListBean selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);

    List<BorrowRepaymentInfoListCustomizeVO> selectExportBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);
}
