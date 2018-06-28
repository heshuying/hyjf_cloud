/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.customize.CustomizeMapper;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserExample;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UserInfoExample;
import com.hyjf.am.user.service.BaseService;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * 用户服务:BaseService实现类
 *
 * @author liuyang
 * @version BaseServiceImpl, v0.1 2018/6/27 9:46
 */
public class BaseServiceImpl extends CustomizeMapper implements BaseService {
    /**
     * 根据用户ID获取用户信息
     *
     * @param userId
     * @return
     */
    @Override
    public User findUserByUserId(int userId) {
        UserExample usersExample = new UserExample();
        usersExample.createCriteria().andUserIdEqualTo(userId);
        List<User> usersList = userMapper.selectByExample(usersExample);
        if (!CollectionUtils.isEmpty(usersList)) {
            return usersList.get(0);
        }
        return null;
    }


    /**
     * 根据用户ID获取用户详情
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfo findUsersInfo(int userId) {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        List<UserInfo> list = userInfoMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 获取所有用户
     * @return
     * @throws ParseException
     */
    @Override
    public List<User> findAllUser() throws ParseException {
        UserExample usersExample = new UserExample();
        UserExample.Criteria cra = usersExample.createCriteria();
        cra.andBankOpenAccountEqualTo(1);
        cra.andIsCaFlagEqualTo(0);
        //法大大上线时间2018-03-14 00:00:00
        cra.andRegTimeGreaterThanOrEqualTo(DateFormat.getDateTimeInstance().parse("2018/3/14"));
        return userMapper.selectByExample(usersExample);
    }

}
