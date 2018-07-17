/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.AccountRechargeResponse;
import com.hyjf.am.trade.service.AccountRecharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author fuqiang
 * @version AccountRechargeController, v0.1 2018/7/17 11:00
 */
@RestController
@RequestMapping("am-trade/accountrecharge")
public class AccountRechargeController extends BaseController {
    @Autowired
    private AccountRecharge accountRecharge;

    /**
     * 获取充值金额
     * @param list 用户id集合
     * @return
     */
    @RequestMapping("/getrechargeprice")
    public AccountRechargeResponse getRechargePrice(@RequestBody List<Integer> list) {
        AccountRechargeResponse response = new AccountRechargeResponse();
        BigDecimal rechargePrice = accountRecharge.getRechargePrice(list);
        if (rechargePrice != null) {
            response.setRechargePrice(rechargePrice);
        }
        return response;
    }
}
