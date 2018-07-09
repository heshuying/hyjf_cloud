package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.beans.DelayRepayInfoBean;
import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.vo.admin.BorrowRepaymentCustomizeVO;
import com.hyjf.am.vo.admin.BorrowRepaymentPlanCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version BorrowRepaymentService, v0.1 2018/7/4 11:37
 */
public interface BorrowRepaymentService {
    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);

    BorrowRepaymentBean searchBorrowRepayment(BorrowRepaymentRequest copyForm);

    List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest copyForm);

    List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest copyForm);

    DelayRepayInfoBean getDelayRepayInfo(String borrowNid);


    DelayRepayInfoBean updateBorrowRepayDelayDays(String borrowNid, String delayDays, String repayTime);
}
