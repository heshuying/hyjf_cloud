package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author xiasq
 * @version AmTradeClient, v0.1 2018/6/19 15:44
 */
public interface AmTradeClient {

    /**
     * 统计加息券每日待收收益
     * @param timeStart
     * @param timeEnd
     * @return
     */
    List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd);

    /**
     * 统计加息券每日已收收益
     * @param timeStart
     * @return timeEnd
     */
    BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd);


    List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean);

    Integer selectMyCouponCount(MyCouponListRequest requestBean);

    List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean);

    /**
     * 根据borrowNid查询优惠券还款列表
     * @param borrowNid
     * @param repayTimeConfig
     * @return
     */
    List<CouponTenderCustomizeVO> selectCouponRecoverAll(String borrowNid, int repayTimeConfig);

    /**
     * 取得分期付款信息
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow);


    int selectMyRewardCount(MyInviteListRequest requestBean);

    BigDecimal selectMyRewardTotal(MyInviteListRequest requestBean);

    List<BorrowAuthCustomizeVO> selectBorrowAuthList(BorrowAuthRequest requestBean);

    int selectBorrowAuthCount(BorrowAuthRequest requestBean);

    List<BorrowAuthCustomizeVO> selectBorrowAuthedList(BorrowAuthRequest requestBean);

    int selectBorrowAuthedCount(BorrowAuthRequest requestBean);

    Integer updateTrusteePaySuccess(String borrowNid);

    STZHWhiteListVO getStzhWhiteListVO(Integer userId, Integer stzUserId);

    /**
     * 获取用户权限
     * @author zhangyk
     * @date 2018/6/29 18:36
     */
    HjhUserAuthVO getUserAuthByUserId(Integer userId);

    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 取得自动投资用加入计划列表
     * @author liubin
     * @return
     */
    List<HjhAccedeVO> selectPlanJoinList();

    /**
     * 计算实际金额 保存creditTenderLog表
     * @author liubin
     * @return
     */
    Map<String,Object> saveCreditTenderLog(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast);

    /**
     * 取得当前债权在清算前已经发生债转的本金
     * @author liubin
     * @return
     */
    BigDecimal getPreCreditCapital(HjhDebtCreditVO credit);

    /**
     * 银行自动债转成功后，更新债转数据
     * @author liubin
     * @return
     */
    boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean, String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap);

    /**
     * 银行自动投资成功后，更新投资数据
     * @author liubin
     * @return
     */
    boolean updateBorrowForAutoTender(BorrowVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean);

    /**
     * 根据是否原始债权获出让人投标成功的授权号
     * @author liubin
     * @return
     */
    String getSellerAuthCode(String sellOrderId, Integer sourceType);

    /**
     * 银行结束债权后，更新债权表为完全承接
     * @author liubin
     * @return
     */
    int updateHjhDebtCreditForEnd(HjhDebtCreditVO credit);

    /**
     * 请求结束债权（追加结束债权任务记录）
     * @author liubin
     * @return
     */
    int requestDebtEnd(HjhDebtCreditVO credit, String sellerUsrcustid, String sellerAuthCode);

    /**
     * 根据标的编号，查询汇计划信息
     * @author liubin
     * @return
     */
    HjhPlanVO getPlanByNid(String borrowNid) ;

    /**
     * 根据creditNid查询债转信息
     * @author liubin
     */
    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);

    /**
     * 根据加入计划订单，取得加入订单
     * @author liubin
     * @date 2018/7/04 19:26
     */
    HjhAccedeVO getHjhAccedeByAccedeOrderId(String contract_id);

    /**
     * 更新加入计划状态
     * @author liubin
     * @date 2018/7/04 19:26
     */
    int updateHjhAccedeByPrimaryKey(HjhAccedeVO hjhAccedeVO);

    /**
     * 插入汇计划自动投资临时表
     * @author liubin
     */
    int insertHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO);

    /**
     * 删除汇计划自动投资临时表
     * @author liubin
     */
    int deleteHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO);

    /**
     * 根据主键，更新汇计划自动投资临时表
     * @author liubin
     */
    int updateHjhPlanBorrowTmpByPK(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO);

    /**
     * APP获取我的优惠券分页数据
     * @param requestBean 参数
     * @return List<CouponUserForAppCustomizeVO>
     */
    List<CouponUserForAppCustomizeVO> getMyCoupon(MyCouponListRequest requestBean);

    /**
     * 批次结束债权用更新 结束债权任务表
     * @param bankCreditEndVO
     * @return
     */
    int updateCreditEndForBatch(BankCreditEndVO bankCreditEndVO);

    /**
     * APP,PC,wechat散标投资获取优惠券列表
     * @param requestBean
     * @return
     */
    CouponResponse getBorrowCoupon(MyCouponListRequest requestBean);

    /**
     * APP,PC,wechat加入计划获取优惠券列表
     * @param requestBean
     * @return
     */
    CouponResponse getPlanCoupon(MyCouponListRequest requestBean);

    /**
     * 根据批次号和日期，取得结束债权任务列表
     * @param bankCreditEndVO
     * @return
     */
    List<BankCreditEndVO> getBankCreditEndListByBatchnoTxdate(BankCreditEndVO bankCreditEndVO);

    /**
     * 根据条件(批次号和日期)，更新结束债权任务状态
     * @param bankCreditEndVO
     * @param status
     * @return
     */
    int updateCreditEndForStatus(BankCreditEndVO bankCreditEndVO, int status);

    /**
     * 合法性检查后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     */
    int updateBatchCreditEndCheck(BankCallBeanVO bankCallBeanVO);

    /**
     * 银行完成后，更新批次结束债权任务
     * @param bankCallBeanVO
     * @return
     */
    int updateBatchCreditEndFinish(BankCallBeanVO bankCallBeanVO);

    /**
     * 根据borrowNid获取BorrowInfoVO对象
     * @param borrowNid
     * @return
     */
    BorrowInfoVO getBorrowInfoByNid(String borrowNid);

    /**
     * 投资异常处理定时任务处理
     * @param request
     * @return
     */
    boolean updateTenderStart(BorrowTenderTmpRequest request);

    /**
     * 获取BorrowTenderTmpVO列表
     * @return
     */
	List<BorrowTenderTmpVO> getBorrowTenderTmpList();

	/**
	 * 获取BatchBorrowTenderCustomizeVO列表
	 * @return
	 */
	List<BatchBorrowTenderCustomizeVO> queryAuthCodeBorrowTenderList();

	/**
	 * @param list
	 */
	void insertAuthCode(List<BatchBorrowTenderCustomizeVO> list);

    List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean);

    Integer countBorrowList(Map<String, Object> requestBean);

    String sumBorrowAmount(Map<String, Object> requestBean);

    List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean);

    Integer countPreapysList(Map<String, Object> requestBean);

    Boolean updateBorrowApicron(ApiCronUpdateRequest requestBean);

    BorrowApicronVO selectBorrowApicron(String bankSeqNO);

    /**
     * 根据订单号查询充值信息
     * @param orderId
     * @return
     */
    AccountRechargeVO selectByOrderId(String orderId);

    /**
     * 更新充值详情信息
     * @param accountRecharge
     */
    void updateAccountRecharge(AccountRechargeVO accountRecharge);
    /**
     * 根据订单号查询充值数量
     * @param ordId
     * @return
     */
    int selectByOrdId(String ordId);

    /**
     * 插入银行卡信息
     * @param bankRequest
     * @return
     */
    int insertSelectiveBank(BankRequest bankRequest);

    /**
     * 修改银行卡信息
     * @param bankAccountBeanRequest
     * @return
     */
    boolean updateBanks(BankAccountBeanRequest bankAccountBeanRequest);

    /**
     * 根据用户userId查询账号体系
     * @param userId
     * @return
     */
    AccountVO getAccount(Integer userId);

    /**
     * 根据订单号查询用户提现记录列表
     * @param ordId
     * @return
     */
    List<AccountWithdrawVO> selectAccountWithdrawByOrdId(String ordId);

    /**
     * 插入提现记录
     * @param record
     */
    void insertAccountWithdrawLog(AccountWithdrawVO record);

    /**
     * 根据订单号查询用户提现记录信息
     * @param logOrderId
     * @return
     */
    AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId);

    /**
     * 更新用户提现记录
     * @param accountwithdraw
     * @return
     */
    int updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw);

    /**
     * 提现后续操作
     * @param bankWithdrawBeanRequest
     * @return
     */
    int updatUserBankWithdrawHandler(BankWithdrawBeanRequest bankWithdrawBeanRequest);

    /**
     * 查询用户标的投资数量
     * @param userId
     * @return
     */
    Integer getBorrowTender(Integer userId);

    /**
     * 根据用户id查询当前充值信息
     * @param userId
     * @return
     */
    List<AccountRechargeVO> getTodayRecharge(Integer userId);

    /**
     * 插入线下充值同步余额信息账户明细
     * @param synBalanceBeanRequest
     * @return
     */
    boolean insertAccountDetails(SynBalanceBeanRequest synBalanceBeanRequest);
}
