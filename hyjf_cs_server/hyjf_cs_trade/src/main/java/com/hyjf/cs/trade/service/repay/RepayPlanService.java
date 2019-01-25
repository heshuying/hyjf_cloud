package com.hyjf.cs.trade.service.repay;

import com.hyjf.cs.trade.bean.BorrowRepayPlanCsVO;
import com.hyjf.cs.trade.bean.repay.RepayPlanBean;

import java.util.List;

public interface RepayPlanService {

    List<BorrowRepayPlanCsVO> getRepayPlan(String borrowNid);

    List<RepayPlanBean> getAppRepayPlan(String borrowNid);
}
