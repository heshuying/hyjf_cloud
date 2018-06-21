package com.hyjf.am.trade.controller;

import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.trade.service.AccountWithdrawService;
import com.hyjf.am.trade.service.SynBalanceService;
import com.hyjf.am.vo.trade.account.AccountVO;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pangchengchao
 * @version SynBalanceController, v0.1 2018/6/20 9:53
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Api(value = "江西银行提现掉单异常处理定时任务")
@RestController
@RequestMapping("/am-trade/synBalance")
public class SynBalanceController {
    @Autowired
    private SynBalanceService synBalanceService;
    /**
     * @Description
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @RequestMapping(value = "/insertAccountDetails")
    public boolean insertAccountDetails(@RequestBody SynBalanceBeanRequest synBalanceBeanRequest){
        return synBalanceService.insertAccountDetails(synBalanceBeanRequest);
    }
}