/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.CreaditPageResponse;
import com.hyjf.am.response.trade.CreditTenderResponse;
import com.hyjf.am.response.trade.TenderToCreditDetailCustomizeResponse;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.customize.trade.TenderToCreditDetailCustomize;
import com.hyjf.am.trade.service.BankCreditTenderService;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author jun 20180621
 */
@Api(value = "债转投资")
@RestController
@RequestMapping("/am-trade/creditTender")
public class CreditTenderController {

    private static Logger logger = LoggerFactory.getLogger(CreditTenderController.class);

    @Autowired
    BankCreditTenderService bankCreditTenderService;


    @GetMapping("/selectByAssignNidAndUserId/{assignNid}/{userId}")
    public CreditTenderResponse selectByAssignNidAndUserId(@PathVariable String assignNid,@PathVariable Integer userId) {
        CreditTenderResponse response = new CreditTenderResponse();
        CreditTender creditTender = bankCreditTenderService.selectByAssignNidAndUserId(assignNid,userId);
        if (creditTender != null) {
            response.setResult(CommonUtils.convertBean(creditTender,CreditTenderVO.class));
        }
        return response;
    }

    
    
    

    @PostMapping("/updateTenderCreditInfo")
    public boolean updateTenderCreditInfo(@RequestBody CreditTenderRequest request){
        boolean ret;
        try {
             ret = bankCreditTenderService.updateTenderCreditInfo(request);
           }catch (Exception e){
            logger.info("updateTenderCreditInfo..."+e);
            ret = false;
           }
            return ret;
    }


    @PostMapping("/getCreditTenderList")
    public CreditTenderResponse getCreditTenderList(@RequestBody CreditTenderRequest request){
        CreditTenderResponse response = new CreditTenderResponse();
        List<CreditTender> creditTenders= bankCreditTenderService.getCreditTenderList(request);
        if (CollectionUtils.isNotEmpty(creditTenders)){
            response.setResultList(CommonUtils.convertBeanList(creditTenders,CreditTenderVO.class));
        }
        return response;
    }
    
    
    @PostMapping("/selectWebCreditTenderDetailForContract")
    public TenderToCreditDetailCustomizeResponse selectWebCreditTenderDetailForContract(@RequestBody Map<String, Object> params) {
    	TenderToCreditDetailCustomizeResponse response = new TenderToCreditDetailCustomizeResponse(); 
    	List<TenderToCreditDetailCustomize> tenderToCreditDetailList =bankCreditTenderService.selectWebCreditTenderDetailForContract(params);
    	if (CollectionUtils.isNotEmpty(tenderToCreditDetailList)){
            response.setResultList(CommonUtils.convertBeanList(tenderToCreditDetailList,TenderToCreditDetailCustomizeVO.class));
        }
    	return response;
    }

    @PostMapping("/selectHJHWebCreditTenderDetail")
    public TenderToCreditDetailCustomizeResponse selectHJHWebCreditTenderDetail(@RequestBody Map<String, Object> params){
        TenderToCreditDetailCustomizeResponse response = new TenderToCreditDetailCustomizeResponse();
        List<TenderToCreditDetailCustomize> tenderToCreditDetailList =bankCreditTenderService.selectHJHWebCreditTenderDetail(params);
        if (CollectionUtils.isNotEmpty(tenderToCreditDetailList)){
            response.setResultList(CommonUtils.convertBeanList(tenderToCreditDetailList,TenderToCreditDetailCustomizeVO.class));
        }
        return response;
    }

    /**
     * 获取我要转让页面的金额
     * @param userId
     * @return
     */
    @GetMapping("/select_credit_page_money_total/{userId}")
    public CreaditPageResponse selectCreditPageMoneyTotal(@PathVariable Integer userId) {
        CreaditPageResponse response = new CreaditPageResponse();

        CreditPageVO page = bankCreditTenderService.selectCreditPageMoneyTotal(userId);
        if (page != null) {
            response.setResult(page);
        }
        return response;
    }


}
