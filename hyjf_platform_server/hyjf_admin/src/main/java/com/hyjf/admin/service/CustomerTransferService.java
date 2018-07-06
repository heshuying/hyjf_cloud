/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CustomerTransferRequest;
import com.hyjf.am.vo.config.AdminSystemVO;

/**
 * @author: sunpeikai
 * @version: CustomerTransferService, v0.1 2018/7/6 9:15
 */
public interface CustomerTransferService {

    /**
     * 根据userName查询用户余额
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject searchBalanceByUsername(String userName);

    /**
     * 参数校验
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject checkCustomerTransferParam(CustomerTransferRequest request);

    /**
     * 根据header中的userId获取登录admin用户信息
     * @auth sunpeikai
     * @param userId 当前admin登录用户的userId
     * @return
     */
    AdminSystemVO getAdminSystemByUserId(Integer userId);

    /**
     * 向数据库的ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 用户转账-发起转账的参数
     * @return
     */
    boolean insertTransfer(CustomerTransferRequest request);
}
