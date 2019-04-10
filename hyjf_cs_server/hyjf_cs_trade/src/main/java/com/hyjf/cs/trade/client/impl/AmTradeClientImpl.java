package com.hyjf.cs.trade.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicp.jetcache.anno.CacheRefresh;
import com.alicp.jetcache.anno.CacheType;
import com.alicp.jetcache.anno.Cached;
import com.hyjf.am.response.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.api.ApiAssetStatusCustomizeResponse;
import com.hyjf.am.response.api.UserLargeScreenResponse;
import com.hyjf.am.response.api.UserLargeScreenTwoResponse;
import com.hyjf.am.response.app.AppNewAgreementResponse;
import com.hyjf.am.response.app.AppProjectInvestListCustomizeResponse;
import com.hyjf.am.response.app.AppProjectListResponse;
import com.hyjf.am.response.app.AppTenderCreditInvestListCustomizeResponse;
import com.hyjf.am.response.bifa.BifaBorrowUserInfoResponse;
import com.hyjf.am.response.bifa.BifaHjhPlanResponse;
import com.hyjf.am.response.bifa.UserIdAccountSumBeanResponse;
import com.hyjf.am.response.callcenter.CallCenterAccountDetailResponse;
import com.hyjf.am.response.config.AppReapyCalendarResponse;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.ContentArticleResponse;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.response.trade.HjhPlanResponse;
import com.hyjf.am.response.trade.account.*;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.trade.calculate.HjhCreditCalcResultResponse;
import com.hyjf.am.response.trade.coupon.CouponRealTenderResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.trade.coupon.HjhCouponLoansResponse;
import com.hyjf.am.response.trade.hgreportdata.cert.CertAccountListResponse;
import com.hyjf.am.response.trade.hgreportdata.nifa.NifaContractEssenceResponse;
import com.hyjf.am.response.user.*;
import com.hyjf.am.response.wdzj.BorrowDataResponse;
import com.hyjf.am.response.wdzj.PreapysListResponse;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.BorrowApicronRequest;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.resquest.api.AsseStatusRequest;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.hgreportdata.cert.CertRequest;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.resquest.user.WebUserRepayTransferRequest;
import com.hyjf.am.vo.admin.AppPushManageVO;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.CertCouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.api.*;
import com.hyjf.am.vo.app.AppNewAgreementVO;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.am.vo.config.ContentArticleVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListIdCustomizeVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.market.AppReapyCalendarResultVO;
import com.hyjf.am.vo.task.autoreview.BorrowCommonCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.trade.IncreaseInterestInvestVO;
import com.hyjf.am.vo.trade.account.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.bifa.BifaBorrowUserInfoVO;
import com.hyjf.am.vo.trade.bifa.UserIdAccountSumBeanVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.trade.repay.*;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.util.HomePageDefine;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Cilent
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    public static final String urlBase = "http://AM-TRADE/am-trade/";

    public static final  String BASE_URL = "http://AM-TRADE/am-trade/projectlist";

    @Value("${am.trade.service.name}")
    private String tradeService;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 统计加息券每日待收收益
     *
     * @param
     * @return
     */
    @Override
    public List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        String url = urlBase + "couponRepay/selectCouponInterestWaitToday/" + timeStart + "/" + timeEnd;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url, CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 统计加息券每日已收收益
     *
     * @param
     * @return
     */
    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        String url = urlBase + "couponRepay/selectCouponInterestReceivedToday/" + timeStart + "/" + timeEnd;
        BigDecimal interest = restTemplate.getForEntity(url, BigDecimal.class).getBody();
        if (interest != null) {
            return interest;
        }
        return null;
    }

    /**
     * 我的优惠券列表
     *
     * @auther: hesy
     * @date: 2018/6/23
     */
    @Override
    public List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/myCouponList";
        MyCouponListResponse response = restTemplate.postForEntity(url, requestBean, MyCouponListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
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
        String url = urlBase + "coupon/myCouponCount";
        IntegerResponse response = restTemplate.postForEntity(url, requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 我的奖励列表
     *
     * @param requestBean
     * @return
     */
    @Override
    public List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean) {
        String url = urlBase + "reward/myRewardList";
        MyRewardListResponse response = restTemplate.postForEntity(url, requestBean, MyRewardListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 我的奖励列表总记录数
     */
    @Override
    public int selectMyRewardCount(MyInviteListRequest requestBean) {
        Response<Integer> response = restTemplate
                .postForEntity(urlBase + "reward/myRewardCount", requestBean, Response.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResult();
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
        String url = urlBase + "reward/myRewardTotal";
        BigDecimalResponse response = restTemplate.postForEntity(url, requestBean, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 受托支付待授权列表
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectBorrowAuthList(BorrowAuthRequest requestBean) {
        String url = urlBase + "borrowauth/list_auth";
        BorrowAuthResponse response = restTemplate.postForEntity(url, requestBean, BorrowAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 受托支付待授权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public int selectBorrowAuthCount(BorrowAuthRequest requestBean) {
        IntegerResponse response = restTemplate
                .postForEntity(urlBase + "borrowauth/count_auth", requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 受托支付已授权列表
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public List<BorrowAuthCustomizeVO> selectBorrowAuthedList(BorrowAuthRequest requestBean) {
        String url = urlBase + "borrowauth/list_authed";
        BorrowAuthResponse response = restTemplate.postForEntity(url, requestBean, BorrowAuthResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 受托支付已授权总记录数
     *
     * @auther: hesy
     * @date: 2018/7/6
     */
    @Override
    public int selectBorrowAuthedCount(BorrowAuthRequest requestBean) {
        IntegerResponse response = restTemplate
                .postForEntity(urlBase + "borrowauth/count_authed", requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 受托支付回调更新
     *
     * @param borrowNid
     * @return
     */
    @Override
    public Integer updateTrusteePaySuccess(String borrowNid) {
        String url = urlBase + "borrowauth/auth_update/" + borrowNid;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据用户id查询受托支付白名单
     *
     * @param userId
     * @param stzUserId
     * @return
     */
    @Override
    public STZHWhiteListVO getStzhWhiteListVO(Integer userId, Integer stzUserId) {
        String url = urlBase + "borrowauth/get_whitelist/" + userId + "/" + stzUserId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户权限
     * @author zhangyk
     * @date 2018/6/29 18:36
     */
    @Override
    public HjhUserAuthVO getUserAuthByUserId(Integer userId) {
        String url = "http://AM-USER/am-user/user/getHjhUserAuthByUserId/" + userId;
        HjhUserAuthResponse response = restTemplate.getForEntity(url,HjhUserAuthResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowAndInfoVO selectBorrowByNid(String borrowNid) {
        String url = urlBase + "borrow/getBorrow/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowAndInfoVO doSelectBorrowByNid(String borrowNid) {
        String url = urlBase + "borrow/doGetBorrow/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 取得自动出借用加入计划列表
     *
     * @return
     * @author liubin
     */
    @Override
    public List<HjhAccedeVO> selectPlanJoinList() {
        String url = urlBase + "autoTenderController/selectPlanJoinList";
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResultList();
    }

    /**
     * 计算计划债转实际金额 保存creditTenderLog表
     *
     * @return
     * @author liubin
     */
    @Override
    public HjhCreditCalcResultVO saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        String url = urlBase + "autoTenderController/saveCreditTenderLog";
        SaveCreditTenderLogRequest request = new SaveCreditTenderLogRequest(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
        HjhCreditCalcResultResponse response = restTemplate.postForEntity(url, request, HjhCreditCalcResultResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 取得当前债权在清算前已经发生债转的本金
     * @param hjhDebtCreditVO
     * @return
     */
    @Override
    public BigDecimal doGetPreCreditCapital(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = urlBase + "autoTenderController/doGetPreCreditCapital";
        BigDecimalResponse response = restTemplate.postForEntity(url, hjhDebtCreditVO, BigDecimalResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)){
            return null;
        }
        return response.getResultDec();
    }

    /**
     * 银行自动债转成功后，更新债转数据
     *
     * @return
     * @author liubin
     */
    @Override
    public boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean, String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO) {
        String url = urlBase + "autoTenderController/updateCreditForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateCreditForAutoTenderRequest request = new UpdateCreditForAutoTenderRequest(creditNid, accedeOrderId, planNid, bankCallBeanVO, tenderUsrcustid, sellerUsrcustid, resultVO);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            logger.error("[" + accedeOrderId + "] 银行自动债转成功后，更新债转数据失败。");
            throw new RuntimeException("银行自动债转成功后，更新债转数据失败。");
        }
        return true;
    }

    /**
     * 银行自动投标成功后，更新出借数据
     *
     * @return
     * @author liubin
     */
    @Override
    public boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean) {
        String url = urlBase + "autoTenderController/updateBorrowForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateBorrowForAutoTenderRequest request = new UpdateBorrowForAutoTenderRequest(borrowNid, accedeOrderId, bankCallBeanVO);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            logger.error("[" + accedeOrderId + "] 银行自动投标成功后，更新出借数据失败。");
            throw new RuntimeException("银行自动投标成功后，更新出借数据失败。");
        }
        return true;
    }

    /**
     * 根据是否原始债权获出让人投标成功的授权号
     *
     * @return
     * @author liubin
     */
    @Override
    public String getSellerAuthCode(String sellOrderId, Integer sourceType) {
        String url = urlBase + "autoTenderController/getSellerAuthCode/" + sellOrderId + "/" + sourceType;
        StringResponse response = restTemplate.getForEntity(url, StringResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResultStr();
    }

    /**
     * 银行结束债权后，更新债权表为完全承接
     *
     * @return
     * @author liubin
     */
    @Override
    public int updateHjhDebtCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = urlBase + "hjhDebtCredit/updateHjhDebtCreditByPK";
        HjhDebtCreditVO hjhDebtCreditNew = new HjhDebtCreditVO();
        hjhDebtCreditNew.setId(hjhDebtCreditVO.getId());
        hjhDebtCreditNew.setCreditStatus(2);//转让状态 2完全承接
        hjhDebtCreditNew.setIsLiquidates(1);//1:已清算(无需清算)
        hjhDebtCreditNew.setUpdateTime(GetDate.getDate());
        IntegerResponse response = restTemplate.postForEntity(url, hjhDebtCreditNew, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 请求结束债权（追加结束债权任务记录）
     * @return
     * @author liubin
     */
    @Override
    public int requestDebtEnd(HjhDebtCreditVO credit, String sellerUsrcustid, String sellerAuthCode) {
        String url = urlBase + "bankCreditEndController/insertBankCreditEndForCreditEnd";
        InsertBankCreditEndForCreditEndRequest request = new InsertBankCreditEndForCreditEndRequest(credit, sellerUsrcustid, sellerAuthCode);
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 根据标的编号，查询汇计划信息
     * @return
     * @author liubin
     */
    @Override
    public HjhPlanVO getPlanByNid(String borrowNid) {
        String url = urlBase + "hjhPlan/getHjhPlanByPlanNid/" + borrowNid;
        com.hyjf.am.response.user.HjhPlanResponse response = restTemplate.getForEntity(url, com.hyjf.am.response.user.HjhPlanResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 根据标的编号，查询汇计划信息
     * @return
     * @author liubin
     */
    @Override
    public HjhPlanVO doGetPlanByNid(String borrowNid) {
        String url = urlBase + "hjhPlan/doGetHjhPlanByPlanNid/" + borrowNid;
        com.hyjf.am.response.user.HjhPlanResponse response = restTemplate.getForEntity(url, com.hyjf.am.response.user.HjhPlanResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 查询资产状态
     *
     * @param request
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     **/
    @Override
    public ApiAssetStatusCustomizeVO selectAssetStatusById(AsseStatusRequest request) {
        ApiAssetStatusCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/selectAssetStatusById", request,
                        ApiAssetStatusCustomizeResponse.class)
                .getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 根据creditNid查询债转信息
     * @author liubin
     */
    @Override
    public HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid) {
        String url = urlBase + "hjhDebtCredit/selectHjhDebtCreditByCreditNid/" + creditNid;
        HjhDebtCreditResponse response = restTemplate.getForEntity(url, HjhDebtCreditResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 根据creditNid查询债转信息
     * @author liubin
     */
    @Override
    public HjhDebtCreditVO doSelectHjhDebtCreditByCreditNid(String creditNid) {
        String url = urlBase + "hjhDebtCredit/doSelectHjhDebtCreditByCreditNid/" + creditNid;
        HjhDebtCreditResponse response = restTemplate.getForEntity(url, HjhDebtCreditResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

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
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public HjhAccedeVO doGetHjhAccedeByAccedeOrderId(String accedeOrderId) {
        String url = urlBase + "hjhAccede/doGetHjhAccedeByAccedeOrderId/" + accedeOrderId;
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 更新加入计划状态
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public int updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO) {
        String url = urlBase + "hjhAccede/updateHjhAccedeByPrimaryKey";
        IntegerResponse response = restTemplate.postForEntity(url, hjhAccedeVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 插入汇计划自动出借临时表
     * @author liubin
     */
    @Override
    public int insertHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/insertHjhPlanBorrowTmp";
        IntegerResponse response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 删除汇计划自动出借临时表
     * @author liubin
     */
    @Override
    public int deleteHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/deleteHjhPlanBorrowTmp";
        IntegerResponse response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 根据主键，更新汇计划自动出借临时表
     * @author liubin
     */
    @Override
    public int updateHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/updateHjhPlanBorrowTmp";
        IntegerResponse response = restTemplate.postForEntity(url, hjhPlanBorrowTmpVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    @Override
    public List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getmycouponbypage";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 批次结束债权用更新 结束债权任务表
     * @author liubin
     */
    @Override
    public int updateCreditEndForBatch(BankCreditEndVO bankCreditEndVO) {
        String url = urlBase + "bankCreditEndController/updateBankCreditEndForBatch";
        BankCreditEndRequest request = new BankCreditEndRequest(bankCreditEndVO);
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    @Override
    public CouponResponse getBorrowCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getborrowcoupon";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    @Override
    public CouponResponse getPlanCoupon(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/getplancoupon";
        CouponResponse response = restTemplate.postForEntity(url, requestBean, CouponResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据批次号和日期，取得结束债权任务列表
     * @param bankCreditEndVO
     * @return
     * @author liubin
     *
     */
    @Override
    public List<BankCreditEndVO> getBankCreditEndListByBatchnoTxdate(BankCreditEndVO bankCreditEndVO) {
        String url = urlBase + "bankCreditEndController/getBankCreditEndListByBatchnoTxdate/" + bankCreditEndVO.getBatchNo() + "/" + bankCreditEndVO.getTxDate();
        BankCreditEndResponse response = restTemplate.getForEntity(url, BankCreditEndResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据条件(批次号和日期)，更新结束债权任务状态
     * @param bankCreditEndVO
     * @param status
     * @return
     * @author liubin
     */
    @Override
    public int updateCreditEndForStatus(BankCreditEndVO bankCreditEndVO, int status) {
        String url = urlBase + "bankCreditEndController/updateBankCreditEndForStatus";
        UpdateBankCreditEndForStatusRequest request = new UpdateBankCreditEndForStatusRequest(bankCreditEndVO, status);
        IntegerResponse response = restTemplate.postForEntity(url, request, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     * @author liubin
     */
    @Override
    public int updateBatchCreditEndCheck(BankCallBeanVO bankCallBeanVO) {
        String url = urlBase + "bankCreditEndController/updateBatchCreditEndCheck";
        IntegerResponse response = restTemplate.postForEntity(url, bankCallBeanVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     * @author liubin
     */
    @Override
    public int updateBatchCreditEndFinish(BankCallBeanVO bankCallBeanVO) {
        String url = urlBase + "bankCreditEndController/updateBatchCreditEndFinish";
        IntegerResponse response = restTemplate.postForEntity(url, bankCallBeanVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
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


    /**
     * 出借异常定时任务更新出借信息
     * @param request
     * @return
     */
    @Override
    public boolean updateTenderStart(BorrowTenderTmpRequest request) {
        String url = "http://AM-TRADE/am-trade/bankException/updateTenderStart";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 获取BorrowTenderTmpVO列表
     */
	@Override
	public List<BorrowTenderTmpVO> getBorrowTenderTmpList() {
		String url = "http://AM-TRADE/am-trade/bankException/getBorrowTenderTmpList";
		BorrowTenderTmpResponse response =restTemplate.getForEntity(url,BorrowTenderTmpResponse.class).getBody();
		if (response!=null){
			return response.getResultList();
		}
		return null;
	}
    /**
     * 投资全部掉单异常处理
     */
    @Override
    public void recharge(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/recharge",BorrowTenderTmpResponse.class).getBody();
    }


    /**
	 * 获取BatchBorrowTenderCustomizeVO列表
	 */
	@Override
	public List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList() {
		String url = "http://AM-TRADE/am-trade/bankException/queryAuthCodeBorrowTenderList";
		BatchBorrowTenderCustomizeResponse response =
				restTemplate.getForEntity(url,BatchBorrowTenderCustomizeResponse.class).getBody();
		if (response!=null){
			return response.getResultList();
		}
		return null;
	}
    /**
     * 自动放款复审任务
     */
    @Override
    public void hjhautoreview(){
        restTemplate.getForEntity("http://AM-TRADE/batch/hjhautoreview/hjhautoreview",BatchBorrowTenderCustomizeResponse.class);
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     */
    @Override
    public void hjhOpenAccountCheck(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAlarmController/batch/hjhOpenAccountCheck",BatchBorrowTenderCustomizeResponse.class);
    }

    /**
     * 汇计划各计划开放额度校验预警任务
     */
    @Override
    public void hjhOrderExitCheck(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAlarmController/batch/hjhOrderExitCheck",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
     * 订单投资异常短信预警
     * @return
     */
    @Override
    public void hjhCalculateFairValue(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoCalculateFairValue/hjhCalculateFairValue",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
     * 订单投资异常短信预警
     * @return
     */
    @Override
    public boolean hjhOrderInvestExceptionCheck(){
        BooleanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAlarmController/batch/hjhOrderInvestExceptionCheck",BooleanResponse.class).getBody();
        if (response!=null){
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 订单投资异常短信预警
     * @return
     */
    @Override
    public void hjhOrderMatchPeriodCheck(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAlarmController/batch/hjhOrderMatchPeriodCheck",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
     * 手续费分账明细插入定时
     * @return
     */
    @Override
    public void poundage(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/poundage",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
     * 汇计划自动结束转让定时任务
     * @return
     */
    @Override
    public void hjhAutoEndCredit(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoEndCredit/hjhAutoEndCredit",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
     *  汇计划自动清算
     * @return
     */
    @Override
    public void hjhAutoCredit(){
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoCredit/hjhAutoCredit",BatchBorrowTenderCustomizeResponse.class);
    }
    /**
	 * 插入AuthCode
	 */
	@Override
	public void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list) {
		String url = "http://AM-TRADE/am-trade/bankException/insertAuthCode";
		BatchBorrowTenderCustomizeRequest request = new BatchBorrowTenderCustomizeRequest();
		request.setBatchBorrowTenderCustomizeList(list);
		restTemplate.postForEntity(url,request,Boolean.class).getBody();
	}

	/**
	 * 网贷之家标的列表
	 * @auther: hesy
	 * @date: 2018/7/16
	 */
    @Override
    public List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/get_borrowlist";
        BorrowDataResponse response =
                restTemplate.postForEntity(url,requestBean,BorrowDataResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 网贷之家标的列表总记录数
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public Integer countBorrowList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/count_borrowlist";
        Response<Integer> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 网贷之家标的列表总金额
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public String sumBorrowAmount(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/sum_borrowamount";
        Response<String> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 网贷之家提前还款列表
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/get_preapyslist";
        PreapysListResponse response =
                restTemplate.postForEntity(url,requestBean,PreapysListResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 网贷之家提前还款列表总记录数
     * @auther: hesy
     * @date: 2018/7/16
     */
    @Override
    public Integer countPreapysList(Map<String, Object> requestBean) {
        String url = "http://AM-TRADE/am-trade/wdzj/borrowdata/count_preapyslist";
        Response<Integer> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新借款API任务表
     * @auther: hesy
     * @date: 2018/7/17
     */
    @Override
    public Boolean updateBorrowApicron(ApiCronUpdateRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repay/update_apicron";
        Response<Boolean> response =
                restTemplate.postForEntity(url,requestBean,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 如果有正在出让的债权,先去把出让状态停止
     * @param borrowNid
     * @return
     */
    @Override
    public Boolean updateBorrowCreditStautus(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/repay/update_borrowcredit_status/" + borrowNid;
        Response<Boolean> response =
                restTemplate.getForEntity(url,Response.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据bankSeqNo检索
     * @auther: hesy
     * @date: 2018/7/17
     */
    @Override
    public BorrowApicronVO selectBorrowApicron(String bankSeqNO) {
        String url = "http://AM-TRADE/am-trade/borrowApicron/getby_bankseqno/" + bankSeqNO;
        BorrowApicronResponse response =
                restTemplate.getForEntity(url,BorrowApicronResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 根据订单号查询充值信息
     * @param orderId
     * @return
     */
    @Override
    public AccountRechargeVO selectByOrderId(String orderId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity(urlBase +"trade/selectByOrderId/"+orderId,AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
    /**
     * 更新充值详情信息
     * @param accountRecharge
     */
    @Override
    public void updateAccountRecharge(AccountRechargeVO accountRecharge) {
        restTemplate.put(urlBase +"trade/updateByPrimaryKeySelective",accountRecharge);
    }
    /**
     * 根据订单号查询充值数量
     * @param ordId
     * @return
     */
    @Override
    public int selectByOrdId(String ordId){
        IntegerResponse response = restTemplate
                .getForEntity(urlBase +"trade/selectByOrdId/"+ordId, IntegerResponse.class).getBody();
        if (response != null) {
            return response.getResultInt();
        }
        return -1;
    }
    /**
     * 插入银行卡信息
     * @param bankRequest
     * @return
     */
    @Override
    public int insertSelectiveBank(BankRequest bankRequest){
        Integer response = restTemplate
                .postForEntity(urlBase +"trade/insertSelectiveBank",bankRequest, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return 0;
    }
    /**
     * 修改银行卡信息
     * @param bankAccountBeanRequest
     * @return
     */
    @Override
    public boolean updateBanks(BankAccountBeanRequest bankAccountBeanRequest) {
        boolean response = restTemplate
                .postForEntity(urlBase +"trade/updateBanks",bankAccountBeanRequest, boolean.class).getBody();
        return response;
    }
    /**
     * 根据用户userId查询账号体系
     * @param userId
     * @return
     */
    @Override
    public AccountVO getAccount(Integer userId) {
        AccountResponse response = restTemplate
                .getForEntity(urlBase +"trade/getAccount/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据订单号查询用户提现记录列表
     * @param ordId
     * @return
     */
    @Override
    public List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId) {
        AccountWithdrawResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/findByOrdId/" + ordId, AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 插入提现记录
     * @param record
     */
    @Override
    public int insertAccountWithdrawLog(AccountWithdrawVO record) {
        IntegerResponse response = restTemplate
                .postForEntity(urlBase +"accountWithdraw/insertAccountWithdrawLog",record, IntegerResponse.class).getBody();
        if (response != null) {
            return response.getResultInt();
        }
        return 0;

    }
    /**
     * 根据订单号查询用户提现记录信息
     * @param logOrderId
     * @return
     */
    @Override
    public AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId) {
        AccountWithdrawResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/getAccountWithdrawByOrdId/" + logOrderId, AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新用户提现记录
     * @param accountwithdraw
     * @return
     */
    @Override
    public boolean updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw) {
        IntegerResponse result = restTemplate
                .postForEntity(urlBase +"accountWithdraw/updateAccountWithdrawLog", accountwithdraw, IntegerResponse.class).getBody();
        return result.getResultInt()>0?true:false;
    }
    /**
     * 提现后续操作
     * @param bankWithdrawBeanRequest
     * @return
     */
    @Override
    public int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest) {
        IntegerResponse result = restTemplate
                .postForEntity(urlBase +"accountWithdraw/updatUserBankWithdrawHandler", bankWithdrawBeanRequest, IntegerResponse.class).getBody();
        return result.getResultInt();
    }
    /**
     * 查询用户标的出借数量
     * @param userId
     * @return
     */
    @Override
    public Integer getBorrowTender(Integer userId) {
        String url = urlBase +"accountWithdraw/getBorrowTender/"+userId;
        AccountWithdrawResponse response= restTemplate.getForEntity(url,AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getUserBorrowTenderCounte();
        }
        return 0;
    }
    /**
     * 根据用户id查询当前充值信息
     * @param userId
     * @return
     */
    @Override
    public List<AccountRechargeVO> getTodayRecharge(Integer userId) {
        AccountRechargeResponse response = restTemplate
                .getForEntity(urlBase +"accountWithdraw/getTodayRecharge/" + userId, AccountRechargeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
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
        String url = urlBase +"synBalance/insertAccountDetails";
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
        UnderLineRechargeResponse response = restTemplate.postForEntity(urlBase +"underLineRecharge/selectUnderLineListBySyn", request, UnderLineRechargeResponse.class).getBody();

        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    @Cached(name="webHomeProjectListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectList",request,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    @Cached(name="webHomeProjectListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public Integer countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countProjectList",request,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    @Override
    @Cached(name="webProjectDetailCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = CustomConstants.PROJECT_DETAIL_CACHE_TIME, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public ProjectCustomeDetailVO searchProjectDetail(Map map) {
        ProjectDetailResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectDetail",map,ProjectDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public CreditListResponse countCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countCreditList",request,CreditListResponse.class).getBody();
        return response;
    }

    @Override
    public CreditListResponse searchCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchWebCreditList",request,CreditListResponse.class).getBody();
        return response;
    }

    /**
     * web:查询计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:27
     */
    @Override
    public Map<String, Object> searchPlanData(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/planData",request,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTotalData();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据count
     * @author zhangyk
     * @date 2018/6/21 15:28
     */
    @Override
    @Cached(name="webPlanListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public Integer countPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countPlanList",request,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * web:查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    @Override
	@Cached(name="webPlanListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
    @Override
    public PlanDetailCustomizeVO getPlanDetail(String planNid){
        HjhPlanDetailResponse response = restTemplate.getForEntity(BASE_URL + "/web/searchPlanDetail/" + planNid,HjhPlanDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /*******************************  web end *************************************/
    /******************************  app start **************************************/
    /**
     *  app端获取散标出借项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    @Override
    @Cached(name="appProjectListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public Integer countAppProjectList(ProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countProjectList",request,AppProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * app端获取散标出借项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    @Override
	@Cached(name="appProjectListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public List<AppProjectListCustomizeVO> searchAppProjectList(AppProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppProjectList",request,AppProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return CollectionUtils.isEmpty(response.getResultList()) ? new ArrayList<>() : response.getResultList();
        }
        return null;
    }

    /**
     * app端获取散标出借项目列表 无缓存版
     * @author cwyang
     * @date 2018/6/20 17:24
     */
    @Override
    public List<AppProjectListCustomizeVO> searchAppProjectListNoCash(AppProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppProjectList",request,AppProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     *  app端查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */

    @Override
    @Cached(name="appCreditListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public ProjectListResponse countAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countCreditList",request,ProjectListResponse.class).getBody();
        return response;
    }

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    @Override
    @Cached(name="appCreditListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public ProjectListResponse searchAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchCreditList",request,ProjectListResponse.class).getBody();
        return response;
    }

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    @Cached(name="appPlanListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public Integer countAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countPlanList",request,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }


    /**
     * APP端查询计划数据list
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    @Cached(name="appPlanListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 2, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<HjhPlanCustomizeVO> searchAppPlanList(ProjectListRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
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
     * 借款公司信息
     * @author zhangyk
     * @date 2018/7/18 13:56
     */
    @Override
    public ProjectCompanyDetailVO searchProjectCompanyDetail(String borrowNid) {
        ProjectCompanyResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectCompany/" + borrowNid, ProjectCompanyResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 借款人信息
     * @author zhangyk
     * @date 2018/7/18 13:57
     */
    @Override
    public WebProjectPersonDetailVO searchProjectPersonDetail(String borrowNid) {
        ProjectPersonDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectPserson/" + borrowNid,ProjectPersonDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
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


    /**
     * 房屋抵押信息
     * @author zhangyk
     * @date 2018/7/18 13:59
     */
    @Override
    public List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid) {
        BorrowHousesResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowhouses/" + borrowNid, BorrowHousesResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 车辆抵押信息
     * @author zhangyk
     * @date 2018/7/18 14:01
     */
    @Override
    public List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid) {
        BorrowCarinfoResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/borrowCarinfo/" + borrowNid,BorrowCarinfoResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder) {
        String url= "http://AM-TRADE/am-trade/borrowRepay/selectBorrowRepayList/" + borrowNid + "/";
        if (null == repaySmsReminder){
            url += "000";  // 为了公用添加特殊处理字符
        }else{
            url += repaySmsReminder.toString();
        }
        BorrowRepayResponse response = restTemplate.getForEntity(
                url,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/updateBorrowRepay/",borrowRepayVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }



    @Override
    public List<BorrowRepayVO> getBorrowRepayList(String borrowNid) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/getBorrowRepayListByBorrowNid/" + borrowNid  ,
                BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public BorrowRepayVO getBorrowRepay(String borrowNid) {
        BorrowRepayResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepay/getBorrowRepayListByBorrowNid/" + borrowNid  ,
                BorrowRepayResponse.class).getBody();
        if (response != null && response.getResultList() != null && !response.getResultList().isEmpty()) {
            return response.getResultList().get(0);
        }
        return null;
    }

    /**
     * 项目详情
     * @author zhangyk
     * @date 2018/7/18 14:06
     */
    @Override
    public ProjectCustomeDetailVO selectProjectDetail(String borrowNid) {
        ProjectDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/getProjectDetail/" + borrowNid, ProjectDetailResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 加入总数
     * @author zhangyk
     * @date 2018/7/18 14:11
     */
    @Override
    public Integer countPlanAccedeRecordTotal(HjhAccedeRequest request) {
        HjhAccedeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAccede/countAccede/" + request.getPlanNid() ,HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getAccedeCount();
        }
        return null;
    }

    @Override
    public AccountVO getAccountByUserId(Integer userId) {
        AccountResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId ,AccountResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer updateOfPlanRepayAccount(AccountVO accountVO) {
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateOfPlanRepayAccount/", accountVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }



    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {
        BorrowTenderResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrowTender/countUserInvest/" +borrowNid + "/" + userId,BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
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
     * 根据出借订单号查询已承接金额
     *
     * @param tenderNid
     * @return
     */
    @Override
    public BigDecimal getAssignCapital(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/get_assign_capital_by_tender_nid/"+tenderNid;
        BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
        return response;
    }

    /**
     * 保存债转日志
     *
     * @param creditTenderLog
     * @return
     */
    @Override
    public Integer saveCreditTenderAssignLog(CreditTenderLogVO creditTenderLog) {
        String url = "http://AM-TRADE/am-trade/borrowTender/save_credit_tender_assign_log";
        IntegerResponse response = restTemplate.postForEntity(url,creditTenderLog,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 查看是否已经插入网站收支明细
     *
     * @param logOrderId
     * @param tenderType 交易类型
     * @return
     */
    @Override
    public Integer countAccountWebListByOrdId(String logOrderId, String tenderType) {
        String url = "http://AM-TRADE/am-trade/borrowTender/countAccountWebListByOrdId/"+tenderType+"/"+tenderType;
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * 获取账户提现列表
     */
    @Override
    public List<AccountWithdrawVO> selectBankWithdrawList() {
        String url = "http://AM-TRADE/am-trade/bankException/selectBankWithdrawList";
        AccountWithdrawResponse response = restTemplate.getForEntity(url,AccountWithdrawResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 调用后平台操作
     */
    @Override
    public Boolean handlerAfterCash(AfterCashParamRequest request) {
        String url = "http://AM-TRADE/am-trade/bankException/handlerAfterCash";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 查询是否已经处理过
     */
    @Override
    public int getAccountlistCntByOrdId(String orderId, String cashSuccess) {
        String url = "http://AM-TRADE/am-trade/account/getAccountlistCntByOrdId/" + orderId + "/" + cashSuccess;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }


    /**
     * @param planAccede
     * @Description 插入计划交易明细表
     * @Author sunss
     * @Date 2018/6/22 10:34
     */
    @Override
    public boolean insertHJHPlanAccede(HjhAccedeVO planAccede) {
        IntegerResponse result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/hjhPlan/insertHJHPlanAccede", planAccede, IntegerResponse.class).getBody();
        if (IntegerResponse.isSuccess(result)) {
            return result.getResultInt() == 0 ? false : true;
        }
        return false;
    }

    /**
     * 检索正在还款中的标的
     *
     * @Author liushouyi
     * @return
     */
    @Override
    public List<BorrowAndInfoVO> selectBorrowList() {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/selectBorrowList/",
                BorrowResponse.class).getBody();
        if (response != null) {
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

    /**
     * 获取正确的borrowVo
     * @author zhangyk
     * @date 2018/9/13 17:33
     */
    @Override
    public RightBorrowVO getRightBorrowByNid(String borrowId) {
        String url = "http://AM-TRADE/am-trade/borrow/getRightBorrowByNid/"+borrowId;
        RightBorrowResponse response = restTemplate.getForEntity(url,RightBorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    /**
     * 出借之前插入tmp表
     *
     * @param request
     */
    @Override
    public boolean updateBeforeChinaPnR(TenderRequest request) {
        logger.info("散标出借开始插入tmp表  参数 :{}",JSONObject.toJSONString(request));
        IntegerResponse result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/insertBeforeTender", request, IntegerResponse.class).getBody();
        if (Response.isSuccess(result)) {
            return result.getResultInt()== 0 ? false : true;
        }
        return false;
    }

    /**
     * 用户出借散标操作表
     *
     * @param tenderBg
     * @return
     */
    @Override
    public boolean borrowTender(TenderBgVO tenderBg) {
        logger.info("用户出借散标操作表,对象为：{}",JSONObject.toJSONString(tenderBg));
        IntegerResponse result =  restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/borrowTender", tenderBg, IntegerResponse.class).getBody();
        if (Response.isSuccess(result)) {
        	logger.info("result.getResultInt()：" + result.getResultInt());
            return result.getResultInt()== 0 ? false : true;
        }
        return false;
    }

    /**
     * 修改状态临时表结果
     *  @param logUserId
     * @param logOrderId
     * @param respCode
     * @param retMsg
     * @param productId
     */
    @Override
    public boolean updateTenderResult(String logUserId, String logOrderId, String respCode, String retMsg, String productId) {
        TenderRetMsg tenderRetMsg = new TenderRetMsg();
        tenderRetMsg.setLogOrderId(logOrderId);
        tenderRetMsg.setLogUserId(logUserId);
        tenderRetMsg.setRespCode(respCode);
        tenderRetMsg.setRetMsg(retMsg);
        tenderRetMsg.setProductId(productId);
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/updateTenderResult", tenderRetMsg, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 获取出借异步结果
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    @Override
    public String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowTenderResult/" + userId + "/" + logOrdId + "/" + borrowNid;
        StringResponse response = restTemplate.getForEntity(url, StringResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultStr();
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

    @Override
    public Integer getTotalInverestCount(String userId) {
        ProjectListResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrow/inverestCount/" + userId,ProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * 根据标的编号查询还款计划
     *
     * @param borrowNid 标的编号
     * @param repaySmsReminder 还款提示短信发送次数
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder) {

        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/selectBorrowPlanRepayList/" + borrowNid + "/" + repaySmsReminder,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/updateBorrowRepayPlan/",borrowRepayPlanVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 根据borrowNid和borrowPeriod查询
     *
     * @param bidNid
     * @param borrowPeriod
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByPeriod(String bidNid, Integer borrowPeriod) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_repay_list_by_period/" + bidNid + "/" + borrowPeriod,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据borrowNid检索
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlansByBorrowNid(String borrowNid) {
        BorrowRepayPlanResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowRepayPlan/select_by_borrownid/" + borrowNid,
                BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 判断是否逾期 逾期或延期时返回false 逾期或延期时不计算提前还款提前还款减息
     * @param borrow
     * @return
     */
    @Override
    public Boolean getOverDueFlag(RightBorrowVO borrow){
        BooleanResponse response = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowRecover/getOverDueFlag", borrow,
                BooleanResponse.class).getBody();
        if (response != null) {
            return response.getResultBoolean();
        }
        return null;
    }

    /**
     * 查询债转承接掉单的数据
     *
     * @return
     */
    @Override
    public List<CreditTenderLogVO> selectCreditTenderLogs() {
        CreditTenderLogResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogs",
                        CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
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

    /**
     * 更新投標記錄
     * @param creditTenderLog
     * @return
     */
    @Override
    public Boolean updateCreditTenderLog(CreditTenderLogVO creditTenderLog) {
        String url = "http://AM-TRADE/am-trade/bankException/updateCreditTenderLog";
        return restTemplate.postForEntity(url, creditTenderLog, Boolean.class).getBody();
    }

    /**
     *同步回调收到后,根据logOrderId检索出借记录表
     * @param logOrderId
     * @return
     */
    @Override
    public CreditTenderLogVO selectCreditTenderLogByOrderId(String logOrderId) {
        CreditTenderLogResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/bankException/selectCreditTenderLogByOrderId/" + logOrderId, CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    /**
     * 獲取賬戶信息
     * @param
     * @return
     */
    @Override
    public AccountVO getAccount(int userId) {
        AccountResponse response
                = restTemplate.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId, AccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public void handle() {

    }

    @Override
    public CreditTenderVO selectByAssignNidAndUserId(String assignNid, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectByAssignNidAndUserId/" + assignNid+"/"+userId;
        CreditTenderResponse response = restTemplate.getForEntity(url,CreditTenderResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }


    @Override
    public boolean updateTenderCreditInfo(CreditTenderRequest request) {
        String url = "http://AM-TRADE/am-trade/creditTender/updateTenderCreditInfo";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();
    }

    /**
     * 获取CreditTenderLogs
     * add by jijun 20180622
     */
    @Override
    public List<CreditTenderLogVO> getCreditTenderLogs(String logOrderId, Integer userId) {
        String url = "http://AM-TRADE/am-trade/bankException/selectByOrderIdAndUserId/" + logOrderId+"/"+userId;
        CreditTenderLogResponse response = restTemplate.getForEntity(url,CreditTenderLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取BorrowCreditList
     * add by jijun 20180622
     */
    @Override
    public List<BorrowCreditVO> getBorrowCreditList(String creditNid, int sellerUserId, String tenderOrderId) {
        BorrowCreditRequest request = new BorrowCreditRequest();
        request.setCreditNid(creditNid);
        request.setSellerUserId(sellerUserId);
        request.setTenderOrderId(tenderOrderId);
        String url = "http://AM-TRADE/am-trade/bankException/getBorrowCreditList";
        BorrowCreditResponse response = restTemplate.postForEntity(url, request, BorrowCreditResponse.class).getBody();
        if(response != null) {
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

    /**
     * 保存债转的数据
     * @param creditTenderBg
     * @return
     */
    @Override
    public Integer saveCreditBgData(CreditTenderBgVO creditTenderBg) {
        String url  = "http://AM-TRADE/am-trade/creditTender/saveCreditBgData";
        logger.info("保存债转的数据：{}",JSONObject.toJSONString(creditTenderBg));
        IntegerResponse response = restTemplate.postForEntity(url,creditTenderBg,IntegerResponse.class).getBody();
        if(IntegerResponse.isSuccess(response)){
            return 1;
        }
        return 0;
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
    public BorrowRecoverVO selectBorrowRecoverById(Integer id) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_id/"+id;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public BorrowRecoverVO selectBorrowRecoverByNid(String nid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_nid/"+nid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverVO> selectBorrowRecoverByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/select_by_borrownid/"+borrowNid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public void updateBorrowRecover(BorrowRecoverVO borrowRecover) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/updateBorrowRecover";
        restTemplate.postForEntity(url,borrowRecover,Boolean.class).getBody();
    }

    /**
     * 根据tenderNid   和  bidNid 查询
     *
     * @param tenderNid
     * @param bidNid
     * @return
     */
    @Override
    public BorrowRecoverVO getBorrowRecoverByTenderNidBidNid(String tenderNid, String bidNid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/getBorrowRecoverByTenderNidBidNid/"+tenderNid+"/"+bidNid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据tenderNid 查询
     *
     * @param tenderNid
     * @return
     */
    @Override
    public BorrowRecoverVO getBorrowRecoverByTenderNid(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/getBorrowRecoverByTenderNid/"+tenderNid;
        BorrowRecoverResponse response = restTemplate.getForEntity(url,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取borrow_recover_plan更新每次还款时间
     *
     * @param bidNid
     * @param creditTenderNid
     * @param periodNow
     * @return
     */
    @Override
    public BorrowRecoverPlanVO getPlanByBidTidPeriod(String bidNid, String creditTenderNid, int periodNow) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/getPlanByBidTidPeriod/"+bidNid+"/"+creditTenderNid+"/"+periodNow;
        BorrowRecoverPlanResponse response = restTemplate.getForEntity(url,BorrowRecoverPlanResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public PlanDetailCustomizeVO getPlanDetailByPlanNid(String planId) {
        HjhPlanDetailResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/projectlist/web/searchPlanDetail/" + planId,HjhPlanDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /**
     * 获取app首页的汇计划列表
     * @author zhangyk
     * @date 2018/7/9 11:19
     */
    @Override
    public List<HjhPlanCustomizeVO> getAppHomePlanList(HjhPlanRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhPlan/selectAppHjhPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }



    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhDebtCreditListByAccedeOrderId/",
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 判断剩余待清算金额是否全部债转
     * 查询条件：PlanOrderIdEqualTo(accedeOrderId)
     *           BorrowNidEqualTo(borrowNid)
     *           CreditStatusNotEqualTo(3)
     *
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId, String borrowNid) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhDebtCreditListByOrderIdNid/" + accedeOrderId + "/" + borrowNid,
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

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
     * 根据borrowNid和orderStatus查询债转列表
     * String borrowNid   不可空
     * List<Integer> creditStatus;  可空
     * @author zhangyk
     * @date 2018/6/29 14:15
     */
    @Override
    public List<HjhDebtCreditVO> selectHjhDebtCreditListByBorrowNidAndStatus(DebtCreditRequest request) {
        HjhDebtCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhDebtCredit/selectHjhDebtCreditListByBorrowNidAndStatus",request,HjhDebtCreditResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer countCreditTenderByBorrowNidAndUserId(Map<String, Object> map) {
        HjhDebtCreditResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhDebtCredit/countCreditTenderByBorrowNidAndUserId",map,HjhDebtCreditResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
        }
        return null;
    }


    @Override
    public AppCreditDetailCustomizeVO selectHjhCreditByCreditNid(String creditNid) {
        HjhAppCreditResponse response = restTemplate.getForEntity("http://AM-TRADE//am-trade/hjhDebtCredit/selectCreditDetailBycreditNid/" + creditNid,HjhAppCreditResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 获取用户当前持有债权列表
     * @param request
     * @return
     */
    @Override
    public List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectCurrentHoldObligatoryRightList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCurrentHoldObligatoryRightList();
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
        String url = urlBase +"assetmanage/selectCurrentHoldObligatoryRightListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getCurrentHoldObligatoryRightCount();
        }
        return 0;
    }
    /**
     * 获取用户已回款债权列表总数
     * @param request
     * @return
     */
    @Override
    public int selectRepaymentListTotal(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectRepaymentListTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentCount();
        }
        return 0;
    }
    /**
     * 获取用户已回款债权列表总数(产品加息需求迁移时添加)
     * @param request
     * @return
     */
    @Override
    public int selectRepaymentListTotalWeb(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectRepaymentListTotalWeb";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getRepayMentCount();
        }
        return 0;
    }

    /**
     * 获取用户债权转让列表总数
     * @param request
     * @return
     */
    @Override
    public int countCreditRecordTotal(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/countCreditRecordTotal";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getTenderCreditDetailCount();
        }
        return 0;
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
     * 获取用户已回款债权列表
     * @param request
     * @return
     */
    @Override
    public List<RepayMentListCustomizeVO> selectRepaymentList(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectRepaymentList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getRepayMentList();
        }
        return null;
    }
    /**
     *获取用户债权转让列表
     * @param request
     * @return
     */
    @Override
    public List<TenderCreditDetailCustomizeVO> selectCreditRecordList(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectCreditRecordList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getCreditRecordList();
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
        String url = urlBase +"assetmanage/countCurrentHoldPlanTotal";
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
        String url = urlBase +"assetmanage/selectCurrentHoldPlanList";
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
        String url = urlBase +"assetmanage/countRepayMentPlanTotal";
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
        String url = urlBase +"assetmanage/selectRepayMentPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getRepayMentPlanList();
        }
        return null;
    }
    /**
     * @Description 获取交易类型列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<AccountTradeVO> selectTradeTypes() {
        String url = urlBase+"accountTrade/selectTradeTypes";
        AccountTradeResponse response = restTemplate.getForEntity(url,AccountTradeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * @Description "获取用户收支明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public int countUserTradeRecordTotal(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/countUserTradeRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getUserTradesCount();
        }
        return 0;
    }
    /**
     * @Description 获取用户收支明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<WebUserTradeListCustomizeVO> searchUserTradeList(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/searchUserTradeList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getUserTrades();
        }
        return null;
    }
    /**
     * @Description 获取用户充值列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public int countUserRechargeRecordTotal(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/countUserRechargeRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getRechargeListCount();
        }
        return 0;
    }
    /**
     * @Description 获取用户充值列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<WebUserRechargeListCustomizeVO> searchUserRechargeList(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/searchUserRechargeList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getRechargeList();
        }
        return null;
    }
    /**
     * @Description 获取用户提现列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public int countUserWithdrawRecordTotal(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/countUserWithdrawRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getWithdrawListCount();
        }
        return 0;
    }
    /**
     * @Description 获取用户提现列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public List<WebUserWithdrawListCustomizeVO> searchUserWithdrawList(TradeDetailBeanRequest request) {
        String url = urlBase+"tradedetail/searchUserWithdrawList";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getWithdrawList();
        }
        return null;
    }

    /**
     * 根据优惠券查询出借信息
     * @param couponTenderNid
     * @return
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
        BorrowTenderCpnResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponperiodrepay/getcoupontenderinfo/" + couponTenderNid,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户优惠券出借信息
     *
     * @param userId
     * @param borrowNid
     * @param logOrdId
     * @param couponGrantId
     * @return
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId) {
        String url = "http://AM-TRADE/am-trade/coupon/getCouponTenderByTender/"+userId+"/"+borrowNid+"/"+logOrdId+"/"+couponGrantId;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        String url = "http://AM-TRADE/am-trade/borrowTender/updateborrowtendercn";
        BorrowTenderCpnResponse response = restTemplate.postForEntity(url,borrowTenderCpn,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return 0;
    }

    /**
     * @param couponGrantId
     * @param userId
     * @Description 根据优惠券ID和用户ID查询优惠券
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 16:20
     */
    @Override
    public CouponUserVO getCouponUser(Integer couponGrantId, Integer userId) {
        String url = "http://AM-TRADE/am-trade/coupon/getCouponUser/" + couponGrantId + "/" + userId;
        CoupUserResponse response = restTemplate.getForEntity(url, CoupUserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 散标优惠券出借
     *
     * @param couponTender
     * @return
     */
    @Override
    public boolean updateCouponTender(CouponTenderVO couponTender) {
        Integer result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/updateCouponTender", couponTender, Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId) {
        HjhCouponLoansResponse response = new HjhCouponLoansResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhlist/"+orderId, HjhCouponLoansResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId) {
        HjhCouponLoansResponse response = new HjhCouponLoansResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhcoupononlylist/"+couponOrderId, HjhCouponLoansResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int countByExample(String tenderNid) {
        CouponResponse response = new CouponResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/couponConfig/countbytendernid/"+tenderNid, CouponResponse.class).getBody();
        if (response != null) {
            return response.getTotalRecord();
        }
        return 0;
    }

    @Override
    public Integer crRecoverPeriod(String tenderNid, int currentRecoverFlg, int period) {
        CouponResponse response = new CouponResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/couponConfig/crrecoverperiod/"+tenderNid+"/"+currentRecoverFlg+"/"+period, CouponResponse.class).getBody();
        if (response != null) {
            return response.getTotalRecord();
        }
        return 0;
    }

    @Override
    public CouponConfigVO selectCouponConfig(String couponCode) {
        CouponConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/selectCouponConfig/" + couponCode, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public BestCouponListVO selectBestCoupon(MyCouponListRequest request) {
        MyBestCouponListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/myBestCouponList", request,MyBestCouponListResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer countAvaliableCoupon(MyCouponListRequest request) {
        MyBestCouponListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/countAvaliableCoupon",request, MyBestCouponListResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCouponCount();
        }
        return 0;
    }

    @Override
    public Integer checkCouponSendExcess(String couponCode) {
        CouponConfigCustomizeResponse cccr = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/checkCouponSendExcess/"+couponCode,CouponConfigCustomizeResponse.class).getBody();
        if (Response.isSuccess(cccr)) {
            return cccr.getCount();
        }
        return null;
    }

    /**
     * 查询汇计划最优优惠券
     *
     * @param request
     * @return
     */
    @Override
    public BestCouponListVO selectHJHBestCoupon(MyCouponListRequest request) {
        MyBestCouponListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/selectHJHBestCoupon",request, MyBestCouponListResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询HJH可用优惠券数量
     *
     * @param request
     * @return
     */
    @Override
    public Integer countHJHAvaliableCoupon(MyCouponListRequest request) {
        Integer response = restTemplate.postForEntity("http://AM-TRADE/am-trade/coupon/getHJHUserCouponAvailableCount", request,Integer.class).getBody();
        return response;
    }

    @Override
    public CouponConfigVO getCouponConfig(String ordId) {
        CouponConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/getcouponconfigbyorderid/" + ordId, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public CouponConfigVO getCouponConfigById(String couponId) {
        CouponConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/getCouponConfigById/" + couponId, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer getCouponProfitTime(String tenderNid) {
        CouponConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/getcouponprofittime/" + tenderNid, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public Integer insertSelective(CouponRecoverVO cr) {
        CouponConfigResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/couponConfig/insertcouponrecover" ,cr, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public int updateOfLoansTenderHjh(AccountVO account) {
        CouponConfigResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/couponConfig/updateloanstenderhjh" ,account, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId) {
        CouponTenderCustomizeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/getcoupontenderlisthjh/"+orderId , CouponTenderCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public CouponRecoverVO updateByPrimaryKeySelective(CouponRecoverVO cr) {
        CouponRecoverResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/couponConfig/updatecouponrecoverhjh" ,cr, CouponRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<TransferExceptionLogVO> selectByRecoverId(int recoverId) {
        TransferExceptionLogResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/selectbyrecoverid/"+recoverId , TransferExceptionLogResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer insertTransferExLog(TransferExceptionLogVO transferExceptionLog) {
        TransferExceptionLogResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/couponConfig/insertTransferexloghjh" ,transferExceptionLog, TransferExceptionLogResponse.class).getBody();
        if (response != null) {
            return response.getFlag();
        }
        return 0;
    }

    /**
     * 判断用户所处的渠道如果不允许债转，可债转金额为0  start
     *
     * @param userId
     * @return
     */
    @Override
    public boolean isAllowChannelAttorn(Integer userId) {
        // 根据userId获取用户注册推广渠道
        UtmPlatResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/user/selectUtmPlatByUserId/" + userId,UtmPlatResponse.class)
                .getBody();
        if (Response.isSuccess(response) && response.getResult() != null && response.getResult().getAttornFlag()==0) {
            return false;
        }
        return true;
    }

    /**
     * 获取可债转金额   转让中本金   累计已转让本金
     *
     * @param userId
     * @return
     */
    @Override
    public CreditPageVO selectCreditPageMoneyTotal(Integer userId) {
        // 根据userId获取用户注册推广渠道
        CreaditPageResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_page_money_total/" + userId,CreaditPageResponse.class)
                .getBody();
        if (Response.isSuccess(response) ) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 查询可债转列表数量
     *
     * @param request
     * @return
     */
    @Override
    public MyCreditListQueryResponse countMyCreditList(MyCreditListQueryRequest request) {
        MyCreditListQueryResponse response =  restTemplate.postForEntity("http://AM-TRADE/am-trade/creditTender/countMyCreditList",request,MyCreditListQueryResponse.class).getBody();
        return response;
    }

    /**
     * 查询可债转列表
     *
     * @param request
     * @return
     */
    @Override
    public MyCreditListQueryResponse searchMyCreditList(MyCreditListQueryRequest request) {
        logger.info("查询可转让列表  参数 : {} ",JSONObject.toJSONString(request));
        MyCreditListQueryResponse response =  restTemplate.postForEntity("http://AM-TRADE/am-trade/creditTender/seachMyCreditList",request,MyCreditListQueryResponse.class).getBody();
        return response;
    }

    /**
     * 查询债转详情
     *
     * @param userId
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    @Override
    public TenderCreditCustomizeVO selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectTenderToCreditDetail/" + userId + "/" + borrowNid + "/" + tenderNid;
        MyCreditListQueryResponse response = restTemplate.getForEntity(url, MyCreditListQueryResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 债转详细预计服务费计算
     *
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    @Override
    public ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/selectExpectCreditFee/" + borrowNid + "/" + tenderNid ;
        ExpectCreditFeeResponse response = restTemplate.getForEntity(url, ExpectCreditFeeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 验证出借人当天是否可以债转
     *
     * @param userId
     * @return
     */
    @Override
    public Integer tenderAbleToCredit(Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/get_tender_credit_count/" + userId ;
        Integer response = restTemplate.getForEntity(url, Integer.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据出借订单号检索已债转还款信息
     *
     * @param tenderId
     * @return
     */
    @Override
    public List<CreditRepayVO> selectCreditRepayList(Integer tenderId) {
        CreditRepayResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_repay_list/" + tenderId , CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据assignNid查询还款信息
     * @author zhangyk
     * @date 2018/9/25 17:30
     */
    @Override
    public List<CreditRepayVO> selectCreditRepayListByAssignNid(String assignNid) {
        CreditRepayResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/creditTender/select_credit_repay_list_by_assignNid/" + assignNid , CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditRepayVO> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status) {
        String url = "http://AM-TRADE/am-trade/creditTender/select_credit_repay_list/" + borrowNid + "/" + tenderOrderId + "/" + periodNow + "/" + status;
        CreditRepayResponse response = restTemplate.getForEntity(url, CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 插入债转  我要债转
     *
     * @param borrowCredit
     */
    @Override
    public Integer insertCredit(BorrowCreditVO borrowCredit) {
        String url = "http://AM-TRADE/am-trade/creditTender/save_credit_tender";
        IntegerResponse response = restTemplate.postForEntity(url, borrowCredit, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return null;
    }

    /**
     * 前端Web页面出借可债转输入出借金额后收益提示 用户未登录 (包含查询条件)
     *
     * @param creditNid
     * @param assignCapital
     * @param userId
     * @return
     */
    @Override
    public TenderToCreditAssignCustomizeVO getInterestInfo(String creditNid, String assignCapital, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/get_interest_info/" + creditNid + "/" + assignCapital + "/" + userId;
        CreditAssignCustomizeResponse response = restTemplate.getForEntity(url, CreditAssignCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取债转数据
     *
     * @param creditNid
     * @return
     */
    @Override
    public BorrowCreditVO getBorrowCreditByCreditNid(String creditNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/get_borrow_credit_by_credit_nid/" + creditNid ;
        BorrowCreditResponse response = restTemplate.getForEntity(url, BorrowCreditResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 债转修改日志表状态
     *
     * @param logOrderId
     * @param logUserId
     * @param retCode
     * @param retMsg
     */
    @Override
    public Integer updateCreditTenderResult(String logOrderId, String logUserId, String retCode, String retMsg) {
        String url = "http://AM-TRADE/am-trade/creditTender/updateCreditTenderResult/" + logOrderId+"/"+logUserId+"/"+retCode+"/"+retMsg ;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 查询债转失败原因
     *
     * @param logOrdId
     * @param userId
     * @return
     */
    @Override
    public String getFailResult(String logOrdId, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/getFailResult/" + logOrdId+"/"+userId ;
        StringResponse response = restTemplate.getForEntity(url, StringResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultStr();
        }
        return "";
    }

    /**
     * 根据logOrdId和userId 查询债转信息
     *
     * @param logOrdId
     * @param userId
     * @return
     */
    @Override
    public CreditTenderVO getCreditTenderByUserIdOrdId(String logOrdId, Integer userId) {
        String url = "http://AM-TRADE/am-trade/creditTender/getCreditTenderByUserIdOrdId/" + logOrdId + "/" + userId;
        CreditTenderResponse response = restTemplate.getForEntity(url, CreditTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public Integer getUserCouponCount(Integer userId, String usedFlag) {
        CouponUserResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponUser/user_coupon_count/" + userId + "/" + usedFlag, CouponUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return 0;
    }

    /**
     * 获取当前有效的冻结记录
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public BankRepayFreezeLogVO getFreezeLogValid(Integer userId, String borrowNid) {
        StringBuilder url = new StringBuilder("http://AM-TRADE/am-trade/repayfreezelog/get_logvalid/");
        if(userId != null){
            url.append(userId).append("/");
        }
        url.append(borrowNid);
        BankRepayFreezeLogResponse response = restTemplate.getForEntity(url.toString(),BankRepayFreezeLogResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据orderId删除
     * @param orderId
     * @return
     */
    @Override
    public Integer deleteFreezeLogByOrderId(String orderId) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/deleteby_orderid/" + orderId;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 添加
     * @param requestBean
     * @return
     */
    @Override
    public Integer addFreezeLog(BankRepayFreezeLogRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/add";
        IntegerResponse response = restTemplate.postForEntity(url, requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据code获取borrowConfig
     * @auther: hesy
     * @date: 2018/7/20
     */
    @Override
    public BorrowConfigVO getConfigByCode(String code) {
        String url = "http://AM-TRADE/am-trade/borrowconfig/get_by_code/" + code;
        BorrowConfigResponse response = restTemplate.getForEntity(url,BorrowConfigResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 出借撤销历史数据处理
     * @param request
     * @return
     */
    @Override
    public boolean updateBidCancelRecord(TenderCancelRequest request) {
        String url = "http://AM-TRADE/am-trade/bankException/updateBidCancelRecord";
        return restTemplate.postForEntity(url,request,Boolean.class).getBody();

    }

    /**
     * 处理撤销出现异常的数据
     * @param info
     * @return
     */
    @Override
    public boolean updateTenderCancelExceptionData(BorrowTenderTmpVO info) {
        String url = "http://AM-TRADE/am-trade/bankException/updateTenderCancelExceptionData";
        return restTemplate.postForEntity(url,info,Boolean.class).getBody();
    }

    /**
     * 查询前一天的出借临时数据并进行处理
     * @return
     */
    @Override
    public List<BorrowTenderTmpVO> getBorrowTenderTmpsForTenderCancel() {
        String url = "http://AM-TRADE/am-trade/bankException/getBorrowTenderTmpsForTenderCancel";
        BorrowTenderTmpResponse response = restTemplate.getForEntity(url, BorrowTenderTmpResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResultList();
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
     * 用户待还款/已还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> repayList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/repay/repaylist",requestBean,
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 垫付机构待还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> orgRepayList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/repay/orgrepaylist",requestBean,
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 垫付机构已还款列表
     * @param requestBean
     * @return
     */
    @Override
    public List<RepayListCustomizeVO> orgRepayedList(RepayListRequest requestBean) {
        RepayListResponse response = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/repay/orgrepayedlist",requestBean,
                RepayListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 用户待还款/已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int repayCount(RepayListRequest requestBean) {
        IntegerResponse response = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/repaycount", requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 垫付机构待还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int orgRepayCount(RepayListRequest requestBean) {
        IntegerResponse response = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/orgrepaycount", requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 垫付机构已还款总记录数
     * @param requestBean
     * @return
     */
    @Override
    public int orgRepayedCount(RepayListRequest requestBean) {
        IntegerResponse response = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/orgrepayedcount", requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 垫付机构本期应还总额
     * @param requestBean
     * @return
     */
    @Override
    public BigDecimal orgRepayWaitTotalCurrent(RepayListRequest requestBean) {
        BigDecimalResponse response = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/orgrepay_waittotal_current", requestBean, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 还款申请更新
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public Boolean repayRequestUpdate(RepayRequestUpdateRequest requestBean){
        BooleanResponse response = restTemplate
                .postForEntity( "http://AM-TRADE/am-trade/repay/update", requestBean, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

    @Override
    public BorrowRecoverPlanVO selectRecoverPlanById(Integer id) {
        BorrowRecoverPlanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/recoverplan/get/"+id,BorrowRecoverPlanResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverPlanVO> selectRecoverPlan(String borrowNid, Integer period) {
        BorrowRecoverPlanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/recoverplan/getby_borrownid_period/"+borrowNid + "/" + period,BorrowRecoverPlanResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询相应的汇计划债转还款记录
     * @param borrowNid
     * @param tenderOrderId
     * @param periodNow
     * @param status
     * @return
     */
    @Override
    public List<HjhDebtCreditRepayVO> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status) {
        String url = "http://AM-TRADE/am-trade/get/" + borrowNid + "/" + tenderOrderId + "/" + periodNow + "/" + status;
        HjhDebtCreditRepayResponse response = restTemplate.getForEntity(url, HjhDebtCreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     *  微信端查询用户当前持有项目列表
     * @param request
     * @return
     */
    @Override
    public QueryMyProjectVO selectWechatCurrentHoldObligatoryRightList(WechatMyProjectRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectWechatCurrentHoldObligatoryRightList";
        QueryMyProjectVOResponse response = restTemplate.postForEntity(url,request, QueryMyProjectVOResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }
    /**
     *  微信端查询用户已回款项目列表
     * @param request
     * @return
     */
    @Override
    public QueryMyProjectVO selectWechatRepaymentList(WechatMyProjectRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectWechatRepaymentList";
        QueryMyProjectVOResponse response = restTemplate.postForEntity(url,request, QueryMyProjectVOResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }
    /**
     *  微信端查询用户债转项目列表
     * @param request
     * @return
     */
    @Override
    public QueryMyProjectVO selectWechatCreditRecordList(WechatMyProjectRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectWechatCreditRecordList";
        QueryMyProjectVOResponse response = restTemplate.postForEntity(url,request, QueryMyProjectVOResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }
    /**
     *  微信端查询用户当前持有计划列表
     * @param request
     * @return
     */
    @Override
    public QueryMyProjectVO selectWechatCurrentHoldPlanList(WechatMyProjectRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectWechatCurrentHoldPlanList";
        QueryMyProjectVOResponse response = restTemplate.postForEntity(url,request, QueryMyProjectVOResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
        }
        return null;
    }
    /**
     *  微信端查询用户已回款计划列表
     * @param request
     * @return
     */
    @Override
    public QueryMyProjectVO selectWechatRepayMentPlanList(WechatMyProjectRequest request) {
        String url = "http://AM-TRADE/am-trade/assetmanage/selectWechatRepayMentPlanList";
        QueryMyProjectVOResponse response = restTemplate.postForEntity(url,request, QueryMyProjectVOResponse.class).getBody();
        if (Validator.isNotNull(response)) {
            return response.getResult();
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
     * 交易类型
     * @return
     */
    @Override
    public List<AppAccountTradeListCustomizeVO> searchAppTradeTypes() {
        String url = "http://AM-TRADE/am-trade/accountTrade/searchAppTradeTypes";
        AppAccountTradeListCustomizeResponse response = restTemplate.getForEntity(url,AppAccountTradeListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }


    @Override
    public List<AppAlreadyRepayListCustomizeVO> selectAppAlreadyRepayList(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectAppAlreadyRepayList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getAppAlreadyRepayList();
        }
        return null;
    }

    @Override
    public List<AppTenderCreditRecordListCustomizeVO> searchAppCreditRecordList(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/searchAppCreditRecordList";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getAppTenderCreditRecordList();
        }
        return null;
    }
    
	/**
	 * 获取债转承接信息
	 * @param nid
	 * by libin
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid) {
		String url = "http://AM-TRADE/am-trade/hjhDebtCredit/getHjhDebtCreditTenderByPrimaryKey/"+nid;
        HjhDebtCreditTenderResponse response = restTemplate.getForEntity(url,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
		return null;
	}

    @Override
    public Integer selectTenderToCreditListCount(AssetManageBeanRequest request) {
        String url = urlBase +"assetmanage/selectTenderToCreditListCount";
        AssetManageResponse response = restTemplate.postForEntity(url,request,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getTenderCreditDetailCount();
        }
        return 0;
    }

    @Override
    public Integer countAppMyPlan(AssetManageBeanRequest params) {
        String url = urlBase +"assetmanage/countAppMyPlan";
        AssetManageResponse response = restTemplate.postForEntity(url,params,AssetManageResponse.class).getBody();
        if (response != null) {
            return response.getAppMyPlanCount();
        }
        return 0;
    }

    @Override
    public List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params) {
        String url = urlBase +"assetmanage/selectAppMyPlanList";
        AssetManageResponse response = restTemplate.postForEntity(url,params,AssetManageResponse.class).getBody();

        if (response != null) {
            return response.getAppMyPlanCustomizeList();
        }
        return null;
    }

    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    @Override
    @Cached(name="appPlanDetailCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 300, timeUnit = TimeUnit.SECONDS)
    public int countPlanBorrowRecordTotal(Map<String, Object> params) {
	    String url = urlBase+"hjhPlan/countPlanBorrowRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }

    /**
     * 查询相应的计划标的记录列表
     * @param params
     * @return
     */
    @Override
    @Cached(name="appPlanDetailListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 300, timeUnit = TimeUnit.SECONDS)
    public List<DebtPlanBorrowCustomizeVO> selectPlanBorrowList(Map<String, Object> params) {
        String url = urlBase+"hjhPlan/selectPlanBorrowList";
        DebtPlanBorrowCustomizeResponse response = restTemplate.postForEntity(url,params,DebtPlanBorrowCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 散标出借记录数
     * @param params
     * @return
     */
    @Override
    public int countProjectInvestRecordTotal(Map<String, Object> params) {
        String url = BASE_URL +"/app/countProjectInvestRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }

    /**
     * 获取borrowId对应的总钱数
     * @param params
     * @return
     */
    @Override
    public String countMoneyByBorrowId(Map<String, Object> params) {
        String url = urlBase + "borrowTender/countMoneyByBorrowId";
        return restTemplate.postForEntity(url,params,String.class).getBody();
    }

    /**
     * 散标出借记录
     * @param params
     * @return
     */
    @Override
    public List<AppProjectInvestListCustomizeVO> selectProjectInvestList(Map<String, Object> params) {
        String url = BASE_URL +"/app/selectProjectInvestList";
        AppProjectInvestListCustomizeResponse response = restTemplate.postForEntity(url,params,AppProjectInvestListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

	/**
	 * 获取债转承接信息ByAssignOrderId
	 * by libin
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId) {
		String url = "http://AM-TRADE/am-trade/hjhDebtCredit/getHjhDebtCreditTenderByAssignOrderId/"+assignOrderId;
        HjhDebtCreditTenderResponse response = restTemplate.getForEntity(url,HjhDebtCreditTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResult();
        }
		return null;
	}
	
	/**
	 * 获取债转承接信息AssignNid
	 * by libin
	 * @return
	 */
	@Override
	public CreditTenderVO getCreditTenderByAssignNid(String assignNid) {
        CreditTenderResponse response =
                restTemplate.getForEntity("http://AM-TRADE/am-trade/bankException/getCreditTenderByAssignNid/"+assignNid,
                        CreditTenderResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
		return null;
	}

	/**
	 * 获取协议模板by DisplayName
	 * @return
	 */
	@Override
	public List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName) {
		String url = "http://AM-TRADE/am-trade/protocol/getProtocolTemplateVOByDisplayName/"+displayName;
		ProtocolTemplateResponse response = restTemplate.getForEntity(url, ProtocolTemplateResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
		return null;
	}

    /**
     * 获得最新协议模板数据
     * @return
     */
    @Override
    public List<ProtocolTemplateVO> getNewInfo() {
        ResponseEntity<Response<ProtocolTemplateVO>> response =
                restTemplate.exchange("http://AM-TRADE/am-trade/protocol/getnewinfo", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Response<ProtocolTemplateVO>>() {});

        List<ProtocolTemplateVO> vo = null;
        if(response.getBody().getResultList().size() > 0){

            vo =  response.getBody().getResultList();
        }
        return vo;
    }

    /**
     * 统计相应的计划总数
     * @param params
     * @return
     */
    @Override
    @Cached(name="appAndWxPlanAccedeSumCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 300, timeUnit = TimeUnit.SECONDS)
    public Long selectPlanAccedeSum(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/hjhPlan/selectPlanAccedeSum";
        return restTemplate.postForEntity(url,params,Long.class).getBody();
    }


    /**
     * 查询相应的计划的加入列表
     * @param params
     * @return
     */
    @Override
    public List<DebtPlanAccedeCustomizeVO> selectPlanAccedeList(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/hjhPlan/selectPlanAccedeList";
        DebtPlanAccedeCustomizeResponse response = restTemplate.postForEntity(url,params,DebtPlanAccedeCustomizeResponse.class).getBody();
        if (response!=null){
             return response.getResultList();
        }
        return null;
    }

    @Override
    public AppNewAgreementVO setProtocolImg(String aliasName) {
        String url = "http://AM-TRADE/am-trade/new/agreement/setProtocolImg/" + aliasName;
        AppNewAgreementResponse response = restTemplate.getForEntity(url, AppNewAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * app端债转承接记录数
     * @param params
     * @return
     */
    @Override
    public int countTenderCreditInvestRecordTotal(Map<String, Object> params) {
        String url = BASE_URL + "/app/countTenderCreditInvestRecordTotal";
        return restTemplate.postForEntity(url,params,Integer.class).getBody();
    }

    /**
     * 获取债转承接记录
     * @param params
     * @return
     */
    @Override
    public List<AppTenderCreditInvestListCustomizeVO> searchTenderCreditInvestList(Map<String, Object> params) {
        String url = BASE_URL + "/app/searchTenderCreditInvestList";
        AppTenderCreditInvestListCustomizeResponse response=restTemplate.postForEntity(url,params,AppTenderCreditInvestListCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取债转出借人次和已债转金额
     * @param transferId
     * @return
     */
    @Override
    public List<BorrowCreditVO> selectBorrowCreditByNid(String transferId) {
        String url = BASE_URL + "/app/selectBorrowCreditByNid/"+transferId;
        BorrowCreditResponse response=restTemplate.getForEntity(url,BorrowCreditResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowProjectTypeVO> selectBorrowProjectByBorrowCd(String borrowCd) {
        BorrowProjectTypeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectBorrowProjectByBorrowCd/" + borrowCd, BorrowProjectTypeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int countAppTradeDetailListRecordTotal(AppTradeDetailBeanRequest request) {
        String url = "http://AM-TRADE/am-trade/tradedetail/countAppTradeDetailListRecordTotal";
        TenderDetailResponse response = restTemplate.postForEntity(url,request,TenderDetailResponse.class).getBody();
        if (response != null) {
            return response.getAppTradeDetailListCount();
        }
        return 0;
    }

    @Override
    public List<AppTradeListCustomizeVO> searchAppTradeDetailList(AppTradeDetailBeanRequest trade) {
        String url = "http://AM-TRADE/am-trade/tradedetail/searchAppTradeDetailList";
        TenderDetailResponse response = restTemplate.postForEntity(url,trade,TenderDetailResponse.class).getBody();

        if (response != null) {
            return response.getAppTradeList();
        }
        return null;
    }

    /**
     * 普通用户管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getUserRepayFeeWaitTotal(Integer userId) {
        BigDecimalResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/feewait_total_user/" + userId, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 担保机构管理费总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayFeeWaitTotal(Integer userId) {
        BigDecimalResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/feewait_total_org/" + userId, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 担保机构总待还
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOrgRepayWaitTotal(Integer userId) {
        BigDecimalResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/repaywait_total_org/" + userId, BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }


    /**
     * 查询广告列表
     * @author zhangyk
     * @date 2018/7/5 15:32
     */
    @Override
    public List<AppAdsCustomizeVO> getBannerList(AdsRequest request) {
        AppAdsCustomizeResponse response = restTemplate.postForEntity("http://AM-MARKET/am-market/ads/searchBanner",request,AppAdsCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            List<AppAdsCustomizeVO> list = new ArrayList<>();
            if (response.getResult() != null){
                list.add(response.getResult());
            }
            return list;
        }
        return null;
    }


    /**
     * 查询移动端首页bannerlist
     * @author zhangyk
     * @date 2018/10/12 11:08
     * 微信APP首页banner添加缓存--by libin
     */
    @Override
	@Cached(name="app&wechatHomeBannerCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
	@CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<AppAdsCustomizeVO> getHomeBannerList(AdsRequest request) {
        AppAdsCustomizeResponse response = restTemplate.postForEntity("http://AM-MARKET/am-market/ads/searchHomeBanner",request,AppAdsCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
           return response.getResultList();
        }
        return null;
    }

    /**
     * 获取承接中的总额度
     * @author zhangyk
     * @date 2018/8/9 11:48
     */
    @Override
    public String sumUndertakeAccount(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCredit/sumUndertakeAmount/" + borrowNid;
        HjhDebtCreditResponse response = restTemplate.getForEntity(url,HjhDebtCreditResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getSum();
        }
        return null;
    }


    /**
     * 承接中的列表
     * @author zhangyk
     * @date 2018/8/9 13:58
     */
    @Override
    public List<ProjectUndertakeListVO> selectProjectUndertakeList(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/hjhDebtCredit/selectProjectUndertakeList";
        HjhCreditUnderTakeResponse response = restTemplate.postForEntity(url,params,HjhCreditUnderTakeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查看机构表是否存在
     *
     * @param instCode
     * @param assetType
     * @return
     */
    @Override
    public HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectAssetBorrowType/" + instCode + "/" + assetType;
        HjhAssetBorrowTypeResponse response = restTemplate.getForEntity(url, HjhAssetBorrowTypeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取还款方式,项目类型
     *
     * @param borrowcCd
     * @return
     */
    @Override
    public List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectProjectRepay/" + borrowcCd;
        BorrowProjectRepayReponse response = restTemplate.getForEntity(url, BorrowProjectRepayReponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询用户受托支付电子账户是否授权
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    @Override
    public STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId) {
        String url = "http://AM-TRADE/am-trade/assetPush/selectStzfWhiteList/" + instCode + "/" + entrustedAccountId;
        STZHWhiteListResponse response = restTemplate.getForEntity(url, STZHWhiteListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 推送资产
     *
     * @param record
     * @return
     */
    @Override
    public int insertAssert(HjhPlanAssetVO record) {
        String url = "http://AM-TRADE/am-trade/assetPush/insertAssert";
        Integer result = restTemplate.postForEntity(url, record, Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }

    /**
     * 推送商家信息
     *
     * @param riskInfo
     */
    @Override
    public void insertRiskInfo(List<InfoBean> riskInfo) {
        String url = "http://AM-TRADE/am-trade/assetPush/insertRiskInfo";
        restTemplate.postForEntity(url, riskInfo, Integer.class).getBody();
    }

    /**
     * 检查是否存在重复资产
     *
     * @param assetId
     * @return
     */
    @Override
    public List<HjhPlanAssetVO> checkDuplicateAssetId(String assetId) {
        String url = "http://AM-TRADE/am-trade/assetPush/checkDuplicateAssetId/" + assetId;
        HjhPlanAssetResponse response = restTemplate.getForEntity(url, HjhPlanAssetResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 录标时添加企业资产
     *
     * @param borrowUserVO
     * @return
     */
    @Override
    public int insertCompanyInfoToBorrowUsers(BorrowUserVO borrowUserVO) {
        String url = urlBase + "assetPush/insertCompanyInfoToBorrowUsers";
        IntegerResponse response = restTemplate.postForEntity(url, borrowUserVO, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
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

    /**
     * 获取还款详情页面数据
     * @auther: hesy
     * @date: 2018/8/7
     */
    @Override
    public JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repay/repay_detail";
        StringResponse response = restTemplate.postForEntity(url,requestBean,StringResponse.class).getBody();

        if (Response.isSuccess(response)) {
            return JSON.parseObject(response.getResultStr());
        }
        return null;
    }

    /**
     * 获取计算完的还款Bean
     * @param paraMap
     * @return
     */
    @Override
    public RepayBean getRepayBean(Map<String, String> paraMap) {
        StringResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/repay/get_repaybean",paraMap,StringResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return JSON.parseObject(response.getResultStr(),RepayBean.class);
        }
        return null;
    }

    /**
     * 获取批量还款页面数据
     */
    @Override
    public ProjectBean getOrgBatchRepayData(BatchRepayDataRequest requestBean) {
        StringResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/repay/get_batch_reapydata",requestBean,StringResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return JSON.parseObject(response.getResultStr(),ProjectBean.class);
        }
        return null;
    }

    /**
     * 计算担保机构批量还款总垫付金额并插入冻结日志
     */
    @Override
    public BigDecimal getOrgBatchRepayTotal(BatchRepayTotalRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repay/get_batch_reapy_total";
        BigDecimalResponse response =restTemplate.postForEntity(url,requestBean,BigDecimalResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultDec();
        }
        return BigDecimal.ZERO;
    }

    /**
     * 根据订单号获取汇计划加入明细
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectHjhAccedeListByOrderId(String accedeOrderId) {
        HjhAccedeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/batchHjhBorrowRepay/selectHjhAccedeListByOrderId/" + accedeOrderId, HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取提成配置信息
     * @param map
     * @return
     */
    @Override
    public Integer getCommisionConfig(HashMap map) {
        IntegerResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/planLockQuit/getCommisionConfig/", map,
                IntegerResponse.class).getBody();
        if (Response.isSuccess(result)) {
            return result.getResultInt();
        }
        return null;
    }

    /**
     *根据订单编号取得该订单的还款列表
     */
    @Override
    public CouponRecoverCustomizeVO selectCurrentCouponRecover(String couponTenderNid, int periodNow) {
        String url = "http://AM-TRADE/am-trade/couponperiodrepay/selectcurrentcouponrecover/" + couponTenderNid + "/" +periodNow;
        logger.info("CouponRecoverCustomizeVO url is :{} ", url);
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新优惠券还款
     * @param cr
     * @return
     */
    @Override
    public boolean updateCouponRecover(CouponRecoverVO cr) {
        String url = "http://AM-TRADE//am-trade/couponPeriodRepay/updatecouponrecover";
        Integer result = restTemplate.postForEntity(url,cr,Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 体验金按收益期限还款
     * @param request
     */
    @Override
    public boolean updateCouponOnlyRecover(CouponRecoverCustomizeRequest request) {
        String url = "http://AM-TRADE/am-trade/couponperiodrepay/updatecoupononlyrecover";
        Integer result = restTemplate.postForEntity(url,request,Integer.class).getBody();
        if (result != null) {
            return result == 0 ? false : true;
        }
        return false;
    }

    /**
     * 获取用户还款计划数据
     * @param borrowNid
     * @param nid
     * @param type
     * @return
     */
    @Override
    public RepayPlanInfoBean getRepayPlanInfo(String borrowNid, String nid, String type){
        RepayPlanInfoBean repayPlanInfoBean = new RepayPlanInfoBean();
        String url = "http://AM-TRADE/am-trade/assetmanage/getRepayPlanInfo/" + borrowNid + "/" + nid + "/" + type;
        RepayPlanResponse response = restTemplate.getForEntity(url,RepayPlanResponse.class).getBody();
        if(Response.isSuccess(response)){
            repayPlanInfoBean.setCurrentHoldRepayMentPlanList(response.getCurrentHoldRepayMentPlanList());
            repayPlanInfoBean.setCurrentHoldRepayMentPlanDetails(response.getCurrentHoldRepayMentPlanDetails());
        }
        return repayPlanInfoBean;
    }

    /**
     * 待计算提成加入列表
     * @return
     */
    @Override
    public List<HjhAccedeVO> getAccedesWaitCompute() {
        HjhWaitComputeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/hjhcommision/accedes_waitcompute", HjhWaitComputeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 提成计算
     * @param hjhLockVo
     * @return
     */
    @Override
    public Boolean commisionCompute(HjhLockVo hjhLockVo){
        Response response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhcommision/compute",hjhLockVo,Response.class).getBody();
        if (Response.isSuccess(response)){
            return true;
        }
        return false;
    }

    /**
     * 获取用户散标转让记录详情
     * @param creditNid
     * @return
     */
    @Override
    public MyCreditDetailBean getMyCreditAssignDetail(String creditNid){
        MyCreditDetailBean myCreditDetailBean = new MyCreditDetailBean();
        String url = "http://AM-TRADE/am-trade/assetmanage/getMyCreditAssignDetail/" + creditNid;
        MyCreditDetailResponse response = restTemplate.getForEntity(url, MyCreditDetailResponse.class).getBody();
        if(Response.isSuccess(response)){
            myCreditDetailBean.setRecordList(response.getRecordList());
            myCreditDetailBean.setAssignedStatistic(response.getAssignedStatistic());
            myCreditDetailBean.setBorrowCredit(response.getBorrowCredit());
        }
        return myCreditDetailBean;
    }
    
    /**
     *  获取协议内容bytenderNid
     * @return
     */
    @Override
    public List<TenderAgreementVO> selectTenderAgreementByTenderNid(String tenderNid) {
        String url = "http://AM-TRADE/am-trade/tenderagreement/selectTenderAgreementByTenderNid/"+tenderNid;
        TenderAgreementResponse response = restTemplate.getForEntity(url, TenderAgreementResponse.class).getBody();
        if(Validator.isNotNull(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取批次放款列表
     * @author Zha Daojian
     * @date 2018/8/27 15:37
     * @param request
     * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
     **/
    @Override
    public  List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request) {
        BatchBorrowRecoverReponse response = restTemplate.
                postForEntity("http://AM-TRADE/am-trade/apiBatchBorrowRecover/getList", request, BatchBorrowRecoverReponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取批次放款列表条数
     *
     * @param request
     * @return
     * @author Zha Daojian
     * @date 2018/8/27 15:57
     **/
    @Override
    public Integer getCountBatchCenter(BatchBorrowRecoverRequest request) {
        IntegerResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/apiBatchBorrowRecover/getListTotal/", request,
                IntegerResponse.class).getBody();
        if (result == null) {
            return 0;
        }
        return result.getResultInt();
    }

    /**
     * 第三方还款明细查询
     *
     * @param request
     * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
     * @author Zha Daojian
     * @date 2018/8/28 10:33
     **/
    @Override
    public List<ApiBorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(ApiBorrowRepaymentInfoRequest request) {
        ApiBorrowRepaymentInfoResponse response = restTemplate.
                postForEntity("http://AM-TRADE/am-trade/apiBorrowRepaymentInfo/selectBorrowRepaymentInfoList", request, ApiBorrowRepaymentInfoResponse.class).
                getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }



    /**
     * 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 14:00
     */
    @Override
    public List<ApiProjectListCustomize> getApiProjectList(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/projectlist/api/getBorrowList";
        ApiProjectListResponse response = restTemplate.postForEntity(url,params,ApiProjectListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

	/**
	 *
	 * 出借预插入
	 * @return
	 * @author libin
	 * @throws Exception
	 */
	@Override
	public boolean updateTenderLog(AutoTenderComboRequest autoTenderComboRequest) {
		IntegerResponse result = restTemplate
	            .postForEntity("http://AM-TRADE/am-trade/autotender/updatetenderlog", autoTenderComboRequest, IntegerResponse.class).getBody();
		if (result != null) {
	        return result.getResultInt().intValue() == 0 ? false : true;
	    }
		return false;
	}

    /**
	 * 根据nid删除BorrowTenderTmp
	 * @auth libin
	 * @return
	 */
	@Override
	public Integer deleteBorrowTenderTmp(String orgOrderId) {
		String url = "http://AM-TRADE/am-trade/tendercancelrepair/deleteBorrowTenderTmp/" + orgOrderId;
		IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
		return response.getResultInt().intValue();
	}

    /**
	 * 根据userId，borrowNid，orderId删除BorrowTenderTmp
	 * @auth libin
	 * @return
	 */
	@Override
	public int deleteBorrowTenderTmpByParam(int userId, String borrowNid, String orderId) {
		String url = "http://AM-TRADE/am-trade/tendercancelrepair/deleteBorrowTenderTmpByParam/" + userId + "/" + borrowNid + "/" + orderId;
		IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
	}

	/**
	 * 根据userId和tenderNid查询出借记录
	 * @author zhangyk
	 * @date 2018/8/30 10:49
	 */
	@Override
    public List<BorrowCreditVO> getBorrowCreditListByUserIdAndTenderNid(String tenderNid, String userId) {
	    String url = "http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditListByUserIdAndTenderNid/"+ userId +"/"+ tenderNid;
	    BorrowCreditResponse response = restTemplate.getForEntity(url,BorrowCreditResponse.class).getBody();
	    if (Response.isSuccess(response)){
	        return response.getResultList();
        }
        return null;
    }


    /**
     * 根据承接编号查询服务费总计
     * @author zhangyk
     * @date 2018/8/30 11:06
     */
    @Override
    public String getBorrowCreditTenderServiceFee(String creditNid) {
        String url = "http://AM-TRADE/am-trade/creditTender/getCreditTenderServiceFee/" +creditNid;
        StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }


    /**
     * 根据tenderNid查询出借记录
     * @author zhangyk
     * @date 2018/8/30 10:49
     */
    @Override
    public List<BorrowCreditVO> getBorrowCreditListByCreditNid(String creditNid) {
        String url = "http://AM-TRADE/am-trade/borrowCredit/getBorrowCreditListByCreditNid/"+ creditNid;
        BorrowCreditResponse response = restTemplate.getForEntity(url,BorrowCreditResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取回款记录信息
     *
     * @param request
     * @return List<RepayListCustomize>
     * @author Zha Daojian
     * @date 2018/9/1 13:20
     **/
    @Override
    public List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request) {
        String url = "http://AM-TRADE/am-trade/invest/searchRepayList";
        ApiRepayListResponse response = restTemplate.postForEntity(url,request,ApiRepayListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 更新资产表 upd by liushouyi
     *
     * @param hjhPlanAssetnewVO
     * @return
     */
    @Override
    public int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetnewVO) {
        IntegerResponse result = restTemplate.postForEntity("http://AM-TRADE/am-trade/assetPush/updateHjhPlanAssetnew", hjhPlanAssetnewVO, IntegerResponse.class).getBody();
        return result.getResultInt();
    }

    /**
     * 查询单个资产根据资产ID upd by liushouyi
     *
     * @param assetId
     * @param instCode
     * @return
     */
    @Override
    public HjhPlanAssetVO selectPlanAsset(String assetId, String instCode) {
        HjhPlanAssetResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectPlanAsset/" + assetId + "/" + instCode, HjhPlanAssetResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 检查是否交过保证金 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowBailVO selectBorrowBail(String borrowNid) {
        String url = urlBase + "/assetPush/select_borrow_bail/" + borrowNid;
        BorrowBailResponse response = restTemplate.getForEntity(url,BorrowBailResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 更新借款表 add by liushouyi
     *
     * @param borrow
     * @return
     */
    @Override
    public boolean updateBorrowByBorrowNid(BorrowAndInfoVO borrow) {
        String url = urlBase + "/assetPush/update_borrow_by_borrow_nid";
        IntegerResponse result = restTemplate.postForEntity(url,borrow,  IntegerResponse.class).getBody();
        return result.getResultInt() > 0 ? true : false;
    }

    /**
     * 更新借款主表
     * @author zhangyk
     * @date 2018/9/13 17:38
     */
    @Override
    public boolean updateRightBorrowByBorrowNid(RightBorrowVO borrow) {
        String url = urlBase + "/assetPush/updateRightBorrowByBorrowNid";
        IntegerResponse result = restTemplate.postForEntity(url,borrow,  IntegerResponse.class).getBody();
        return result.getResultInt() > 0 ? true : false;
    }

    /**
     * 获取系统配置 add by liushouyi
     *
     * @param configCd
     * @return
     */
    @Override
    public String getBorrowConfig(String configCd) {
        String url = urlBase + "/assetPush/select_borrow_config/" + configCd;
        StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
        if (response != null) {
            return response.getResultStr();
        }
        return null;
    }

    /**
     * 插入保证金 add by liushouyi
     *
     * @param borrowBail
     * @return
     */
    @Override
    public Integer insertBorrowBail(BorrowBailVO borrowBail) {
        String url = urlBase + "/assetPush/insert_borrow_bail/" + borrowBail;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
    }



    /**
     * 获取逾期的标的
     * @return
     */
    @Override
    public List<BorrowAndInfoVO> selectOverdueBorrowList() {
        String url = "http://AM-TRADE/am-trade/borrow/selectOverdueBorrowList";
        BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 计划锁定
     *  @param accedeOrderId
     * @param inverestUserInfo
     * @param commissioUserInfoVO
     * @param bankOpenAccountVO
     * @param userInfoCustomizeVOS
     */
    @Override
    public void updateForLock(String accedeOrderId, UserInfoVO inverestUserInfo, UserInfoVO commissioUserInfoVO, BankOpenAccountVO bankOpenAccountVO, List<UserInfoCustomizeVO> userInfoCustomizeVOS) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateLockRepayInfo";
        HjhLockVo hjhLockVo = new HjhLockVo();
        hjhLockVo.setAccedeOrderId(accedeOrderId);
        hjhLockVo.setInverestUserInfo(inverestUserInfo);
        hjhLockVo.setCommissioUserInfoVO(commissioUserInfoVO);
        hjhLockVo.setBankOpenAccountVO(bankOpenAccountVO);
        hjhLockVo.setUserInfoCustomizeVOS(userInfoCustomizeVOS);
        restTemplate.postForEntity(url,hjhLockVo,null);
    }

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    @Override
    public void updateForQuit(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/planLockQuit/updateQuitRepayInfo/" + accedeOrderId;
        restTemplate.getForEntity(url, String.class);
    }

    /**
     * 查询待退出计划的标的
     *
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectWaitQuitHjhList() {
        HjhAccedeResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhAccede/selectWaitQuitHjhList/",
                HjhAccedeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 根据nid和当前时间查询borrow
     * @author zhangyk
     * @date 2018/9/3 16:41
     */
    @Override
    public BorrowAndInfoVO getBorrowByNidAndNowTime(String borrowNid, Integer nowTime) {
        String url = "http://AM-TRADE/am-trade/borrow/getByNidAndNowTime/"+borrowNid + "/" + nowTime;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public int countAccountWithdraw(String ordId) {
        String url = "http://AM-TRADE/am-trade/account/countAccountWithdraw/"+ordId ;
        IntegerResponse response = restTemplate.getForEntity(url,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 交易明细查询
     * @return
     * @Author : huanghui
     */
    @Override
    public List<ApiTransactionDetailsCustomizeVO> selectTransactionDetails(TransactionDetailsResultBean transactionDetailsResultBean) {
        ApiTransactionDetailsCustomizeResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/accountList/selectTransactionDetails",
                transactionDetailsResultBean, ApiTransactionDetailsCustomizeResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据放款编号获取该标的的出借信息 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    @Override
    public List<BorrowTenderVO> getBorrowTenderListByBorrowNid(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderListByBorrowNid/"+borrowNid;
        BorrowTenderResponse response = restTemplate.getForEntity(url,BorrowTenderResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据合同编号查询合同要素信息 add by liushouyi
     *
     * @param contractNo
     * @return
     */
    @Override
    public List<NifaContractEssenceVO> selectNifaContractEssenceByContractNo(String contractNo) {
        String url = "http://AM-TRADE/am-trade/nifa_contract_essence/select_nifa_contract_essence/"+contractNo;
        NifaContractEssenceResponse response = restTemplate.getForEntity(url,NifaContractEssenceResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    @Override
    public List<NifaContractTemplateVO> selectNifaContractTemplateByTemplateNid(String templetId) {
        String url = "http://AM-TRADE/am-trade/nifa_contract_essence/select_nifa_contract_template/"+templetId;
        NifaContractTemplateResponse response = restTemplate.getForEntity(url,NifaContractTemplateResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    @Override
    public List<NifaFieldDefinitionVO> selectNifaFieldDefinition() {
        String url = "http://AM-TRADE/am-trade/nifa_contract_essence/select_nifa_field_definition";
        NifaFieldDefinitionResponse response = restTemplate.getForEntity(url,NifaFieldDefinitionResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取还款计算公式
     *
     * @param borrowStyle
     * @return
     */
    @Override
    public List<BorrowStyleVO> selectBorrowStyleWithBLOBs(String borrowStyle) {
        String url = "http://AM-TRADE/am-trade/borrow/select_borrow_style_with_blobs/"+borrowStyle;
        BorrowStyleResponse response = restTemplate.getForEntity(url,BorrowStyleResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取用户出借订单还款详情
     *
     * @param nid
     * @return
     */
    @Override
    public List<BorrowRecoverPlanVO> selectBorrowRecoverPlanList(String nid) {
        String url = "http://AM-TRADE/am-trade/recoverplan/select_borrow_recover_plan_list/" + nid;
        BorrowRecoverPlanResponse response = restTemplate.getForEntity(url,BorrowRecoverPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssenceVO
     * @return
     */
    @Override
    public Integer insertNifaContractEssence(NifaContractEssenceVO nifaContractEssenceVO) {
        String url = urlBase + "nifa_contract_essence/insert_nifa_contract_essence";
        IntegerResponse response = restTemplate.postForEntity(url, nifaContractEssenceVO, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public boolean handlerAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest) {
        BooleanResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/chinapnr/handlerAfterCash", chinaPnrWithdrawRequest, BooleanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultBoolean();
        }
        return false;

    }

    @Override
    public void updateAccountWithdrawByOrdId(String ordId, String reason) {
        String url = "http://AM-TRADE/am-trade/chinapnr/updateAccountWithdrawByOrdId/"+ordId+"/"+ reason;
        restTemplate.getForEntity(url,IntegerResponse.class).getBody();
    }


    /**
     * 查询用户出借次数 包含直投类、债转、汇添金
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int selectUserTenderCount(Integer userId) {
        String url = "http://AM-TRADE/am-trade/webPandect/selectUserTenderCount/"+userId;
        Integer response = restTemplate.getForEntity(url,Integer.class).getBody();
        return response;
    }

    /**
     * 借款人还款表 add by liushouyi
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod) {
        BooleanResponse response = restTemplate
                .getForEntity( urlBase + "nifa_repay_info/insert_nifa_repay_info/" + borrowNid + "/" + repayPeriod, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 合同状态变更数据生成 add by liushouyi
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaContractStatus(String borrowNid, Integer repayPeriod) {
        BooleanResponse response = restTemplate
                .getForEntity( urlBase + "nifa_repay_info/insert_nifa_contract_status/" + borrowNid + "/" + repayPeriod, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 出借人回款记录生成 add by liushouyi
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    @Override
    public boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod) {
        BooleanResponse response = restTemplate
                .getForEntity( urlBase + "nifa_repay_info/insert_nifa_received_payments/" + borrowNid + "/" + repayPeriod, BooleanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 根据订单号查询产品加息信息
     * @auth sunpeikai
     * @param orderId 订单id
     * @return
     */
    @Override
    public IncreaseInterestInvestVO getIncreaseInterestInvestByOrdId(String orderId) {
        String url = urlBase + "projectlist/app/getIncreaseInterestInvestByOrdId/" + orderId;
        IncreaseInterestInvestResponse response = restTemplate.getForEntity(url,IncreaseInterestInvestResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 清算日前一天，扫描处于复审中或者投资中的原始标的进行预警
     * @return
     */
    @Override
    public Boolean alermBeforeLiquidateCheck(){
        String url = "http://AM-TRADE/am-trade/hjhAlarmController/batch/alermBeforeLiquidateCheck";
        BooleanResponse response = restTemplate.getForEntity(url,BooleanResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultBoolean();
        }
        return false;
    }
    /**
     * 查询产品加息信息
     * @auth sunpeikai
     * @param tenderNid 对应tender表里的nid
     * @return
     */
    @Override
    public IncreaseInterestInvestVO getIncreaseInterestInvestByTenderNid(String tenderNid) {
        String url = urlBase + "projectlist/app/getIncreaseInterestInvestByTenderNid/" + tenderNid;
        IncreaseInterestInvestResponse response = restTemplate.getForEntity(url,IncreaseInterestInvestResponse.class).getBody();
        if(Response.isSuccess(response)){
           return response.getResult();
        }
        return null;
    }

    /**
     * 查询出借记录
     * @author wenxin
     * @date 2018/8/27 13:00
     */
    @Override
    public List<InvestListCustomizeVO> searchInvestListNew(Map<String, Object> params) {
        String url = "http://AM-TRADE/am-trade/investList/api/getInvestList";
        ApiInvestResponse response = restTemplate.postForEntity(url,params,ApiInvestResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 獲取銀行開戶信息(根据出借信息查询)
     * @author wenxin
     * @date 2018/8/27 13:00
     */
    @Override
    public List<BankOpenAccountVO> sarchInvestOfBankOpenAccount(List<Integer> userId) {
        //BankOpenAccountListResponse response = restTemplate.getForEntity("http://AM-USER/am-user/bankopen/selectByListId/" + userId, BankOpenAccountListResponse.class).getBody();
        BankOpenAccountListResponse response = restTemplate.postForEntity("http://AM-USER/am-user/bankopen/selectByListId/",userId,BankOpenAccountListResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public ProjectBean searchRepayProjectDetail(ProjectBean form)  {
        ProjectBean result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrow/searchRepayProjectDetail/", form,
                ProjectBean.class).getBody();
        if (result != null) {
            return result.getResult();
        }
        return null;
    }
    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeResponse result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectUserRepayProjectList/", params,
                WebUserRepayProjectListCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
    }

    @Override
    public List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params){
        WebUserRepayProjectListCustomizeResponse result =  restTemplate.postForEntity(
            "http://AM-TRADE/am-trade/borrow/selectOrgRepayProjectList/", params,
                WebUserRepayProjectListCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
    }

    @Override
    public Integer countBatchCenter (BatchCenterCustomize batchCenterCustomize){
        IntegerResponse result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/countBatchCenter/", batchCenterCustomize,
                IntegerResponse.class).getBody();
        if (result != null) {
            return result.getResultInt();
        }
        return null;
    }

    @Override
    public List<BatchCenterCustomizeVO> selectBatchCenterList (BatchCenterCustomize batchCenterCustomize){
        BatchCenterCustomizeResponse result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/selectBatchCenterList/", batchCenterCustomize,
                BatchCenterCustomizeResponse.class).getBody();
        if (result != null) {
            return result.getResultList();
        }
        return null;
        }

    @Override
    public String getborrowIdByProductId (Map<String, Object> params){
        String result =  restTemplate.postForEntity(
        "http://AM-TRADE/am-trade/borrow/getborrowIdByProductId/", params,
                String.class).getBody();
        if (result != null) {
        return result;
        }
        return null;
    }

    @Override
    public BorrowAndInfoVO getborrowByProductId(String productId) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowVOByNid/"+productId;
        BorrowResponse response=restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (Validator.isNotNull(response)){
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
    @Override
    public List<BorrowCreditVO> selectBorrowCreditList() {
        BorrowCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrowCredit/selectBorrowCreditList/",
                BorrowCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO) {
        Integer result =  restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/borrowCredit/updateBorrowCredit/", borrowCreditVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
    }
    @Override
    public List<CouponUserVO> selectCouponUser(int nowBeginDate, int nowEndDate,Integer type) {
        CouponUserResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponUser/selectCouponUser/" + nowBeginDate + "/" + nowEndDate+ "/" + type, CouponUserResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<BatchCouponTimeoutCommonCustomizeVO> selectCouponQuota(int threeBeginDate, int threeEndDate) {
        String url = "http://AM-TRADE/am-trade/couponRepay/selectCouponQuota/" + threeBeginDate + "/" + threeEndDate;
        BatchCouponTimeoutCommonResponse response = restTemplate.getForEntity(url, BatchCouponTimeoutCommonResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public List<HjhLabelVO> seleHjhLabel(String borrowStyle) {
        HjhLabelResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlan/seleHjhLabel/" + borrowStyle, HjhLabelResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public HjhPlanVO getHjhPlan(String planNid) {
        com.hyjf.am.response.user.HjhPlanResponse response = restTemplate.getForEntity("http://AM-TRADE//am-trade/hjhPlan/gethjhplan/"+planNid,
                com.hyjf.am.response.user.HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public HjhAccedeVO getHjhAccede(String orderId) {
        HjhAccedeResponse response = restTemplate.getForEntity("http://AM-TRADE//am-trade/hjhPlan/gethjhaccede/"+orderId,HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/coupon/getborrowtendercpnlist/"+borrowNid;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
    @Override
    public int updateOfLoansTender(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofloanstender",account,
                AccountResponse.class).getBody();
        if (result != null) {
            return result.getUpdateFlag();
        }
        return 0;
    }
    @Override
    public boolean getSendRepeat(CouponUserSearchRequest couponUserRequest) {
        String url = "http://AM-TRADE/am-trade/couponUser/getsendrepeat";
        CouponUserResponse response = restTemplate.postForEntity(url,couponUserRequest,CouponUserResponse.class).getBody();
        return response.getIsSend();
    }
    @Override
    public Integer insertCouponUser(CouponUserVO couponUser) {
        String url = "http://AM-TRADE/am-trade/couponUser/insertCouponUser";
        CouponUserResponse response = restTemplate.postForEntity(url,couponUser,CouponUserResponse.class).getBody();
        return response.getCount();
    }
    /**
     * 查询回款日历总数
     * @param params
     * @return
     */
    @Override
    public Integer countRepaymentCalendar(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/countBorrowRepayment",
                params, AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }

    /**
     * 查询回款日历明细
     * @param params
     * @return
     */
    @Override
    public List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/searchRepaymentCalendar", params, AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 返回用户最近回款时间戳-秒
     * @param params
     * @return
     */
    @Override
    public Integer searchNearlyRepaymentTime(Map<String, Object> params) {
        AppReapyCalendarResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/user/repayCalendar/searchNearlyRepaymentTime", params, AppReapyCalendarResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return null;
    }
    @Override
    public int countAccountListByOrdId(String ordId, String type) {
        AccountListResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/accountList/selectAccountListByOrdId/" + ordId+"/"+type, AccountListResponse.class).getBody();
        if (response != null  && response.getResult() != null) {
            return 1;
        }
        return 0;
    }


    @Override
    public Integer insertAccountListSelective(AccountListVO accountListVO) {
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/accountList/insertAccountListSelective/", accountListVO,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;

    }

    @Override
    public int countByNidAndTrade(String nid, String trade) {
        String url = "http://AM-TRADE/am-trade/accountList/countbynidandtrade/"+nid+"/"+trade;
        AccountListResponse response = restTemplate.getForEntity(url,AccountListResponse.class).getBody();
        if (response != null) {
            return response.getTotalRecord();
        }
        return 1;
    }
    @Override
    public int updateOfRepayCouponHjh(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofrepaycouponhjh/", account,
                AccountResponse.class).getBody();
        if (result != null) {
            return result.getUpdateFlag();
        }
        return 0;
    }

    @Override
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/account/getbankmerchantaccount/"+accountCode,
                BankMerchantAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public int updateBankMerchatAccount(BankMerchantAccountVO account) {
        BankMerchantAccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updatebankmerchantaccount",account,
                BankMerchantAccountResponse.class).getBody();
        if (result != null) {
            return result.getRecordTotal();
        }
        return 0;
    }

    @Override
    public int insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList) {
        BankMerchantAccountListResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/insertbankmerchantaccountlist",bankMerchantAccountList,
                BankMerchantAccountListResponse.class).getBody();
        if (result != null) {
            return result.getFlag();
        }
        return 0;
    }

    @Override
    public int updateOfRepayTender(AccountVO account) {
        AccountResponse result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/account/updateofrepaytender",account,
                AccountResponse.class).getBody();
        if (result != null) {
            return result.getUpdateFlag();
        }
        return 0;
    }
    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid) {
        CouponTenderCustomizeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/getcoupontenderlist/"+borrowNid , CouponTenderCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer crRecoverPeriod(String tenderNid, int period) {
        CouponRecoverResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/updaterecoverperiod/"+tenderNid + "/" + period ,CouponRecoverResponse.class).getBody();
        if (response != null) {
            return response.getRecordTotal();
        }
        return null;
    }
	@Override
	public List<String> selectNidForCouponOnly(CouponRepayRequest couponRepayRequest) {
		String url = "http://AM-TRADE/am-trade/couponperiodrepay/selectnidforcoupononly";
		List<String> recoverNidList = restTemplate.postForEntity(url,couponRepayRequest,List.class).getBody();
		if (!CollectionUtils.isEmpty(recoverNidList)) {
			return recoverNidList;
		}
		return null;
	}
	@Override
	public CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
		String url = "http://AM-TRADE/am-trade/borrowTender/getcurrentcouponrecover/"+couponTenderNid+"/"+periodNow;
		CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResult();
		}
		return null;
	}
	  @Override
	    public ProjectBeanVO getRepayProjectDetail(ProjectBeanVO form)  {
	        ProjectBeanResponse response =  restTemplate.postForEntity(
	                "http://AM-TRADE/am-trade/borrow/getRepayProjectDetail/", form,
	                ProjectBeanResponse.class).getBody();
	        if (response != null) {
	            return response.getResult();
	        }
	        return null;
	    }
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
     * 获取用户出借数量
     *
     * @param userId
     * @return
     */
    @Override
    public int countNewUserTotal(Integer userId) {
        Integer result = restTemplate
                .getForEntity(urlBase + "borrowTender/countNewUserTotal/" + userId,  Integer.class).getBody();
        if (result != null) {
            return result;
        }
        return 0;
    }

    @Override
    public List<MyCouponListCustomizeVO> selectWechatCouponList(MyCouponListRequest requestBean) {
        String url = urlBase + "coupon/wechatcouponlist";
        MyCouponListResponse response = restTemplate.postForEntity(url, requestBean, MyCouponListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据资产来源查询保证金配置 add by liushouyi
     *
     * @param instCode
     * @return
     */
    @Override
    public BailConfigInfoCustomizeVO selectBailConfigByInstCode(String instCode) {
        String url = urlBase + "bail_config/select_bail_config_by_instcode/" + instCode;
        BailConfigInfoCustomizeResponse response = restTemplate.getForEntity(url,BailConfigInfoCustomizeResponse.class).getBody();
        if (response!=null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


    /**
     * 查询产品类型
     * @author zhangyk
     * @date 2018/10/9 15:53
     */
    @Override
    @Cached(name="webHomeProjectTypeListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 600, timeUnit = TimeUnit.SECONDS)
    public List<BorrowProjectTypeVO> getProjectTypeList() {
        String url = "http://AM-TRADE/am-trade/config/projecttype/getProjectType";
        BorrowProjectTypeResponse response = restTemplate.getForEntity(url,BorrowProjectTypeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CouponRepayMonitorVO> selectCouponRepayMonitor(String nowDay) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/selectCouponRepayMonitor/"+nowDay;
        CouponRepayMonitorResponse response = restTemplate.getForEntity(url,CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int insertCouponRepayMonitor(CouponRepayMonitorVO monitor) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/insertCouponRepayMonitor";
        CouponRepayMonitorResponse response = restTemplate.postForEntity(url,monitor, CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getInsertFlag();
        }
        return 0;
    }

    @Override
    public int updateCouponRepayMonitor(CouponRepayMonitorVO monitor) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/updateCouponRepayMonitor";
        CouponRepayMonitorResponse response = restTemplate.postForEntity(url,monitor,CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getUpdateFlag();
        }
        return 0;
    }


    /**
     * 查询标的列表(协议)
     * @author zhangyk
     * @date 2018/10/18 14:49
     */
    @Override
    public List<BorrowListVO> searchBorrowList4Protocol(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowList";
        BorrowListResponse response = restTemplate.postForEntity(url,map,BorrowListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return  response.getResultList();
        }
        return null;
    }


    @Override
    public List<DebtBorrowCustomizeVO> searchDebtBorrowList4Protocol(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/getDebtBorrowList";
        DebtBorrowListResponse response = restTemplate.postForEntity(url,map,DebtBorrowListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectLoanDetailList(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/selectProjectLoanDetailList";
        WebProjectRepayListCustomizeResponse response = restTemplate.postForEntity(url,map,WebProjectRepayListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserDebtInvestList(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/selectUserDebtInvestList";
        WebUserInvestListCustomizeResponse response = restTemplate.postForEntity(url,map,WebUserInvestListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int planInfoCountProjectRepayPlanRecordTotal(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/planInfoCountProjectRepayPlanRecordTotal";
        IntegerResponse response = restTemplate.postForEntity(url,map,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public int myTenderCountProjectRepayPlanRecordTotal(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/myTenderCountProjectRepayPlanRecordTotal";
        IntegerResponse response = restTemplate.postForEntity(url,map,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    @Override
    public List<WebUserInvestListCustomizeVO> selectUserInvestList(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/selectUserInvestLsit";
        WebUserInvestListCustomizeResponse response = restTemplate.postForEntity(url,map,WebUserInvestListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowCustomizeVO> searchBorrowList(Map<String, Object> params) {
        String url = urlBase+"borrow/searchBorrowList";
        BorrowCustomizeXYResponse response = restTemplate.postForEntity(url,params,BorrowCustomizeXYResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 标的放款记录分期count
     */
    @Override
    public Integer countProjectRepayPlanRecordTotal(Map<String,Object> paraMap) {
        String url = "http://AM-TRADE/am-trade/borrow/count_project_repay";
        WebProjectRepayListCustomizeResponse response =
                restTemplate.postForEntity(url, paraMap, WebProjectRepayListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getTotal();
        }
        return 0;
    }

    @Override
    public List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(Map<String, Object> map) {
        String url = "http://AM-TRADE/am-trade/borrow/selectProjectRepayPlanList";
        WebProjectRepayListCustomizeResponse response = restTemplate.postForEntity(url,map,WebProjectRepayListCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID查询用户提现记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<AccountWithdrawVO> selectAccountWithdrawByUserId(Integer userId) {
        String url = tradeService + "/accountWithdraw/selectAccountWithdrawByUserId/" + userId;
        AccountWithdrawResponse response = restTemplate.getForEntity(url, AccountWithdrawResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID 查询用户充值记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<AccountRechargeVO> selectRechargeListByUserId(Integer userId) {
        String url = tradeService + "/accountrecharge/selectRechargeListByUserId/" + userId;
        AccountRechargeResponse response = restTemplate.getForEntity(url, AccountRechargeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID查询用户出借记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectBorrowTenderByUserId(Integer userId) {
        String url = tradeService + "/borrowTender/selectBorrowTenderByUserId/" + userId;
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID 查询用户承接记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<CreditTenderVO> selectCreditTenderByUserId(Integer userId) {
        String url = tradeService + "/borrowTender/selectBorrowTenderByUserId/" + userId;
        CreditTenderResponse response = restTemplate.getForEntity(url, CreditTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户ID查询用户加入记录
     *
     * @param userId
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectHjhAccedeListByUserId(Integer userId) {
        String url = tradeService + "/hjhAccede/selectHjhAccedeListByUserId/" + userId;
        HjhAccedeResponse response =  restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据承接订单号查询承接记录
     *
     * @param assignOrderId
     * @return
     */
    @Override
    public CreditTenderVO selectCreditTenderByAssignOrderId(String assignOrderId) {
        String url = tradeService + "/creditTender/selectCreditTenderByAssignOrderId/" + assignOrderId;
        CreditTenderResponse response =  restTemplate.getForEntity(url, CreditTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据加入订单号查询优惠券出借
     *
     * @param orderId
     * @return
     */
    @Override
    public CouponRealTenderVO selectCouponRealTenderByOrderId(String orderId) {
        String url = tradeService + "/coupon/selectCouponRealTenderByOrderId/" + orderId;
        CouponRealTenderResponse response =  restTemplate.getForEntity(url, CouponRealTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据优惠券出借ID查询优惠券出借
     *
     * @param couponTenderId
     * @return
     */
    @Override
    public CouponTenderVO selectCouponTenderByCouponTenderId(String couponTenderId) {
        String url = tradeService + "/coupon/selectCouponTenderByCouponTenderId/" + couponTenderId;
        com.hyjf.am.response.trade.coupon.CouponTenderResponse response = restTemplate.getForEntity(url, com.hyjf.am.response.trade.coupon.CouponTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据优惠券ID查询优惠券使用情况
     *
     * @param couponGrantId
     * @return
     */
    @Override
    public CouponUserVO selectCouponUserById(Integer couponGrantId) {
        String url = tradeService + "/coupon/selectCouponUserById/" + couponGrantId;
        CouponUserResponse response = restTemplate.getForEntity(url, CouponUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据优惠券出借ID获取优惠券出借信息
     *
     * @param couponTenderId
     * @return
     */
    @Override
    public BorrowTenderCpnVO selectBorrowTenderCpnByCouponTenderId(String couponTenderId) {
        String url = tradeService + "/coupon/selectBorrowTenderCpnByCouponTenderId/" + couponTenderId;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url, BorrowTenderCpnResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据出借订单号查询出借信息
     *
     * @param orderId
     * @return
     */
    @Override
    public BorrowTenderVO selectBorrowTenderByOrderId(String orderId) {
        String url = tradeService + "/borrowTender/selectBorrowTenderByOrderId/" + orderId;
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }



    /**
     * 首页汇计划推广计划列表 - 首页显示 ②	若没有可投计划，则显示锁定期限短的
     * @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @Override
    public List<HjhPlanCustomizeVO> selectIndexHjhExtensionPlanListByLockTime(AppHomePageRequest request) {
        String url = "http://AM-TRADE/am-trade/projectlist/apphomepage/selectIndexHjhExtensionPlanListByLockTime";
        HjhPlanResponse response = restTemplate.postForEntity(url,request, HjhPlanResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 首页汇计划推广计划列表 - 首页显示
     *  @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @Override
    public List<HjhPlanCustomizeVO> selectIndexHjhExtensionPlanList(AppHomePageRequest request) {
        String url = "http://AM-TRADE/am-trade/projectlist/apphomepage/selectIndexHjhExtensionPlanList";
        HjhPlanResponse response = restTemplate.postForEntity(url,request, HjhPlanResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询首页定时发标,出借中,复审中的项目
     * @Author yangchangwei 2018/10/16
     * @param request
     * @return
     */
    @Override
    public List<AppProjectListCustomizeVO> selectHomeProjectList(AppHomePageRequest request) {
        String url = "http://AM-TRADE/am-trade/projectlist/apphomepage/selectHomeProjectList";
        AppProjectListResponse response = restTemplate.postForEntity(url,request,AppProjectListResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    /**
     * 查询首页还款中的项目
     * @Author yangchangwei 2019/3/4
     * @param request
     * @return
     */
    @Override
    public List<AppProjectListCustomizeVO> selectHomeRepayProjectList(AppHomePageRequest request) {
        String url = "http://AM-TRADE/am-trade/projectlist/apphomepage/selectHomeRepayProjectList";
        AppProjectListResponse response = restTemplate.postForEntity(url,request,AppProjectListResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }
    /**
     * 添加
     * @author wgx
     * @date 2018/10/16
     */
    @Override
    public Integer addOrgFreezeLog(BankRepayOrgFreezeLogRequest requestBean) {
        String url = "http://AM-TRADE/am-trade/repayOrgFreezeLog/add";
        IntegerResponse response = restTemplate.postForEntity(url, requestBean, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据orderId删除
     * @author wgx
     * @date 2018/10/16
     */
    @Override
    public Integer deleteOrgFreezeLog(String orderId, String borrowNid) {
        StringBuilder url = new StringBuilder("http://AM-TRADE/am-trade/repayOrgFreezeLog/delete/");
        url.append(orderId);
        if (StringUtils.isNotBlank(borrowNid)) {
            url.append("/").append(borrowNid);
        }
        IntegerResponse response = restTemplate.getForEntity(url.toString(), IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据条件查询垫付机构冻结日志
     * @author wgx
     * @date 2018/10/16
     */
    @Override
    public List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String orderId, String borrowNid) {
        StringBuilder url = new StringBuilder("http://AM-TRADE/am-trade/repayOrgFreezeLog/getValid/");
        url.append(orderId);
        if(StringUtils.isNotBlank(borrowNid)){
            url.append("/").append(borrowNid);
        }
        BankRepayOrgFreezeLogResponse response = restTemplate.getForEntity(url.toString(), BankRepayOrgFreezeLogResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据条件查询垫付机构冻结日志
     * @author wgx
     * @date 2018/10/16
     */
    @Override
    public List<BankRepayOrgFreezeLogVO> getBankRepayOrgFreezeLogList(String borrowNid) {
        StringBuilder url = new StringBuilder("http://AM-TRADE/am-trade/repayOrgFreezeLog/getByNid/");
        url.append("/").append(borrowNid);
        BankRepayOrgFreezeLogResponse response = restTemplate.getForEntity(url.toString(), BankRepayOrgFreezeLogResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }
    /**
     * 获取有效公告
     * @author cwyang 2018-10-18
     * @return
     */
    @Override
    @Cached(name="appHomeAnnouncementsCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 300, timeUnit = TimeUnit.SECONDS)
    public List<AppPushManageVO> getAnnouncements() {
        String url = "http://AM-TRADE/am-trade/projectlist/apphomepage/getAnnouncements";
        AppPushManageResponse response = restTemplate.getForEntity(url, AppPushManageResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return CollectionUtils.isEmpty(response.getResultList()) ? new ArrayList<>() : response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowCustomizeVO> searchBorrowCustomizeList(BorrowCommonCustomizeVO borrowCommonCustomize) {
        String url = "http://AM-TRADE/am-trade/borrow/searchBorrowCustomizeList";
        BorrowCustomizeResponse response = restTemplate.postForEntity(url,borrowCommonCustomize,BorrowCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<PlanInvestCustomizeVO> selectInvestCreditList(Map<String, Object> param) {
        String url = "http://AM-TRADE/am-trade/htj/selectInvestCreditList";
        PlanInvestResponse response = restTemplate.postForEntity(url,param,PlanInvestResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<PlanInvestCustomizeVO> selectCreditCreditList(Map<String, Object> param) {
        String url = "http://AM-TRADE/am-trade/htj/selectCreditCreditList";
        PlanInvestResponse response = restTemplate.postForEntity(url,param,PlanInvestResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<PlanLockCustomizeVO> selectUserProjectListCapital(Map<String, Object> param) {
        String url = "http://AM-TRADE/am-trade/htj/selectUserProjectListCapital";
        PlanLockResponse response = restTemplate.postForEntity(url,param,PlanLockResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public String selectPlanInfoSum(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/htj/selectPlanInfoSum/"+ accedeOrderId;
        StringResponse response = restTemplate.getForEntity(url,StringResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultStr();
        }
        return null;
    }

    @Override
    @Cached(name="wechatHomeProjectListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<WechatHomeProjectListVO> getWechatProjectList(Map<String, Object> projectMap){
        String url = HomePageDefine.WECHAT_HOME_PROJECT_LIST_URL;
        WechatProjectListResponse res = restTemplate.postForEntity(url, projectMap, WechatProjectListResponse.class).getBody();
        if (Response.isSuccess(res)){
            return res.getResultList();
        }
        return null;
    }

    /**
     * @author libin
     * 抽出查询统计信息的方法
     * @date 2018/9/5 11:38
     */
    @Override
    @Cached(name="wechatTotalInvestAndInterestCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.MINUTES)
    public TotalInvestAndInterestResponse getTotalInvestAndInterestResponse(){
        TotalInvestAndInterestResponse res = restTemplate.getForEntity(HomePageDefine.INVEST_INVEREST_AMOUNT_URL, TotalInvestAndInterestResponse.class).getBody();
        return res;
    }

    @Override
    @Cached(name="wechatHomePlanLaterCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<WechatHomeProjectListVO> getWechatHomePlanLater() {
        WechatProjectListResponse res = restTemplate.getForEntity(HomePageDefine.WECHAT_HOME_PLAN_LATER_URL,WechatProjectListResponse.class).getBody();
        if (Response.isSuccess(res)){
            return res.getResultList();
        }
        return null;
    }

    @Override
    @Cached(name="wechatHomeRepaymentsProjectList-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<WechatHomeProjectListVO> getWechatHomeRepaymentsProjectList() {
        WechatProjectListResponse res =  restTemplate.getForEntity(HomePageDefine.WECHAT_HOME_REPAYMENT_URL, WechatProjectListResponse.class).getBody();
        if (Response.isSuccess(res)){
            return res.getResultList();
        }
        return null;
    }

    @Override
    @Cached(name="webCreditListCountCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public int getWebCreditListCount(CreditListRequest request) {
        CreditListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/projectlist/web/countCreditList", request, CreditListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return 0;
    }

    @Override
    @Cached(name="webCreditListCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 5, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    public List<CreditListVO> getWebCreditList(CreditListRequest request) {
        CreditListResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/projectlist/web/searchWebCreditList", request, CreditListResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 抽出查询noticeList的方法
     * @date 2018/9/5 11:38
     */
    @Cached(name="webHomeNoticeCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    @Override
    public List<ContentArticleVO> getNoticeList(ContentArticleRequest contentArticleRequest) {
        String url = "http://AM-CONFIG/am-config/article/noticeList";
        ContentArticleResponse response = restTemplate.postForEntity(url,contentArticleRequest,ContentArticleResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 抽出查询bannner的方法
     * @date 2018/9/5 11:38
     */
    @Cached(name="webHomeBannerCache-", expire = CustomConstants.HOME_CACHE_LIVE_TIME, cacheType = CacheType.BOTH)
    @CacheRefresh(refresh = 60, stopRefreshAfterLastAccess = 60, timeUnit = TimeUnit.SECONDS)
    @Override
    public List<AppAdsCustomizeVO> getWebHomeBannerList(AdsRequest request) {
        String url = "http://AM-MARKET/am-market/ads/getBannerList";
        AppAdsCustomizeResponse res = restTemplate.postForEntity(url,request,AppAdsCustomizeResponse.class).getBody();
        if (Response.isSuccess(res)){
            return res.getResultList();
        }
        return null;
    }

    /**
     *
     * @param borrowNid
     * @return
     * @Author : huanghui
     */
    @Override
    public WebUserTransferBorrowInfoCustomizeVO getUserTransferBorrowInfo(String borrowNid) {
        WebUserTransferBorrowInfoCustomizeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/get_user_transfer_borrow_info/" + borrowNid, WebUserTransferBorrowInfoCustomizeResponse.class).getBody();
        if (response != null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 转让通知借款人 统计
     * @param repayTransferRequest
     * @return
     * @Author : huanghui
     */
    @Override
    public Integer getUserRepayDetailAjaxCount(WebUserRepayTransferRequest repayTransferRequest) {
        IntegerResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/repay/getUserRepayDetailAjaxCount/", repayTransferRequest, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 获取用户待还标的列表
     * @param repayTransferRequest
     * @return
     * @Author : huanghui
     */
    @Override
    public List<WebUserRepayTransferCustomizeVO> getUserRepayDetailAjax(WebUserRepayTransferRequest repayTransferRequest) {
        WebUserRepayTransferCustomizeResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/repay/userRepayDetailAjax/", repayTransferRequest, WebUserRepayTransferCustomizeResponse.class).getBody();

        if (response != null && Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    /**
     * AMES借款人受托支付申请异步回调更新数据
     * @param borrowNid
     * @return
     */
    @Override
    public boolean updateAemsTrusteePaySuccess(String borrowNid){
        String url = "http://AM-TRADE/am-trade/trustee/update/" + borrowNid;
        BooleanResponse response = restTemplate.getForEntity(url, BooleanResponse.class).getBody();
        if(Response.isSuccess(response)){
            return response.getResultBoolean();
        }
        return false;
    }

    /**
     * 根据机构编号,查询还款计划数量
     *
     * @param param
     * @return
     */
    @Override
    public Integer selectBorrowRepayPlanCountsByInstCode(Map<String, Object> param) {
        AemsBorrowRepayPlanCustomizeResponse response = restTemplate.postForObject(
                "http://AM-TRADE/am-trade/aems/repayplan/selectBorrowRepayPlanCountsByInstCode", param, AemsBorrowRepayPlanCustomizeResponse.class);
        if (response != null) {
            return response.getCount();
        }
        return 0;
    }

    @Override
    public BooleanResponse updateHjhPlanJoinOff() {
        String url = "http://AM-TRADE/am-trade/hjhPlanSwitchController/batch/hjhPlanJoinOff";
        BooleanResponse response = restTemplate.getForObject(url, BooleanResponse.class);
        if (response != null && Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    @Override
    public BooleanResponse updateHjhPlanJoinOn() {
        String url = "http://AM-TRADE/am-trade/hjhPlanSwitchController/batch/hjhPlanJoinOn";
        BooleanResponse response = restTemplate.getForObject(url, BooleanResponse.class);
        if (response != null && Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    @Override
    public void autoIssueRecover() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhAutoIssueRecover/autoIssueRecover", String.class);
    }

    @Override
    public BooleanResponse updateMatchDays() {
        BooleanResponse response = restTemplate.getForObject("http://AM-TRADE/am-trade/tenderMatchDaysController/batch/tenderMatchDays", BooleanResponse.class);
        if (response != null && Response.isSuccess(response)) {
            return response;
        }
        return null;
    }

    @Override
    public void downloadFile() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/nifa_file_deal/download_file", boolean.class);
    }

    @Override
    public void uploadFile() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/nifa_file_deal/upload_file", boolean.class);
    }

    @Override
    public void updateRepayInfo() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/late_and_credit/update_repay_info", boolean.class);
    }

    @Override
    public void countRechargeMoney() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/countRechargeMoney", String.class);
    }

    @Override
    public void updateDayMarkLine() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/day_mark_line_total/update_day_mark_line", String.class);
    }

    @Override
    public void taskAssign() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/taskAssign", String.class);
    }

    @Override
    public void taskRepayAssign() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/autoReqRepayController/autoReqRepay", boolean.class);
    }

    @Override
    public void taskReviewBorrowAssign() {
        restTemplate.getForEntity("http://AM-TRADE/batch/borrowautoreview/autoreview", String.class);
    }

    @Override
    public void taskAssignLoans() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/increaseinterestLoans", String.class);
    }

    @Override
    public void taskAssignRepay() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/increaseInterestRepay", String.class);
    }

    @Override
    public void noneSplitBorrow() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/timing_borrow/issue/none_split_borrow", String.class).getBody();
    }

    @Override
    public void hjhBorrow() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/timing_borrow/issue/hjh_borrow", String.class).getBody();
    }

    @Override
    public void splitBorrow() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/timing_borrow/issue/split_borrow", String.class).getBody();
    }

    @Override
    public void couponExpired() {
        restTemplate.getForObject("http://AM-TRADE/am-trade/batch/coupon/expired", StringResponse.class);
    }

    @Override
    public void dataInfo() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/calculate_invest_interest", String.class);
    }

    @Override
    public void downloadRedFile() {
        restTemplate.getForEntity("http://AM-TRADE/am-trade/batch/downloadFiles", String.class);
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

    /** 测评获取冻结金额和代收本经明细 */
    @Override
    public CallCenterAccountDetailVO queryAccountEvalDetail(Integer userId){
        String url = "http://AM-TRADE/am-trade/callcenter/queryAccountEvalDetail/"+ userId;
        CallCenterAccountDetailResponse response = restTemplate.getForEntity(url, CallCenterAccountDetailResponse.class).getBody();
        if (response != null && Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取需要推送法大大协议的标的
     * add by yangchangwei 2018-11-26
     * @return
     */
    @Override
    public List<BorrowApicronVO> getFddPushBorrowList() {


        String url = "http://AM-TRADE/am-trade/batch/fddpush/getfddpushborrowlist";
        BorrowApicronResponse response = restTemplate.getForEntity(url, BorrowApicronResponse.class).getBody();
        if (response != null && Response.isSuccess(response)) {
            return response.getResultList();
        }

        return null;

    }

    /**
     * 开始推送法大大协议
     * add by yangchangwei 2018-11-27
     * @param borrowApicronVO
     */
    @Override
    public void updateFddPush(BorrowApicronVO borrowApicronVO) {
        BorrowApicronRequest request;
        request = CommonUtils.convertBean(borrowApicronVO,BorrowApicronRequest.class);
        String url = "http://AM-TRADE/am-trade/batch/fddpush/updateFddPush";
        restTemplate.postForEntity(url, request,null).getBody();

    }

    /**
     * 查询用户借款笔数(企业)
     *
     * @param username
     * @return
     */
    @Override
    public Integer selectBorrowUsersCount(String username) {
        String url = urlBase + "nifa_statistical/select_borrow_users_count/" + username;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 查询用户借款笔数(个人)
     *
     * @param username
     * @return
     */
    @Override
    public Integer selectBorrowManInfoCount(String username) {
        String url = urlBase + "nifa_statistical/select_borrow_man_info_count/" + username;
        IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultInt();
        }
        return 0;
    }

    /**
     * 根据borrowNid，tenderNid，accedeOrderId查找放款记录
     * add by nxl
     * @param borrowRecoverVO
     * @return
     */
    @Override
    public BorrowRecoverVO getRecoverDateByTenderNid(BorrowRecoverVO borrowRecoverVO) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/getRecoverDateByTenderNid";
        BorrowRecoverResponse response = restTemplate.postForEntity(url,borrowRecoverVO,BorrowRecoverResponse.class).getBody();
        if (Validator.isNotNull(response)&&response.getRtn().equals(Response.SUCCESS)){
            return response.getResult();
        }
        return null;
    }
    /**
     * 获取投资红包金额
     * add by nxl
     * @param realTenderId
     * @return
     */
    @Override
    public BigDecimal getRedPackageSum(String realTenderId) {
        String url = "http://AM-TRADE/am-trade/coupon/getRedPackageSum/"+realTenderId;
        BigDecimalResponse response =restTemplate.getForEntity(url,BigDecimalResponse.class).getBody();
        if (Validator.isNotNull(response)&&response.getRtn().equals(Response.SUCCESS)){
            return response.getResultDec();
        }
        return null;
    }

    @Override
    public List<CertAccountListCustomizeVO> queryCertAccountList(CertRequest request) {
        String url = urlBase + "cert/queryCertAccountList";
        CertAccountListResponse response = restTemplate.postForEntity(url, request, CertAccountListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<AccountListVO> getAccountListVOListByRequest(CertRequest request) {

        String url = urlBase + "cert/getAccountListVOListByRequest";
        AccountListResponse response = restTemplate.postForEntity(url,request,AccountListResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowRepayVO> getBorrowRepayListByRequest(CertRequest request) {
        String url = urlBase + "cert/getBorrowRepayListByRequest";
        BorrowRepayResponse response = restTemplate.postForEntity(url,request,BorrowRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowRepayPlanVO> getBorrowRepayPlanListByRequest(CertRequest request) {
        String url = urlBase + "cert/getBorrowRepayPlanListByRequest";
        BorrowRepayPlanResponse response = restTemplate.postForEntity(url,request,BorrowRepayPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CertCouponRecoverVO> getCouponRecoverListByCertRequest(CertRequest certRequest) {
        String url = urlBase + "cert/getCouponRecoverListByCertRequest";
        CouponRecoverResponse response = restTemplate.postForEntity(url,certRequest,CouponRecoverResponse.class).getBody();
        if (response != null) {
            return response.getCertCouponRecoverVOList();
        }
        return null;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnListByCertRequest(CertRequest certRequest) {
        String url = urlBase + "cert/getBorrowTenderCpnListByCertRequest";
        BorrowTenderCpnResponse response = restTemplate.postForEntity(url,certRequest,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CouponRealTenderVO> getCouponRealTenderListByCertRequest(CertRequest certRequest) {
        String url = urlBase + "cert/getCouponRealTenderListByCertRequest";
        CouponRealTenderResponse response = restTemplate.postForEntity(url,certRequest,CouponRealTenderResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverVO> selectBorrowRecoverListByRequest(CertRequest certRequest) {
        String url = urlBase + "cert/selectBorrowRecoverListByRequest";
        BorrowRecoverResponse response = restTemplate.postForEntity(url,certRequest,BorrowRecoverResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhDebtCreditRepayVO> getHjhDebtCreditRepayListByRequest(CertRequest certRequest) {
        String url = urlBase + "cert/getHjhDebtCreditRepayListByRequest";
        HjhDebtCreditRepayResponse response = restTemplate.postForEntity(url,certRequest,HjhDebtCreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditRepayVO> getCreditRepayListByRequest(CertRequest certRequest) {
        String url = urlBase + "cert/getCreditRepayListByRequest";
        CreditRepayResponse response = restTemplate.postForEntity(url,certRequest,CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowRecoverPlanVO> selectBorrowRecoverPlanListByRequest(CertRequest certRequest) {
        String url = urlBase + "cert/selectBorrowRecoverPlanListByRequest";
        BorrowRecoverPlanResponse response = restTemplate.postForEntity(url,certRequest,BorrowRecoverPlanResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhDebtCreditRepayVO> getHjhDebtCreditRepayListByRepayOrdId(CertRequest certRequest) {
        String url = urlBase + "cert/getHjhDebtCreditRepayListByRepayOrdId";
        HjhDebtCreditRepayResponse response = restTemplate.postForEntity(url,certRequest,HjhDebtCreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<CreditRepayVO> getCreditRepayListByRepayOrdId(CertRequest certRequest) {
        String url = urlBase + "cert/getCreditRepayListByRepayOrdId";
        CreditRepayResponse response = restTemplate.postForEntity(url,certRequest,CreditRepayResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据机构编号查询还款计划
     *
     * @param param
     * @return
     */
    @Override
    public List<AemsBorrowRepayPlanCustomizeVO> selectBorrowRepayPlanList(Map<String, Object> param) {
        String url = "http://AM-TRADE/am-trade/aems/repayplan/selectBorrowRepayPlanList";
        AemsBorrowRepayPlanCustomizeResponse response = restTemplate.postForEntity(url, param, AemsBorrowRepayPlanCustomizeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }


            /** 当前出借人
     * @return
     */
    @Override
    public Integer countCurrentTenderUser() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/countCurrentTenderUser";
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * 平台前十大融资人融资待还余额占比
     * @return
     */
    @Override
    public BigDecimal sumBorrowUserMoneyTopTen() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/sumBorrowUserMoneyTopTen";
        return restTemplate.getForEntity(url,BigDecimal.class).getBody();
    }

    /**
     * 代还总金额
     * @return
     */
    @Override
    public BigDecimal sumBorrowUserMoney() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/sumBorrowUserMoney";
        return restTemplate.getForEntity(url,BigDecimal.class).getBody();
    }

    /**
     * 平台单一融资人最大融资待还余额占比
     * @return
     */
    @Override
    public BigDecimal sumBorrowUserMoneyTopOne() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/sumBorrowUserMoneyTopOne";
        return restTemplate.getForEntity(url,BigDecimal.class).getBody();
    }

    @Override
    public CertAccountListIdCustomizeVO queryCertAccountListId(CertRequest request) {
        String url = urlBase + "cert/queryCertAccountListId";
        CertAccountListResponse response = restTemplate.postForEntity(url, request, CertAccountListResponse.class).getBody();
        if (response != null) {
            return response.getCertAccountListIdCustomizeVO();
        }
        return null;
    }

    @Override
    public List<HjhDebtCreditVO> getHjhDebtCreditListByCreditNid(String creditNid) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/getHjhDebtCreditListByCreditNid/" + creditNid ,
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<HjhDebtCreditVO> getHjhDebtCreditListByBorrowNid(String borrowNid) {
        HjhDebtCreditResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/hjhDebtCredit/getHjhDebtCreditListByBorrowNid/" + borrowNid ,
                HjhDebtCreditResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据标的编号查询资产推送表
     *
     * @param borrowNid
     * @return
     */
    @Override
    public HjhPlanAssetVO selectHjhPlanAssetByBorrowNid(String borrowNid) {
        HjhPlanAssetResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/assetPush/selectHjhPlanAssetByBorrowNid/" + borrowNid, HjhPlanAssetResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
            /** 获取借款用户信息
     * @param borrowNid
     * @param companyOrPersonal
     * @return
     */
    @Override
    public BifaBorrowUserInfoVO getBorrowUserInfo(String borrowNid, String companyOrPersonal) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getBorrowUserInfo/"+borrowNid+"/"+companyOrPersonal;
        BifaBorrowUserInfoResponse response = restTemplate.getForEntity(url, BifaBorrowUserInfoResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 服务费=放款服务费+还款服务费
     * @param borrowNid
     * @return
     */
    @Override
    public String selectServiceCostSum(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/borrowRecover/selectServiceCostSum/"+borrowNid;
        return restTemplate.getForEntity(url, String.class).getBody();
    }

    /**
     * 散标转让服务费
     * @param creditNid
     * @return
     */
    @Override
    public BigDecimal getBorrowCreditFeeSumByCreditNid(String creditNid) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getBorrowCreditFeeSumByCreditNid/"+creditNid;
        return restTemplate.getForEntity(url, BigDecimal.class).getBody();
    }

    /**
     * 智投转让服务费
     * @param creditNid
     * @return
     */
    @Override
    public BigDecimal getHjhCreditFeeSumByCreditNid(String creditNid) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getHjhCreditFeeSumByCreditNid/"+creditNid;
        return restTemplate.getForEntity(url, BigDecimal.class).getBody();
    }

    /**
     * 获取智投数
     * @param planNid
     * @return
     */
    @Override
    public int countHjhPlan(String planNid) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/countHjhPlan/"+planNid;
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * 获取智投列表
     * @return
     */
    @Override
    public List<HjhPlanVO> selectHjhPlanInfoList() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/selectHjhPlanInfoList";
        BifaHjhPlanResponse response = restTemplate.getForEntity(url,BifaHjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 已开户且出借>0的用户
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<UserIdAccountSumBeanVO> getBorrowTenderAccountSum(Integer startDate, Integer endDate) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getBorrowTenderAccountSum/"+startDate+"/"+endDate;
        UserIdAccountSumBeanResponse response=restTemplate.getForEntity(url,UserIdAccountSumBeanResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借款人信息
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public List<BorrowAndInfoVO> selectBorrowUserInfo(Integer startDate, Integer endDate) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/selectBorrowUserInfo/"+startDate+"/"+endDate;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 借贷笔数
     * @param time
     * @return
     */
    @Override
    public int getLoanNum(Date time) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getLoanNum";
        return restTemplate.postForEntity(url,time,Integer.class).getBody();
    }

    /**
     * 累计借贷余额
     * @return
     */
    @Override
    public BigDecimal getWillPayMoney() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getWillPayMoney";
        return restTemplate.getForEntity(url,BigDecimal.class).getBody();
    }

    /**
     * 累计借贷余额笔数
     * @return
     */
    @Override
    public int getTotalLoanBalanceNum() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getTotalLoanBalanceNum";
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * 累计借款人
     * @return
     */
    @Override
    public Integer countBorrowUser() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/countBorrowUser";
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     * 累计投资人数
     * @param time
     * @return
     */
    @Override
    public int getTenderCount(Date time) {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/getTenderCount";
        return restTemplate.postForEntity(url,time,Integer.class).getBody();
    }

    /**
     * 当前借款人
     * @return
     */
    @Override
    public Integer countCurrentBorrowUser() {
        String url = "http://AM-TRADE/am-trade/bifaDataReport/countCurrentBorrowUser";
        return restTemplate.getForEntity(url,Integer.class).getBody();
    }

    /**
     *获取当前用户的还款项目
     * @param userId
     * @param roleId
     * @param borrowNid
     * @return
     */
    @Override
    public BorrowInfoVO searchRepayProject(Integer userId, String roleId, String borrowNid){
        BorrowInfoResponse response=restTemplate.getForEntity("http://AM-TRADE/am-trade/aems/repay/get_borrow/"+userId+"/"+roleId+"/"+borrowNid,BorrowInfoResponse.class).getBody();
        if (Response.SUCCESS.equals(response.getRtn())){
            return response.getResult();
        }
        return null;
    }

    /**
     * 根据借款编号查询当前标的是否有承接失败的债权
     * @return
     */
    @Override
    public boolean getFailCredit(String borrowNid) {
        String url = "http://AM-TRADE/am-trade/repay/getFailCredit/" + borrowNid;
        return restTemplate.getForEntity(url, boolean.class).getBody();
    }


    /**
     * 获取crm投资
     *
     * @return
     */
    @Override
    public List<BorrowTenderVO> selectCrmBorrowTenderList() {
        String url = "http://AM-TRADE/am-trade/crm/selectCrmBorrowTenderList/";
        BorrowTenderResponse response = restTemplate.getForEntity(url, BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }


    /**
     * 获取crm智投投资
     *
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectCrmHjhAccedeList() {
        String url = "http://AM-TRADE/am-trade/crm/selectCrmHjhAccedeList/";
        HjhAccedeResponse response = restTemplate.getForEntity(url, HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 查询用户账户信息金额信息
     *
     * @return
     */
    @Override
    public BankAccountManageCustomizeVO queryAccountUserMoney(Integer userId) {
        BankAccountManageCustomizeResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/borrow/query_account_userMoney/" + userId ,
                BankAccountManageCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取所有在帮助中心显示的模板列表
     * add by nxl 20190313
     * PC 1.1.2
     * @return
     */
    @Override
    public List<ProtocolTemplateVO> getAllShowProtocolTemp() {
        String url = "http://AM-TRADE/am-trade/protocol/getAllShowProtocolTemp";
        ProtocolTemplateResponse response = restTemplate.getForEntity(url, ProtocolTemplateResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 统计最后三天的服务记录 add by nxl
     * app和危险的统计计划加入数量
     *  @author nxl
     * @date 2019/3/25 14:11
     */
    @Override
    public Integer countPlanAccedeRecord(HjhAccedeRequest request) {
        IntegerResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlan/countPlanAccedeRecord/" + request.getPlanNid() ,IntegerResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultInt();
        }
        return null;
    }


    @Override
    public IntegerResponse insertScreenData(ScreenDataBean screenDataBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/screen_data/insert_data",
                screenDataBean, IntegerResponse.class);
    }

    @Override
    public BigDecimal findUserFreeMoney(Integer userId) {
        String url = "http://AM-TRADE/am-trade/screen_data/find_free_money/" + userId;
        BigDecimalResponse body = restTemplate.getForEntity(url, BigDecimalResponse.class).getBody();
        if (body != null) {
          return body.getResultDec();
        }
        return null;

    }

    @Override
    public BigDecimal findYearMoney(Integer userId, String orderId, Integer productType, BigDecimal investMoney) {
        String url = "http://AM-TRADE/am-trade/screen_data/find_year_money/" + userId+"/"+orderId+"/"+productType+"/"+investMoney;
        BigDecimalResponse body = restTemplate.getForEntity(url, BigDecimalResponse.class).getBody();
        if (body != null) {
            return body.getResultDec();
        }
        return null;

    }

    @Override
    public IntegerResponse dealRepayMoney(ScreenDataBean screenDataBean) {
        return restTemplate.postForObject("http://AM-TRADE/am-trade/screen_data/deal_repay_money",
                screenDataBean, IntegerResponse.class);

    }


    @Override
    public UserLargeScreenVO getScalePerformance(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/getscaleperformance";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }

    @Override
    public UserLargeScreenVO getMonthScalePerformanceList(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/getmonthscaleperformancelist";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }
    @Override
    public UserLargeScreenVO getTotalAmount(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/gettotalamount";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }
    @Override
    public UserLargeScreenVO getAchievementDistributionList(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/getachievementdistributionlist";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }
    @Override
    public UserLargeScreenVO getMonthReceivedPayments(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/getmonthreceivedpayments";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }
    @Override
    public UserLargeScreenVO getUserCapitalDetails(){
        String url = "http://AM-TRADE/am-trade/user_large_screen/getusercapitaldetails";
        UserLargeScreenResponse response = restTemplate.getForEntity(url, UserLargeScreenResponse.class).getBody();
        if(response!=null){
            return response.getResult();
        }
        return new UserLargeScreenVO();
    }

    /**
     * 屏幕二日业绩(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getDayScalePerformanceList() {
        String url = "http://AM-TRADE/am-trade/user_large_screen_two/getdayscaleperformancelist";
        UserLargeScreenTwoResponse response = restTemplate.getForEntity(url, UserLargeScreenTwoResponse.class).getBody();
        // 查到数据为空,显示初始化
        List<EchartsResultVO> list = new ArrayList<>();
        EchartsResultVO vo = new EchartsResultVO();
        vo.setCurrentOwner("暂无数据");
        vo.setMoney("0");
        list.add(vo);

        if (null == response.getResult()){
            UserLargeScreenTwoVO result = new UserLargeScreenTwoVO();
            result.setDayScalePerformanceListNew(list);
            result.setDayScalePerformanceListOld(list);
            return result;
        }
        if(CollectionUtils.isEmpty(response.getResult().getDayScalePerformanceListNew())){
            response.getResult().setDayScalePerformanceListNew(list);
        }
        if(CollectionUtils.isEmpty(response.getResult().getDayScalePerformanceListOld())){
            response.getResult().setDayScalePerformanceListOld(list);
        }
        return response.getResult();
    }

    /**
     * 屏幕二日回款(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getDayReceivedPayments() {
        String url = "http://AM-TRADE/am-trade/user_large_screen_two/getdayreceivedpayments";
        UserLargeScreenTwoResponse response = restTemplate.getForEntity(url, UserLargeScreenTwoResponse.class).getBody();
        // 查到数据为空,显示初始化
        List<EchartsResultVO> list = new ArrayList<>();
        EchartsResultVO vo = new EchartsResultVO();
        vo.setCurrentOwner("暂无数据");
        vo.setMoney2("0");
        vo.setMoney("0");
        list.add(vo);

        if (null == response.getResult()){
            UserLargeScreenTwoVO result = new UserLargeScreenTwoVO();
            result.setDayReceivedPaymentsNew(list);
            result.setDayReceivedPaymentsOld(list);
            return result;
        }
        if(CollectionUtils.isEmpty(response.getResult().getDayReceivedPaymentsNew())){
            response.getResult().setDayReceivedPaymentsNew(list);
        }
        if(CollectionUtils.isEmpty(response.getResult().getDayReceivedPaymentsOld())){
            response.getResult().setDayReceivedPaymentsOld(list);
        }
        return response.getResult();
    }

    /**
     * 本月数据统计(新客组、老客组)
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getMonthDataStatistics(List<MonthDataStatisticsVO> currentOwnersAndUserIds) {
        String url = "http://AM-TRADE/am-trade/user_large_screen_two/getmonthdatastatistics";
        UserLargeScreenTwoResponse response = restTemplate.postForEntity(url, currentOwnersAndUserIds, UserLargeScreenTwoResponse.class).getBody();
        // 查到数据为空,显示初始化
        List<MonthDataStatisticsVO> list = new ArrayList<>();
        MonthDataStatisticsVO vo = new MonthDataStatisticsVO();
        vo.setCurrentOwner("暂无数据");
        list.add(vo);

        if (null == response.getResult()){
            UserLargeScreenTwoVO result = new UserLargeScreenTwoVO();
            result.setMonthDataStatisticsNew(list);
            result.setMonthDataStatisticsOld(list);
            return result;
        }
        if(CollectionUtils.isEmpty(response.getResult().getMonthDataStatisticsNew())){
            response.getResult().setMonthDataStatisticsNew(list);
        }
        if(CollectionUtils.isEmpty(response.getResult().getMonthDataStatisticsOld())){
            response.getResult().setMonthDataStatisticsOld(list);
        }
        return response.getResult();
    }

    /**
     * 运营部月度业绩数据
     * @return
     */
    @Override
    public UserLargeScreenTwoVO getOperMonthPerformanceData() {
        String url = "http://AM-TRADE/am-trade/user_large_screen_two/getopermonthperformancedata";
        UserLargeScreenTwoResponse response = restTemplate.getForEntity(url, UserLargeScreenTwoResponse.class).getBody();
        // 查到数据为空,显示初始化
        if(null == response.getResult()){
            UserLargeScreenTwoVO result = new UserLargeScreenTwoVO();
            OperMonthPerformanceDataVO vo = new OperMonthPerformanceDataVO();
            result.setOperMonthPerformanceData(vo);
            return result;
        }
        return response.getResult();
    }
}
