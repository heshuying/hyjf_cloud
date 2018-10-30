package com.hyjf.am.user.service.admin.locked;

import com.hyjf.am.bean.admin.LockedConfig;

import java.util.Map;

/**
 * @author cui
 * @version LockedConfigService, v0.1 2018/9/27 11:11
 */
public interface LockedConfigService {

    void saveWebConfig(LockedConfig.Config webConfig);

    void saveAdminConfig(LockedConfig.Config adminConfig);

    /**
     *
     * @param userName 用户名，手机号
     * @param loginPassword 登录密码
     * @return
     */
    public Map<String, String> insertErrorPassword(String userName, String loginPassword);



}
