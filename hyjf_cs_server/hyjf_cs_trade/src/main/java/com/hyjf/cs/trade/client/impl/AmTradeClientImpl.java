package com.hyjf.cs.trade.client.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.*;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.app.AppNewAgreementResponse;
import com.hyjf.am.response.app.AppProjectInvestListCustomizeResponse;
import com.hyjf.am.response.app.AppProjectListResponse;
import com.hyjf.am.response.app.AppTenderCreditInvestListCustomizeResponse;
import com.hyjf.am.response.market.AppAdsCustomizeResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.response.trade.account.AccountRechargeResponse;
import com.hyjf.am.response.trade.account.*;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.response.user.BankOpenAccountResponse;
import com.hyjf.am.response.user.HjhPlanResponse;
import com.hyjf.am.response.user.HjhUserAuthResponse;
import com.hyjf.am.response.user.UtmPlatResponse;
import com.hyjf.am.response.wdzj.BorrowDataResponse;
import com.hyjf.am.response.wdzj.PreapysListResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.api.ApiProjectListCustomize;
import com.hyjf.am.vo.app.AppNewAgreementVO;
import com.hyjf.am.vo.app.AppProjectInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTenderCreditInvestListCustomizeVO;
import com.hyjf.am.vo.app.AppTradeListCustomizeVO;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.market.AppAdsCustomizeVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.*;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

    public static final String urlBase = "http://AM-TRADE/am-trade/";

    public static final  String BASE_URL = "http://AM-TRADE/am-trade/projectlist";

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
    public BorrowVO selectBorrowByNid(String borrowNid) {
        String url = urlBase + "borrow/getBorrow/" + borrowNid;
        BorrowResponse response = restTemplate.getForEntity(url, BorrowResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 取得自动投资用加入计划列表
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
    public Map<String, Object> saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast) {
        String url = urlBase + "autoTenderController/saveCreditTenderLog";
        SaveCreditTenderLogRequest request = new SaveCreditTenderLogRequest(credit, hjhAccede, orderId, orderDate, yujiAmoust, isLast);
        MapResponse response = restTemplate.postForEntity(url, request, MapResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResultMap();
    }

    /**
     * 取得当前债权在清算前已经发生债转的本金
     * @param hjhDebtCreditVO
     * @return
     */
    @Override
    public BigDecimal getPreCreditCapital(HjhDebtCreditVO hjhDebtCreditVO) {
        String url = urlBase + "autoTenderController/getPreCreditCapital";
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
    public boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean, String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap) {
        String url = "autoTenderController/updateCreditForAutoTender";
        BankCallBeanVO bankCallBeanVO = new BankCallBeanVO();
        BeanUtils.copyProperties(bean, bankCallBeanVO);
        UpdateCreditForAutoTenderRequest request = new UpdateCreditForAutoTenderRequest(creditNid, accedeOrderId, planNid, bankCallBeanVO, tenderUsrcustid, sellerUsrcustid, resultMap);
        Response response = restTemplate.postForEntity(url, request, Response.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            logger.error("[" + accedeOrderId + "] 银行自动债转成功后，更新债转数据失败。");
            throw new RuntimeException("银行自动债转成功后，更新债转数据失败。");
        }
        return true;
    }

    /**
     * 银行自动投资成功后，更新投资数据
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
            logger.error("[" + accedeOrderId + "] 银行自动投资成功后，更新投资数据失败。");
            throw new RuntimeException("银行自动投资成功后，更新投资数据失败。");
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
        hjhDebtCreditVO.setCreditStatus(2);//转让状态 2完全承接
        hjhDebtCreditVO.setIsLiquidates(1);
        IntegerResponse response = restTemplate.postForEntity(url, hjhDebtCreditVO, IntegerResponse.class).getBody();
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
        HjhPlanResponse response = restTemplate.getForEntity(url, HjhPlanResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return null;
        }
        return response.getResult();
    }

    /**
     * 查询资产状态
     *
     * @param assetListRequest
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     **/
    @Override
    public AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest) {
        AssetDetailCustomizeResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/assetList/findDetailById", assetListRequest,
                        AssetDetailCustomizeResponse.class)
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
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    @Override
    public HjhAccedeVO getHjhAccedeByAccedeOrderId(String accedeOrderId) {
        String url = urlBase + "hjhAccede/getHjhAccedeListByAccedeOrderId/" + accedeOrderId;
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
     * 插入汇计划自动投资临时表
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
     * 删除汇计划自动投资临时表
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
     * 根据主键，更新汇计划自动投资临时表
     * @author liubin
     */
    @Override
    public int updateHjhPlanBorrowTmpByPK(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO) {
        String url = urlBase + "hjhPlanBorrowTmpController/updateHjhPlanBorrowTmpByPK";
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
     * 投资异常定时任务更新投资信息
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
	 * 插入AuthCode
	 */
	@Override
	public void updateAuthCode(List<BatchBorrowTenderCustomizeVO> list) {
		String url = "http://AM-TRADE/am-trade/bankException/updateAuthCode";
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
        Response<BorrowApicronVO> response =
                restTemplate.getForEntity(url,Response.class).getBody();
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
        BooleanResponse result = restTemplate
                .postForEntity(urlBase +"accountWithdraw/updateAccountwithdrawLog", accountwithdraw, BooleanResponse.class).getBody();
        return result.getResultBoolean();
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
     * 查询用户标的投资数量
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
    public List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer countProjectList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countProjectList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    @Override
    public ProjectCustomeDetailVO searchProjectDetail(Map map) {
        ProjectDetailResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchProjectDetail",map,ProjectDetailResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
        if (Response.isSuccess(response)) {
            return response.getResult();
        }
        return null;
    }


    @Override
    public CreditListResponse countCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countCreditList --> response = {}",response);
        return response;
    }

    @Override
    public CreditListResponse searchCreditList(CreditListRequest request) {
        CreditListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchWebCreditList",request,CreditListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchCreditList --> response = {}",response);
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
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
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
    public Integer countPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/web/countPlanList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countProjectList --> response = {}",response);
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
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/web/searchPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchPlanList --> response = {}",response);
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
     *  app端获取散标投资项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    @Override
    public Integer countAppProjectList(ProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countProjectList",request,AppProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppProjectList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getCount();
        }
        return null;
    }

    /**
     * app端获取散标投资项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    @Override
    public List<AppProjectListCustomizeVO> searchAppProjectList(AppProjectListRequest request) {
        AppProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchAppProjectList",request,AppProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
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
    public ProjectListResponse countAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countCreditList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    @Override
    public ProjectListResponse searchAppCreditList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchCreditList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppProjectList --> response = {}",response);
        return response;
    }

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/22 9:59
     */
    @Override
    public Integer countAppPlanList(ProjectListRequest request) {
        ProjectListResponse response =  restTemplate.postForEntity(BASE_URL + "/app/countPlanList",request,ProjectListResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> countAppPlanList --> response = {}",response);
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
    public List<HjhPlanCustomizeVO> searchAppPlanList(ProjectListRequest request) {
        com.hyjf.am.response.trade.HjhPlanResponse response =  restTemplate.postForEntity(BASE_URL + "/app/searchPlanList",request, com.hyjf.am.response.trade.HjhPlanResponse.class).getBody();
        logger.info("WebProjectListClientImpl --> searchAppPlanList --> response = {}",response);
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


    /******************************  app end **************************************/

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
     * 借款主体信息
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
     * 根据投资订单号查询已承接金额
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
    public boolean handlerAfterCash(JSONObject params) {
        String url = "http://AM-TRADE/am-trade/bankException/handlerAfterCash";
        return restTemplate.postForEntity(url,params,Boolean.class).getBody();
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
    public List<BorrowVO> selectBorrowList() {
        BorrowResponse response = restTemplate.getForEntity(
                "http://AM-TRADE/am-trade/trade/selectRepayBorrowList/",
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
    public BorrowVO getBorrowByNid(String borrowId) {
        String url = "http://AM-TRADE/am-trade/borrow/getBorrowByNid/"+borrowId;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (response!=null){
            return response.getResult();
        }
        return null;
    }

    /**
     * 投资之前插入tmp表
     *
     * @param request
     */
    @Override
    public boolean updateBeforeChinaPnR(TenderRequest request) {
        logger.info("散标投资开始插入tmp表");
        IntegerResponse result = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/insertBeforeTender", request, IntegerResponse.class).getBody();
        if (Response.isSuccess(result)) {
            return result.getResultInt()== 0 ? false : true;
        }
        return false;
    }

    /**
     * 用户投资散标操作表
     *
     * @param tenderBg
     * @return
     */
    @Override
    public boolean borrowTender(TenderBgVO tenderBg) {
        logger.info("用户投资散标操作表");
        IntegerResponse result =  restTemplate
                .postForEntity("http://AM-TRADE/am-trade/borrow/borrowTender", tenderBg, IntegerResponse.class).getBody();
        if (Response.isSuccess(result)) {
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
     * 获取投资异步结果
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
                "http://AM-TRADE/am-trade/borrowRepayPlan/selectBorrowRepayPlan/" + borrowNid + "/" + repaySmsReminder,
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
     * 根据承接订单号查询债转投资表
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
     * 獲取銀行開戶信息
     * @param userId
     * @return
     */
    @Override
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        BankOpenAccountResponse response = restTemplate
                .getForEntity("http://AM-USER/am-user/bankopen/selectById/" + userId, BankOpenAccountResponse.class).getBody();
        if (response != null) {
            return response.getResult();
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
     *同步回调收到后,根据logOrderId检索投资记录表
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
        Integer response = restTemplate.postForEntity(url,creditTenderBg,Integer.class).getBody();
        return response;
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
     * 根据投资订单号获取协议列表
     * @param nid
     * @return
     */
    @Override
    public List<TenderAgreementVO> selectTenderAgreementByNid(String nid) {
        String url = urlBase +"tenderagreement/selectTenderAgreementByNid/"+nid;
        TenderAgreementResponse response = restTemplate.getForEntity(url,TenderAgreementResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
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
     * 根据优惠券查询投资信息
     * @param couponTenderNid
     * @return
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
        String url = "http://AM-TRADE/am-trade/batch/getCouponTenderInfo/"+couponTenderNid;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    /**
     * 获取用户优惠券投资信息
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
     * 优惠券投资
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
        CouponResponse response = new CouponResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhlist/"+orderId, CouponResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId) {
        CouponResponse response = new CouponResponse();
        response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/coupon/getborrowtendercpnhjhcoupononlylist/"+couponOrderId, CouponResponse.class).getBody();
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
        return null;
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
        if (Response.isSuccess(response) && response.getResult().getAttornFlag()==0) {
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
     * 验证投资人当天是否可以债转
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
     * 根据投资订单号检索已债转还款信息
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
        return restTemplate.postForEntity(url, borrowCredit, Integer.class).getBody();
    }

    /**
     * 前端Web页面投资可债转输入投资金额后收益提示 用户未登录 (包含查询条件)
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
        return null;
    }

    /**
     * 获取当前有效的冻结记录
     * @auther: hesy
     * @date: 2018/7/10
     */
    @Override
    public BankRepayFreezeLogVO getFreezeLogValid(Integer userId, String borrowNid) {
        String url = "http://AM-TRADE/am-trade/repayfreezelog/get_logvalid/"+userId + "/" + borrowNid;
        BankRepayFreezeLogResponse response = restTemplate.getForEntity(url,BankRepayFreezeLogResponse.class).getBody();
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
     * 投资撤销历史数据处理
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
     * 查询前一天的投资临时数据并进行处理
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
     *查询汇计划债转投资表
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
     *  更新投资协议信息
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
     * 通过主键获取投资协议信息
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
    public List<DebtPlanBorrowCustomizeVO> selectPlanBorrowList(Map<String, Object> params) {
        String url = urlBase+"hjhPlan/selectPlanBorrowList";
        DebtPlanBorrowCustomizeResponse response = restTemplate.postForEntity(url,params,DebtPlanBorrowCustomizeResponse.class).getBody();
        if (response!=null){
            return response.getResultList();
        }
        return null;
    }

    /**
     * 散标投资记录数
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
     * 散标投资记录
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
     * 统计相应的计划总数
     * @param params
     * @return
     */
    @Override
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
     * 获取债转投资人次和已债转金额
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
        Response<Double> response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/feewait_total_user/" + userId, Response.class).getBody();
        if (Response.isSuccess(response)) {
            return new BigDecimal(response.getResult());
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
        Response<Double> response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/feewait_total_org/" + userId, Response.class).getBody();
        if (Response.isSuccess(response)) {
            return new BigDecimal(response.getResult());
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
        Response<Double> response = restTemplate.getForEntity("http://AM-TRADE/am-trade/repay/repaywait_total_org/" + userId, Response.class).getBody();
        if (Response.isSuccess(response)) {
            return new BigDecimal(response.getResult());
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
    public HjhPlanAssetVO checkDuplicateAssetId(String assetId) {
        String url = "http://AM-TRADE/am-trade/assetPush/checkDuplicateAssetId/" + assetId;
        HjhPlanAssetResponse response = restTemplate.getForEntity(url, HjhPlanAssetResponse.class).getBody();
        if (response != null) {
            return response.getResult();
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
     * 根据订单号获取汇计划加入明细
     *
     * @param accedeOrderId
     * @return
     */
    @Override
    public List<HjhAccedeVO> selectHjhAccedeListByOrderId(String accedeOrderId) {
        HjhAccedeResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/batchHjhBorrowRepay/selectHjhAccedeListByOrderId/" + accedeOrderId, HjhAccedeResponse.class).getBody();
        if (response != null) {
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
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponperiodrepay/selectcurrentcouponrecover/" + couponTenderNid + "/" +periodNow,CouponRecoverCustomizeResponse.class).getBody();
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
                postForEntity("http://AM-TRADE/am-trade/adminBatchBorrowRecover/getList", request, BatchBorrowRecoverReponse.class).
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
        Integer result = restTemplate.postForEntity(
                "http://AM-TRADE/am-trade/adminBatchBorrowRecover/getListTotal/", request,
                Integer.class).getBody();
        if (result == null) {
            return 0;
        }
        return result;
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
	 * 投资预插入
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
		String url = "http://AM-TRADE/am-trade/tendercancelexception/deleteBorrowTenderTmp/" + orgOrderId;
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
		String url = "http://AM-TRADE/am-trade/tendercancelexception/deleteBorrowTenderTmpByParam/" + userId + "/" + borrowNid + "/" + orderId;
		IntegerResponse response = restTemplate.getForEntity(url, IntegerResponse.class).getBody();
        if (response == null || !Response.isSuccess(response)) {
            return 0;
        }
        return response.getResultInt().intValue();
	}

	/**
	 * 根据userId和tenderNid查询投资记录
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
            response.getResultStr();
        }
        return null;
    }


    /**
     * 根据tenderNid查询投资记录
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
    public boolean updateBorrowByBorrowNid(BorrowVO borrow) {
        String url = urlBase + "/assetPush/update_borrow_by_borrow_nid";
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
    public List<BorrowVO> selectOverdueBorrowList() {
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
    public BorrowVO getBorrowByNidAndNowTime(String borrowNid, Integer nowTime) {
        String url = "http://AM-TRADE/am-trade/borrow/getByNidAndNowTime/"+borrowNid + "/" + nowTime;
        BorrowResponse response = restTemplate.getForEntity(url,BorrowResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
}
