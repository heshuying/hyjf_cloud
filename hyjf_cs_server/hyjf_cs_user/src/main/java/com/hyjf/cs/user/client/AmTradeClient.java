/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client;

import com.hyjf.am.response.trade.WrbInvestRecordResponse;
import com.hyjf.am.response.trade.wrbInvestRecoverPlanResponse;
import com.hyjf.am.response.user.WrbAccountResponse;
import com.hyjf.am.response.user.WrbInvestSumResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.WrbInvestRecordRequest;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/6/20 12:46
 */
public interface AmTradeClient {

    HjhInstConfigVO selectInstConfigByInstCode(String instCode);

    AccountVO getAccount(Integer userId);

    List<RecentPaymentListCustomizeVO> selectRecentPaymentList(Integer userId);
    /**
     * 根据userId获得填充userPortrait的info --用户画像定时任务用
     * */
    List<BatchUserPortraitQueryVO> searchInfoForUserPortrait(BatchUserPortraitRequest batchUserPortraitRequest);

    Integer countCouponValid(Integer userId);

    List<CouponUserListCustomizeVO> selectCouponUserList(Map<String,Object> mapParameter);

    /**
     * 获取账户信息通过userId范围
     * @param ids
     * @return
     */
    List<AccountVO> getAccountByUserIds(List<Integer> ids);

    /**
     * 获取出借人本金信息
     * @param productSearchForPage
     * @return
     */
    ProductSearchForPageVO selectUserPrincipal(ProductSearchForPageVO productSearchForPage);

    /**
     * 已回收本金和 已回收利息
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomizeVO queryRecoverMoney(Integer userId);

    /**
     * 融通宝累计收益计算
     * @param userId
     * @return
     */
    WebPandectRecoverMoneyCustomizeVO queryRecoverMoneyForRtb(Integer userId);

    /**
     * 待收本金和 待收利息
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomizeVO queryWaitMoney(Integer userId);

    /**
     * 插入预约授权记录表
     * @param userId
     * @return
     */
    WebPandectWaitMoneyCustomizeVO queryWaitMoneyForRtb(Integer userId);

    /**
     * 获取汇天利 购买明细表可赎回金额总额
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumRestAmount(Integer userId);

    /**
     * 债转统计
     * @param userId
     * @return
     */
    WebPandectCreditTenderCustomizeVO queryCreditInfo(Integer userId);

    /**
     * 债转信息
     * 去掉已债转（r.recover_status=1）; 去掉待收已债转（r.recover_status=0）
     * @param userId
     * @param recoverStatus
     * @return
     */
    WebPandectBorrowRecoverCustomizeVO queryRecoverInfo(Integer userId, int recoverStatus);

    /**
     * 获取汇天利 总收益
     * @param userId
     * @return
     */
    BigDecimal queryHtlSumInterest(Integer userId);

    /**
     * 获取某用户优惠券待收收益总和
     * @param userId
     * @return
     */
    String selectCouponInterestTotal(Integer userId);

    /**
     *获取某用户优惠券累计收益总和
     * @param userId
     * @return
     */
    String selectCouponReceivedInterestTotal(Integer userId);


    /**
     * 查询用户出借次数 包含直投类、债转、汇添金
     * @param userId
     * @return
     */
    int selectUserTenderCount(Integer userId);

    /**
     * 查询有效未读的优惠券列表
     * @param userId
     * @return
     */
    List<CouponUserCustomizeVO> selectLatestCouponValidUNReadList(Integer userId);

    /**
     * 获得购买列表数
     * @param htlTradeRequest
     * @return
     */
    Integer countHtlIntoRecord(HtlTradeRequest htlTradeRequest);


    /**
     * 获取购买产品列表
     * @param htlTradeRequest
     * @return
     */
    List<HtlProductIntoRecordVO> getIntoRecordList(HtlTradeRequest htlTradeRequest);

    /**
     * 获得汇天利转出列表数
     * @param htlTradeRequest
     * @return
     */
    Integer countProductRedeemRecord(HtlTradeRequest htlTradeRequest);
    /**
     * 获取汇天利转出记录列表(自定义)
     * @param htlTradeRequest
     * @return
     */
    List<HtlProductRedeemVO> getRedeemRecordList(HtlTradeRequest htlTradeRequest);

    /**
     *获取用户当前持有债权列表总数
     * @param
     * @return
     */
    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);

    /**
     * 获取用户当前持有债权列表
     * @param request
     * @return
     */
    List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request);

    /**
     * 获取用户已回款债权列表总数
     * @param request
     * @return
     */
    int selectRepaymentListTotal(AssetManageBeanRequest request);

    /**
     *
     * @param request
     * @return
     */
    List<AppAlreadyRepayListCustomizeVO> selectAlreadyRepayList(AssetManageBeanRequest request);

    /**
     * 获取用户转让列表数量
     * @param request
     * @return
     */
    int countCreditRecordTotal(AssetManageBeanRequest request);

    /**
     *
     * @param request
     * @return
     */
    List<AppTenderCreditRecordListCustomizeVO> searchCreditRecordList(AssetManageBeanRequest request);

    /**
     * 获取当前持有计划列表总数
     * @param request
     * @return
     */
    int countCurrentHoldPlanTotal(AssetManageBeanRequest request);

    /**
     * 获取当前持有计划列表
     * @param request
     * @return
     */
    List<CurrentHoldPlanListCustomizeVO> selectCurrentHoldPlanList(AssetManageBeanRequest request);

    /**
     * 获取已回款计划列表总数
     * @param request
     * @return
     */
    Integer countRepayMentPlanTotal(AssetManageBeanRequest request);

    /**
     * 获取已回款计划列表
     * @param request
     * @return
     */
    List<RepayMentPlanListCustomizeVO> selectRepayMentPlanList(AssetManageBeanRequest request);


    CouponConfigVO getCouponConfig(String nid);

    BorrowAndInfoVO selectBorrowByBorrowNid(String borrowNid);

    BorrowStyleVO selectBorrowStyleByStyle(String borrowStyle);

    int countRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params);

    List<AppRepayPlanListCustomizeVO> selectRepayRecoverList(AppRepayPlanListBeanRequest params);

    int countRepayPlanListRecordTotal(AppRepayPlanListBeanRequest params);

    List<AppRepayPlanListCustomizeVO> selectRepayPlanList(AppRepayPlanListBeanRequest params);

    int countCouponRepayRecoverListRecordTotal(AppRepayPlanListBeanRequest params);

    String selectReceivedInterest(AppRepayPlanListBeanRequest params);

    List<AppRepayPlanListCustomizeVO> selectCouponRepayRecoverList(AppRepayPlanListBeanRequest params);

    AppProjectContractDetailCustomizeVO selectProjectContractDetail(AppProjectContractDetailBeanRequest params);

    AppProjectDetailCustomizeVO selectProjectDetail(String borrowNid);

    List<AppProjectContractRecoverPlanCustomizeVO> selectProjectContractRecoverPlan(AppProjectContractDetailBeanRequest params);

    BorrowCreditVO selectCreditTenderByCreditNid(String creditNid);

    List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayPlanList(AppRepayPlanListBeanRequest params);

    List<AppTenderCreditRepayPlanListCustomizeVO> selectTenderCreditRepayRecoverPlanList(AppRepayPlanListBeanRequest params);

    List<AppTenderToCreditListCustomizeVO> selectTenderToCreditList(Map<String,Object> params);

    /**
     * APP获取我的优惠券分页数据
     * @param requestBean 参数
     * @return List<CouponUserForAppCustomizeVO>
     */
    List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean);

    /**
     * 借款人受托支付申请异步回调更新数据
     * @param borrowNid
     * @return
     */
    Boolean updateTrusteePaySuccess(String borrowNid);

    /**
     * 查询信托白名单
     *
     * @param instCode
     * @param receiptAccountId
     * @return
     */
    STZHWhiteListVO getSTZHWhiteList(String instCode, String receiptAccountId);

    /**
     * 第三方用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateBeforeCash(ApiUserWithdrawRequest request);

    /**
     * 根据用户ID修改account表的电子账户
     * @param userId
     * @param accountId
     * @return
     */
    Integer updateAccountNumberByUserId(int userId, String accountId);

    /**
     *根据机构编号检索机构信息
     * @param instcode
     * @return
     */
    HjhInstConfigVO selectHjhInstConfig(String instcode);

    /**
     * 根据标的ID查询可出借标的信息
     * @param borrowNid
     * @return
     */
    List<WrbBorrowListCustomizeVO> searchBorrowListByNid(String borrowNid);

    /**
     * 根据优惠券编号获取优惠券配置信息
     * @param couponCode
     * @return
     */
    CouponConfigVO getCouponByCouponCode(String couponCode);

    /**
     *获取某天出借情况汇总
     * @param date
     * @return
     */
    WrbInvestSumResponse getDaySum(Date date);

    /**
     * 根据平台用户id获取账户信息
     * @param userId
     * @return
     */
    WrbAccountResponse getAccountInfo(String userId);

    /**
     * 获取用户优惠券信息
     * @param userId
     * @return
     */
    WrbAccountResponse getCouponInfo(String userId);

    /**
     * 出借记录查询
     * @param request
     * @return 出借记录
     * @throws Exception
     */
    WrbInvestRecordResponse getInvestRecord(WrbInvestRecordRequest request);

    /**
     * 获取出借记录回款计划
     * @param userId
     * @param investRecordId 流水号
     * @param borrowNid
     * @return
     */
    wrbInvestRecoverPlanResponse getRecoverPlan(String userId, String investRecordId, String borrowNid);
    /**
     * 获取某天出借情况
     * @param invest_date
     * @param limit
     * @param page
     * @return
     */
    List<BorrowTenderVO> getInvestDetail(Date invest_date, Integer limit, Integer page);

    /**
     * 查询标的出借情况
     * @param borrowNid
     * @param investTime
     * @return
     */
    List<WrbBorrowTenderCustomizeVO> selectWrbBorrowTender(String borrowNid, Date investTime);

    /**
     * 根据标的号和出借时间查询出借情况
     * @param borrowNid
     * @param investTime
     * @return
     */
    WrbBorrowTenderSumCustomizeVO searchBorrowTenderSumByNidAndTime(String borrowNid, Date investTime);

    boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest);

    List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request);

    /**
     * 获取正确的额borrowVo对象
     */
    RightBorrowVO getRightBorrowByNid(String borrowId);

    Integer selectMyCouponCount(MyCouponListRequest requestBean);
    BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean);

    /**
     * 获得所有协议类型
     * @return
     */
    List<ProtocolTemplateVO> getProtocolTypes();

    /** 用户测评配置 */
    List<EvaluationConfigVO> selectEvaluationConfig(EvaluationConfigVO record);
}
