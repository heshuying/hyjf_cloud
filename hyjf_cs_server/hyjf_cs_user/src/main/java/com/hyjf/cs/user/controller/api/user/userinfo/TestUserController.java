/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.user.userinfo;

import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.userinfo.ApiUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author: sunpeikai
 * @version: ApiUserInfoController, v0.1 2018/8/24 10:09
 */
@RestController
@RequestMapping(value = "/hyjf-web/test")
public class TestUserController extends BaseUserController {

    @Autowired
    private ApiUserInfoService apiUserInfoService;

    @PostMapping(value = "/sleep")
    public Object test(){
       int seconds =  new Random().nextInt(300) + 400;
        try {
            Thread.sleep(seconds);
            logger.info(">>>>>>>>>>>>>>>>>>>" + seconds  + "<<<<<<<<<<<<<<");
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        return "OK";
    }


}
