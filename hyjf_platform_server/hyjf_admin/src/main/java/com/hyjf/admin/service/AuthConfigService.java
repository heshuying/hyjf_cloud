package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;

/**
 * 授權配置接口
 * jijun
 */
public interface AuthConfigService {

    /**
     * 獲取授權配置
     * @return
     */
    AdminAuthConfigResponse getAuthConfigList();

    /**
     * 操作记录
     * @return
     */
    AdminAuthConfigLogResponse getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request);
}
