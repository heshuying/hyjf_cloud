/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.activity;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.trade.UserTenderResponse;
import com.hyjf.am.resquest.trade.UserTenderRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.borrow.BorrowTenderService;
import com.hyjf.am.resquest.trade.SumTenderAmountRequest;
import com.hyjf.am.vo.activity.UserTenderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author yaoyong
 * @version CalInvestAmountController, v0.1 2019/4/17 16:51
 */
@RestController
@RequestMapping("/am-trade/investAmount")
public class CalInvestAmountController extends BaseController {

    @Autowired
    private BorrowTenderService borrowTenderService;

    @PostMapping("/sum")
    public BigDecimalResponse sumInvestAmount(@RequestBody UserTenderRequest request) {
        logger.info("sumInvestAmount run, request is: {}", request);
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal investAmount = borrowTenderService.getInvestAmountByPeriod(request.getStartDate(), request.getEndDate());
        if (investAmount != null) {
            response.setResultDec(investAmount);
        } else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }


    @PostMapping("/annual")
    public BigDecimalResponse sumAnnualInvestAmount(@RequestBody UserTenderRequest request) {
        logger.info("annual run, request is: {}", request);
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal annualInvestAmount = borrowTenderService.getAnnualInvestAmount(request.getUserId(), request.getStartDate(), request.getEndDate());
        logger.debug("annualInvestAmount: {}", annualInvestAmount);
        if (annualInvestAmount != null) {
            response.setResultDec(annualInvestAmount);
        } else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }


    @PostMapping("/")
    public BigDecimalResponse getTenderAmount(@RequestBody SumTenderAmountRequest request) {
        logger.info("getTenderAmount run, request is: {}", request);
        BigDecimalResponse response = new BigDecimalResponse();
        BigDecimal userInvestAmount = borrowTenderService.getUserInvestAmount(request.getUserId(), request.getStartDate(), request.getEndDate(), request.getClient());
        logger.debug("userInvestAmount: {}", userInvestAmount);

        if (userInvestAmount != null) {
            response.setResultDec(userInvestAmount);
        } else {
            response.setResultDec(BigDecimal.ZERO);
        }
        return response;
    }

    @PostMapping("/top5")
    public UserTenderResponse sumAnnualInvestAmountTop5(@RequestBody UserTenderRequest request) {
        logger.info("sumAnnualInvestAmountTop5 run, request is: {}", request);
        UserTenderResponse response = new UserTenderResponse();
        List<UserTenderVO> vos = borrowTenderService.getSumAnnualInvestAmountTop5(request.getStartDate(),request.getEndDate());
        response.setResultList(vos);
        return response;
    }
}
