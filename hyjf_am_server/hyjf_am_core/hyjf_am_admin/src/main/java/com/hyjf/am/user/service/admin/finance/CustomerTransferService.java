/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance;

import java.util.List;

import com.hyjf.am.user.dao.model.auto.AccountChinapnr;
import com.hyjf.am.user.dao.model.auto.User;

/**
 * @author: sunpeikai
 * @version: CustomerTransferService, v0.1 2018/7/6 9:30
 */
public interface CustomerTransferService {

    /**
     * 根据userName查询list，如果查询到多个user信息，controller会封装报错信息：未查询到正确的用户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<User> searchUserByUsername(String userName);

    /**
     * 根据userId查询AccountChinapnr开户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountChinapnr> searchAccountChinapnrByUserId(Integer userId);

    /**
     * 根据userId查询User用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    User searchUserByUserId(Integer userId);
}
