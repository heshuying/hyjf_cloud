/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmDataCollectClient;
import com.hyjf.admin.client.AmTradeClient;
import com.hyjf.admin.service.WebsiteService;
import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteServiceImpl, v0.1 2018/7/6 10:53
 */
@Service
public class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmDataCollectClient amDataCollectClient;

    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        List<AccountTradeVO> list = amTradeClient.selectTradeTypes();
        return list;
    }

    @Override
    public AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList) {
        AccountWebListResponse response = amDataCollectClient.queryAccountWebList(accountWebList);
        return response;
    }

    @Override
    public String selectBorrowInvestAccount(AccountWebListVO accountWebList) {
        String total = amDataCollectClient.selectBorrowInvestAccount(accountWebList);
        return null;
    }
}
