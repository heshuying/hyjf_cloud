package com.hyjf.wbs.user.service;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.wbs.user.dao.model.auto.BankCard;
import com.hyjf.wbs.user.dao.model.auto.User;

/**
 * @author cui
 * @version UserService, v0.1 2019/4/25 17:04
 */
public interface UserService {

    User findUserById(Integer userIdd);

    BankCard selectBankCardByUserId(Integer userIdd);
}
