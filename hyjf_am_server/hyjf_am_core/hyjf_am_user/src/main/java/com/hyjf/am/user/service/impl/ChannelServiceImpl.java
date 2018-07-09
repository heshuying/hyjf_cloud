/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.impl;

import com.hyjf.am.user.dao.mapper.customize.ChannelCustomizeMapper;
import com.hyjf.am.user.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yaoyong
 * @version ChannelServiceImpl, v0.1 2018/7/9 12:00
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    ChannelCustomizeMapper channelCustomizeMapper;

    @Override
    public String selectChannelName(Integer userId) {
        String channelName = channelCustomizeMapper.selectChannelNameByUserId(userId);

        return channelName;
    }
}
