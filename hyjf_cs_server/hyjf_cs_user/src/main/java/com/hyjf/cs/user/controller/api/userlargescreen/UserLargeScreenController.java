/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.userlargescreen;

import com.hyjf.cs.user.bean.AutoStateQueryResultBean;
import com.hyjf.cs.user.bean.UserLargeScreenBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tanyy
 * @version UserLargeScreenController, v0.1 2019/3/18 10:22
 */
@Api(value = "用户大屏接口", tags = "用户大屏接口")
@RestController
@RequestMapping("/hyjf-api/user/large_screen")
public class UserLargeScreenController {

    @ApiOperation(value = " 大屏幕一数据接口",notes = " 大屏幕一数据接口")
    @GetMapping(value = "/one")
    public UserLargeScreenBean oneInit() {
        UserLargeScreenBean resultBean = new UserLargeScreenBean();
        return resultBean;
    }
}
