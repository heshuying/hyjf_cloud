/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.WebPandectBorrowRecoverCustomizeResponse;
import com.hyjf.am.response.trade.WebPandectCreditTenderCustomizeResponse;
import com.hyjf.am.response.trade.WebPandectRecoverMoneyCustomizeResponse;
import com.hyjf.am.response.trade.WebPandectWaitMoneyCustomizeResponse;
import com.hyjf.am.trade.dao.model.customize.WebPandectBorrowRecoverCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectCreditTenderCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectRecoverMoneyCustomize;
import com.hyjf.am.trade.dao.model.customize.WebPandectWaitMoneyCustomize;
import com.hyjf.am.trade.service.front.trade.WebPandectService;
import com.hyjf.am.vo.trade.WebPandectBorrowRecoverCustomizeVO;
import com.hyjf.am.vo.trade.WebPandectCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.WebPandectRecoverMoneyCustomizeVO;
import com.hyjf.am.vo.trade.WebPandectWaitMoneyCustomizeVO;
import com.hyjf.common.validator.Validator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author zhangqingqing
 * @version WebPandectController, v0.1 2018/7/23 11:37
 */
@RestController
@RequestMapping("am-trade/webPandect")
public class WebPandectController {

    @Autowired
    WebPandectService webPandectService;

    @GetMapping("/queryRecoverMoney/{userId}")
    public WebPandectRecoverMoneyCustomizeResponse queryRecoverMoney(@PathVariable Integer userId){
        WebPandectRecoverMoneyCustomizeResponse response = new WebPandectRecoverMoneyCustomizeResponse();
        WebPandectRecoverMoneyCustomize webPandect = webPandectService.queryRecoverMoney(userId);
        WebPandectRecoverMoneyCustomizeVO webPandectVO = new WebPandectRecoverMoneyCustomizeVO();
        if (Validator.isNotNull(webPandect)){
            BeanUtils.copyProperties(webPandect,webPandectVO);
            response.setResult(webPandectVO);
        }
        return response;
    }

    @GetMapping("/queryRecoverMoneyForRtb/{userId}")
    public WebPandectRecoverMoneyCustomizeResponse queryRecoverMoneyForRtb(@PathVariable Integer userId) {
        WebPandectRecoverMoneyCustomizeResponse response = new WebPandectRecoverMoneyCustomizeResponse();
        WebPandectRecoverMoneyCustomize webPandect = webPandectService.queryRecoverMoneyForRtb(userId);
        WebPandectRecoverMoneyCustomizeVO webPandectVO = new WebPandectRecoverMoneyCustomizeVO();
        if (Validator.isNotNull(webPandect)){
            BeanUtils.copyProperties(webPandect,webPandectVO);
            response.setResult(webPandectVO);
        }
        return response;
    }

    @GetMapping("/queryWaitMoney/{userId}")
    public WebPandectWaitMoneyCustomizeResponse queryWaitMoney(@PathVariable Integer userId) {
        WebPandectWaitMoneyCustomizeResponse response = new WebPandectWaitMoneyCustomizeResponse();
        WebPandectWaitMoneyCustomize wait = webPandectService.queryWaitMoney(userId);
        WebPandectWaitMoneyCustomizeVO waitVO = new WebPandectWaitMoneyCustomizeVO();
        if (null!=wait){
            BeanUtils.copyProperties(wait,waitVO);
            response.setResult(waitVO);
        }
        return response;
    }

    @GetMapping("/queryWaitMoneyForRtb/{userId}")
    public WebPandectWaitMoneyCustomizeResponse queryWaitMoneyForRtb(@PathVariable Integer userId) {
        WebPandectWaitMoneyCustomizeResponse response = new WebPandectWaitMoneyCustomizeResponse();
        WebPandectWaitMoneyCustomize wait = webPandectService.queryWaitMoneyForRtb(userId);
        WebPandectWaitMoneyCustomizeVO waitVO = new WebPandectWaitMoneyCustomizeVO();
        if (null!=wait){
            BeanUtils.copyProperties(wait,waitVO);
            response.setResult(waitVO);
        }
        return response;
    }

    @GetMapping("/queryHtlSumRestAmount/{userId}")
    public BigDecimal queryHtlSumRestAmount(@PathVariable Integer userId) {
        BigDecimal sum = webPandectService.queryHtlSumRestAmount(userId);
        return sum;
    }

    @GetMapping("/queryCreditInfo/{userId}")
    public WebPandectCreditTenderCustomizeResponse queryCreditInfo(@PathVariable Integer userId) {
        WebPandectCreditTenderCustomizeResponse response = new WebPandectCreditTenderCustomizeResponse();
        WebPandectCreditTenderCustomize creditTender = webPandectService.queryCreditInfo(userId);
        WebPandectCreditTenderCustomizeVO creditTenderVO = new WebPandectCreditTenderCustomizeVO();
        if (null!=creditTender){
            BeanUtils.copyProperties(creditTender,creditTenderVO);
            response.setResult(creditTenderVO);
        }
        return response;
    }

    @GetMapping("/queryRecoverInfo/{userId}/{recoverStatus}")
    public WebPandectBorrowRecoverCustomizeResponse queryRecoverInfo(@PathVariable Integer userId, @PathVariable Integer recoverStatus) {
        WebPandectBorrowRecoverCustomizeResponse response = new WebPandectBorrowRecoverCustomizeResponse();
        WebPandectBorrowRecoverCustomize result = webPandectService.queryRecoverInfo(userId,recoverStatus);
        if (result!=null){
            WebPandectBorrowRecoverCustomizeVO resultVO = new WebPandectBorrowRecoverCustomizeVO();
            BeanUtils.copyProperties(result,resultVO);
            response.setResult(resultVO);
        }
        return response;
    }

    @GetMapping("/queryHtlSumInterest/{userId}")
    public BigDecimal queryHtlSumInterest(@PathVariable Integer userId) {
        BigDecimal sum = webPandectService.queryHtlSumInterest(userId);
        return sum;
    }

    @GetMapping("/selectUserTenderCount/{userId}")
    public int selectUserTenderCount(@PathVariable Integer userId){
        int count = webPandectService.selectUserTenderCount(userId);
        return count;
    }
}
