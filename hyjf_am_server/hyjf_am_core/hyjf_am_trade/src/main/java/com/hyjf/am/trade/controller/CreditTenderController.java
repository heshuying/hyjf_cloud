/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller;

import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.customize.trade.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.trade.TenderToCreditDetailCustomize;
import com.hyjf.am.trade.service.BankCreditTenderService;
import com.hyjf.am.vo.trade.*;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    /**
     * 查询我可转让列表数量
     * @param request
     * @return
     */
    @PostMapping("/countMyCreditList")
    public MyCreditListQueryResponse searchCreditListCount(@RequestBody @Valid MyCreditListQueryRequest request){
        MyCreditListQueryResponse response = new MyCreditListQueryResponse();
        Integer count = bankCreditTenderService.searchCreditListCount(request);
        response.setCount(count);
        return response;
    }


    /**
     * 查询我可转让列表
     * @param request
     * @return
     */
    @PostMapping("/seachMyCreditList")
    public MyCreditListQueryResponse searchCreditList(@RequestBody @Valid MyCreditListQueryRequest request){
        MyCreditListQueryResponse response = new MyCreditListQueryResponse();
        List<TenderCreditCustomize> list = bankCreditTenderService.searchCreditList(request);
        if (CollectionUtils.isNotEmpty(list)){
            response.setResultList(CommonUtils.convertBeanList(list,TenderCreditCustomizeVO.class));
        }
        return response;
    }

    /**
     * 查询债转详情
     *
     * @return
     */
    @GetMapping("/selectTenderToCreditDetail/{userId}/{borrowNid}/{tenderNid}")
    public MyCreditListQueryResponse selectTenderToCreditDetail(@PathVariable Integer userId, @PathVariable String borrowNid, @PathVariable String tenderNid) {
        MyCreditListQueryResponse response = new MyCreditListQueryResponse();
        TenderCreditCustomize bean = bankCreditTenderService.selectTenderToCreditDetail(userId, borrowNid, tenderNid);
        if (bean != null) {
            response.setResult(CommonUtils.convertBean(bean, TenderCreditCustomizeVO.class));
        }
        return response;
    }

    /**
     * 债转详细预计服务费计算
     *
     * @return
     */
    @GetMapping("/selectExpectCreditFee/{borrowNid}/{tenderNid}")
    public ExpectCreditFeeResponse selectExpectCreditFee(@PathVariable String borrowNid, @PathVariable String tenderNid) {
        ExpectCreditFeeResponse response = new ExpectCreditFeeResponse();
        ExpectCreditFeeVO bean = bankCreditTenderService.selectExpectCreditFee(borrowNid, tenderNid);
        if (bean != null) {
            response.setResult(bean);
        }
        return response;
    }

    /**
     * 投资人当天是否可以债转
     * @param userId
     * @return
     */
    @GetMapping("/get_tender_credit_count/{userId}")
    public Integer tenderAbleToCredit(@PathVariable Integer userId) {
        Integer result = bankCreditTenderService.tenderAbleToCredit(userId);
        return result;
    }

    /**
     * 获取债转还款列表
     * @param tenderNid
     * @return
     */
    @GetMapping("/select_credit_repay_list/{tenderNid}")
    public CreditRepayResponse selectCreditRepayList( @PathVariable String tenderNid) {
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> list = bankCreditTenderService.selectCreditRepayList(tenderNid);
        if (list != null) {
            response.setResultList(CommonUtils.convertBeanList(list, CreditRepayVO.class));
        }
        return response;
    }

    /**
     * 保存债转的数据
     * @param request
     * @return
     */
    @PostMapping("/save_credit_tender")
    public Integer saveCreditTender(@RequestBody @Valid BorrowCreditVO request){
        try{
            bankCreditTenderService.saveCreditTender(request);
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

}
