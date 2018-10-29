/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;


import com.hyjf.am.user.service.BaseService;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;

/**
 * @author cui
 * @version LockedUserService, v0.1 2018/9/25 11:34
 */
public interface LockedUserService extends BaseService {

    /**
     * 插入密码错误超限用户信息
     * @param lockedUserInfoVO
     * @return
     */
    public int inserLockedUser(LockedUserInfoVO lockedUserInfoVO);

}
