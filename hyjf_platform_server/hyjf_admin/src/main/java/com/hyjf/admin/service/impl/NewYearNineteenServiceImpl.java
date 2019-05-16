package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.client.AmMarketClient;
import com.hyjf.admin.service.NewYearNineteenService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.NewYearActivityRewardResponse;
import com.hyjf.am.resquest.admin.NewYearNineteenRequestBean;
import org.springframework.beans.factory.annotation.Autowired;
import com.hyjf.am.response.admin.NewYearActivityResponse;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2019/3/25.
 */
@Service
public class NewYearNineteenServiceImpl implements NewYearNineteenService {

    @Autowired
    private AmMarketClient amMarketClient;

    @Override
    public NewYearActivityRewardResponse selectAwardList(NewYearNineteenRequestBean requestBean) {
        return amMarketClient.selectAwardList(requestBean);
    }
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

    @Override
    public NewYearActivityRewardResponse selectAwardInfo(NewYearNineteenRequestBean request) {
        return amMarketClient.selectAwardInfo(request);
    }

    @Override
    public BooleanResponse updateStatus(NewYearNineteenRequestBean request) {
        return amMarketClient.updateStatus(request);
    }
}
