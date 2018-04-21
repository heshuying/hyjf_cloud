package com.hyjf.cs.user.controller.user;

import com.hyjf.cs.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiasq
 * @version RegisterController, v0.1 2018/4/21 15:06
 */
public class RegisterController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Autowired
    private UserService userService;
}
