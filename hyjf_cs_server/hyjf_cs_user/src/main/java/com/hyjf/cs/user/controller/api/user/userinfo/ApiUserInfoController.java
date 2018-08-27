/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.userinfo;

import com.alibaba.fastjson.JSON;
import com.hyjf.cs.user.bean.SyncUserInfoRequestBean;
import com.hyjf.cs.user.bean.SyncUserInfoResultBean;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.userinfo.ApiUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: ApiUserInfoController, v0.1 2018/8/24 10:09
 */
@Api(value = "api端-第三方用户信息查询",tags = "api端-第三方用户信息查询")
@RestController
@RequestMapping(value = "/server/user")
public class ApiUserInfoController extends BaseUserController {

    @Autowired
    private ApiUserInfoService apiUserInfoService;

    @ApiOperation(value = "用户信息查询",notes = "用户信息查询")
    @PostMapping(value = "/syncUserInfo")
    public SyncUserInfoResultBean syncUserInfo(@RequestBody SyncUserInfoRequestBean requestBean){
        logger.info(JSON.toJSONString(requestBean));
        return apiUserInfoService.syncUserInfo(requestBean);
    }

}
