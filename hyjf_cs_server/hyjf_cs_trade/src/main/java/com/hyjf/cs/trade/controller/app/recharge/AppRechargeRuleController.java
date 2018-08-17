/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.app.recharge;

import com.hyjf.cs.trade.bean.app.AppRechargeDescResultBean;
import com.hyjf.cs.trade.service.recharge.AppRechargeRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version AppRechargeRuleController, v0.1 2018/7/25 14:49
 */
@Api(tags = "app端-获取充值规则")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-app/user/bank/recharge")
public class AppRechargeRuleController {

    @Autowired
    AppRechargeRuleService appRechargeRuleService;

    @ApiOperation(value = "app端获取充值规则", notes = "app端获取充值规则")
    @GetMapping(value = "/rechargeRule")
    public AppRechargeDescResultBean rechargeRule() {
        return appRechargeRuleService.getRechargeRule();
    }
}
