package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.dao.mapper.auto.LockedUserInfoMapper;
import com.hyjf.am.user.dao.model.auto.LockedUserInfo;
import com.hyjf.am.user.service.front.user.LockedUserService;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;

public class LockedUserServiceImpl implements LockedUserService {
    @Autowired
    protected LockedUserInfoMapper lockedUserInfoMapper;
    /**
     * 插入密码错误超限用户信息
     * @param lockedUserInfoVO
     * @return
     */
    public int inserLockedUser(LockedUserInfoVO lockedUserInfoVO){
        LockedUserInfo lockedUserInfo=new LockedUserInfo();
        try {
            BeanUtils.copyProperties(lockedUserInfo,lockedUserInfoVO);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        int insertLockedUser=lockedUserInfoMapper.insert(lockedUserInfo);
        return insertLockedUser;
    }
}
