package com.hyjf.admin.service;

import com.hyjf.am.response.admin.NewYearActivityResponse;
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
}
