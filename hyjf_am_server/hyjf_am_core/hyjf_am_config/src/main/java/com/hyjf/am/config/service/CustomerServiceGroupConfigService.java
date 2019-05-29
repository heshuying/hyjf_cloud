/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;

import java.util.List;

/**
 * 客组配置Service
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigService, v0.1 2019/5/29 10:56
 */
public interface CustomerServiceGroupConfigService extends BaseService {
    /**
     * 获取客组配置
     *
     * @return
     */
    List<CustomerServiceGroupConfig> selectCustomerServiceGroupConfigList();
}
