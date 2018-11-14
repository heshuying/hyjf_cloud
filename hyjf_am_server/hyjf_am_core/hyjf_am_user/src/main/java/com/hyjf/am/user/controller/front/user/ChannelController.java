/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.user;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.front.user.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    /**
     *  查询所有非主单的用户
     *
     * @return
     */
    @RequestMapping("/getUsersInfoList")
    public IntegerResponse getUsersInfoList() {
        List<Integer> utmPlat = channelService.getUsersInfoList();
        IntegerResponse response = new IntegerResponse();
        if (null != utmPlat) {
            response.setResult(utmPlat);
        }
        return response;
    }

    /**
     *  app渠道Ios、pc、wechat、android开户数
     *
     * @return
     */
    @RequestMapping("/getUsersList/{sourceIdSrch}")
    public IntegerResponse getUsersList(@PathVariable String sourceIdSrch) {
        List<Integer> utmPlat = channelService.getUsersList(sourceIdSrch);
        IntegerResponse response = new IntegerResponse();
        if (null != utmPlat) {
            response.setResult(utmPlat);
        }
        return response;
    }
}
