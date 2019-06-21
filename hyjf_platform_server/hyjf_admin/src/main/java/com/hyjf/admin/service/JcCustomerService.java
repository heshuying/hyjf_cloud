/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.JcCustomerServerRequest;
import com.hyjf.am.response.admin.CustomerServerResponse;

/**
 * @author yaoyong
 * @version JcCustomerService, v0.1 2019/6/21 11:04
 */
public interface JcCustomerService {

    /**
     * 获取客户服务列表
     * @param request
     * @return
     */
    CustomerServerResponse getServerList(JcCustomerServerRequest request);
}
