/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.activity;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yaoyong
 * @version CalInvestAmountController, v0.1 2019/4/17 16:51
 */
@RestController
@RequestMapping("/am-trade/investAmount")
public class CalInvestAmountController extends BaseController {

    @Autowired
    private BorrowTenderService borrowTenderService;

    @GetMapping("/sum/{startDate}/{endDate}")
    public BigDecimalResponse sumInvestAmount(@PathVariable Date startDate, @PathVariable Date endDate) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal investAmount = borrowTenderService.getInvestAmountByPeriod(startDate, endDate);
        if (investAmount != null) {
            response.setResultDec(investAmount);
        } else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }


    @GetMapping("/annual/{userId}/{startDate}/{endDate}")
    public BigDecimalResponse sumAnnualInvestAmount(@PathVariable Integer userId, @PathVariable Date startDate, @PathVariable Date endDate) {
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal annualInvestAmount = borrowTenderService.getAnnualInvestAmount(userId, startDate, endDate);
        if (annualInvestAmount != null) {
            response.setResultDec(annualInvestAmount);
        } else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }
}
