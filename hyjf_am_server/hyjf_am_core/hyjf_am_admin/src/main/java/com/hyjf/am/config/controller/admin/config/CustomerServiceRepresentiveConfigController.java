package com.hyjf.am.config.controller.admin.config;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.config.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.config.service.config.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.response.config.CustomerServiceRepresentiveConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;
import com.hyjf.am.vo.config.CustomerServiceRepresentiveConfigVO;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 坐席配置Controller
 * @author wgx
 * @date 2019/5/30
 */
@RestController
@RequestMapping("/am-admin/customerServiceRepresentiveConfig")
public class CustomerServiceRepresentiveConfigController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceRepresentiveConfigController.class);

    @Autowired
    private CustomerServiceRepresentiveConfigService customerServiceRepresentiveConfigService;
    @Autowired
    private CustomerServiceGroupConfigService customerServiceGroupConfigService;

    /**
     * 获取坐席配置列表
     *
     * @param request
     * @return
     */
    @RequestMapping("/getCustomerServiceRepresentiveConfigList")
    public CustomerServiceRepresentiveConfigResponse getCustomerServiceRepresentiveConfigList(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        try {
            response.setResultList(Collections.emptyList());
            int count = customerServiceRepresentiveConfigService.countCustomerServiceRepresentiveConfig(request);
            if (count > 0) {
                List<CustomerServiceRepresentiveConfig> customerServiceRepresentiveConfigList = customerServiceRepresentiveConfigService.getCustomerServiceRepresentiveConfigList(request, count);
                if (!CollectionUtils.isEmpty(customerServiceRepresentiveConfigList)) {
                    List<CustomerServiceRepresentiveConfigVO> resultList = CommonUtils.convertBeanList(customerServiceRepresentiveConfigList, CustomerServiceRepresentiveConfigVO.class);
                    response.setResultList(resultList);
                }
                response.setTotal(count);
            }
        } catch (Exception e) {
            logger.error("【坐席配置】获取坐席配置列表失败！", e);
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("获取坐席配置列表失败！");
            return response;
        }
        return response;
    }

    /**
     * 添加坐席配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/insertCustomerServiceRepresentiveConfig")
    public CustomerServiceRepresentiveConfigResponse insertCustomerServiceRepresentiveConfig(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        if (StringUtils.isBlank(request.getUserName())) {
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("姓名为不能为空！");
            return response;
        }
        if (request.getGroupId() == null) {
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("客服组不能为空！");
            return response;
        }
        if (request.getStatus() == null) {
            request.setStatus(2);// 默认禁用
        }
        try {
            CustomerServiceRepresentiveConfig config = new CustomerServiceRepresentiveConfig();
            BeanUtils.copyProperties(request, config);
            CustomerServiceGroupConfig groupConfig = customerServiceGroupConfigService.getCustomerServiceGroupConfigById(request.getGroupId());
            config.setGroupName(groupConfig.getGroupName());
            config.setIsNew(groupConfig.getIsNew());
            if (groupConfig.getStatus() == 2 && config.getStatus() == 1) {
                logger.error("【坐席配置】客组配置为禁用状态！");
                response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
                response.setMessage("所选客组配置为禁用状态！");
                return response;
            }
            customerServiceRepresentiveConfigService.insertCustomerServiceRepresentiveConfig(config);
        } catch (Exception e) {
            logger.error("【坐席配置】添加坐席配置信息失败！", e);
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("添加坐席配置信息失败！");
            return response;
        }
        return response;
    }

    /**
     * 修改坐席配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/updateCustomerServiceRepresentiveConfig")
    public CustomerServiceRepresentiveConfigResponse updateCustomerServiceRepresentiveConfig(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        if (request.getId() == null) {
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("id不能为空！");
            return response;
        }
        try {
            CustomerServiceRepresentiveConfig config = new CustomerServiceRepresentiveConfig();
            BeanUtils.copyProperties(request, config);
            if(request.getGroupId() != null) {
                CustomerServiceGroupConfig groupConfig = customerServiceGroupConfigService.getCustomerServiceGroupConfigById(request.getGroupId());
                config.setGroupName(groupConfig.getGroupName());
                config.setIsNew(groupConfig.getIsNew());
                if (groupConfig.getStatus() == 2 && config.getStatus() == 1) {
                    logger.error("【坐席配置】客组配置为禁用状态！");
                    response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
                    response.setMessage("所选客组配置为禁用状态！");
                    return response;
                }
            }
            customerServiceRepresentiveConfigService.updateCustomerServiceRepresentiveConfig(config);
        } catch (Exception e) {
            logger.error("【坐席配置】修改坐席配置信息失败！", e);
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("修改坐席配置信息失败！");
            return response;
        }
        return response;
    }

    /**
     * 删除坐席配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/deleteCustomerServiceRepresentiveConfig")
    public CustomerServiceRepresentiveConfigResponse deleteCustomerServiceRepresentiveConfig(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        if (request.getId() == null) {
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("id不能为空！");
            return response;
        }
        try {
            customerServiceRepresentiveConfigService.deleteCustomerServiceRepresentiveConfig(request.getId());
        } catch (Exception e) {
            logger.error("【坐席配置】删除坐席配置信息失败！", e);
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("删除坐席配置信息失败！");
            return response;
        }
        return response;
    }

    /**
     * 校验坐席配置信息
     *
     * @param request
     * @return
     */
    @RequestMapping("/checkCustomerServiceRepresentiveConfig")
    public CustomerServiceRepresentiveConfigResponse checkCustomerServiceRepresentiveConfig(@RequestBody CustomerServiceRepresentiveConfigRequest request) {
        CustomerServiceRepresentiveConfigResponse response = new CustomerServiceRepresentiveConfigResponse();
        try {
            Map<String,Object> result = customerServiceRepresentiveConfigService.checkCustomerServiceRepresentiveConfig(request);
            if(!"success".equals(result.get("success"))){
                response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
                response.setMessage(result.get("message").toString());
                return response;
            }
        } catch (Exception e) {
            logger.error("【坐席配置】校验坐席配置信息失败！", e);
            response.setRtn(CustomerServiceRepresentiveConfigResponse.FAIL);
            response.setMessage("校验坐席配置信息失败！");
            return response;
        }
        return response;
    }
}
