package com.hyjf.admin.service;

import com.hyjf.admin.beans.vo.HjhUserAuthConfigCustomizeAPIVO;
import com.hyjf.am.response.admin.AdminAuthConfigLogResponse;
import com.hyjf.am.response.admin.AdminAuthConfigCustomizeResponse;
import com.hyjf.am.response.admin.AdminAuthConfigResponse;
import com.hyjf.am.vo.admin.HjhUserAuthConfigCustomizeVO;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthConfigVO;

import javax.servlet.http.HttpServletRequest;

/**
 * 授權配置接口
 * jijun
 */
public interface AuthConfigService {

    /**
     * 獲取授權配置
     * @return
     */
    AdminAuthConfigCustomizeResponse getAuthConfigList();

    /**
     * 操作记录
     * @return
     */
    AdminAuthConfigLogResponse getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request);

    /**
     * 授权配置详情
     * @param id
     * @return
     */
    AdminAuthConfigResponse getAuthConfigById(Integer id);

    /**
     * 修改授权配置
     * @param form
     * @param request
     * @return
     */
    int updateAuthConfig(HjhUserAuthConfigVO form);
}
