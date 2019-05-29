/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;

import java.util.List;

/**
 * 坐席配置Serivce
 *
 * @author liuyang
 * @version CustomerServiceRepresentiveConfigService, v0.1 2019/5/29 14:13
 */
public interface CustomerServiceRepresentiveConfigService extends BaseService {
    /**
     * 获取坐席配置
     *
     * @return
     */
    List<CustomerServiceRepresentiveConfig> selectCustomerServiceRepresentiveConfig();
}
