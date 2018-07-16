/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller;

import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.user.dao.model.customize.ChannelCustomize;
import com.hyjf.am.user.service.ChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @GetMapping("/selectChannelNameById/{userId}")
    public String selectChannelNameById(@PathVariable Integer userId) {
        String channelName = channelService.selectChannelName(userId);
        return channelName;
    }

    /**
     * 分页获取数据
     * @param channelCustomize 查询参数
     * @return
     */
    @RequestMapping("/getchannellist")
    public UtmResponse getChannelList(@RequestBody @Valid ChannelCustomize channelCustomize) {
        UtmResponse response = new UtmResponse();
        List<ChannelCustomize> pageList = channelService.getChannelList(channelCustomize);
        if (pageList != null) {
            response.setResultList(pageList);
        }
        return response;
    }

    /**
     * 获取总条数
     * @param channelCustomize 查询参数
     * @return UtmResultResponse
     */
    @RequestMapping("/getchannelcount")
    public UtmResponse getChannelCoount(@RequestBody @Valid ChannelCustomize channelCustomize) {
        UtmResponse response = new UtmResponse();
        Integer size = channelService.getChannelCoount(channelCustomize);
        if (size != null) {
            response.setRecordTotal(size);
        }
        return response;
    }
}
