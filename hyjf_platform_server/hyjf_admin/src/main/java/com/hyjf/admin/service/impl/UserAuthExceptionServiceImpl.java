/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.UserauthClient;
import com.hyjf.admin.service.UserAuthExceptionService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.user.AdminUserAuthListResponse;
import com.hyjf.am.resquest.user.AdminUserAuthListRequest;
import com.hyjf.am.vo.user.AdminUserAuthListVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: sunpeikai
 * @version: UserAuthExceptionServiceImpl, v0.1 2018/7/2 10:34
 * 自动投资债转授权异常
 */
@Service
public class UserAuthExceptionServiceImpl implements UserAuthExceptionService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserauthClient userauthClient;

    @Override
    public Integer getRecordCount(AdminUserAuthListRequest request) {
        userauthClient.userauthlist(request);
        return 0;
    }

    @Override
    public AdminUserAuthListResponse selectUserAuthList(AdminUserAuthListRequest request) {
        AdminUserAuthListResponse response = userauthClient.userauthlist(request);
        return response;
    }
}
