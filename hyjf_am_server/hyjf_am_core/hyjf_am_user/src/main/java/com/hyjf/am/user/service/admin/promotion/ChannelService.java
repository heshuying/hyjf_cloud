/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.promotion;

import com.hyjf.am.user.dao.model.customize.ChannelCustomize;

import java.util.List;

/**
 * @author yaoyong
 * @version ChannelService, v0.1 2018/7/9 12:00
 */
public interface ChannelService {
    String selectChannelName(Integer userId);

    /**
     * @Author walter.limeng
     * @Description  获取推广总条数
     * @Date 11:36 2018/7/14
     * @Param channelCustomizeVO
     * @return Integer
     */
    Integer getChannelCoount(ChannelCustomize channelCustomize);

    /**
     * @Author walter.limeng
     * @Description  分页获取推广数据
     * @Date 11:37 2018/7/14
     * @Param channelCustomizeVO
     * @return List<ChannelCustomizeVO>
     */
    List<ChannelCustomize> getChannelList(ChannelCustomize channelCustomize);
}
