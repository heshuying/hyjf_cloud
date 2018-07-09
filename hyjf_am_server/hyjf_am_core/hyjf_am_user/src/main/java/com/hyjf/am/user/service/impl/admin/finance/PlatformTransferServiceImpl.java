/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl.admin.finance;

import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.service.admin.finance.PlatformTransferService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 13:55
 */
@Service
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {

    /**
     * 根据userId列表查询user列表
     * @auth sunpeikai
     * @param userIds 用户id列表
     * @return
     */
    @Override
    public List<User> findUserListByUserIds(String userIds) {
        return null;
    }
}
