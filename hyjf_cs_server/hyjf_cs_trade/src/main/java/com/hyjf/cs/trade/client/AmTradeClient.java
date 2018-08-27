package com.hyjf.cs.trade.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.CreditListResponse;
import com.hyjf.am.response.trade.MyCreditListQueryResponse;
import com.hyjf.am.response.trade.ProjectListResponse;
import com.hyjf.am.response.trade.coupon.CouponResponse;
import com.hyjf.am.resquest.admin.UnderLineRechargeRequest;
import com.hyjf.am.resquest.app.AppTradeDetailBeanRequest;
import com.hyjf.am.resquest.assetpush.InfoBean;
import com.hyjf.am.resquest.market.AdsRequest;
import com.hyjf.am.resquest.trade.*;
import com.hyjf.am.resquest.user.BankAccountBeanRequest;
import com.hyjf.am.resquest.user.BankRequest;
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
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.cs.trade.bean.MyCreditDetailBean;
import com.hyjf.cs.trade.bean.RepayPlanInfoBean;
import com.hyjf.cs.trade.bean.repay.ProjectBean;
import com.hyjf.cs.trade.bean.repay.RepayBean;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.HashMap;
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
     * @param bean
     * @param accountwithdraw
     * @param bankCard
     * @param withdrawFee
     * @return
     */
    boolean handlerAfterCash(BankCallBeanVO bean, AccountWithdrawVO accountwithdraw, BankCardVO bankCard,
                             String withdrawFee);


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
     * api: 查询标的列表
     * @author zhangyk
     * @date 2018/8/27 13:59
     */
    List<ApiProjectListCustomize> getApiProjectList(Map<String,Object> params);
}
