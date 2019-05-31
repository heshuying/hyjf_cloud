/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;

/**
 * 推送禁用渠道Service
 *
 * @author liuyang
 * @version CustomerServiceChannelService, v0.1 2019/5/29 15:55
 */
public interface CustomerServiceChannelService extends BaseService {
    /**
     * 根据sourceId查询该渠道是否被禁用
     *
     * @param sourceId
     * @return
     */
    CustomerServiceChannel selectCustomerServiceChannelBySourceId(Integer sourceId);
}
