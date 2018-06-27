package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.vo.trade.account.AccountVO;

/**
 * @author xiasq
 * @version AccountService, v0.1 2018/4/25 10:40
 */
public interface AccountService {

    void insert(Account account);

     Account getAccount(Integer userId);

    int updateOfRTBLoansTender(Account account);

    Integer updateOfPlanRepayAccount(AccountVO accountVO);
}
