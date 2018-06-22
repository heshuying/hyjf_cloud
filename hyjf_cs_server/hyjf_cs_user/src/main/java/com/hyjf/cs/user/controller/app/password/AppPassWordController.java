/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.password;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangc
 */
@Api(value = "密码相关服务")
@RestController
@RequestMapping("/app/user/password")
public class AppPassWordController {
    private static final Logger logger = LoggerFactory.getLogger(AppPassWordController.class);


}