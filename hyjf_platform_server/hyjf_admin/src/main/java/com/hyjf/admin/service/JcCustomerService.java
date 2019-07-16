/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.CustomerServerResponse;
import com.hyjf.am.resquest.message.JcCustomerServerRequest;

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

    /**
     * 添加客户服务
     * @param request
     * @return
     */
    CustomerServerResponse addCustomerServer(JcCustomerServerRequest request);

    /**
     * 根据id查询客户服务
     * @param id
     * @return
     */
    CustomerServerResponse getCustomerServer(String id);

    /**
     * 修改客户服务信息
     * @param request
     * @return
     */
    CustomerServerResponse updateCustomerServer(JcCustomerServerRequest request);
}
