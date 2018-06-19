package com.hyjf.cs.trade.service;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.common.service.BaseService;


public interface BaseTradeService extends BaseService{
    /**
     * 获取银行开户信息
     *
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);
}
