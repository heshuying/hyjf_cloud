/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.safe;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.safe.SafeService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangqingqing
 * @version AppUserController, v0.1 2018/6/11 14:51
 */

@Api(value = "app端用户接口",description = "app端用户接口")
@RestController
@RequestMapping("/app/appUser")
public class AppSafeController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppSafeController.class);

    @Autowired
    private SafeService safeService;


}
