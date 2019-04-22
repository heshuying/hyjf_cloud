/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.web;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.service.WithdrawRuleConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现规则配置Controller
 *
 * @author liuyang
 * @version WithdrawRuleConfigController, v0.1 2019/4/19 16:34
 */
@RestController
@RequestMapping("/am-config/withdrawRuleConfig")
public class WithdrawRuleConfigController extends BaseConfigController {


    @Autowired
    private WithdrawRuleConfigService withdrawRuleConfigService;

    /**
     * 获取提现规则配置
     *
     * @param userType
     * @param withdrawmoney
     * @return
     */
    @GetMapping("/selectWithdrawRuleConfig/{userType}/{withdrawmoney}")
    public WithdrawRuleConfigResponse selectWithdrawRuleConfig(@PathVariable(value = "userType") Integer userType, @PathVariable(value = "withdrawmoney") String withdrawmoney) {
        WithdrawRuleConfigResponse response = new WithdrawRuleConfigResponse();
        WithdrawRuleConfig WithdrawRuleConfig = this.withdrawRuleConfigService.selectWithdrawRuleConfig(userType,withdrawmoney);

    }
}
