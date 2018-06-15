package com.hyjf.am.market.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.market.service.ActivityService;

/**
 * @author xiasq
 * @version ActivityController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/activity")
public class ActivityController {
    private static final Logger logger = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private ActivityService activityService;


}
