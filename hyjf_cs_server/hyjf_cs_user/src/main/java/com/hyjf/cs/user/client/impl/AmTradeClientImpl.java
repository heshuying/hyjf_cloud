/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.UnderLineRechargeResponse;
import com.hyjf.am.response.app.*;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.response.trade.coupon.CouponResponseForCoupon;
import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.response.user.RecentPaymentListCustomizeResponse;
import com.hyjf.am.response.user.WrbAccountResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.WrbInvestRecordRequest;
import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.resquest.app.AppProjectContractDetailBeanRequest;
import com.hyjf.am.resquest.app.AppRepayPlanListBeanRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BatchUserPortraitRequest;
import com.hyjf.am.resquest.user.HtlTradeRequest;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.app.*;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowListCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.wrb.WrbBorrowTenderSumCustomizeVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.am.vo.user.RecentPaymentListCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClientImpl, v0.1 2018/6/20 12:45
 */
@Cilent
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
    public List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(BatchUserPortraitRequest batchUserPortraitRequest) {
        String url = tradeService+"/batch/search_user_portrait_list";
        BatchUserPortraitQueryResponse response = restTemplate.postForEntity(url,batchUserPortraitRequest,BatchUserPortraitQueryResponse.class).getBody();
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

    @Override
    public HjhInstConfigVO selectHjhInstConfig(String instcode) {
        HjhInstConfigResponse response = restTemplate.getForObject(
                "http://AM-TRADE/am-trade/hjhInstConfig/selectInstConfigByInstCode/" + instcode,
                HjhInstConfigResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

	@Override
	public List<WrbBorrowListCustomizeVO> searchBorrowListByNid(String borrowNid) {
		WrbBorrowListResponse response = restTemplate
				.getForObject("http://AM-TRADE/am-trade/wrb/borrow_list/" + borrowNid, WrbBorrowListResponse.class);
		if (response != null) {
            return response.getResultList();
        }
		return null;
	}

    @Override
    public CouponConfigVO getCouponByCouponCode(String couponCode) {
        String url = "http://AM-TRADE/am-trade/couponConfig/selectCouponConfig/" + couponCode;
        CouponConfigResponse response = restTemplate.getForEntity(url, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page) {
        WrbInvestRequest request = new WrbInvestRequest();
        request.setDate(invest_date);
        request.setLimit(limit);
        request.setPage(page);
        BorrowTenderResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/wrb/borrow_tender",
                request, BorrowTenderResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<WrbBorrowTenderCustomizeVO> selectWrbBorrowTender(String borrowNid, Date investTime) {
        WrbInvestRequest request = new WrbInvestRequest();
        request.setDate(investTime);
        request.setBorrowNid(borrowNid);
        WrbBorrowTenderCustomizeResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/wrb/wrb_borrow_tender", request, WrbBorrowTenderCustomizeResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime) {
        WrbInvestRequest request = new WrbInvestRequest();
        request.setDate(investTime);
        request.setBorrowNid(borrowNid);
        WrbBorrowTenderSumCustomizeResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/wrb/search_borrow_tender_sum", request, WrbBorrowTenderSumCustomizeResponse.class);
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 插入线下充值同步余额信息账户明细
     * @param synBalanceBeanRequest
     * @return
     */
    @Override
    public boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest) {
        String url = "http://AM-TRADE/am-trade/synBalance/insertAccountDetails";
        return restTemplate.postForEntity(url, synBalanceBeanRequest, Boolean.class).getBody();
    }

    /**
     * 获取线下充值类型列表
     * @param request
     * @return
     * @Author : huanghui
     */
    @Override
    public List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request) {
        UnderLineRechargeResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/synBalance/selectUnderLineListBySyn", request, UnderLineRechargeResponse.class).getBody();

        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public WrbInvestSumResponse getDaySum(Date date) {
        return restTemplate.getForObject(
                "http://AM-TRADE/am-trade/wrb/ws_sum/" + date, WrbInvestSumResponse.class);

    }

    @Override
    public WrbAccountResponse getAccountInfo(String userId) {
        return restTemplate.getForObject(
                "http://AM-TRADE/am-trade/wrb/getAccountInfo/" + userId, WrbAccountResponse.class);
    }

    @Override
    public WrbAccountResponse getCouponInfo(String userId) {
        return restTemplate.getForObject(
                "http://AM-TRADE/am-trade/wrb/getCouponInfo/" + userId, WrbAccountResponse.class);
    }

    @Override
    public WrbInvestRecordResponse getInvestRecord(WrbInvestRecordRequest request) {
        return restTemplate.postForObject(
                "http://AM-TRADE/am-trade/wrb/getInvestRecord" ,request, WrbInvestRecordResponse.class);
    }

    @Override
    public wrbInvestRecoverPlanResponse getRecoverPlan(String userId, String investRecordId, String borrowNid) {
        return restTemplate.getForObject(
                "http://AM-TRADE/am-trade/wrb/getRecoverPlan/" + userId+"/"+investRecordId+"/"+borrowNid, wrbInvestRecoverPlanResponse.class);
    }

    /**
     * 获取正确的borrowVo
     */
    @Override
    public RightBorrowVO getRightBorrowByNid(String borrowId) {
        String url = "http://AM-TRADE/am-trade/borrow/getRightBorrowByNid/"+ borrowId;
        RightBorrowResponse response = restTemplate.getForEntity(url,RightBorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    /**
     * 统计总的优惠券数
     *
     * @param requestBean
     * @return
     */
    @Override
    public Integer selectMyCouponCount(MyCouponListRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/coupon/myCouponCount";
        IntegerResponse response = restTemplate.postForEntity(url, requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 统计总的奖励金额
     *
     * @param requestBean
     * @return
     */
    @Override
    public BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/reward/myRewardTotal";
        BigDecimalResponse response = restTemplate.postForEntity(url, requestBean, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获得所有协议类型
     * @return
     */
    @Override
    public List<ProtocolTemplateVO> getProtocolTypes() {
        ResponseEntity<Response<ProtocolTemplateVO>> response =
                restTemplate.exchange("http://AM-TRADE/am-trade/protocol/getnewinfo", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<ProtocolTemplateVO>>() {});

        List<ProtocolTemplateVO> vo = null;
        if(response.getBody().getResultList().size() > 0){

            vo =  response.getBody().getResultList();
        }
        return vo;
    }

    /** 用户测评配置 */
    @Override
    public List<EvaluationConfigVO> selectEvaluationConfig(EvaluationConfigVO record){
        EvaluationConfigResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/tradedetail/selectEvaluationConfig/", record, EvaluationConfigResponse.class).getBody();
        if (response != null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
}
