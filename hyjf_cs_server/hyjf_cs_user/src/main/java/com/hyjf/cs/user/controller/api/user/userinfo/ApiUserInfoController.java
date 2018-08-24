/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.userinfo;

import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: sunpeikai
 * @version: ApiUserInfoController, v0.1 2018/8/24 10:09
 */
@Api(value = "api端-第三方用户信息查询",tags = "api端-第三方用户信息查询")
@RestController
@RequestMapping(value = "/api/user")
public class ApiUserInfoController extends BaseUserController {

}
