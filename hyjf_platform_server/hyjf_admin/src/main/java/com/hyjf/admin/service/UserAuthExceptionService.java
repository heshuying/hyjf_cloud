/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;

import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionService, v0.1 2018/7/2 10:33
 */
public interface UserAuthExceptionService {
    /**
     * 自动债转授权异常list
     * @auth 孙沛凯
     * @param request 自动债转授权异常的筛选条件
     * @return list
     */
    AdminUserAuthListResponse selectUserAuthList(AdminUserAuthListRequest request);
    /**
     * 同步用户授权状态
     * @auth 孙沛凯
     * @param userId 用户id
     * @param type 1自动投资授权  2债转授权
     * @return
     */
    AdminUserAuthListResponse synUserAuth(Integer userId,Integer type);
}
