/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.promotion.impl;

import com.hyjf.am.user.dao.model.customize.ChannelCustomize;
import com.hyjf.am.user.service.admin.promotion.ChannelService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.promotion.channel.ChannelCustomizeVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version ChannelServiceImpl, v0.1 2018/7/9 12:00
 */
@Service
public class ChannelServiceImpl extends BaseServiceImpl implements ChannelService {

    @Override
    public String selectChannelName(Integer userId) {
        String channelName = channelCustomizeMapper.selectChannelNameByUserId(userId);

        return channelName;
    }

    @Override
    public Integer getChannelCoount(ChannelCustomize channelCustomize) {
        return channelCustomizeMapper.countList(channelCustomize);
    }

    @Override
    public List<ChannelCustomize> getChannelList(ChannelCustomizeVO channelCustomize) {
        return channelCustomizeMapper.selectList(channelCustomize);
    }
}
