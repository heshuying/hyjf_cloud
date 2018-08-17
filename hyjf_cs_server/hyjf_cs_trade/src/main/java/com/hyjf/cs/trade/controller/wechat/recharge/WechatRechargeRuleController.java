/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.wechat.recharge;

import com.hyjf.cs.trade.bean.WxRechargeDescResultBean;
import com.hyjf.cs.trade.service.recharge.WechatRechargeRuleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version WechatRechargeRuleController, v0.1 2018/7/26 9:23
 */
@Api(tags = "wechat端-获取充值规则")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/hyjf-wechat/wx/recharge")
public class WechatRechargeRuleController {
    @Autowired
    WechatRechargeRuleService wechatRechargeRuleService;

    @ApiOperation(value = "wechat端获取充值规则", notes = "wechat端获取充值规则")
    @PostMapping(value = "/rechargeRule")
    public WxRechargeDescResultBean rechargeRule() {
        return wechatRechargeRuleService.getRechargeRule();
    }
}
