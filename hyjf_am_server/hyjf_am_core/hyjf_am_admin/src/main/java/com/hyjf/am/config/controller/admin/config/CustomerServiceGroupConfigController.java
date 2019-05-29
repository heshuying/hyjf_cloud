package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import com.hyjf.am.vo.config.CustomerServiceGroupConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wgx
 * @date 2019/5/29
 */
@RestController
@RequestMapping("/am-admin/customerServiceGroupConfig")
public class CustomerServiceGroupConfigController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceGroupConfigController.class);

    @Autowired
    private CustomerServiceGroupConfigService customerServiceGroupConfigService;

    /**
     * 获取客组配置列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCustomerServiceGroupConfigList")
    public CustomerServiceGroupConfigResponse getCustomerServiceGroupConfigList(@RequestBody CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigResponse response = new CustomerServiceGroupConfigResponse();
        try {
            int count = customerServiceGroupConfigService.countCustomerServiceGroupConfig(request);
            if (count > 0) {
                List<CustomerServiceGroupConfig> customerServiceGroupConfigList = customerServiceGroupConfigService.getCustomerServiceGroupConfigList(request, count);
                if (!CollectionUtils.isEmpty(customerServiceGroupConfigList)) {
                    List<CustomerServiceGroupConfigVO> resultList = CommonUtils.convertBeanList(customerServiceGroupConfigList, CustomerServiceGroupConfigVO.class);
                    response.setResultList(resultList);
                }
                response.setTotal(count);
            }
        } catch (Exception e) {
            logger.error("【客组配置】获取客组配置列表失败！", e);
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("获取客组配置列表失败！");
            return response;
        }
        return response;
    }

    /**
     * 添加客组配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/insertCustomerServiceGroupConfig")
    public CustomerServiceGroupConfigResponse insertCustomerServiceGroupConfig(@RequestBody CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigResponse response = new CustomerServiceGroupConfigResponse();
        try {
            CustomerServiceGroupConfig config = new CustomerServiceGroupConfig();
            BeanUtils.copyProperties(request, config);
            customerServiceGroupConfigService.insertCustomerServiceGroupConfig(config);
        } catch (Exception e) {
            logger.error("【客组配置】添加客组配置信息失败！", e);
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("添加客组配置信息失败！");
            return response;
        }
        return response;
    }

    /**
     * 修改客组配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateCustomerServiceGroupConfig")
    public CustomerServiceGroupConfigResponse updateCustomerServiceGroupConfig(@RequestBody CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigResponse response = new CustomerServiceGroupConfigResponse();
        if (request.getId() == null) {
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("id为不能为空！");
            return response;
        }
        try {
            CustomerServiceGroupConfig config = new CustomerServiceGroupConfig();
            BeanUtils.copyProperties(request, config);
            customerServiceGroupConfigService.updateCustomerServiceGroupConfig(config);
        } catch (Exception e) {
            logger.error("【客组配置】修改客组配置信息失败！", e);
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("修改客组配置信息失败！");
            return response;
        }
        return response;
    }

    /**
     * 删除客组配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteCustomerServiceGroupConfig")
    public CustomerServiceGroupConfigResponse deleteCustomerServiceGroupConfig(@RequestBody CustomerServiceGroupConfigRequest request) {
        CustomerServiceGroupConfigResponse response = new CustomerServiceGroupConfigResponse();
        if (request.getId() == null) {
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("id为不能为空！");
            return response;
        }
        try {
            customerServiceGroupConfigService.deleteCustomerServiceGroupConfig(request.getId());
        } catch (Exception e) {
            logger.error("【客组配置】删除客组配置信息失败！", e);
            response.setRtn(CustomerServiceGroupConfigResponse.FAIL);
            response.setMessage("删除客组配置信息失败！");
            return response;
        }
        return response;
    }
}
