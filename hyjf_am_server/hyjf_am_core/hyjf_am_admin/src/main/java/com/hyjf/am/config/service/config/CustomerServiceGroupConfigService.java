package com.hyjf.am.config.service.config;

import com.hyjf.am.config.dao.model.auto.CustomerServiceGroupConfig;
import com.hyjf.am.resquest.config.CustomerServiceGroupConfigRequest;

import java.util.List;

/**
 * @author wgx
 * @date 2019/5/29
 */
public interface CustomerServiceGroupConfigService {
    /**
     * 获取客组数量
     * @param request
     * @return
     */
    int countCustomerServiceGroupConfig(CustomerServiceGroupConfigRequest request);

    /**
     * 获取客组配置列表
     * @param request
     * @param total
     * @return
     */
    List<CustomerServiceGroupConfig> getCustomerServiceGroupConfigList(CustomerServiceGroupConfigRequest request, int total);

    /**
     * 添加客组配置
     * @param config
     */
    void insertCustomerServiceGroupConfig(CustomerServiceGroupConfig config);

    /**
     * 修改客组配置
     * @param config
     */
    void updateCustomerServiceGroupConfig(CustomerServiceGroupConfig config);

    /**
     * 删除客组配置
     * @param id
     */
    void deleteCustomerServiceGroupConfig(Integer id);
}
