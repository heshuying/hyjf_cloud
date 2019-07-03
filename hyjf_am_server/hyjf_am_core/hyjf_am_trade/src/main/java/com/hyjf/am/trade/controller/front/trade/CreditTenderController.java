/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.CreditRepay;
import com.hyjf.am.trade.dao.model.auto.CreditTender;
import com.hyjf.am.trade.dao.model.customize.TenderCreditCustomize;
import com.hyjf.am.trade.dao.model.customize.TenderToCreditDetailCustomize;
import com.hyjf.am.trade.service.front.account.BankCreditTenderService;
import com.hyjf.am.vo.trade.*;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.FormatRateUtil;

import io.swagger.annotations.Api;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jun 20180621
 */
@Api(value = "债转出借")
@RestController
@RequestMapping("/am-trade/creditTender")
public class CreditTenderController extends BaseController {

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
        	List<TenderCreditCustomize> list2=new ArrayList<>();
            //平台所有利率（参考年回报率，历史年回报率，折让率，加息利率）全部统一为：
            // 小数点后一位（除非后台配置为小数点后两位且不为0时，则展示小数点后两位）；
            for (TenderCreditCustomize tenderCreditCustomize : list) {
            	tenderCreditCustomize.setBorrowApr(FormatRateUtil.formatBorrowApr(tenderCreditCustomize.getBorrowApr()));
            	list2.add(tenderCreditCustomize);
			}
            response.setResultList(CommonUtils.convertBeanList(list2,TenderCreditCustomizeVO.class));
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
        bean.setBorrowApr(FormatRateUtil.formatBorrowApr(bean.getBorrowApr()));
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
     * 出借人当天是否可以债转
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
     * 获取assignNId查询债转还款列表
     * @param assignNid
     * @return
     */
    @GetMapping("/select_credit_repay_list_by_assignNid/{assignNid}")
    public CreditRepayResponse selectCreditRepayListByAssignNid( @PathVariable String assignNid) {
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> list = bankCreditTenderService.selectCreditRepayListByAssignNid(assignNid);
        if (list != null) {
            response.setResultList(CommonUtils.convertBeanList(list, CreditRepayVO.class));
        }
        return response;
    }

    /**
     * 获取债转还款列表
     * @param tenderNid
     * @return
     */
    @GetMapping("/select_credit_repay_list/{borrowNid}/{tenderNid}/{periodNow}/{status}")
    public CreditRepayResponse selectCreditRepayList(@PathVariable String borrowNid, @PathVariable String tenderNid, @PathVariable Integer periodNow, @PathVariable Integer status) {
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> list = bankCreditTenderService.selectCreditRepayList(borrowNid,tenderNid,periodNow,status);
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
    public IntegerResponse saveCreditTender(@RequestBody @Valid BorrowCreditVO request){
        IntegerResponse response = new IntegerResponse();

        Integer creditNid =  bankCreditTenderService.saveCreditTender(request);
        response.setResultInt(creditNid);
        return response;

    }

    /**
     * 前端Web页面出借可债转输入出借金额后收益提示 用户未登录 (包含查询条件)
     * @param creditNid
     * @param assignCapital
     * @param userId
     * @return
     */
    @GetMapping("/get_interest_info/{creditNid}/{assignCapital}/{userId}")
    public CreditAssignCustomizeResponse getInterestInfo( @PathVariable String creditNid,@PathVariable String assignCapital,@PathVariable Integer userId) {
        CreditAssignCustomizeResponse response = new CreditAssignCustomizeResponse();
        TenderToCreditAssignCustomizeVO bean = bankCreditTenderService.getInterestInfo(creditNid,assignCapital,userId);
        response.setResult(bean);
        return response;
    }

    /**
     * 根据creditNid 查询债转信息
     * @param creditNid
     * @return
     */
    @GetMapping("/get_borrow_credit_by_credit_nid/{creditNid}")
    public BorrowCreditResponse getBorrowCreditByCreditNid( @PathVariable String creditNid) {
        BorrowCreditResponse response = new BorrowCreditResponse();
        BorrowCreditVO bean = bankCreditTenderService.getBorrowCreditByCreditNid(creditNid);
        response.setResult(bean);
        return response;
    }

    /**
     * 保存债转异步数据
     * @param request
     * @return
     */
    @PostMapping("/saveCreditBgData")
    public IntegerResponse saveCreditBgData(@RequestBody @Valid CreditTenderBgVO request){
        IntegerResponse response = new IntegerResponse();
        try{
            bankCreditTenderService.insertCreditBgData(request);
            response.setResultInt(1);
        }catch (Exception e){
            logger.error(e.getMessage());
            response.setResultInt(0);
        }
        return response;
    }

    /**
     * 修改债转结果
     * @param logOrderId
     * @param logUserId
     * @param retCode
     * @param retMsg
     * @return
     */
    @GetMapping("/updateCreditTenderResult/{logOrderId}/{logUserId}/{retCode}/{retMsg}")
    public IntegerResponse updateCreditTenderResult( @PathVariable String logOrderId,@PathVariable String logUserId,@PathVariable String retCode,@PathVariable String retMsg){
        IntegerResponse response = new IntegerResponse();
        response.setResultInt(bankCreditTenderService.updateCreditTenderResult(logOrderId,logUserId,retCode,retMsg));
        return response;
    }

    /**
     * 查询债转失败原因
     * @param logOrderId
     * @param logUserId
     * @return
     */
    @GetMapping("/getFailResult/{logOrderId}/{logUserId}")
    public StringResponse getFailResult( @PathVariable String logOrderId,@PathVariable String logUserId){
        StringResponse response = new StringResponse();
        response.setResultStr(bankCreditTenderService.getFailResult(logOrderId,logUserId));
        return response;
    }

    /**
     * 查询债转信息
     * @param logOrderId
     * @param logUserId
     * @return
     */
    @GetMapping("/getCreditTenderByUserIdOrdId/{logOrderId}/{logUserId}")
    public CreditTenderResponse getCreditTenderByUserIdOrdId( @PathVariable String logOrderId,@PathVariable String logUserId){
        CreditTenderResponse response = new CreditTenderResponse();
        CreditTenderVO bean =bankCreditTenderService.getCreditTenderByUserIdOrdId(logOrderId,logUserId);
        response.setResult(bean);
        return response;
    }

    /**
     * 获取utm用户债转出借
     * @param list utm注册用户userid集合
     * @return
     */
    @PostMapping("/gethzrtenderprice")
    public CreditTenderResponse getHzrTenderPrice(@RequestBody List<Integer> list) {
        CreditTenderResponse response = new CreditTenderResponse();
        BigDecimal hzrTenderPrice = bankCreditTenderService.getHzrTenderPrice(list);
        if (hzrTenderPrice != null) {
            response.setHzrTenderPrice(hzrTenderPrice);
        }
        return response;
    }


    /**
     * 查询债转承接数
     * @author zhangyk
     * @date 2018/7/25 17:23
     */
    @PostMapping("/getCreditDetailTenderCount")
    public CountResponse getCreditTenderCount(@RequestBody Map<String,Object> param){
        CountResponse response = new CountResponse();
        Integer count = bankCreditTenderService.getCreditTenderCount(param);
        response.setCount(count);
        return response;
    }


    /**
     * 查询债转承接list
     * @author zhangyk
     * @date 2018/7/25 17:23
     */
    @PostMapping("/getCreditDetailTenderList")
    public CreditTenderListResponse getCreditTenderList(@RequestBody Map<String,Object> param){
        CreditTenderListResponse response = new CreditTenderListResponse();
        List<CreditTenderListCustomizeVO> list = bankCreditTenderService.getCreditTenderList(param);
        response.setResultList(list);
        return response;
    }




}
