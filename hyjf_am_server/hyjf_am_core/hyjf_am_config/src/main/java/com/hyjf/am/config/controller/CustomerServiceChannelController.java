/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.service.CustomerServiceChannelService;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 推送禁用渠道Controller
 *
 * @author liuyang
 * @version CustomerServiceChannelController, v0.1 2019/5/29 15:54
 */
@RestController
@RequestMapping("/am-config/customerServiceChannel")
public class CustomerServiceChannelController extends BaseConfigController {

    @Autowired
    private CustomerServiceChannelService customerServiceChannelService;

}
