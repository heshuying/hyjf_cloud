package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminProtocolResponse;
import com.hyjf.am.resquest.admin.AdminProtocolRequest;

/**
 * @author：yinhui
 * @Date: 2018/8/8  15:59
 */
public interface ProtocolService {

    /**
     * 获取全部列表分页
     *
     * @return
     */
    AdminProtocolResponse searchPage(AdminProtocolRequest request);

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    AdminProtocolResponse getProtocolTemplateById(AdminProtocolRequest request);

    /**
     * 查询协议模板数量
     *
     * @return
     */
    Integer getProtocolTemplateNum(AdminProtocolRequest request);

    /**
     * 添加协议模板
     *
     * @return
     */
    void insertProtocolTemplate(AdminProtocolRequest request);

    /**
     * 修改协议模板
     *
     * @return
     */
    void updateProtocolTemplate(AdminProtocolRequest request);

    /**
     * 删除协议模板
     *
     */
    void deleteProtocolTemplate(AdminProtocolRequest request);
}
