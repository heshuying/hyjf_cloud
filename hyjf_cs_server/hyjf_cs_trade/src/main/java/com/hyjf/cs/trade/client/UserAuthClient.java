package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.user.HjhUserAuthVO;

public interface UserAuthClient {

    /**
     * 获取用户权限
     * @author zhangyk
     * @date 2018/6/29 18:36
     */
    public HjhUserAuthVO getUserAuthByUserId(Integer userId);
}
