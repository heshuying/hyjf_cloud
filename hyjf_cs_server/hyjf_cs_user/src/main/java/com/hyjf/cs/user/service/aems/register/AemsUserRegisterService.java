/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.aems.register;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.bean.AemsUserRegisterRequestBean;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.RegisterRequest;

/**
 * AEMS系统:用户注册Service
 *
 * @author liuyang
 * @version AemsUserRegisterService, v0.1 2018/12/6 11:44
 */
public interface AemsUserRegisterService extends BaseUserService {

    /**
     * 参数校验
     *
     * @param registerRequest
     */
    void apiCheckParam(RegisterRequest registerRequest);

    /**
     * 根据手机号查询用户
     *
     * @param mobile
     * @return
     */
    UserVO getUsersByMobile(String mobile);

    /**
     * 获取授权状态
     *
     * @param userId
     * @return
     */
    String getAutoInvesStatus(Integer userId);

    /**
     * aems用户注册
     * @param aemsUserRegisterRequestBean
     * @param registerRequest
     * @param ip
     * @return
     */
    UserVO apiRegister(AemsUserRegisterRequestBean aemsUserRegisterRequestBean, RegisterRequest registerRequest, String ip);
}
