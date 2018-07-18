package com.hyjf.cs.trade.client;

import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.repay.BorrowAuthCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
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
    public Integer countAppProjectList(AppProjectListRequest request);

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
    public List<WebProjectListCustomizeVO> searchAppPlanList(ProjectListRequest request);
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
    List<BorrowVO> selectBorrowList();

    /**
     * 获取borrow对象
     * @param borrowId
     * @return
     */
    BorrowVO getBorrowByNid(String borrowId);

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
}
