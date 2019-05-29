/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfigExample;
import com.hyjf.am.config.service.CustomerServiceRepresentiveConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 坐席配置Service实现类
 *
 * @author liuyang
 * @version CustomerServiceRepresentiveConfigServiceImpl, v0.1 2019/5/29 14:13
 */
@Service
public class CustomerServiceRepresentiveConfigServiceImpl extends BaseServiceImpl implements CustomerServiceRepresentiveConfigService {

    /**
     * 获取坐席配置
     *
     * @return
     */
    @Override
    public List<CustomerServiceRepresentiveConfig> selectCustomerServiceRepresentiveConfig() {
        CustomerServiceRepresentiveConfigExample example = new CustomerServiceRepresentiveConfigExample();
        CustomerServiceRepresentiveConfigExample.Criteria cra = example.createCriteria();
        // 启用状态 1.启用2.禁用
        cra.andStatusEqualTo(1);
        return this.customerServiceRepresentiveConfigMapper.selectByExample(example);
    }
}
