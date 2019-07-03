package com.hyjf.admin.service.config;

import com.hyjf.am.response.config.CustomerServiceRepresentiveConfigResponse;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;

/**
 * 坐席配置
 * @author wgx
 * @date 2019/5/30
 */
public interface CustomerServiceRepresentiveConfigService {

    /**
     * 获取坐席配置列表
     * @param request
     * @return
     */
    CustomerServiceRepresentiveConfigResponse getCustomerServiceRepresentiveConfigList(CustomerServiceRepresentiveConfigRequest request);

    /**
     * 添加坐席配置
     * @param request
     */
    CustomerServiceRepresentiveConfigResponse insertCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);

    /**
     * 修改坐席配置
     * @param request
     */
    CustomerServiceRepresentiveConfigResponse updateCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);

    /**
     * 删除坐席配置
     * @param request
     */
    CustomerServiceRepresentiveConfigResponse deleteCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);

    /**
     * 校验坐席配置
     * @param request
     */
    CustomerServiceRepresentiveConfigResponse checkCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);
}
