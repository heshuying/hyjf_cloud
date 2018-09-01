/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;


import com.hyjf.am.response.admin.IncreaseInterestRepayPlanResponse;
import com.hyjf.am.resquest.admin.IncreaseInterestRepayPlanRequest;
import com.hyjf.am.vo.admin.IncreaseInterestRepayDetailVO;

import java.util.List;

/**
 * @author wenxin
 * @version IncreaseInterestRepayService.java, v0.1 2018年8月30日
 */
public interface IncreaseInterestRepayPlanService {
    /**
     * 融通宝加息还款计划
     *
     * @Title searchPage
     * @param request
     * @return
     */
    IncreaseInterestRepayPlanResponse searchPage(IncreaseInterestRepayPlanRequest request);

    /**
     * 融通宝加息还款计划导出
     *
     * @Title selectRecordList
     * @param request
     * @return
     */
    List<IncreaseInterestRepayDetailVO> selectRecordList(IncreaseInterestRepayPlanRequest request);
}
