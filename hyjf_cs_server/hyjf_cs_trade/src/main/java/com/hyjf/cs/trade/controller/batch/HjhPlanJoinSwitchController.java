/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.service.batch.HjhPlanJoinSwitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version HjhPlanJoinSwitchController, v0.1 2018/12/18 15:34
 * 汇计划开关
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/hjhPlanSwitch/batch")
public class HjhPlanJoinSwitchController {

    @Autowired
    private HjhPlanJoinSwitchService hjhPlanJoinSwitchService;

    @RequestMapping("/hjhPlanJoinOff")
    public BooleanResponse hjhPlanJoinOff() {
        BooleanResponse response = new BooleanResponse();
        response = hjhPlanJoinSwitchService.hjhPlanJoinOff();
        return response;
    }

    @RequestMapping("/hjhPlanJoinOn")
    public BooleanResponse hjhPlanJoinOn() {
        BooleanResponse response = new BooleanResponse();
        response = hjhPlanJoinSwitchService.hjhPlanJoinOn();
        return response;
    }
}
