package com.hyjf.am.user.service.admin.margin;

import com.hyjf.am.response.admin.AdminSubConfigResponse;
import com.hyjf.am.response.user.UserInfoCustomizeResponse;
import com.hyjf.am.user.service.BaseService;

/**
 * @author by xiehuili on 2018/7/10.
 */
public interface SubConfigService extends BaseService{

    /**
     * 保证金配置，根据用户名称查询用户信息
     *
     * @param userName
     * @return
     */
    UserInfoCustomizeResponse selectUserInfoByUserName(String userName);

    /**
     * 根据用户名查询分账名单是否存在
     * @param username
     * @return
     */
    AdminSubConfigResponse selectByExampleUsername(String username);
}
