/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.client;

import com.hyjf.am.user.dao.model.auto.BankCard;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.vo.user.BankCardVO;

import java.util.List;

/**
 * @author wangjun
 * @version AccountBankClient, v0.1 2018/6/6 13:49
 */
public interface AccountBankClient {
    List<BankCard> getTiedCardOfAccountBank(User user);
}
