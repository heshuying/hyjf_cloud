package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.NewYearNineteenService;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2019/3/25.
 */
@Service
public class NewYearNineteenServiceImpl implements NewYearNineteenService {

    @Autowired
    AmAdminClient amAdminClient;
    /**
     * 查询累计年华投资
     * @param newYearNineteenRequestBean
     * @return
     */
    @Override
    public NewYearActivityResponse selectInvestList(NewYearNineteenRequestBean newYearNineteenRequestBean){
        return amAdminClient.selectInvestList(newYearNineteenRequestBean);
    }

}
