package com.hyjf.am.trade.service.admin;

import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;

import java.util.List;

/**
 * @author：wenxin
 * @Date: 2018/8/30
 */
public interface IncreaseInterestRepayPlanService {

    int getIncreaseInterestRepayPlanCount(IncreaseInterestRepayPlanRequest request);

    List<IncreaseInterestRepayDetailVO> getIncreaseInterestRepayPlanList(IncreaseInterestRepayPlanRequest request);
}
