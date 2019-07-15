/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.service;

import com.hyjf.am.resquest.message.JcCustomerServerRequest;
import com.hyjf.cs.message.bean.ic.JcCustomerService;

import java.util.List;

/**
 * @author yaoyong
 * @version JcCustomerServerService, v0.1 2019/6/24 10:04
 */
public interface JcCustomerServerService {

    /**
     * 查询服务列表数量
     * @param request
     * @return
     */
    long countServerList(JcCustomerServerRequest request);

    /**
     * 查询服务列表
     * @param request
     * @return
     */
    List<JcCustomerService> getServerList(JcCustomerServerRequest request);

    /**
     * 添加客户服务
     * @param customerService
     * @return
     */
    Integer insertServer(JcCustomerService customerService);

    /**
     * 根据id获取客户服务
     * @param id
     * @return
     */
    JcCustomerService getCustomerServer(String id);

    /**
     * 修改客服服务信息
     * @param customerService
     * @return
     */
    Integer updateCustomerServer(JcCustomerService customerService);

    /**
     * 获取客户服务数据
     * @return
     */
    JcCustomerService getCustomerService();
}
