/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.service.CustomerServiceGroupConfigService;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 客组配置Controller
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigController, v0.1 2019/5/29 10:53
 */
@RestController
@RequestMapping("/am-config/customerServiceGroupConfig")
public class CustomerServiceGroupConfigController extends BaseConfigController {

    @Autowired
    private CustomerServiceGroupConfigService customerServiceGroupConfigService;


    /**
     * 获取客组配置
     *
     * @return
     */
    @RequestMapping("/selectCustomerServiceGroupConfigList")
    public CustomerServiceGroupConfigResponse selectCustomerServiceGroupConfigList() {
        CustomerServiceGroupConfigResponse response = new CustomerServiceGroupConfigResponse();
        List<CustomerServiceGroupConfig> customerServiceGroupConfigList = customerServiceGroupConfigService.selectCustomerServiceGroupConfigList();
        if (!CollectionUtils.isEmpty(customerServiceGroupConfigList)) {
            List<CustomerServiceGroupConfigVO> resultList = CommonUtils.convertBeanList(customerServiceGroupConfigList, CustomerServiceGroupConfigVO.class);
            response.setResultList(resultList);
        }
        return response;
    }
}
