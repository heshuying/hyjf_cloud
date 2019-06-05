/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CustomerServiceChannel;
import com.hyjf.am.config.service.CustomerServiceChannelService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.CustomerServiceChannelResponse;
import com.hyjf.am.vo.config.CustomerServiceChannelVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/selectCustomerServiceChannelBySourceId/{sourceId}")
    public CustomerServiceChannelResponse selectCustomerServiceChannelBySourceId(@PathVariable Integer sourceId) {
        CustomerServiceChannelResponse response = new CustomerServiceChannelResponse();
        CustomerServiceChannel customerServiceChannel = this.customerServiceChannelService.selectCustomerServiceChannelBySourceId(sourceId);
        if (customerServiceChannel != null) {
            CustomerServiceChannelVO customerServiceChannelVO = new CustomerServiceChannelVO();
            BeanUtils.copyProperties(customerServiceChannel, customerServiceChannelVO);
            // 设置返回结果
            response.setResult(customerServiceChannelVO);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }
}
