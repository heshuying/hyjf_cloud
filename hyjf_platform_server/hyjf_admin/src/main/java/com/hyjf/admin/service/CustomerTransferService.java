/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;

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
}
