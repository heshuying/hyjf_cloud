package com.hyjf.cs.user.service.aems.syncuserinfo;

import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.user.service.BaseUserService;

public interface AemsSyncUserService extends BaseUserService {

    BankOpenAccountVO getUserByAccountId(String accountId);

    /**
     * 获取用户的账户信息
     *
     * @param userId
     * @return 用户的身份证号
     */
    AccountVO getAccount(Integer userId);
}
