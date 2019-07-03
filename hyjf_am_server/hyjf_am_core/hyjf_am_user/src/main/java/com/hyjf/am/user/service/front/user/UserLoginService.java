package com.hyjf.am.user.service.front.user;

import java.util.Date;

/**
 * @author xiasq
 * @version UserLoginService, v0.1 2019/5/5 14:07
 */
public interface UserLoginService {
    /**
     * 查询用户在某段时间内是否有过登录
     * @param userId
     * @param startDate
     * @param endDate
     * @return
     */
    boolean hasLogin(Integer userId, Date startDate, Date endDate);
}
