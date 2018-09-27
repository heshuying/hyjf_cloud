package com.hyjf.am.config.service.locked;

import com.hyjf.am.bean.admin.LockedConfig;

/**
 * @author cui
 * @version LockedConfigService, v0.1 2018/9/27 11:11
 */
public interface LockedConfigService {

    void saveWebConfig(LockedConfig.Config webConfig);

    void saveAdminConfig(LockedConfig.Config adminConfig);

}
