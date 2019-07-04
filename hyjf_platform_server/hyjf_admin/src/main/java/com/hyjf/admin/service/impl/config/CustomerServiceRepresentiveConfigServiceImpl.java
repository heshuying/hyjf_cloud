package com.hyjf.admin.service.impl.config;

import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.config.CustomerServiceRepresentiveConfigService;
import com.hyjf.am.response.config.CustomerServiceRepresentiveConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 坐席配置
 * @author wgx
 * @date 2019/5/29
 */
@Service
public class CustomerServiceRepresentiveConfigServiceImpl implements CustomerServiceRepresentiveConfigService {

    @Autowired
    AmConfigClient amConfigClient;

    /**
     * 获取坐席配置列表
     * @param request
     * @return
     */
    @Override
    public CustomerServiceRepresentiveConfigResponse getCustomerServiceRepresentiveConfigList(CustomerServiceRepresentiveConfigRequest request) {
        return amConfigClient.getCustomerServiceRepresentiveConfigList(request);
    }

    /**
     * 添加坐席配置
     * @param request
     */
    @Override
    public CustomerServiceRepresentiveConfigResponse insertCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        return amConfigClient.insertCustomerServiceRepresentiveConfig(request);
    }

    /**
     * 修改坐席配置
     * @param request
     */
    @Override
    public CustomerServiceRepresentiveConfigResponse updateCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        return amConfigClient.updateCustomerServiceRepresentiveConfig(request);
    }

    /**
     * 删除坐席配置
     * @param request
     */
    @Override
    public CustomerServiceRepresentiveConfigResponse deleteCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        return amConfigClient.deleteCustomerServiceRepresentiveConfig(request);
    }

    /**
     * 校验坐席配置
     * @param request
     */
    @Override
    public CustomerServiceRepresentiveConfigResponse checkCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request) {
        return amConfigClient.checkCustomerServiceRepresentiveConfig(request);
    }
}
