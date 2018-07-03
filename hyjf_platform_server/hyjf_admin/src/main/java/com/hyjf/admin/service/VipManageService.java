/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.VipManageRequest;

/**
 * @author yaoyong
 * @version VipManageService, v0.1 2018/7/2 14:59
 */
public interface VipManageService {
    JSONObject initVipManage();

    /**
     * 查询vip信息
     * @param vipManageRequest
     * @return
     */
    JSONObject searchList(VipManageRequest vipManageRequest);
}
