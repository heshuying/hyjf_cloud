package com.hyjf.admin.service.impl.config;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.config.CustomerServiceGroupConfigService;
import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wgx
 * @date 2019/5/29
 */
@Service
public class CustomerServiceGroupConfigServiceImpl implements CustomerServiceGroupConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    /**
     * 获取客组配置列表
     * @param request
     * @return
     */
    @Override
    public CustomerServiceGroupConfigResponse getCustomerServiceGroupConfigList(CustomerServiceGroupConfigRequest request) {
        return amConfigClient.getCustomerServiceGroupConfigList(request);
    }

    /**
     * 添加客组配置
     * @param request
     */
    @Override
    public CustomerServiceGroupConfigResponse insertCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        return amConfigClient.insertCustomerServiceGroupConfig(request);
    }

    /**
     * 修改客组配置
     * @param request
     */
    @Override
    public CustomerServiceGroupConfigResponse updateCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        return amConfigClient.updateCustomerServiceGroupConfig(request);
    }

    /**
     * 删除客组配置
     * @param request
     */
    @Override
    public CustomerServiceGroupConfigResponse deleteCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        return amConfigClient.deleteCustomerServiceGroupConfig(request);
    }

    /**
     * 校验客组配置
     * @param request
     */
    @Override
    public CustomerServiceGroupConfigResponse checkCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request) {
        return amConfigClient.checkCustomerServiceGroupConfig(request);
    }
}
