/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.client.AmUserClient;
import com.hyjf.admin.service.UserPortraitScoreSerivce;
import com.hyjf.am.response.admin.UserPortraitScoreResponse;
import com.hyjf.am.resquest.admin.UserPortraitScoreRequest;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version UserPortraitScoreSerivceImpl, v0.1 2018/8/9 11:40
 */
@Service
public class UserPortraitScoreSerivceImpl implements UserPortraitScoreSerivce {

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AmTradeClient amTradeClient;

    @Override
    public UserPortraitScoreResponse selectRecordList(UserPortraitScoreRequest request) {
        return amUserClient.selectScoreRecordList(request);
    }

    @Override
    public List<ParamNameVO> getParamNameList(String other1) {
        return amConfigClient.getParamName(other1);
    }

    @Override
    public List<AccountRechargeVO> getAccountRecharge(int userId) {
        return amTradeClient.getAccountRecharge(userId);
    }


}
