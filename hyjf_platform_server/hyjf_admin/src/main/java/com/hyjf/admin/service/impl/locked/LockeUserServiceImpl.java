/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.locked;

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.service.locked.LockedUserService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.locked.LockedUserMgrResponse;
import com.hyjf.am.resquest.admin.locked.LockedeUserListRequest;
import com.hyjf.am.vo.admin.locked.LockedUserInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cui
 * @version LockeUserServiceImpl, v0.1 2018/9/25 11:41
 */
@Service
public class LockeUserServiceImpl implements LockedUserService {

    @Autowired
    private AmAdminClient amAdminClient;


    @Override
    public LockedUserMgrResponse getLockedUserList(LockedeUserListRequest request, boolean isFront) {

        return amAdminClient.getLockedUserList(request,isFront);
    }

    @Override
    public Response<?> unlock(LockedUserInfoVO vo, boolean isFront) {

        BooleanResponse response=amAdminClient.unlock(vo,isFront);

        return response.getResultBoolean()? new Response<>():new Response<>(Response.FAIL,Response.ERROR_MSG);

    }


}
