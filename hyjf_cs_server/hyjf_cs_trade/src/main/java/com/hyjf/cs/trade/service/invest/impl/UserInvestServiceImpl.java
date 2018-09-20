package com.hyjf.cs.trade.service.invest.impl;

import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.invest.UserInvestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @version UserInvestServiceImpl, v0.1 2018/9/1 13:18
 * @Author: Zha Daojian
 */
public class UserInvestServiceImpl implements UserInvestService {

    @Autowired
    private AmTradeClient amTradeClient;
    /**
     * 获取回款记录信息
     *
     * @param request
     * @return List<RepayListCustomize>
     * @author Zha Daojian
     * @date 2018/9/1 13:20
     **/
    @Override
    public List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request) {
        return amTradeClient.searchRepayList(request);
    }
}
