/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance.impl;

import com.hyjf.am.user.dao.model.auto.AccountChinapnr;
import com.hyjf.am.user.dao.model.auto.AccountChinapnrExample;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.service.admin.finance.CustomerTransferService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: CustomerTransferServiceImpl, v0.1 2018/7/6 9:30
 */
@Service("userCustomerTransferServiceImpl")
public class CustomerTransferServiceImpl extends BaseServiceImpl implements CustomerTransferService {

    /**
     * 根据userName查询list，如果查询到多个user信息，controller会封装报错信息：未查询到正确的用户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<User> searchUserByUsername(String userName) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(userName);
        List<User> userList = this.userMapper.selectByExample(example);
        return userList;
    }

    /**
     * 根据userId查询AccountChinapnr开户信息
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountChinapnr> searchAccountChinapnrByUserId(Integer userId) {
        AccountChinapnrExample example = new AccountChinapnrExample();
        AccountChinapnrExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<AccountChinapnr> accountChinapnrList = this.accountChinapnrMapper.selectByExample(example);
        return accountChinapnrList;
    }

    /**
     * 根据userId查询User用户信息
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public User searchUserByUserId(Integer userId) {
        return this.findUserByUserId(userId);
    }
}
