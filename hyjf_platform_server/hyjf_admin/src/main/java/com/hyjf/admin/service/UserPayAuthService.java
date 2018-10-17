/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.user.UserPayAuthResponse;
import com.hyjf.am.resquest.user.*;
import java.util.List;

/**
 * @author nxl
 * @version UserCenterService, v0.1 2018/6/20 15:34
 */
public interface UserPayAuthService {

    /**
     * 查找用户信息
     *
     * @param request
     * @return
     */
    UserPayAuthResponse selectUserMemberList(UserPayAuthRequest request);
}
