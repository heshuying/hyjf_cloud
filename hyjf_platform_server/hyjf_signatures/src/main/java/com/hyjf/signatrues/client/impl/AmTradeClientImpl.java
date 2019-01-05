package com.hyjf.signatrues.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.app.AppNewAgreementResponse;
import com.hyjf.am.response.app.AppProjectInvestListCustomizeResponse;
import com.hyjf.am.response.app.AppProjectListResponse;
import com.hyjf.am.response.app.AppTenderCreditInvestListCustomizeResponse;
import com.hyjf.am.response.config.AppReapyCalendarResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.response.trade.HjhPlanResponse;
import com.hyjf.am.response.trade.account.*;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.trade.calculate.HjhCreditCalcResultResponse;
import com.hyjf.am.response.trade.coupon.CouponRealTenderResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.trade.coupon.HjhCouponLoansResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.response.wdzj.BorrowDataResponse;
import com.hyjf.am.response.wdzj.PreapysListResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.api.ApiRepayListCustomizeVO;
import com.hyjf.am.vo.app.AppNewAgreementVO;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.IncreaseInterestInvestVO;
import com.hyjf.am.vo.trade.account.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.trade.repay.*;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.signatrues.bean.*;
import com.hyjf.signatrues.client.AmTradeClient;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {

    public static final String urlBase = "http://AM-TRADE/am-trade/";


    @Value("${am.trade.service.name}")
    private String tradeService;

    @Resource
    private RestTemplate restTemplate;



    /**
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public HjhAccedeVO getHjhAccedeByAccedeOrderId(String accedeOrderId) {
        String url = urlBase + "hjhAccede/getHjhAccedeByAccedeOrderId/" + accedeOrderId;
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }



    /**
     * 根据borrowNid获取BorrowInfoVO对象
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO getBorrowInfoByNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowInfoByNid/"+borrowNid;
        BorrowInfoResponse response=restTemplate.getForEntity(url,BorrowInfoResponse.class).getBody();
        if(response!=null) {
            return response.getResult();
        }
        return null;
    }





    /******************************  app end **************************************/

    /**
     * 根据借款编号获取借款人公司信息
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowUserVO getBorrowUser(String borrowNid) {
        BorrowUserResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/borrowUserInfo/" + borrowNid,
                BorrowUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }



    /**
     * 借款主体信息（根据借款编号获取借款人信息）
     * @author zhangyk
     * @date 2018/7/18 13:58
     */
    @Override
    public BorrowManinfoVO getBorrowManinfo(String borrowNid) {
        BorrowManinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowManinfo/" + borrowNid ,BorrowManinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }




    @Override
    public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest) {
        String url = "http://AM-TRADE/am-trade/borrowTender/selectBorrowTender";
        BorrowTenderResponse response = restTemplate.postForEntity(url,btRequest,BorrowTenderResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<FddTempletVO> getFddTempletList(Integer protocolType) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getFddTempletList/"+protocolType;
        FddTempletResponse response = restTemplate.getForEntity(url,FddTempletResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int saveTenderAgreement(TenderAgreementVO info) {
        String url = "http://AM-TRADE/am-trade/borrowTender/saveTenderAgreement";
        return restTemplate.postForEntity(url,info,Integer.class).getBody();
    }

    @Override
    public int updateTenderAgreement(TenderAgreementVO tenderAgreement) {
        String url = "http://AM-TRADE/am-trade/borrowTender/updateTenderAgreement";
        return restTemplate.postForEntity(url,tenderAgreement,Integer.class).getBody();
    }

    @Override
    public List<BorrowTenderVO> getBorrowTenderListByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderListByNid/"+nid;
        BorrowTenderResponse response = restTemplate.getForEntity(url,BorrowTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }



    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    @Override
    public BorrowAndInfoVO getBorrowByNid(String borrowId) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowByNid/"+borrowId;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }


    @Override
    public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/hjhPlan/selectUserHjhInvistDetail";
        UserHjhInvistDetailCustomizeResponse response = restTemplate.postForEntity(url,params,UserHjhInvistDetailCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 获取还款方式
     */
    @Override
    public BorrowStyleVO getBorrowStyle(String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowStyle/"+borrowStyle;
        BorrowStyleResponse response=restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if(response!=null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 根据承接订单号查询债转出借表
     * @param assignNid
     * @return
     */
    @Override
    public List<CreditTenderVO> selectCreditTender(String assignNid) {
        CreditTenderResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTender/"+assignNid,
                        CreditTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }



    @Override
    public List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/creditTender/getCreditTenderList";
        CreditTenderResponse response = restTemplate.postForEntity(url, request, CreditTenderResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
        String url  = "http://AM-TRADE/am-trade/creditTender/selectWebCreditTenderDetailForContract";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url, params, TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String, Object> params) {
        String url  = "http://AM-TRADE/am-trade/creditTender/selectHJHWebCreditTenderDetail";
        TenderToCreditDetailCustomizeResponse response = restTemplate.postForEntity(url,params,TenderToCreditDetailCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            response.getResultList();
        }
        return null;
    }



    @Override
    public BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/selectBorrowRecoverByTenderNid/"+tenderAgreementID;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }


    @Override
    public void updateBorrowRecover(BorrowRecoverVO borrowRecover) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/updateBorrowRecover";
        restTemplate.postForEntity(url,borrowRecover,Boolean.class).getBody();
    }

    /**


    /**
     * 汇计划债转协议下载
     * @return
     */
    @Override
    public List<HjhDebtCreditTenderVO> selectHjhCreditTenderListByAssignOrderId(String assignOrderId) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhCreditTenderListByAssignOrderId/"+assignOrderId;
        HjhDebtCreditTenderResponse response = restTemplate.getForEntity(url,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取债转信息
     * @param request1
     * @return
     */
    @Override
    public List<HjhDebtCreditVO> getHjhDebtCreditList(HjhDebtCreditRequest request1) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCredit/getHjhDebtCreditList";
        HjhDebtCreditResponse response=restTemplate.postForEntity(url,request1,HjhDebtCreditResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }




    /**
     * 根据出借订单号获取协议列表
     * @param nid
     * @return
     */
    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String nid) {
        String url = urlBase +"tenderagreement/selectTenderAgreementByNid/"+nid;
        AssetManageResponse response = restTemplate.getForEntity(url,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getTenderAggementList();
        }
        return null;
    }


    /**
     *查询汇计划债转出借表
     * @param request
     * @return
     */
    @Override
    public List<HjhDebtCreditTenderVO> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCreditTender/getHjhDebtCreditTenderList";
        HjhDebtCreditTenderResponse response =
                restTemplate.postForEntity(url,request,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }




    /**
     *  更新出借协议信息
     * @return
     */
    @Override
    public List<TenderAgreementVO> getTenderAgreementListByTenderNidAndStatusNot2(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/getTenderAgreementListByTenderNidAndStatusNot2/"+tenderNid;
        TenderAgreementResponse response = restTemplate.getForEntity(url, TenderAgreementResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 通过主键获取出借协议信息
     * @param tenderAgreementID
     * @return
     */
    @Override
    public TenderAgreementVO getTenderAgreementInfoByPrimaryKey(String tenderAgreementID) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/getTenderAgreementInfo/"+tenderAgreementID;
        TenderAgreementResponse response = restTemplate.getForEntity(url,TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }



    /**
     * 根据borrowNid查询风控信息
     * @author zhangyk
     * @date 2018/8/10 15:21
     */
    @Override
    public BorrowInfoWithBLOBsVO selectBorrowInfoWithBLOBSVOByBorrowId(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowInfoBLOBByborrowNid/" + borrowNid;
        BorrowInfoWithBLOBResponse response = restTemplate.getForEntity(url,BorrowInfoWithBLOBResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    @Override
    public List<BorrowCreditVO> getBorrowCreditList(BorrowCreditRequest request1) {
        String url = "http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditList";
        BorrowCreditResponse response=restTemplate.postForEntity(url,request1,BorrowCreditResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据条件获取还款计划分期信息
     * add by yangchangwei 20181227
     * @param borrowNid
     * @param nid
     * @param period
     * @return
     */
    @Override
    public BorrowRecoverPlanVO getBorrowRecoverPlanByNidandPeriod(String borrowNid, String nid, Integer period) {

        BorrowRecoverPlanVO info = new BorrowRecoverPlanVO();
        info.setBorrowNid(borrowNid);
        info.setNid(nid);
        info.setRecoverPeriod(period);
        String url = "http://AM-TRADE/am-trade/batch/fddpush/getBorrowRecoverPlanByNidandPeriod";
        BorrowRecoverPlanResponse response = restTemplate.postForEntity(url, info, BorrowRecoverPlanResponse.class).getBody();
        if(response != null){
            return response.getResult();
        }
        return null;
    }


    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 15:47
     * @param contractId
     * @return ApplyAgreementInfoVO
     **/
    @Override
    public List<ApplyAgreementInfoVO>  selectApplyAgreementInfoByContractId(String contractId) {
        String url = "http://AM-TRADE/am-trade/applyAgreement/selectApplyAgreementInfoByContractId/"+contractId;
        ApplyAgreementInfoResponse response = restTemplate.getForEntity(url,ApplyAgreementInfoResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
