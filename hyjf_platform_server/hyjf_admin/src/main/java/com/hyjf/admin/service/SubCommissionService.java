/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.SubCommissionRequest;

/**
 * @author: sunpeikai
 * @version: SubCommissionService, v0.1 2018/7/10 9:45
 */
public interface SubCommissionService {
    /**
     * 发起账户分佣所需的detail信息
     * @auth sunpeikai
     * @param loginUserId 当前登录用户id
     * @return
     */
    JSONObject searchDetails(Integer loginUserId);

    /**
     * 平台账户分佣
     * @auth sunpeikai
     * @param loginUserId 当前登录用户id
     * @param request 插入数据参数
     * @return
     */
    JSONObject subCommission(Integer loginUserId, SubCommissionRequest request);
}
