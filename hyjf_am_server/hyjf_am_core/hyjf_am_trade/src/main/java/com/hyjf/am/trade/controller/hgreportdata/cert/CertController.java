/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.hgreportdata.cert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.CouponRecoverResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.coupon.CouponRealTenderResponse;
import com.hyjf.am.response.trade.hgreportdata.cert.CertAccountListResponse;
import com.hyjf.am.response.trade.hgreportdata.cert.CertBorrowResponse;
import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.CertAccountListCustomize;
import com.hyjf.am.trade.dao.model.customize.CertAccountListIdCustomize;
import com.hyjf.am.trade.service.hgreportdata.cert.CertService;
import com.hyjf.am.vo.admin.coupon.CertCouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.cert.CertBorrowUpdateVO;
import com.hyjf.am.vo.trade.cert.CertBorrowVO;
import com.hyjf.am.vo.trade.coupon.CouponRealTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.common.util.CommonUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Api(value = "应急中心")
@RestController
@RequestMapping("/am-trade/cert")
public class CertController extends BaseController {

    @Autowired
    CertService certService;


    @PostMapping("/queryCertAccountList")
    public CertAccountListResponse queryCertAccountList(@RequestBody CertRequest certRequest) {
        CertAccountListResponse response = new CertAccountListResponse();
        List<CertAccountListCustomize> certAccountListCustomizes = certService.queryCertAccountList(certRequest);
        if (!CollectionUtils.isEmpty(certAccountListCustomizes)) {
            List<CertAccountListCustomizeVO> voList = CommonUtils.convertBeanList(certAccountListCustomizes, CertAccountListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getCertAccountListCustomizeVO")
    public CertAccountListResponse getCertAccountListCustomizeVO(@RequestBody CertRequest certRequest) {
        logger.info("getCertAccountListCustomizeVO:" + JSONObject.toJSONString(certRequest));
        CertAccountListResponse response = new CertAccountListResponse();
        List<CertAccountListCustomize> certAccountListCustomizes = certService.getCertAccountListCustomizeVO(certRequest);
        if (!CollectionUtils.isEmpty(certAccountListCustomizes)) {
            List<CertAccountListCustomizeVO> voList = CommonUtils.convertBeanList(certAccountListCustomizes, CertAccountListCustomizeVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/queryCertAccountListId")
    public CertAccountListResponse queryCertAccountListId(@RequestBody CertRequest certRequest) {
        logger.info("queryCertAccountListId:" + JSONObject.toJSONString(certRequest));

        CertAccountListResponse response = new CertAccountListResponse();
        CertAccountListIdCustomize certAccountListIdCustomize = certService.queryCertAccountListId(certRequest);
        response.setCertAccountListIdCustomizeVO(CommonUtils.convertBean(certAccountListIdCustomize,CertAccountListIdCustomizeVO.class));
        return response;
    }

    @PostMapping("/getAccountListVOListByRequest")
    public AccountListResponse getAccountListVOListByRequest(@RequestBody CertRequest certRequest) {
        logger.info("getAccountListVOListByRequest:" + JSONObject.toJSONString(certRequest));
        AccountListResponse response = new AccountListResponse();
        List<AccountList> accountLists = certService.getAccountListVOListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(accountLists)) {
            List<AccountListVO> voList = CommonUtils.convertBeanList(accountLists, AccountListVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getBorrowRepayListByRequest")
    public BorrowRepayResponse getBorrowRepayListByRequest(@RequestBody CertRequest certRequest) {
        BorrowRepayResponse response = new BorrowRepayResponse();
        List<BorrowRepay> borrowRepayList = certService.getBorrowRepayListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(borrowRepayList)) {
            List<BorrowRepayVO> voList = CommonUtils.convertBeanList(borrowRepayList, BorrowRepayVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    @PostMapping("/getBorrowRepayPlanListByRequest")
    public BorrowRepayPlanResponse getBorrowRepayPlanListByRequest(@RequestBody CertRequest certRequest) {
        logger.info("getBorrowRepayPlanListByRequest:" + JSONObject.toJSONString(certRequest));
        BorrowRepayPlanResponse response = new BorrowRepayPlanResponse();
        List<BorrowRepayPlan> borrowRepayPlan = certService.getBorrowRepayPlanListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(borrowRepayPlan)) {
            List<BorrowRepayPlanVO> voList = CommonUtils.convertBeanList(borrowRepayPlan, BorrowRepayPlanVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getCouponRecoverListByCertRequest")
    public CouponRecoverResponse getCouponRecoverListByCertRequest(@RequestBody CertRequest certRequest) {
        CouponRecoverResponse response = new CouponRecoverResponse();
        List<CouponRecover> couponRecoverList = certService.getCouponRecoverListByCertRequest(certRequest);
        if (!CollectionUtils.isEmpty(couponRecoverList)) {
            List<CertCouponRecoverVO> voList = CommonUtils.convertBeanList(couponRecoverList, CertCouponRecoverVO.class);
            response.setCertCouponRecoverVOList(voList);
        }
        return response;
    }

    @PostMapping("/getBorrowTenderCpnListByCertRequest")
    public BorrowTenderCpnResponse getBorrowTenderCpnListByCertRequest(@RequestBody CertRequest certRequest) {
        BorrowTenderCpnResponse response = new BorrowTenderCpnResponse();
        List<BorrowTenderCpn> borrowTenderCpnList = certService.getBorrowTenderCpnListByCertRequest(certRequest);
        if (!CollectionUtils.isEmpty(borrowTenderCpnList)) {
            List<BorrowTenderCpnVO> voList = CommonUtils.convertBeanList(borrowTenderCpnList, BorrowTenderCpnVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getCouponRealTenderListByCertRequest")
    public CouponRealTenderResponse getCouponRealTenderListByCertRequest(@RequestBody CertRequest certRequest) {
        CouponRealTenderResponse response = new CouponRealTenderResponse();
        List<CouponRealTender> couponRealTenderList = certService.getCouponRealTenderListByCertRequest(certRequest);
        if (!CollectionUtils.isEmpty(couponRealTenderList)) {
            List<CouponRealTenderVO> voList = CommonUtils.convertBeanList(couponRealTenderList, CouponRealTenderVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    @PostMapping("/selectBorrowRecoverListByRequest")
    public BorrowRecoverResponse selectBorrowRecoverListByRequest(@RequestBody CertRequest certRequest) {
        BorrowRecoverResponse response = new BorrowRecoverResponse();
        List<BorrowRecover> borrowRecoverList = certService.selectBorrowRecoverListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(borrowRecoverList)) {
            List<BorrowRecoverVO> voList = CommonUtils.convertBeanList(borrowRecoverList, BorrowRecoverVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    @PostMapping("/getHjhDebtCreditRepayListByRequest")
    public HjhDebtCreditRepayResponse getHjhDebtCreditRepayListByRequest(@RequestBody CertRequest certRequest) {
        logger.info("getHjhDebtCreditRepayListByRequest:" + JSONObject.toJSONString(certRequest));
        HjhDebtCreditRepayResponse response = new HjhDebtCreditRepayResponse();
        List<HjhDebtCreditRepay> hjhDebtCreditRepayList = certService.getHjhDebtCreditRepayListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(hjhDebtCreditRepayList)) {
            List<HjhDebtCreditRepayVO> voList = CommonUtils.convertBeanList(hjhDebtCreditRepayList, HjhDebtCreditRepayVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    @PostMapping("/getCreditRepayListByRequest")
    public CreditRepayResponse getCreditRepayListByRequest(@RequestBody CertRequest certRequest) {
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> creditRepayList = certService.getCreditRepayListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(creditRepayList)) {
            List<CreditRepayVO> voList = CommonUtils.convertBeanList(creditRepayList, CreditRepayVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    @PostMapping("/selectBorrowRecoverPlanListByRequest")
    public BorrowRecoverPlanResponse selectBorrowRecoverPlanListByRequest(@RequestBody CertRequest certRequest) {
        BorrowRecoverPlanResponse response = new BorrowRecoverPlanResponse();
        List<BorrowRecoverPlan> couponRealTenderList = certService.selectBorrowRecoverPlanListByRequest(certRequest);
        if (!CollectionUtils.isEmpty(couponRealTenderList)) {
            List<BorrowRecoverPlanVO> voList = CommonUtils.convertBeanList(couponRealTenderList, BorrowRecoverPlanVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getHjhDebtCreditRepayListByRepayOrdId")
    public HjhDebtCreditRepayResponse getHjhDebtCreditRepayListByRepayOrdId(@RequestBody CertRequest certRequest) {
        HjhDebtCreditRepayResponse response = new HjhDebtCreditRepayResponse();
        List<HjhDebtCreditRepay> hjhDebtCreditRepayList = certService.getHjhDebtCreditRepayListByRepayOrdId(certRequest);
        if (!CollectionUtils.isEmpty(hjhDebtCreditRepayList)) {
            List<HjhDebtCreditRepayVO> voList = CommonUtils.convertBeanList(hjhDebtCreditRepayList, HjhDebtCreditRepayVO.class);
            response.setResultList(voList);
        }
        return response;
    }

    @PostMapping("/getCreditRepayListByRepayOrdId")
    public CreditRepayResponse getCreditRepayListByRepayOrdId(@RequestBody CertRequest certRequest) {
        CreditRepayResponse response = new CreditRepayResponse();
        List<CreditRepay> creditRepayList = certService.getCreditRepayListByRepayOrdId(certRequest);
        if (!CollectionUtils.isEmpty(creditRepayList)) {
            List<CreditRepayVO> voList = CommonUtils.convertBeanList(creditRepayList, CreditRepayVO.class);
            response.setResultList(voList);
        }
        return response;
    }
    /**
     * 根据标示，查找国家互联网应急中心（产品配置历史数据上报）
     * @param flg
     * @return
     */
    @GetMapping("/selectCertBorrowByFlg/{flg}")
    public CertBorrowResponse selectCertBorrowByFlg(@PathVariable String flg){
        CertBorrowResponse response = new CertBorrowResponse();
        response.setRtn(Response.FAIL);
        List<CertBorrow> certBorrowList =certService.selectCertBorrowConfig(flg);
        if(org.apache.commons.collections.CollectionUtils.isNotEmpty(certBorrowList)){
            List<CertBorrowVO> borrowVOList = CommonUtils.convertBeanList(certBorrowList,CertBorrowVO.class);
            response.setResultList(borrowVOList);
            response.setRtn(Response.SUCCESS);
        }
        return response;
    }

    /**
     * 批量更新
     * @param updateVO
     * @return
     */
    @PostMapping("/updateCertBorrowStatusBatch")
    public IntegerResponse updateCertBorrowStatusBatch(@RequestBody CertBorrowUpdateVO updateVO){
        IntegerResponse response = new IntegerResponse();
        try{
           certService.updateCertBorrowStatusBatch(updateVO);
            response.setResult(1);
        }catch (Exception e){
            response.setResult(0);
        }
        return response;
    }
}
