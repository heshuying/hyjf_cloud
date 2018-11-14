/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.front.user.impl;

import com.hyjf.am.user.service.front.user.ChannelService;
import com.hyjf.am.user.service.impl.BaseServiceImpl;
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
    public List<Integer> getUsersInfoList(){

        List<Integer> utmPlat = utmPlatCustomizeMapper.selectUsersInfo();
        return utmPlat;
    }

    @Override
    public List<Integer> getUsersList(String sourceIdSrch){

        List<Integer> utmPlat = utmPlatCustomizeMapper.selectUsers(sourceIdSrch);
        return utmPlat;
    }


}
