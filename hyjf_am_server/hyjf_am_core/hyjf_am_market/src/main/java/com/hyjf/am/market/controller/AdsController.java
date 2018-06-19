package com.hyjf.am.market.controller;

import com.hyjf.am.response.market.AdsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.market.service.AdsService;

/**
 * @author xiasq
 * @version ActivityController, v0.1 2018/4/19 15:38
 */

@RestController
@RequestMapping("/ads")
public class AdsController {
    private static final Logger logger = LoggerFactory.getLogger(AdsController.class);

    @Autowired
    private AdsService adsService;


    @RequestMapping("/findAdsById/{activityId}")
    public AdsResponse findActivityById(@PathVariable Integer activityId){

        return null;
    }

    /**
     * batch 批量检测到期活动
     */
    @RequestMapping("/batch/update")
    public void updateActivityEndStatus(){
        logger.info("批量更新到期活动...");
        adsService.updateActivityEndStatus();
    }
}
