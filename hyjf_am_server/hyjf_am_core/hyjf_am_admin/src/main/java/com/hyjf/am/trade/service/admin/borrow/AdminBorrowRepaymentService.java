package com.hyjf.am.trade.service.admin.borrow;

import com.hyjf.am.resquest.admin.BorrowRepaymentPlanRequest;
import com.hyjf.am.resquest.admin.BorrowRepaymentRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowRepay;
import com.hyjf.am.trade.dao.model.auto.BorrowRepayPlan;
import com.hyjf.am.trade.dao.model.customize.*;
import com.hyjf.am.vo.trade.borrow.BorrowRepayBeanVO;

import java.text.ParseException;
import java.util.List;

/**
 * @author pangchengchao
 * @version AdminBorrowRepaymentService, v0.1 2018/7/4 14:33
 */
public interface AdminBorrowRepaymentService {
    int countBorrowRecover(BorrowRepaymentRequest request);


    AdminBorrowRepaymentCustomize sumBorrowRecoverList(BorrowRepaymentRequest request);

    List<AdminBorrowRepaymentPlanCustomize> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);

    AdminRepayDelayCustomize selectBorrowInfo(String borrowNid) throws ParseException;

    BorrowRepay getBorrowRepayDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    BorrowRepayPlan getBorrowRepayPlanDelay(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    int updateBorrowRepayDelayDays(String borrowNid, String delayDays) throws ParseException;

    List<AdminBorrowRepaymentCustomize> selectBorrowRepaymentList(BorrowRepaymentRequest request);

    BorrowRepayBean getBorrowRepayInfo(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    BorrowRepayPlanBean getBorrowRepayPlanInfo(String borrowNid, String borrowApr, String borrowStyle) throws ParseException;

    int countBorrowRepaymentPlan(BorrowRepaymentRequest request);

    List<AdminBorrowRepaymentPlanCustomize> selectBorrowRepaymentPlanList(BorrowRepaymentRequest request);

    AdminBorrowRepaymentPlanCustomize sumBorrowRepaymentPlanInfo(BorrowRepaymentRequest request);


	int exportRepayClkActBorrowRepaymentInfoListCount(BorrowRepaymentPlanRequest request);
}
