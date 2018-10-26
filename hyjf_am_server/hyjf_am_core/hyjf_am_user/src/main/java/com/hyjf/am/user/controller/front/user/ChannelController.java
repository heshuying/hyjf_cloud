/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yaoyong
 * @version ChannelController, v0.1 2018/7/9 11:55
 * 获取用户注册时渠道名称
 */
@RestController
@RequestMapping("/am-user/channel")
public class ChannelController extends BaseController {
    private Logger logger = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    ChannelService channelService;

    @RequestMapping("/getchannelnamebyuserd/{userId}")
    public UtmResponse selectChannelNameByUserId(@PathVariable Integer userId) {
        UtmResponse response = new UtmResponse();
        String channelName = channelService.selectChannelName(userId);
        response.setChannelName(channelName);
        return response;
    }
}
