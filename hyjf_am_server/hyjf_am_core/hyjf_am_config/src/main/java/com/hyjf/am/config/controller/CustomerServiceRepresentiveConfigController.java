/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.service.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.response.config.CustomerServiceRepresentiveConfigResponse;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 坐席配置Controller
 *
 * @author liuyang
 * @version CustomerServiceRepresentiveConfigController, v0.1 2019/5/29 14:11
 */
@RestController
@RequestMapping("/am-config/customerServiceRepresentiveConfig")
public class CustomerServiceRepresentiveConfigController extends BaseConfigController {

    @Autowired
    private CustomerServiceRepresentiveConfigService customerServiceRepresentiveConfigService;

    /**
     * 获取客组类型为新客组的坐席配置
     *
     * @return
     */
    @RequestMapping("/selectCustomerServiceRepresentiveConfig")
    public CustomerServiceRepresentiveConfigResponse selectCustomerServiceRepresentiveConfig() {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        List<CustomerServiceRepresentiveConfig> customerServiceGroupConfigList = customerServiceRepresentiveConfigService.selectCustomerServiceRepresentiveConfig();
        if (!CollectionUtils.isEmpty(customerServiceGroupConfigList)) {
            List<CustomerServiceRepresentiveConfigVO> resultList = CommonUtils.convertBeanList(customerServiceGroupConfigList, CustomerServiceRepresentiveConfigVO.class);
            response.setResultList(resultList);
        }
        return response;
    }


    /**
     * 根据当前拥有人姓名查询坐席配置
     *
     * @return
     */
    @GetMapping("/selectCustomerServiceRepresentiveConfigByUserName/{currentOwner}")
    public CustomerServiceRepresentiveConfigResponse selectCustomerServiceRepresentiveConfigByUserName(@PathVariable String currentOwner) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        CustomerServiceRepresentiveConfig customerServiceGroupConfig = customerServiceRepresentiveConfigService.selectCustomerServiceRepresentiveConfigByUserName(currentOwner);
        if (customerServiceGroupConfig != null) {
            CustomerServiceRepresentiveConfigVO customerServiceRepresentiveConfig = new CustomerServiceRepresentiveConfigVO();
            BeanUtils.copyProperties(customerServiceGroupConfig, CustomerServiceRepresentiveConfigVO.class);
            response.setResult(customerServiceRepresentiveConfig);
        }
        return response;
    }

}
