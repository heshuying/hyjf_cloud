/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user;

import com.hyjf.am.user.service.BaseService;

/**
 * @author yaoyong
 * @version ChannelService, v0.1 2018/7/9 12:00
 */
public interface ChannelService extends BaseService {
    String selectChannelName(Integer userId);
}
