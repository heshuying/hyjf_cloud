/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfigExample;
import com.hyjf.am.config.service.CustomerServiceGroupConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 客组配置Service实现类
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigServiceImpl, v0.1 2019/5/29 10:57
 */
@Service
public class CustomerServiceGroupConfigServiceImpl extends BaseServiceImpl implements CustomerServiceGroupConfigService {
    /**
     * 获取客组配置
     *
     * @return
     */
    @Override
    public List<CustomerServiceGroupConfig> selectCustomerServiceGroupConfigList() {
        CustomerServiceGroupConfigExample example = new CustomerServiceGroupConfigExample();
        CustomerServiceGroupConfigExample.Criteria cra = example.createCriteria();
        // 启用状态 1.启用2.禁用
        cra.andStatusEqualTo(1);
        return this.customerServiceGroupConfigMapper.selectByExample(example);
    }
}
