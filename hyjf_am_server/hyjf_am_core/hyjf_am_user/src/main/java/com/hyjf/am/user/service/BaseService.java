/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service;

import com.hyjf.am.user.dao.model.auto.*;

import java.text.ParseException;
import java.util.List;

/**
 * 用户服务:BaseService
 *
 * @author liuyang
 * @version BaseService, v0.1 2018/6/27 9:46
 */
public interface BaseService {


    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    User findUserByUserId(int userId);

    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    UserInfo findUsersInfo(int userId);

    /**
     * 获取所有用户
     * @return
     */
    List<User> findAllUser() throws ParseException;

    /**
     * 根据用户Id查询用户CA认证相关信息
     * @param userId
     * @return
     */
    CorpOpenAccountRecord getCorpOpenAccountRecord(Integer userId);

    /**
     * 根据用户Id查询用户CA认证相关信息
     * @param userId
     * @return
     */
    CertificateAuthority selectCAInfoByUserId(Integer userId);

    /**
     * 获取推荐人姓名查找用户
     * @param userId
     * @return
     */
    SpreadsUser selectSpreadsUsersByUserId(int userId);

    /**
     * 获取开户信息
     * @param example
     * @return
     */
    BankOpenAccount selectByExample(BankOpenAccountExample example);
}
