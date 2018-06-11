/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service;

import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.User;

import java.util.List;

/**
 * @author wangjun
 * @version AccountBankService, v0.1 2018/6/6 13:43
 */
public interface AccountBankService {

    /**
     * @param user
     * @return
     */
    List<BankCard> getTiedCardOfAccountBank(User user);
}
