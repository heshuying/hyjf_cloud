/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.web;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.WithdrawRuleConfig;
import com.hyjf.am.config.service.WithdrawRuleConfigService;
import com.hyjf.am.response.config.WithdrawRuleConfigResponse;
import com.hyjf.am.resquest.admin.CertLogRequestBean;
import com.hyjf.am.resquest.config.WithdrawRuleConfigRequest;
import com.hyjf.am.vo.config.WithdrawRuleConfigVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
     * @param request
     * @return
     */
    @PostMapping("/selectWithdrawRuleConfig")
    public WithdrawRuleConfigResponse selectWithdrawRuleConfig(@RequestBody @Valid WithdrawRuleConfigRequest request) {
        WithdrawRuleConfigResponse response = new WithdrawRuleConfigResponse();
        WithdrawRuleConfig withdrawRuleConfig = this.withdrawRuleConfigService.selectWithdrawRuleConfig(request);
        if (withdrawRuleConfig != null) {
            WithdrawRuleConfigVO withdrawRuleConfigVO = new WithdrawRuleConfigVO();
            BeanUtils.copyProperties(withdrawRuleConfig, withdrawRuleConfigVO);
            response.setResult(withdrawRuleConfigVO);
        }
        return response;
    }
}
