package com.hyjf.am.market.controller;

import com.hyjf.am.market.service.ActivityBatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version ActivityBatchController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/activity")
public class ActivityBatchController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityBatchController.class);

    @Autowired
    private ActivityBatchService activityBatchService;

    @RequestMapping("/batch/update")
    public void updateActivityEndStatus(){
        logger.info("批量更新到期活动...");
        activityBatchService.updateActivityEndStatus();
    }
}
