package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentInfoBean;
import com.hyjf.am.resquest.admin.BorrowRepaymentInfoRequset;
import com.hyjf.am.vo.admin.BorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentInfoService, v0.1 2018/7/7 14:22
 */
public interface BorrowRepaymentInfoService {
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String s);

    BorrowRepaymentInfoBean selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset copyForm);

    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentInfoRequset copyForm);
}
