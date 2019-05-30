package com.hyjf.admin.service.config;

import com.hyjf.am.response.config.CustomerServiceGroupConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;

/**
 * 客组配置
 * @author wgx
 * @date 2019/5/29
 */
public interface CustomerServiceGroupConfigService {

    /**
     * 获取客组配置列表
     * @param request
     * @return
     */
    CustomerServiceGroupConfigResponse getCustomerServiceGroupConfigList(CustomerServiceGroupConfigRequest request);

    /**
     * 添加客组配置
     * @param request
     */
    CustomerServiceGroupConfigResponse insertCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request);

    /**
     * 修改客组配置
     * @param request
     */
    CustomerServiceGroupConfigResponse updateCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request);

    /**
     * 删除客组配置
     * @param request
     */
    CustomerServiceGroupConfigResponse deleteCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request);
}
