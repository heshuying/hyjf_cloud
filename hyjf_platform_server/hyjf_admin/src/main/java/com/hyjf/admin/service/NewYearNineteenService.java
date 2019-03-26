package com.hyjf.admin.service;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.response.admin.NewYearActivityRewardResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;

/**
 * @author xiehuili on 2019/3/25.
 */
public interface NewYearNineteenService {

    /**
     * 查询累计年华投资
     * @param newYearNineteenRequestBean
     * @return
     */
    NewYearActivityResponse selectInvestList(NewYearNineteenRequestBean newYearNineteenRequestBean);
    /**
     * 查询奖励列表
     * @param requestBean
     * @return
     */
    NewYearActivityRewardResponse selectAwardList(NewYearNineteenRequestBean requestBean);

    /**
     * 查询奖励明细详情
     * @param request
     * @return
     */
    NewYearActivityRewardResponse selectAwardInfo(NewYearNineteenRequestBean request);

    /**
     * 修改发放状态
     * @param request
     * @return
     */
    BooleanResponse updateStatus(NewYearNineteenRequestBean request);
}
