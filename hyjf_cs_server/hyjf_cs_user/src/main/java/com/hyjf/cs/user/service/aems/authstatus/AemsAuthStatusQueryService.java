package com.hyjf.cs.user.service.aems.authstatus;

import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

/**
 * @version AemsAuthStatusQueryService, v0.1 2018/12/6 10:22
 * @Author: Zha Daojian
 */
public interface AemsAuthStatusQueryService extends BaseUserService {

    /**
     *
     * 根据电子账户号查询帐号
     * @param accountId
     * @return
     */
    BankOpenAccountVO selectBankOpenAccountByAccountId(String accountId);

    /**
     *
     * 缴费授权  还款授权
     * @return
     */
    BankCallBean getTermsAuthQuery(int userId, String channel);
}
