/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.activity;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author yaoyong
 * @version CalInvestAmountController, v0.1 2019/4/17 16:51
 */
@RestController
@RequestMapping("/am-trade/activity")
public class CalInvestAmountController extends BaseController {

    @Autowired
    private BorrowTenderService borrowTenderService;

    @RequestMapping("/invest_amount")
    public BigDecimalResponse sumInvestAmount() {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal investAmount = borrowTenderService.getInvestAmountByPeriod();
        if (investAmount != null) {
            response.setResultDec(investAmount);
        }else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }


    @RequestMapping("/annual_invest_amount")
    public BigDecimalResponse sumAnnualInvestAmount(@PathVariable Integer userId) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal annualInvestAmount = borrowTenderService.getAnnualInvestAmount(userId);
        if (annualInvestAmount != null) {
            response.setResultDec(annualInvestAmount);
        }else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }
}
