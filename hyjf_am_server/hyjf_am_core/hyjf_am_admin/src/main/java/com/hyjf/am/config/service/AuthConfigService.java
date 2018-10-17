/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigCustomize;
import com.hyjf.am.config.dao.model.customize.HjhUserAuthConfigLogCustomize;
import com.hyjf.am.vo.admin.HjhUserAuthConfigLogCustomizeVO;

import java.util.List;

/**
 * 授权配置
 * @author jun
 * @version AuthConfigService, v0.1 2018/10/16 11:46
 */
public interface AuthConfigService {
    /**
     * 授权配置总条数
     * @return
     */
    int getAuthConfigCount();

    /**
     * 授权配置列表
     */
    List<HjhUserAuthConfigCustomize> getAuthConfigList();

    /**
     * 操作记录总条数
     * @return
     */
    int getAuthConfigLogCount();

    /**
     * 操作记录列表
     * @param request
     * @return
     */
    List<HjhUserAuthConfigLogCustomize> getAuthConfigLogList(HjhUserAuthConfigLogCustomizeVO request);
}
