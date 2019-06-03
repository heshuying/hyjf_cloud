package com.hyjf.am.config.service.config;

import com.hyjf.am.config.dao.model.auto.CustomerServiceRepresentiveConfig;
import com.hyjf.am.resquest.config.CustomerServiceRepresentiveConfigRequest;

import java.util.List;
import java.util.Map;

/**
 * 坐席配置
 *
 * @author wgx
 * @date 2019/5/30
 */
public interface CustomerServiceRepresentiveConfigService {
    /**
     * 获取坐席数量
     *
     * @param request
     * @return
     */
    int countCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);

    /**
     * 获取坐席配置列表
     *
     * @param request
     * @param total
     * @return
     */
    List<CustomerServiceRepresentiveConfig> getCustomerServiceRepresentiveConfigList(CustomerServiceRepresentiveConfigRequest request, int total);

    /**
     * 添加坐席配置
     *
     * @param config
     */
    void insertCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfig config);

    /**
     * 修改坐席配置
     *
     * @param config
     */
    void updateCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfig config);

    /**
     * 删除坐席配置
     *
     * @param id
     */
    void deleteCustomerServiceRepresentiveConfig(Integer id);

    /**
     * 根据id修改冗余客组名称和是否新客
     *  @param groupId
     * @param groupName
     * @param isNew
     * @param updateUserId
     */
    void updateGroupNameAndIsNew(Integer groupId, String groupName, Integer isNew, Integer updateUserId);

    /**
     * 根据客组禁用坐席配置
     *
     * @param groupId
     * @param updateUserId
     */
    void updateCustomerServiceRepresentiveConfigByGroup(Integer groupId, Integer updateUserId);

    /**
     * 校验坐席配置信息
     *
     * @param request
     * @return
     */
    Map<String, Object> checkCustomerServiceRepresentiveConfig(CustomerServiceRepresentiveConfigRequest request);
}
