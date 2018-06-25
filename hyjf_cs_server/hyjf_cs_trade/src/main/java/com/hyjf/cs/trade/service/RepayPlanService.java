package com.hyjf.cs.trade.service;

import com.hyjf.cs.trade.bean.BorrowRepayPlanCsVO;

import java.util.List;

public interface RepayPlanService {

    List<BorrowRepayPlanCsVO> getRepayPlan(String borrowNid);
}
