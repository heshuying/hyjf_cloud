/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.app.*;
import com.hyjf.am.response.app.AppAlreadyRepayListCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.trade.account.AccountWithdrawResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.trade.coupon.CouponResponseForCoupon;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.resquest.app.AppProjectContractDetailBeanRequest;
import com.hyjf.am.resquest.app.AppRepayPlanListBeanRequest;
import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.HandleAccountRechargeRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.vo.app.*;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClientImpl, v0.1 2018/6/20 12:45
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    @Autowired
    private RestTemplate restTemplate;

    @Value("${am.trade.service.name}")
    private String tradeService;

    @Override
    public HjhInstConfigVO selectInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity(tradeService+"/hjhInstConfig/selectInstConfigByInstCode/"+instCode, HjhInstConfigResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户account信息
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(tradeService+"/trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<RecentPaymentListCustomizeVO> selectRecentPaymentList(Integer userId) {
        RecentPaymentListCustomizeResponse response = restTemplate
                .getForEntity(tradeService+"/borrow/selectRecentPaymentList/" + userId, RecentPaymentListCustomizeResponse.class)
                .getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(String userIds) {
        String url = tradeService+"/batch/search_user_portrait_list/" + userIds;
        BatchUserPortraitQueryResponse response = restTemplate.getForEntity(url, BatchUserPortraitQueryResponse.class).getBody();
        if(response != null){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public Integer countCouponValid(Integer userId) {
        String url =tradeService+"/couponUser/countCouponValid/" + userId;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    @Override
    public List<CouponUserListCustomizeVO> selectCouponUserList(Map<String, Object> mapParameter) {
        String url = tradeService+"/couponUser/selectCouponUserList";
        CouponUserListCustomizeResponse response = restTemplate.postForEntity(url,mapParameter,CouponUserListCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取账户信息通过userId范围
     * @param ids
     * @return
     */
    @Override
    public List<AccountVO> getAccountByUserIds(List<Integer> ids) {
        String url="http://AM-TRADE/am-trade/account/getAccountByUserIds";
        AccountResponse response=restTemplate.postForEntity(url,ids,AccountResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage) {
        String url = tradeService+"/htlCommonController/selectUserPrincipal";
        ProductSearchForPageResponse response = restTemplate.postForEntity(url,productSearchForPage,ProductSearchForPageResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectRecoverMoneyCustomizeVO queryRecoverMoney(Integer userId) {
        String url = tradeService+"/webPandect/queryRecoverMoney/"+userId;
        WebPandectRecoverMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectRecoverMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectRecoverMoneyCustomizeVO queryRecoverMoneyForRtb(Integer userId) {
        String url = tradeService+"/webPandect/queryRecoverMoneyForRtb/"+userId;
        WebPandectRecoverMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectRecoverMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectWaitMoneyCustomizeVO queryWaitMoney(Integer userId) {
        String url = tradeService+"/webPandect/queryWaitMoney/"+userId;
        WebPandectWaitMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectWaitMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectWaitMoneyCustomizeVO queryWaitMoneyForRtb(Integer userId) {
        String url = tradeService+"/webPandect/queryWaitMoneyForRtb/"+userId;
        WebPandectWaitMoneyCustomizeResponse response = restTemplate.getForEntity(url,WebPandectWaitMoneyCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BigDecimal queryHtlSumRestAmount(Integer userId) {
        String url = tradeService+"/webPandect/queryHtlSumRestAmount/"+userId;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    @Override
    public WebPandectCreditTenderCustomizeVO queryCreditInfo(Integer userId) {
        String url = tradeService+"/webPandect/queryCreditInfo/"+userId;
        WebPandectCreditTenderCustomizeResponse response = restTemplate.getForEntity(url,WebPandectCreditTenderCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public WebPandectBorrowRecoverCustomizeVO queryRecoverInfo(Integer userId, int recoverStatus) {
        String url = tradeService+"/webPandect/queryRecoverInfo/"+userId+"/"+recoverStatus;
        WebPandectBorrowRecoverCustomizeResponse response = restTemplate.getForEntity(url,WebPandectBorrowRecoverCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BigDecimal queryHtlSumInterest(Integer userId) {
        String url = tradeService+"/webPandect/queryHtlSumInterest/"+userId;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    @Override
    public String selectCouponInterestTotal(Integer userId) {
        String url = tradeService+"/couponConfig/selectCouponInterestTotal/"+userId;
        String response = restTemplate.getForEntity(url,String.class).getBody();
        return response;
    }

    @Override
    public String selectCouponReceivedInterestTotal(Integer userId) {
        String url = tradeService+"/couponConfig/selectCouponReceivedInterestTotal/"+userId;
        String response = restTemplate.getForEntity(url,String.class).getBody();
        return response;
    }


    @Override
    public int selectUserTenderCount(Integer userId) {
        String url = tradeService+"/webPandect/selectUserTenderCount/"+userId;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        return response;
    }

    @Override
    public List<CouponUserCustomizeVO> selectLatestCouponValidUNReadList(Integer userId) {
        CouponUserCustomizeResponse response = restTemplate
                .getForEntity(tradeService+"/bankException/selectLatestCouponValidUNReadList/" + userId, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获得购买列表数
     * @param htlTradeRequest
     * @return
     */
    @Override
    public Integer countHtlIntoRecord(HtlTradeRequest htlTradeRequest) {
        HtlProductIntoRecordResponse response = restTemplate
                .postForEntity(tradeService+"/htl/countHtlIntoRecord", htlTradeRequest,HtlProductIntoRecordResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 获取购买产品列表
     * @param htlTradeRequest
     * @return
     */
    @Override
    public List<HtlProductIntoRecordVO> getIntoRecordList(HtlTradeRequest htlTradeRequest) {
        HtlProductIntoRecordResponse response = restTemplate
                .postForEntity(tradeService+"/htl/getIntoRecordList", htlTradeRequest,HtlProductIntoRecordResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获得汇天利转出列表数
     * @param htlTradeRequest
     * @return
     */
    @Override
    public Integer countProductRedeemRecord(HtlTradeRequest htlTradeRequest) {
        HtlProductRedeemResponse response = restTemplate
                .postForEntity(tradeService+"/htl/countProductRedeemRecord", htlTradeRequest,HtlProductRedeemResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 获取汇天利转出记录列表(自定义)
     * @param htlTradeRequest
     * @return
     */
    @Override
    public List<HtlProductRedeemVO> getRedeemRecordList(HtlTradeRequest htlTradeRequest) {
        HtlProductRedeemResponse response = restTemplate
                .postForEntity(tradeService+"/htl/getRedeemRecordList", htlTradeRequest,HtlProductRedeemResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取用户当前持有债权列表总数
     * @param request
     * @return
     */
    @Override
    public int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldObligatoryRightListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldObligatoryRightCount();
        }
        return 0;
    }

    /**
     * 获取用户当前持有债权列表
     * @param request
     * @return
     */
    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldObligatoryRightList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldObligatoryRightList();
        }
        return null;
    }

    /**
     * 获取用户已回款债权列表总数
     * @param request
     * @return
     */
    @Override
    public int selectRepaymentListTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectRepaymentListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentCount();
        }
        return 0;
    }


    @Override
    public List<AppAlreadyRepayListCustomizeVO> selectAlreadyRepayList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectAlreadyRepayList";
        AppAlreadyRepayListCustomizeResponse response = restTemplate.postForEntity(url,request,AppAlreadyRepayListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    /**
     *
     * @param request
     * @return
     */
    @Override
    public int countCreditRecordTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countCreditRecordTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getTenderCreditDetailCount();
        }
        return 0;
    }

    @Override
    public List<AppTenderCreditRecordListCustomizeVO> searchCreditRecordList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/searchAppCreditRecordList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getAppTenderCreditRecordList();
        }
        return null;
    }

    /**
     * 获取当前持有计划列表总数
     * @param request
     * @return
     */
    @Override
    public int countCurrentHoldPlanTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countCurrentHoldPlanTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldPlanCount();
        }
        return 0;
    }

    /**
     * 获取当前持有计划列表
     * @param request
     * @return
     */
    @Override
    public List<CurrentHoldPlanListCustomizeVO> selectCurrentHoldPlanList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectCurrentHoldPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCurrentHoldPlanList();
        }
        return null;
    }


    /**
     * 获取已回款计划列表总数
     * @param request
     * @return
     */
    @Override
    public Integer countRepayMentPlanTotal(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/countRepayMentPlanTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentPlanCount();
        }
        return 0;
    }



    /**
     * 获取已回款计划列表
     * @param request
     * @return
     */
    @Override
    public List<RepayMentPlanListCustomizeVO> selectRepayMentPlanList(AssetManageBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectRepayMentPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentPlanList();
        }
        return null;
    }


    @Override
    public CouponConfigVO getCouponConfig(String ordId) {
        String url = "http://AM-TRADE/am-trade/couponConfig/getcouponconfigbyorderid/" + ordId;
        CouponConfigResponse response = restTemplate.getForEntity(url, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrow/"+borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowStyleVO selectBorrowStyleByStyle(String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/borrow/selectBorrowStyleByStyle/"+borrowStyle;
        BorrowStyleResponse response = restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取相应的还款数
     * @param params
     * @return
     */
    @Override
    public int countRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/countRepayRecoverListRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }


    @Override
    public List<AppRepayPlanListCustomizeVO> selectRepayRecoverList(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectRepayRecoverList";
        AppRepayPlanListCustomizeResponse response=restTemplate.postForEntity(url,params,AppRepayPlanListCustomizeResponse.class).getBody();
        if (response!=null){
            response.getResultList();
        }
        return null;
    }

    @Override
    public int countRepayPlanListRecordTotal(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/countRepayPlanListRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }

    @Override
    public List<AppRepayPlanListCustomizeVO> selectRepayPlanList(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectRepayPlanList";
        AppRepayPlanListCustomizeResponse response=restTemplate.postForEntity(url,params,AppRepayPlanListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int countCouponRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/countCouponRepayRecoverListRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }

    @Override
    public String selectReceivedInterest(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectReceivedInterest";
        return restTemplate.postForEntity(url,params,String.class).getBody();
    }

    @Override
    public List<AppRepayPlanListCustomizeVO> selectCouponRepayRecoverList(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectCouponRepayRecoverList";
        AppRepayPlanListCustomizeResponse response =restTemplate.postForEntity(url,params,AppRepayPlanListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public AppProjectContractDetailCustomizeVO selectProjectContractDetail(AppProjectContractDetailBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectProjectContractDetail";
        AppProjectContractDetailCustomizeResponse response=restTemplate.postForEntity(url,params,AppProjectContractDetailCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public AppProjectDetailCustomizeVO selectProjectDetail(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/selectProjectDetail/"+borrowNid;
        AppProjectDetailCustomizeResponse response=restTemplate.getForEntity(url,AppProjectDetailCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<AppProjectContractRecoverPlanCustomizeVO> selectProjectContractRecoverPlan(AppProjectContractDetailBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectProjectContractRecoverPlan";
        AppProjectContractRecoverPlanCustomizeResponse response=restTemplate.postForEntity(url,params,AppProjectContractRecoverPlanCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowCreditVO selectCreditTenderByCreditNid(String creditNid) {
        String url = "http://AM-TRADE/am-trade/borrow/selectCreditTenderByCreditNid/"+creditNid;
        BorrowCreditResponse response=restTemplate.getForEntity(url,BorrowCreditResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayPlanList(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectTenderCreditRepayPlanList";
        AppTenderCreditRepayPlanListCustomizeResponse response=restTemplate.postForEntity(url,params,AppTenderCreditRepayPlanListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayRecoverPlanList(AppRepayPlanListBeanRequest params) {
        String url = "http://AM-TRADE/am-trade/borrow/selectTenderCreditRepayRecoverPlanList";
        AppTenderCreditRepayPlanListCustomizeResponse response = restTemplate.postForEntity(url,params,AppTenderCreditRepayPlanListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<AppTenderToCreditListCustomizeVO> selectTenderToCreditList(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectTenderToCreditList";
        AppTenderToCreditListCustomizeResponse response=restTemplate.postForEntity(url,params,AppTenderToCreditListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/coupon/getmycouponbypage";
        CouponResponseForCoupon response = restTemplate.postForEntity(url, requestBean, CouponResponseForCoupon.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款人受托支付申请异步回调更新数据
     * @param borrowNid
     * @return
     */
    @Override
    public Boolean updateTrusteePaySuccess(String borrowNid){
        String url = "http://AM-TRADE/am-trade/trustee/update/" + borrowNid;
        BooleanResponse response = restTemplate.getForEntity(url, BooleanResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 查询信托白名单
     *
     * @param instCode
     * @param receiptAccountId
     * @return
     */
    @Override
    public STZHWhiteListVO getSTZHWhiteList(String instCode, String receiptAccountId){
        String url = "http://AM-TRADE/am-trade/trustee/getSTZHWhiteList/" + instCode + "/" + receiptAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 插入充值记录
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
        String url = tradeService + "/trade/insertAccountRecharge";
        AccountRechargeResponse response = restTemplate.postForEntity(url,accountRechargeVO,AccountRechargeResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getCount();
        }
        return 0;
    }

    /**
     * 根据orderId查询充值记录
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountRechargeVO> selectAccountRechargeByOrderId(String orderId) {
        String url = tradeService + "/trade/selectAccountRechargeByOrderId/" + orderId;
        AccountRechargeResponse response = restTemplate.getForEntity(url,AccountRechargeResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新充值的相关信息(接口调用)
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String handleRechargeInfo(HandleAccountRechargeRequest request) {
        String url = tradeService + "/trade/handleRechargeInfo";
        StringResponse response = restTemplate.postForEntity(url,request,StringResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }

    /**
     * 更新充值的相关信息(页面调用)
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String handleRechargeOnlineInfo(HandleAccountRechargeRequest request) {
        String url = tradeService + "/trade/handleRechargeOnlineInfo";
        StringResponse response = restTemplate.postForEntity(url,request,StringResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }

    /**
     * 第三方用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateBeforeCash(ApiUserWithdrawRequest request) {
        String url = tradeService + "/withdraw/updateBeforeCash";
        IntegerResponse response = restTemplate.postForEntity(url,request,IntegerResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据orderId查询出status=2的账户提现信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public AccountWithdrawVO getAccountWithdrawByOrderId(String orderId) {
        String url = tradeService + "/withdraw/getAccountWithdrawByOrderId/" + orderId;
        AccountWithdrawResponse response = restTemplate.getForEntity(url,AccountWithdrawResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 执行提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String handlerAfterCash(ApiUserWithdrawRequest request) {
        String url = tradeService + "/withdraw/handlerAfterCash";
        StringResponse response = restTemplate.postForEntity(url,request,StringResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }

    /**
     * 查询某用户 id 的提现记录，带分页
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountWithdrawVO> searchAccountWithdrawByUserIdPaginate(ApiUserWithdrawRequest request) {
        String url = tradeService + "/withdraw/searchAccountWithdrawPaginate";
        AccountWithdrawResponse response = restTemplate.postForEntity(url,request,AccountWithdrawResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID修改account表的电子账户
     *
     * @param userId
     * @param accountId
     * @return
     */
    @Override
    public Integer updateAccountNumberByUserId(int userId, String accountId) {
        String url = tradeService + "/account/updateAccountNumberByUserId/" + userId+"/"+accountId;
        IntegerResponse response = restTemplate.getForEntity(url,IntegerResponse.class).getBody();
        if(IntegerResponse.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }
}
