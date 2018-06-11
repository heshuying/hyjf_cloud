/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.callcenter.service;


import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.UserVO;

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
    List<BankCardVO> getTiedCardOfAccountBank(UserVO user);
}
