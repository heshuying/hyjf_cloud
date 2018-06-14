package com.hyjf.am.user.controller.batch;

import com.hyjf.am.user.service.batch.UserEntryBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version UserEntryBatchController, v0.1 2018/6/12 14:58
 */

@RestController
@RequestMapping("/userBatch")
public class UserEntryBatchController {
    private static final Logger logger = LoggerFactory.getLogger(UserEntryBatchController.class);

    @Autowired
    private UserEntryBatchService userEntryBatchService;

    @RequestMapping("/entry/update")
    public void entryUpdate(){
        logger.info("员工入职修改客户属性...");
        userEntryBatchService.updateUserEntryInfo();
    }
}
