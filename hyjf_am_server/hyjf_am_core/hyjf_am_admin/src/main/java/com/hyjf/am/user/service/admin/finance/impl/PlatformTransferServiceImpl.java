/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.finance.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.finance.PlatformTransferService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 13:55
 */
@Service("amUserPlatformTransferServiceImpl")
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {

    /**
     * 根据userId列表查询user列表
     * @auth sunpeikai
     * @param userIds 用户id列表
     * @return
     */
    @Override
    public List<User> findUserListByUserIds(String userIds) {
        String[] ids = userIds.split(",");
        List<User> userList = new ArrayList<>();
        for(String userId:ids){
            if(StringUtils.isNotEmpty(userId)){
                Integer id = Integer.valueOf(userId);
                User user = this.findUserByUserId(id);
                userList.add(user);
            }
        }
        return userList;
    }
}
