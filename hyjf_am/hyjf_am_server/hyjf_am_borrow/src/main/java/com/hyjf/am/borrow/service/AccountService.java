package com.hyjf.am.borrow.service;

import com.hyjf.am.borrow.dao.model.auto.Account;

/**
 * @author xiasq
 * @version AccountService, v0.1 2018/4/25 10:40
 */
public interface AccountService {

    void insert(Account account);

     Account getAccount(Integer userId);
}
