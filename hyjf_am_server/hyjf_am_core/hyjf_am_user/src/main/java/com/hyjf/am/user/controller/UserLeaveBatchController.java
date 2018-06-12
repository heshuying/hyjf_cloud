package com.hyjf.am.user.controller;

import com.hyjf.am.user.service.UserLeaveBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version UserLeaveBatchController, v0.1 2018/6/12 14:58
 */

@RestController
@RequestMapping("/userBatch")
public class UserLeaveBatchController {
    private static final Logger logger = LoggerFactory.getLogger(UserLeaveBatchController.class);

    @Autowired
    private UserLeaveBatchService userLeaveBatchService;

    @RequestMapping("/leave/update")
    public void leaveUpdate(){
        logger.info("员工离职修改客户属性...");
        userLeaveBatchService.updateUserLeaveInfo();
    }
}
