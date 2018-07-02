package com.hyjf.am.user.controller.batch;

import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.batch.UserLeaveBatchService;
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
@RequestMapping("/userBatch/leave")
public class UserLeaveBatchController extends BaseController {

    @Autowired
    private UserLeaveBatchService userLeaveBatchService;

    @RequestMapping("/update")
    public void leaveUpdate(){
        logger.info("员工离职修改客户属性...");
        userLeaveBatchService.updateUserLeaveInfo();
    }
}
