/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.dao.model.auto.CustomerServiceChannelExample;
import com.hyjf.am.config.service.CustomerServiceChannelService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 推送禁用渠道Service实现类
 *
 * @author liuyang
 * @version CustomerServiceChannelServiceImpl, v0.1 2019/5/29 16:15
 */
@Service
public class CustomerServiceChannelServiceImpl extends BaseServiceImpl implements CustomerServiceChannelService {
    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    @Override
    public CustomerServiceChannel selectCustomerServiceChannelBySourceId(Integer sourceId) {
        CustomerServiceChannelExample example = new CustomerServiceChannelExample();
        CustomerServiceChannelExample.Criteria cra = example.createCriteria();
        cra.andChannelIdEqualTo(sourceId);
        List<CustomerServiceChannel> list = this.customerServiceChannelMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
