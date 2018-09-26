/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.locked;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;

/**
 * @author cui
 * @version LockedUserService, v0.1 2018/9/25 11:34
 */
public interface LockedUserService {

    LockedUserMgrResponse getLockedUserList(LockedeUserListRequest request, boolean isFront);

    Response<?> unlock(LockedUserInfoVO vo, boolean isFront);

}
