package com.hyjf.cs.trade.client;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.admin.AssetListRequest;
import com.hyjf.am.resquest.admin.BatchBorrowRecoverRequest;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.api.ApiRepayListRequest;
import com.hyjf.am.resquest.api.AutoTenderComboRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.resquest.trade.ApiCronUpdateRequest;
import com.hyjf.am.resquest.trade.AppProjectListRequest;
import com.hyjf.am.resquest.trade.AssetManageBeanRequest;
import com.hyjf.am.resquest.trade.BankRepayFreezeLogRequest;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.resquest.trade.BatchRepayDataRequest;
import com.hyjf.am.resquest.trade.BorrowAuthRequest;
import com.hyjf.am.resquest.trade.BorrowCreditRequest;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.resquest.trade.CreditListRequest;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.resquest.trade.DebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhAccedeRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.resquest.trade.HjhDebtCreditTenderRequest;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyCreditListQueryRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.trade.ProjectListRequest;
import com.hyjf.am.resquest.trade.RepayListRequest;
import com.hyjf.am.resquest.trade.RepayRequestDetailRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.resquest.trade.SynBalanceBeanRequest;
import com.hyjf.am.resquest.trade.TenderCancelRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.resquest.trade.TradeDetailBeanRequest;
import com.hyjf.am.resquest.trade.WechatMyProjectRequest;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.admin.AssetDetailCustomizeVO;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.admin.BatchBorrowRecoverVo;
import com.hyjf.am.vo.admin.NifaContractTemplateVO;
import com.hyjf.am.vo.admin.NifaFieldDefinitionVO;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.UnderLineRechargeVO;
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
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO;
import com.hyjf.am.vo.trade.ApiTransactionDetailsCustomizeVO;
import com.hyjf.am.vo.trade.AppProjectListCustomizeVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.am.vo.trade.BatchCouponTimeoutCommonCustomizeVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.BorrowRecoverPlanVO;
import com.hyjf.am.vo.trade.ChinapnrExclusiveLogWithBLOBsVO;
import com.hyjf.am.vo.trade.ChinapnrLogVO;
import com.hyjf.am.vo.trade.CreditPageVO;
import com.hyjf.am.vo.trade.CreditRepayVO;
import com.hyjf.am.vo.trade.CreditTenderBgVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.ExpectCreditFeeVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.am.vo.trade.IncreaseInterestInvestVO;
import com.hyjf.am.vo.trade.InvestListCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.ProjectCompanyDetailVO;
import com.hyjf.am.vo.trade.ProjectCustomeDetailVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderCreditCustomizeVO;
import com.hyjf.am.vo.trade.TenderCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.TenderToCreditAssignCustomizeVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectListCustomizeVO;
import com.hyjf.am.vo.trade.WebProjectPersonDetailVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.account.AppAccountTradeListCustomizeVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.assetmanage.AppAlreadyRepayListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppMyPlanCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.AppTenderCreditRecordListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldObligatoryRightListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.CurrentHoldPlanListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.QueryMyProjectVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentListCustomizeVO;
import com.hyjf.am.vo.trade.assetmanage.RepayMentPlanListCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BatchBorrowTenderCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BatchCenterCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.borrow.BorrowCarinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowConfigVO;
import com.hyjf.am.vo.trade.borrow.BorrowHousesVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowInfoWithBLOBsVO;
import com.hyjf.am.vo.trade.borrow.BorrowManinfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayVO;
import com.hyjf.am.vo.trade.borrow.BorrowStyleVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderTmpVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowUserVO;
import com.hyjf.am.vo.trade.borrow.DebtPlanBorrowCustomizeVO;
import com.hyjf.am.vo.trade.borrow.ProjectUndertakeListVO;
import com.hyjf.am.vo.trade.borrow.RightBorrowVO;
import com.hyjf.am.vo.trade.borrow.TenderBgVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.AppCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.hjh.BorrowBailVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditRepayVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhLabelVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanBorrowTmpVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.am.vo.trade.htj.DebtPlanAccedeCustomizeVO;
import com.hyjf.am.vo.trade.nifa.NifaContractEssenceVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.trade.repay.RepayListCustomizeVO;
import com.hyjf.am.vo.trade.repay.WebUserRepayProjectListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserRechargeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserTradeListCustomizeVO;
import com.hyjf.am.vo.trade.tradedetail.WebUserWithdrawListCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.cs.trade.bean.BatchCenterCustomize;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.bean.TransactionDetailsResultBean;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

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

    BorrowAndInfoVO selectBorrowByNid(String borrowNid);

    /**
     * 取得自动投资用加入计划列表
     * @author liubin
     * @return
     */
    List<HjhAccedeVO> selectPlanJoinList();

    /**
     * 计算计划债转实际金额 保存creditTenderLog表
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
    boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean, String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap);

    /**
     * 银行自动投资成功后，更新投资数据
     * @author liubin
     * @return
     */
    boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean);

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
     * @param hjhPlanBorrowTmpVO
     * @return
     */
    int updateHjhPlanBorrowTmp(HjhPlanBorrowTmpVO hjhPlanBorrowTmpVO);

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
	void updateAuthCode(List<BatchBorrowTenderCustomizeVO> list);

    List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean);

    Integer countBorrowList(Map<String, Object> requestBean);

    String sumBorrowAmount(Map<String, Object> requestBean);

    List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean);

    Integer countPreapysList(Map<String, Object> requestBean);

    Boolean updateBorrowApicron(ApiCronUpdateRequest requestBean);

    Boolean updateBorrowCreditStautus(String borrowNid);

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
    int insertAccountWithdrawLog(AccountWithdrawVO record);

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
    boolean updateAccountwithdrawLog(AccountWithdrawVO accountwithdraw);

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

    /**
     * 获取线下充值类型列表
     * @param request
     * @return
     * @Author : huanghui
     */
    List<UnderLineRechargeVO> selectUnderLineRechargeList(UnderLineRechargeRequest request);

    /**
     * 获取项目列表
     * @param request
     * @return
     */
    List<WebProjectListCustomizeVO> searchProjectList(ProjectListRequest request);

    /**
     * 查询所有分页总数
     * @param request
     * @return
     */
    public Integer countProjectList(ProjectListRequest request);

    /**
     * 获取标的详情
     * @author zhangyk
     * @date 2018/6/22 19:24
     */
    public ProjectCustomeDetailVO searchProjectDetail(Map map);

    /**
     *  查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse countCreditList(CreditListRequest request);

    /**
     *  查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public CreditListResponse searchCreditList(CreditListRequest request);


    /**
     * 查询计划专区上部统计数据
     * @author zhangyk
     * @date 2018/6/21 15:27
     */
    public Map<String,Object>  searchPlanData(ProjectListRequest request);

    /**
     * 查询计划专区总数据count
     * @author zhangyk
     * @date 2018/6/21 15:28
     */
    public Integer  countPlanList(ProjectListRequest request);

    /**
     * 查询计划专区总数据list
     * @author zhangyk
     * @date 2018/6/21 15:29
     */
    public List<HjhPlanCustomizeVO> searchPlanList(ProjectListRequest request);

    /**
     * 查询计划基本详情
     * @author zhangyk
     * @date 2018/7/14 18:20
     */
    public PlanDetailCustomizeVO getPlanDetail(String planNid);

    // --------------------------- web end  -----------------------------------

    /* ************************  app start  **************************************/

    /**
     *  app端获取散标投资项目count
     * @author zhangyk
     * @date 2018/6/20 17:23
     */
    public Integer countAppProjectList(ProjectListRequest request);

    /**
     * app端获取散标投资项目列表
     * @author zhangyk
     * @date 2018/6/20 17:24
     */
    public List<AppProjectListCustomizeVO> searchAppProjectList(AppProjectListRequest request);

    /**
     *  app端查询债权转让所有分页总数
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public ProjectListResponse countAppCreditList(ProjectListRequest request);

    /**
     *  APP端查询债权转让数据列表
     * @author zhangyk
     * @date 2018/6/19 16:39
     */
    public ProjectListResponse searchAppCreditList(ProjectListRequest request);

    /**
     * APP端查询计划数据count
     * @author zhangyk
     * @date 2018/6/21 19:17
     */
    public Integer countAppPlanList(ProjectListRequest request);

    /**
     * APP端查询计划数据list
     * @author zhangyk
     * @date 2018/6/21 19:17
     */
    public List<HjhPlanCustomizeVO> searchAppPlanList(ProjectListRequest request);
    /* ************************  app end  **************************************/

    public BorrowUserVO getBorrowUser(String borrowNid);

    /**
     * 公司项目详情
     * @author zhangyk
     * @date 2018/6/26 15:58
     */
    public ProjectCompanyDetailVO searchProjectCompanyDetail(String borrowNid);

    /**
     * 个人项目详情
     * @author zhangyk
     * @date 2018/6/26 15:59
     */
    public WebProjectPersonDetailVO searchProjectPersonDetail(String borrowNid);

    /**
     * 借款信息
     * @param borrowNid
     * @return
     */
    BorrowManinfoVO getBorrowManinfo(String borrowNid);

    /**
     * 房屋抵押信息
     * @author zhangyk
     * @date 2018/7/18 13:59
     */
    List<BorrowHousesVO> getBorrowHousesByNid(String borrowNid);

    /**
     * 车辆抵押信息
     * @author zhangyk
     * @date 2018/7/18 14:00
     */
    List<BorrowCarinfoVO> getBorrowCarinfoByNid(String borrowNid);


    /**
     * 检索borrowRecover数据
     *
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    List<BorrowRepayVO> selectBorrowRepayList(String borrowNid, Integer repaySmsReminder);

    /**
     * 更新borrowRepay
     *
     * @param borrowRepayVO
     * @return
     */
    Integer updateBorrowRepay(BorrowRepayVO borrowRepayVO);


    /**
     * 根据borrowNid 获取还款信息
     * @author zhangyk
     * @date 2018/6/30 14:01
     */
    List<BorrowRepayVO> getBorrowRepayList(String borrowNid);

    BorrowRepayVO getBorrowRepay(String borrowNid);

    /**
     * 项目详情
     * @author zhangyk
     * @date 2018/7/18 14:06
     */
    ProjectCustomeDetailVO selectProjectDetail(String borrowNid);


    /**
     * 统计计划加入总数
     * @author zhangyk
     * @date 2018/6/27 19:05
     */
    Integer countPlanAccedeRecordTotal(HjhAccedeRequest request);


    AccountVO getAccountByUserId(Integer userId);

    /**
     * 计划退出更新状态用
     * @Author liushouyi
     * @param account
     * @return
     */
    Integer updateOfPlanRepayAccount(AccountVO account);



    public Integer  countUserInvest(Integer userId, String borrowNid);

    public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest);

    public List<FddTempletVO> getFddTempletList(Integer protocolType);

    public int saveTenderAgreement(TenderAgreementVO info);

    public int updateTenderAgreement(TenderAgreementVO tenderAgreement);

    List<BorrowTenderVO> getBorrowTenderListByNid(String nid);

    /**
     * 根据投资订单号查询已承接金额
     * @param tenderNid
     * @return
     */
    BigDecimal getAssignCapital(String tenderNid);

    /**
     * 保存债转日志
     * @param creditTenderLog
     * @return
     */
    Integer saveCreditTenderAssignLog(CreditTenderLogVO creditTenderLog);

    /**
     * 查看是否已经插入网站收支明细
     * @param logOrderId
     * @param tenderType  交易类型
     * @return
     */
    Integer countAccountWebListByOrdId(String logOrderId, String tenderType);

    /**
     * @Description 插入计划交易明细表  加入计划成功后 操作计划表和明细表
     * @Author sunss
     * @Date 2018/6/22 10:34
     */
    boolean insertHJHPlanAccede(HjhAccedeVO planAccede);

    /**
     * 检索正在还款中的标的
     * @return
     */
    List<BorrowAndInfoVO> selectBorrowList();

    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    BorrowAndInfoVO getBorrowByNid(String borrowId);


    /**
     * 获取正确的额borrowVo对象
     * @author zhangyk
     * @date 2018/9/13 17:35
     */
    RightBorrowVO getRightBorrowByNid(String borrowId) ;

    /**
     * 投资之前插入tmp表
     * @param request
     */
    boolean updateBeforeChinaPnR(TenderRequest request);

    /**
     * 用户投资散标操作表
     * @param tenderBg
     * @return
     */
    boolean borrowTender(TenderBgVO tenderBg);

    /**
     * 修改状态临时表结果
     * @param logUserId
     * @param logOrderId
     * @param respCode
     * @param retMsg
     * @param productId
     */
    boolean updateTenderResult(String logUserId, String logOrderId, String respCode, String retMsg, String productId);

    /**
     * 获取投资异步结果
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    String getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid);


    /**
     * 获取还款方式
     * @param borrowStyle
     * @return
     */
    public BorrowStyleVO getBorrowStyle(String borrowStyle);

    /**
     * 会计划投资详情
     * @param params
     * @return
     */
    public UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params);

    /**
     * 根据用户id获取总投资笔数
     * @author zhangyk
     * @date 2018/7/5 18:04
     */
    public Integer getTotalInverestCount(String userId);



    PlanDetailCustomizeVO getPlanDetailByPlanNid(String planId);


    /**
     * 获取app首页的汇计划列表
     * @author zhangyk
     * @date 2018/7/9 11:19
     */
    List<HjhPlanCustomizeVO> getAppHomePlanList(HjhPlanRequest request);


    /**
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByAccedeOrderId(String accedeOrderId);

    /**
     *
     * @param accedeOrderId
     * @param borrowNid
     * @return
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByOrderIdNid(String accedeOrderId,String borrowNid);


    /**
     * 汇计划债转协议下载
     * @return
     */
    public List<HjhDebtCreditTenderVO> selectHjhCreditTenderListByAssignOrderId(String assignOrderId);


    /**
     * 获取债转信息
     * @param request1
     * @return
     */
    public List<HjhDebtCreditVO> getHjhDebtCreditList(HjhDebtCreditRequest request1) ;



    /**
     * 根据borrowNid和orderStatus查询债转列表
     * String borrowNid   不可空
     * List<Integer> creditStatus;  可空
     * @author zhangyk
     * @date 2018/6/29 14:15
     */
    List<HjhDebtCreditVO> selectHjhDebtCreditListByBorrowNidAndStatus(DebtCreditRequest request);


    /**
     * 查询债转投资数目
     * @author zhangyk
     * @date 2018/6/29 14:36
     */
    Integer countCreditTenderByBorrowNidAndUserId(Map<String,Object> map);


    /**
     * 根据债转编号查询债转信息
     * @author zhangyk
     * @date 2018/6/30 11:04
     */
    AppCreditDetailCustomizeVO selectHjhCreditByCreditNid(String creditNid);

    /**
     * 根据标的编号查询还款计划
     * @param borrowNid
     * @param repaySmsReminder
     * @return
     */
    List<BorrowRepayPlanVO> selectBorrowRepayPlan(String borrowNid, Integer repaySmsReminder);

    /**
     * 短信发送后更新borrowRecoverPlan
     *
     * @param borrowRepayPlanVO
     * @return
     */
    Integer updateBorrowRepayPlan(BorrowRepayPlanVO borrowRepayPlanVO);

    /**
     * 根据borrowNid和borrowPeriod查询
     * @param bidNid
     * @param borrowPeriod
     * @return
     */
    List<BorrowRepayPlanVO> getBorrowRepayPlansByPeriod(String bidNid, Integer borrowPeriod);

    List<BorrowRepayPlanVO> getBorrowRepayPlansByBorrowNid(String borrowNid);

    List<CreditTenderLogVO> selectCreditTenderLogs();

    List<CreditTenderVO> selectCreditTender(String assignNid);

    BankOpenAccountVO getBankOpenAccount(Integer userId);

    Boolean updateCreditTenderLog(CreditTenderLogVO creditTenderLog);

    CreditTenderLogVO selectCreditTenderLogByOrderId(String logOrderId);

    AccountVO getAccount(int sellerUserId);

    void handle();

    CreditTenderVO selectByAssignNidAndUserId(String logOrderId, Integer userId);

    boolean updateTenderCreditInfo(CreditTenderRequest request);

    List<CreditTenderLogVO> getCreditTenderLogs(String logOrderId, Integer userId);

    List<BorrowCreditVO> getBorrowCreditList(String creditNid, int sellerUserId, String tenderOrderId);

    List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request);

    List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String,Object> params);

    List<TenderToCreditDetailCustomizeVO> selectHJHWebCreditTenderDetail(Map<String,Object> params);

    /**
     * 保存债转的数据
     * @param creditTenderBg
     */
    Integer saveCreditBgData(CreditTenderBgVO creditTenderBg);

    BorrowRecoverVO selectBorrowRecoverByTenderNid(String tenderAgreementID);

    BorrowRecoverVO selectBorrowRecoverById(Integer id);

    BorrowRecoverVO selectBorrowRecoverByNid(String nid);

    List<BorrowRecoverVO> selectBorrowRecoverByBorrowNid(String borrowNid);

    void updateBorrowRecover(BorrowRecoverVO borrowRecover);

    /**
     * 根据tenderNid   和  bidNid 查询
     * @param tenderNid
     * @param bidNid
     * @return
     */
    BorrowRecoverVO getBorrowRecoverByTenderNidBidNid(String tenderNid, String bidNid);

    /**
     * 根据tenderNid 查询
     * @param tenderNid
     * @return
     */
    BorrowRecoverVO getBorrowRecoverByTenderNid(String tenderNid);

    /**
     * 获取borrow_recover_plan更新每次还款时间
     * @param bidNid
     * @param creditTenderNid
     * @param periodNow
     * @return
     */
    BorrowRecoverPlanVO getPlanByBidTidPeriod(String bidNid, String creditTenderNid, int periodNow);

    /**
     * 获取用户当前持有债权列表
     * @param request
     * @return
     */
    List<CurrentHoldObligatoryRightListCustomizeVO> selectCurrentHoldObligatoryRightList(AssetManageBeanRequest request);
    /**
     * 获取用户当前持有债权列表总数
     * @param request
     * @return
     */
    int selectCurrentHoldObligatoryRightListTotal(AssetManageBeanRequest request);
    /**
     * 获取用户已回款债权列表总数
     * @param request
     * @return
     */
    int selectRepaymentListTotal(AssetManageBeanRequest request);
    /**
     * 获取用户已回款债权列表总数(产品加息需求迁移时添加)
     * @param request
     * @return
     */
    int selectRepaymentListTotalWeb(AssetManageBeanRequest request);
    /**
     * 获取用户债权转让列表总数
     * @param request
     * @return
     */
    int countCreditRecordTotal(AssetManageBeanRequest request);

    /**
     * 根据投资订单号获取协议列表
     * @param nid
     * @return
     */
    List<TenderAgreementVO> selectTenderAgreementByNid(String nid);

    /**
     * 获取用户已回款债权列表
     * @param request
     * @return
     */
    List<RepayMentListCustomizeVO> selectRepaymentList(AssetManageBeanRequest request);
    /**
     *获取用户债权转让列表
     * @param request
     * @return
     */
    List<TenderCreditDetailCustomizeVO> selectCreditRecordList(AssetManageBeanRequest request);

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

    /**
     * @Description 获取交易类型列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<AccountTradeVO> selectTradeTypes();
    /**
     * @Description "获取用户收支明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countUserTradeRecordTotal(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户收支明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WebUserTradeListCustomizeVO> searchUserTradeList(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户充值列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countUserRechargeRecordTotal(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户充值列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WebUserRechargeListCustomizeVO> searchUserRechargeList(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户提现列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    int countUserWithdrawRecordTotal(TradeDetailBeanRequest form);
    /**
     * @Description 获取用户提现列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<WebUserWithdrawListCustomizeVO> searchUserWithdrawList(TradeDetailBeanRequest form);

    /**
     * 取得优惠券投资信息
     * @param couponTenderNid
     * @return
     */
    BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid);

    /**
     * 获取用户优惠券投资信息
     * @param userId
     * @param borrowNid
     * @param logOrdId
     * @param couponGrantId
     * @return
     */
    BorrowTenderCpnVO getCouponTenderByTender(Integer userId, String borrowNid, String logOrdId, Integer couponGrantId);

    /**
     * @Author walter.limeng
     * @Description  更新borrowTenderCpn表
     * @Date 10:57 2018/7/18
     * @Param borrowTenderCpn
     * @return
     */
    int updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn);

    /**
     * @Description 根据优惠券ID和用户ID查询优惠券
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 16:20
     */
    CouponUserVO getCouponUser(Integer couponGrantId, Integer userId);

    /**
     * 优惠券投资
     * @param couponTender
     * @return
     */
    boolean updateCouponTender(CouponTenderVO couponTender);

    /**
     * @Author walter.limeng
     * @Description  获取汇计划投资列表（优惠券）
     * @Date 10:37 2018/7/17
     * @Param orderId 订单ID
     * @return
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId);

    /**
     * @Author walter.limeng
     * @Description  优惠券单独投资时用
     * @Date 10:47 2018/7/17
     * @Param couponOrderId
     * @return
     */
    List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId);

    /**
     * @Author walter.limeng
     * @Description  根据tenderNid查询放款优惠券总数
     * @Date 13:54 2018/7/17
     * @Param tenderNid
     * @return
     */
    int countByExample(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  更新还款期
     * @Date 14:15 2018/7/17
     * @Param tenderNid 投资订单编号
     * @Param currentRecoverFlg 0:非还款期，1;还款期
     * @Param period 期数
     * @return
     */
    Integer crRecoverPeriod(String tenderNid, int currentRecoverFlg, int period);


    CouponConfigVO selectCouponConfig(String couponCode);


    BestCouponListVO selectBestCoupon(MyCouponListRequest request);


    Integer countAvaliableCoupon(MyCouponListRequest request);

    /**
     * 查询汇计划最优优惠券
     * @param request
     * @return
     */
    BestCouponListVO selectHJHBestCoupon(MyCouponListRequest request);

    /**
     *
     * @param couponCode
     * @return
     */
    Integer checkCouponSendExcess(String couponCode);
    /**
     * 查询HJH可用优惠券数量
     * @param request
     * @return
     */
    Integer countHJHAvaliableCoupon(MyCouponListRequest request);

    /**
     * @Author walter.limeng
     * @Description  根据优惠券投资订单编号，取得优惠券信息
     * @Date 11:51 2018/7/17
     * @Param ordId
     * @return
     */
    CouponConfigVO getCouponConfig(String ordId);

    /**
     * @Author walter.limeng
     * @Description  取得体验金收益期限
     * @Date 14:30 2018/7/17
     * @Param tenderNid
     * @return
     */
    Integer getCouponProfitTime(String tenderNid);

    /**
     * @Author walter.limeng
     * @Description  保存CouponRecover
     * @Date 14:38 2018/7/17
     * @Param CouponRecoverVO
     * @return Integer 1:成功  ；0 失败
     */
    Integer insertSelective(CouponRecoverVO cr);

    /**
     * @Author walter.limeng
     * @Description  更新账户信息(投资人)
     * @Date 14:47 2018/7/17
     * @Param account
     * @return
     */
    int updateOfLoansTenderHjh(AccountVO account);

    /**
     * @Author walter.limeng
     * @Description  根据订单ID查询所有的优惠券还款
     * @Date 16:55 2018/7/17
     * @Param orderId
     * @return
     */
    List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId);

    /**
     * @Author walter.limeng
     * @Description  更新couponRecover对象
     * @Date 9:31 2018/7/18
     * @Param cr
     * @return
     */
    CouponRecoverVO updateByPrimaryKeySelective(CouponRecoverVO cr);

    /**
     * @Author walter.limeng
     * @Description  根据recoverid查询交易记录
     * @Date 9:41 2018/7/18
     * @Param recoverId
     * @return
     */
    List<TransferExceptionLogVO> selectByRecoverId(int recoverId);

    /**
     * @Author walter.limeng
     * @Description  新增日志
     * @Date 9:52 2018/7/18
     * @Param transferExceptionLog
     * @return
     */
    Integer insertTransferExLog(TransferExceptionLogVO transferExceptionLog);

    /**
     * 判断用户所处的渠道如果不允许债转，可债转金额为0  start
     * @param userId
     * @return
     */
    boolean isAllowChannelAttorn(Integer userId);

    /**
     * 获取可债转金额   转让中本金   累计已转让本金
     * @param userId
     * @return
     */
    CreditPageVO selectCreditPageMoneyTotal(Integer userId);

    /**
     * 查询可债转列表数量
     * @param request
     * @return
     */
    MyCreditListQueryResponse countMyCreditList(MyCreditListQueryRequest request);

    /**
     * 查询可债转列表
     * @param request
     * @return
     */
    MyCreditListQueryResponse searchMyCreditList(MyCreditListQueryRequest request);

    /**
     * 查询债转详情
     * @param userId
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    TenderCreditCustomizeVO selectTenderToCreditDetail(Integer userId, String borrowNid, String tenderNid);

    /**
     * 债转详细预计服务费计算
     * @param borrowNid
     * @param tenderNid
     * @return
     */
    ExpectCreditFeeVO selectExpectCreditFee(String borrowNid, String tenderNid);

    /**
     * 验证投资人当天是否可以债转
     * @param userId
     * @return
     */
    Integer tenderAbleToCredit(Integer userId);

    /**
     * 根据投资订单号检索已债转还款信息
     * @param tenderId
     * @return
     */
    List<CreditRepayVO> selectCreditRepayList(Integer tenderId);

    List<CreditRepayVO> selectCreditRepayList(String borrowNid, String tenderOrderId, Integer periodNow, Integer status);

    /**
     * 插入债转  我要债转
     * @param borrowCredit
     */
    Integer insertCredit(BorrowCreditVO borrowCredit);

    /**
     * 前端Web页面投资可债转输入投资金额后收益提示 用户未登录 (包含查询条件)
     * @param creditNid
     * @param assignCapital
     * @param userId
     * @return
     */
    TenderToCreditAssignCustomizeVO getInterestInfo(String creditNid, String assignCapital, Integer userId);

    /**
     * 获取债转数据
     * @param creditNid
     * @return
     */
    BorrowCreditVO getBorrowCreditByCreditNid(String creditNid);

    /**
     * 债转修改日志表状态
     * @param logOrderId
     * @param logUserId
     * @param retCode
     * @param retMsg
     */
    Integer updateCreditTenderResult(String logOrderId, String logUserId, String retCode, String retMsg);

    /**
     * 查询债转失败原因
     * @param logOrdId
     * @param userId
     * @return
     */
    String getFailResult(String logOrdId, Integer userId);

    /**
     * 根据logOrdId和userId 查询债转信息
     * @param logOrdId
     * @param userId
     * @return
     */
    CreditTenderVO getCreditTenderByUserIdOrdId(String logOrdId, Integer userId);

    /**
     * 获取账户提现列表
     * @return
     */
    List<AccountWithdrawVO> selectBankWithdrawList();

    /**
     *
     * @param params
     * @return
     */
    boolean handlerAfterCash(JSONObject params);


    /**
     * 查询是否已经处理过
     * @return
     */
    int getAccountlistCntByOrdId(String orderId, String cashSuccess);

    /**
     * 查询用户有效的优惠券数目
     * @author zhangyk
     * @date 2018/7/4 15:31
     */
    Integer getUserCouponCount(Integer userId, String usedFlag);

    BankRepayFreezeLogVO getFreezeLogValid(Integer userId, String borrowNid);

    Integer deleteFreezeLogByOrderId(String orderId);

    Integer addFreezeLog(BankRepayFreezeLogRequest requestBean);

    BorrowConfigVO getConfigByCode(String code);

    boolean updateBidCancelRecord(TenderCancelRequest request);

    boolean updateTenderCancelExceptionData(BorrowTenderTmpVO info);

    List<BorrowTenderTmpVO> getBorrowTenderTmpsForTenderCancel();

    /**
     * 查询汇计划债转投资表
     * @param request
     * @return
     */
    List<HjhDebtCreditTenderVO> getHjhDebtCreditTenderList(HjhDebtCreditTenderRequest request);

    List<RepayListCustomizeVO> repayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> orgRepayList(RepayListRequest requestBean);

    List<RepayListCustomizeVO> orgRepayedList(RepayListRequest requestBean);

    int repayCount(RepayListRequest requestBean);

    int orgRepayCount(RepayListRequest requestBean);

    int orgRepayedCount(RepayListRequest requestBean);

    Boolean repayRequestUpdate(RepayRequestUpdateRequest requestBean);

    BorrowRecoverPlanVO selectRecoverPlanById(Integer id);

    List<BorrowRecoverPlanVO> selectRecoverPlan(String borrowNid, Integer period);

    List<HjhDebtCreditRepayVO> selectHjhDebtCreditRepay(String borrowNid, String tenderOrderId, int periodNow, int status);

    QueryMyProjectVO selectWechatCurrentHoldObligatoryRightList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatRepaymentList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatCreditRecordList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatCurrentHoldPlanList(WechatMyProjectRequest request);

    QueryMyProjectVO selectWechatRepayMentPlanList(WechatMyProjectRequest request);

    /**
     * 获取投资协议集合
     * @param tenderNid
     * @return
     */
    List<TenderAgreementVO> getTenderAgreementListByTenderNidAndStatusNot2(String tenderNid);

    /**
     * 通过主键获取投资协议
     * @param tenderAgreementID
     * @return
     */
    TenderAgreementVO getTenderAgreementInfoByPrimaryKey(String tenderAgreementID);

    /**
     * 交易类型
     * @return
     */
    List<AppAccountTradeListCustomizeVO> searchAppTradeTypes();

    List<AppAlreadyRepayListCustomizeVO> selectAppAlreadyRepayList(AssetManageBeanRequest params);

    List<AppTenderCreditRecordListCustomizeVO> searchAppCreditRecordList(AssetManageBeanRequest params);


    /**
     * 散标投资记录数
     * @param params
     * @return
     */
    int countProjectInvestRecordTotal(Map<String,Object> params);

    /**
     * borrowId对应的总钱数
     * @param params
     * @return
     */
    String countMoneyByBorrowId(Map<String,Object> params);

    /**
     * 散标投资记录
     * @param params
     * @return
     */
    List<AppProjectInvestListCustomizeVO> selectProjectInvestList(Map<String,Object> params);



    /**
     * 获取债转承接信息
     * @param nid
     * by libin
     * @return
     */
    HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid);

    Integer selectTenderToCreditListCount(AssetManageBeanRequest params);

    Integer countAppMyPlan(AssetManageBeanRequest params);

    List<AppMyPlanCustomizeVO> selectAppMyPlanList(AssetManageBeanRequest params);


    /**
     * 统计相应的计划加入记录总数
     * @param params
     * @return
     */
    int countPlanBorrowRecordTotal(Map<String,Object> params);

    /**
     * 查询相应的计划标的记录列表
     * @param params
     * @return
     */
    List<DebtPlanBorrowCustomizeVO> selectPlanBorrowList(Map<String,Object> params);

    /**
     * 获取债转承接信息by AssignOrderId
     * @param assignOrderId
     * by libin
     * @return
     */
    HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId);

    /**
     * 获取债转承接信息by AssignNid
     * @param assignNid
     * @return
     */
    CreditTenderVO getCreditTenderByAssignNid(String assignNid);

    /**
     * 获取协议模板by DisplayName
     * @param displayName
     * @return
     */
    List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName);

    /**
     * 统计相应的计划总数
     * @param params
     * @return
     */
    Long selectPlanAccedeSum(Map<String,Object> params);

    /**
     * 查询相应的计划的加入列表
     * @param params
     * @return
     */
    List<DebtPlanAccedeCustomizeVO> selectPlanAccedeList(Map<String,Object> params);

    /**
     * 获得协议模板图片
     * @param aliasName 别名
     * @return
     */
    AppNewAgreementVO setProtocolImg(String aliasName);

    /**
     * APP端债转记录数
     * @param params
     * @return
     */
    int countTenderCreditInvestRecordTotal(Map<String,Object> params);

    /**
     * 获取债转承接记录列表
     * @param params
     * @return
     */
    List<AppTenderCreditInvestListCustomizeVO> searchTenderCreditInvestList(Map<String,Object> params);

    /**
     * 获取债转投资人次和已债转金额
     * @param transferId
     * @return
     */
    List<BorrowCreditVO> selectBorrowCreditByNid(String transferId);

    /**
     * 获取项目类型
     *
     * @param borrowCd
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectByBorrowCd(String borrowCd);

    int countAppTradeDetailListRecordTotal(AppTradeDetailBeanRequest trade);

    List<AppTradeListCustomizeVO> searchAppTradeDetailList(AppTradeDetailBeanRequest trade);

    BigDecimal getUserRepayFeeWaitTotal(Integer userId);

    BigDecimal getOrgRepayFeeWaitTotal(Integer userId);

    BigDecimal getOrgRepayWaitTotal(Integer userId);

    /**
     * 查询广告列表
     * @author zhangyk
     * @date 2018/7/5 15:32
     */
    List<AppAdsCustomizeVO> getBannerList(AdsRequest request);

    /**
     * 获取承接中的总额度
     * @author zhangyk
     * @date 2018/8/9 11:48
     */
    String sumUndertakeAccount(String borrowNid);

    /**
     * 承接中的列表
     * @author zhangyk
     * @date 2018/8/9 13:58
     */
    List<ProjectUndertakeListVO> selectProjectUndertakeList(Map<String,Object> params);

    /**
     * 获取机构信息
     *
     * @param instCode
     * @param assetType
     * @return
     */
    HjhAssetBorrowTypeVO selectAssetBorrowType(String instCode, Integer assetType);

    /**
     * 根据项目类型去还款方式
     *
     * @param borrowcCd
     * @return
     */
    List<BorrowProjectRepayVO> selectProjectRepay(String borrowcCd);

    /**
     * 获取受托支付电子账户列表
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 插入资产表
     *
     * @param record
     * @return
     */
    int insertAssert(HjhPlanAssetVO record);

    /**
     * 插入资产表
     *
     * @param riskInfo
     */
    void insertRiskInfo(List<InfoBean> riskInfo);

    /**
     * 检查是否存在重复资产
     *
     * @param assetId
     * @return
     */
    HjhPlanAssetVO checkDuplicateAssetId(String assetId);

    /**
     * 录标时添加企业资产
     *
     * @param record
     * @return
     */
    int insertCompanyInfoToBorrowUsers(BorrowUserVO record);

    /**
     * 根据borrowNid查询风控信息
     * @author zhangyk
     * @date 2018/8/10 15:21
     */
    BorrowInfoWithBLOBsVO selectBorrowInfoWithBLOBSVOByBorrowId(String borrowNid);

    JSONObject getRepayDetailData(RepayRequestDetailRequest requestBean);

    RepayBean getRepayBean(Map<String, String> paraMap);

    ProjectBean getOrgBatchRepayData(BatchRepayDataRequest requestBean);

    /**
     *根据订单编号取得该订单的还款列表
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    CouponRecoverCustomizeVO selectCurrentCouponRecover(String couponTenderNid, int periodNow);

    /**
     *更新优惠券还款
     * @param cr
     */
    boolean updateCouponRecover(CouponRecoverVO cr);

    /**
     *体验金按收益期限还款
     * @param request
     */
    boolean updateCouponOnlyRecover(CouponRecoverCustomizeRequest request);

    /**
     * 根据订单号获取汇计划加入明细
     *
     * @param accedeOrderId
     * @return
     */
    List<HjhAccedeVO> selectHjhAccedeListByOrderId(String accedeOrderId);


    /**
     * 获取提成配置信息
     * @param map
     * @return
     */
    Integer getCommisionConfig(HashMap map);

    /**
     * 获取用户还款计划数据
     * @param borrowNid
     * @param nid
     * @param type
     * @return
     */
    RepayPlanInfoBean getRepayPlanInfo(String borrowNid, String nid, String type);

    /**
     * 待计算提成加入列表
     * @return
     */
    List<HjhAccedeVO> getAccedesWaitCompute();
    /**
     * 提成计算
     * @param hjhLockVo
     * @return
     */
    Boolean commisionCompute(HjhLockVo hjhLockVo);

    /**
     * 获取用户散标转让记录详情
     * @param creditNid
     * @return
     */
    MyCreditDetailBean getMyCreditAssignDetail(String creditNid);
    
    /**
     * 获取投资协议集合BYtenderNid
     * @param tenderNid
     * @return
     */
    List<TenderAgreementVO> selectTenderAgreementByTenderNid(String tenderNid);


    /**
     * 查询资产状态
     * @author Zha Daojian
     * @date 2018/8/27 10:27
     * @param assetListRequest
     * @return com.hyjf.am.vo.admin.AssetDetailCustomizeVO
     **/
    AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest);

    /**
     * 获取批次放款列表
    * @author Zha Daojian
    * @date 2018/8/27 15:37
    * @param request
    * @return java.util.List<com.hyjf.am.vo.admin.BatchBorrowRecoverVo>
    **/
    List<BatchBorrowRecoverVo> getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    /**
     * 获取批次放款列表条数
     *
     * @param request
     * @return
     * @author Zha Daojian
     * @date 2018/8/27 15:57
     **/
    Integer getCountBatchCenter(BatchBorrowRecoverRequest request);


    /**
     * 第三方还款明细查询
     * @param request
     * @return java.util.List<com.hyjf.am.vo.trade.ApiBorrowRepaymentInfoCustomizeVO>
     * @author Zha Daojian
     * @date 2018/8/28 10:33
     **/
    List<ApiBorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(ApiBorrowRepaymentInfoRequest request);

    /**
     * api: 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 13:59
     */
    List<ApiProjectListCustomize> getApiProjectList(Map<String,Object> params);

	/**
	 *
	 * 投资预插入
	 * @author Administrator
	 * @throws Exception
	 */
    /**
     * api: 查询投资记录列表
     * @author zhangyk
     * @date 2018/8/27 13:59
     */
    List<InvestListCustomizeVO> searchInvestListNew(Map<String,Object> params);

    /**
     * 獲取銀行開戶信息(根据投资信息查询)
     * @author wenxin
     * @date 2018/8/27 13:00
     */
    List<BankOpenAccountVO> sarchInvestOfBankOpenAccount(List<Integer> userId);

    /**
     *
     * 投资预插入
     *
     * @param borrowNid
     * @param orderId
     * @param userId
     * @param account
     * @param ip
     * @return
     * @author Administrator
     * @throws Exception
     */
    boolean updateTenderLog(AutoTenderComboRequest autoTenderComboRequest);

    /**
     * 根据id删除BorrowTenderTmp
     * @param orgOrderId
     * @return
     */
    Integer deleteBorrowTenderTmp(String orgOrderId);

    /**
     * 投资失败,删除投资临时表
     * @param borrowNid
     * @param userId
     * @param orderId
     */
    int deleteBorrowTenderTmpByParam(int userId, String borrowNid, String orderId);

    /**
     * 更新资产表 add by liushouyi
     *
     * @param hjhPlanAssetnewVO
     * @return
     */
    int updateHjhPlanAssetnew(HjhPlanAssetVO hjhPlanAssetnewVO);

    /**
     * 查询单个资产根据资产ID
     *
     * @param assetId
     * @param instCode
     * @return
     */
    HjhPlanAssetVO selectPlanAsset(String assetId, String instCode);

    /**
     * 检查是否交过保证金 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    BorrowBailVO selectBorrowBail(String borrowNid);

    /**
     * 更新借款表 add by liushouyi
     *
     * @param borrow
     * @return
     */
    boolean updateBorrowByBorrowNid(BorrowAndInfoVO borrow);

    /**
     * 更新借款主表
     * @author zhangyk
     * @date 2018/9/13 17:39
     */
    boolean updateRightBorrowByBorrowNid(RightBorrowVO borrow);

    /**
     * 获取系统配置 add by liushouyi
     *
     * @param configCd
     * @return
     */
    String getBorrowConfig(String configCd);

    /**
     * 插入保证金 add by liushouyi
     *
     * @param borrowBail
     * @return
     */
    Integer insertBorrowBail(BorrowBailVO borrowBail);

    /**
     * 根据userId和tenderNid查询投资记录
     * @author zhangyk
     * @date 2018/8/30 10:51
     */
    public List<BorrowCreditVO> getBorrowCreditListByUserIdAndTenderNid(String tenderNid, String userId);

    /**
     * 根据承接编号查询服务费总计
     * @author zhangyk
     * @date 2018/8/30 11:06
     */
    public String getBorrowCreditTenderServiceFee(String creditNid);

    /**
     * 根据creditNid查询投资记录
     * @author zhangyk
     * @date 2018/8/30 10:51
     */
    public List<BorrowCreditVO> getBorrowCreditListByCreditNid(String creditNid);
    /**
     * 获取回款记录信息
     *
     * @param request
     * @return List<RepayListCustomize>
     * @author Zha Daojian
     * @date 2018/9/1 13:20
     **/
    List<ApiRepayListCustomizeVO> searchRepayList(ApiRepayListRequest request);

    /**
     * 获取逾期的标的
     * @return
     */
    List<BorrowAndInfoVO> selectOverdueBorrowList();

    /**
     * 计划锁定
     *  @param accedeOrderId
     * @param inverestUserInfo
     * @param commissioUserInfoVO
     * @param bankOpenAccountVO
     * @param userInfoCustomizeVOS
     */
    void updateForLock(String accedeOrderId, UserInfoVO inverestUserInfo, UserInfoVO commissioUserInfoVO, BankOpenAccountVO bankOpenAccountVO, List<UserInfoCustomizeVO> userInfoCustomizeVOS);

    /**
     * 计划退出
     *
     * @param accedeOrderId
     */
    void updateForQuit(String accedeOrderId);

    /**
     * 查询待退出计划的标的
     *
     * @return
     */
    List<HjhAccedeVO> selectWaitQuitHjhList();


    /**
     * 根据nid和当前时间查询borrow
     * @author zhangyk
     * @date 2018/9/3 16:40
     */
    BorrowAndInfoVO getBorrowByNidAndNowTime(String borrowNid,Integer nowTime);

    /**
     * 查询提现订单号数量
     * @param ordId
     * @return
     */
    int countAccountWithdraw(String ordId);

    /**
     * 交易明细查询
     * @param resultBean
     * @return
     * @Author : huanghui
     */
    List<ApiTransactionDetailsCustomizeVO> selectTransactionDetails(TransactionDetailsResultBean resultBean);

    /**
     * 取得检证数据
     * @param id
     * @return
     */
    ChinapnrExclusiveLogWithBLOBsVO selectChinapnrExclusiveLog(long id);

    /**
     * 将状态更新成[2:处理中]
     * @param record
     * @return
     */
    int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBsVO record);

    /**
     * 取得成功时的信息
     * @param ordId
     * @return
     */
    List<ChinapnrLogVO> getChinapnrLog(String ordId);

    /**
     * 汇付提现后处理
     * @param chinaPnrWithdrawRequest
     * @return
     */
    boolean handlerAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest);

    /**
     * 更新提现表
     * @param ordId
     * @param reason
     */
    void updateAccountWithdrawByOrdId(String ordId, String reason);

    /**
     * 更新状态
     * @param uuid
     * @param status
     */
    void updateChinapnrExclusiveLogStatus(long uuid, String status);

    /**
     * 根据放款编号获取该标的的投资信息 add by liushouyi
     *
     * @param borrowNid
     * @return
     */
    List<BorrowTenderVO> getBorrowTenderListByBorrowNid(String borrowNid);

    /**
     * 根据合同编号查询合同要素信息 add by liushouyi
     *
     * @param contractNo
     * @return
     */
    List<NifaContractEssenceVO> selectNifaContractEssenceByContractNo(String contractNo);

    /**
     * 根据合同编号获取合同模版约定条款
     *
     * @param templetId
     * @return
     */
    List<NifaContractTemplateVO> selectNifaContractTemplateByTemplateNid(String templetId);

    /**
     * 获取最新互金字段定义
     *
     * @return
     */
    List<NifaFieldDefinitionVO> selectNifaFieldDefinition();

    /**
     * 获取还款计算公式
     *
     * @param borrowStyle
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleWithBLOBs(String borrowStyle);

    /**
     * 获取用户投资订单还款详情
     *
     * @param nid
     * @return
     */
    List<BorrowRecoverPlanVO> selectBorrowRecoverPlanList(String nid);

    /**
     * 插入合同信息要素表
     *
     * @param nifaContractEssenceVO
     * @return
     */
    Integer insertNifaContractEssence(NifaContractEssenceVO nifaContractEssenceVO);

    /**
     * 查询用户投资次数 包含直投类、债转、汇添金
     * @auth sunpeikai
     * @param
     * @return
     */
    int selectUserTenderCount(Integer userId);

    /**
     * 借款人还款表
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaRepayInfo(String borrowNid, Integer repayPeriod);

    /**
     * 合同状态变更数据生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaContractStatus(String borrowNid, Integer repayPeriod);

    /**
     * 出借人回款记录生成
     *
     * @param borrowNid
     * @param repayPeriod
     * @return
     */
    boolean insertNifaReceivedPayments(String borrowNid, Integer repayPeriod);

    /**
     * 根据订单号查询产品加息信息
     * @auth sunpeikai
     * @param orderId 订单id
     * @return
     */
    IncreaseInterestInvestVO getIncreaseInterestInvestByOrdId(String orderId);

    /**
     * 查询产品加息信息
     * @auth sunpeikai
     * @param tenderNid 对应tender表里的nid
     * @return
     */
    IncreaseInterestInvestVO getIncreaseInterestInvestByTenderNid(String tenderNid);

	ProjectBean searchRepayProjectDetail(ProjectBean form);

	List<WebUserRepayProjectListCustomizeVO> selectUserRepayProjectList(Map<String, Object> params);

	String getborrowIdByProductId(Map<String, Object> params);

	Long countBatchCenter(BatchCenterCustomize batchCenterCustomize);

	List<BatchCenterCustomizeVO> selectBatchCenterList(BatchCenterCustomize batchCenterCustomize);

	List<WebUserRepayProjectListCustomizeVO> selectOrgRepayProjectList(Map<String, Object> params);

	List<BorrowCreditVO> getBorrowCreditList(BorrowCreditRequest request1);

	Integer updateBorrowCredit(BorrowCreditVO borrowCreditVO);

	List<BorrowCreditVO> selectBorrowCreditList();

	List<CouponUserVO> selectCouponUser(int nowBeginDate, int nowEndDate);

	List<BatchCouponTimeoutCommonCustomizeVO> selectCouponQuota(int threeBeginDate, int threeEndDate);

	List<HjhLabelVO> seleHjhLabel(String borrowStyle);

	HjhAccedeVO getHjhAccede(String orderId);

	HjhPlanVO getHjhPlan(String planNid);

	List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid);

	int updateOfLoansTender(AccountVO account);

	boolean getSendRepeat(CouponUserSearchRequest couponUserRequest);

	Integer insertCouponUser(CouponUserVO couponUser);

	Integer searchNearlyRepaymentTime(Map<String, Object> params);

	List<AppReapyCalendarResultVO> searchRepaymentCalendar(Map<String, Object> params);

	Integer countRepaymentCalendar(Map<String, Object> params);

	int countAccountListByOrdId(String ordId, String type);

	Integer insertAccountListSelective(AccountListVO accountListVO);

	int countByNidAndTrade(String nid, String trade);

	int updateOfRepayCouponHjh(AccountVO account);

	BankMerchantAccountVO getBankMerchantAccount(String accountCode);

	int updateBankMerchatAccount(BankMerchantAccountVO account);

	int insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountList);

	int updateOfRepayTender(AccountVO account);

	Integer crRecoverPeriod(String tenderNid, int period);

	List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid);

	List<String> selectNidForCouponOnly(CouponRepayRequest couponRepayRequest);

	CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow);
	
}
