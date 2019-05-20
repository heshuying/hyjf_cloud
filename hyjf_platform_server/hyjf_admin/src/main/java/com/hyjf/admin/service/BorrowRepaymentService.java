package com.hyjf.admin.service;

import com.hyjf.admin.beans.BorrowRepaymentBean;
import com.hyjf.admin.beans.BorrowRepaymentPlanBean;
import com.hyjf.admin.beans.DelayRepayInfoBean;
import com.hyjf.admin.beans.RepayInfoBean;
import com.hyjf.am.response.admin.AdminBorrowRepaymentResponse;
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

    AdminBorrowRepaymentResponse exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest copyForm);

    List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest copyForm);

    DelayRepayInfoBean getDelayRepayInfo(String borrowNid);


    DelayRepayInfoBean updateBorrowRepayDelayDays(String borrowNid, String delayDays, String repayTime);

    RepayInfoBean getRepayInfo(String borrowNid);

    BorrowRepaymentPlanBean searchBorrowRepaymentPlan(BorrowRepaymentRequest copyForm);

    List<BorrowRepaymentPlanCustomizeVO> selectBorrowRepaymentPlanList(BorrowRepaymentRequest copyForm);

	Integer countBorrowRepayment(BorrowRepaymentRequest request);

	RepayInfoBean getRepayInfo2(String borrowNid);
}
