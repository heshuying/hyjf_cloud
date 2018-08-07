/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.request.DadaCenterCouponRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.response.trade.PushMoneyResponse;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.DataCenterCouponCustomizeVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.AccountTradeVO;
import com.hyjf.am.vo.trade.BankCreditEndVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TransferExceptionLogVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AmTradeClient, v0.1 2018/7/5 10:47
 */
public interface AmTradeClient {

    /**
     * 更新商户子账户的金额信息
     * @param merchantAccount
     */
    Integer updateByPrimaryKeySelective(MerchantAccountVO merchantAccount);

    /**
     * 查询商户配置表相应的账户配置
     * @return
     */
    MerchantAccountResponse selectRecordList(MerchantAccountListRequest request);
    /**
     * 查询定向转账列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getDirectionalTransferCount(DirectionalTransferListRequest request);
    /**
     * 根据筛选条件查询定向转账list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AccountDirectionalTransferVO> searchDirectionalTransferList(DirectionalTransferListRequest request);

    /**
     * 根据筛选条件查询绑定日志count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getBindLogCount(BindLogListRequest request);
    /**
     * 根据筛选条件查询绑定日志list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BindLogVO> searchBindLogList(BindLogListRequest request);

    /**
     * 根据userId查询Account列表，按理说只能取出来一个Account，但是service需要做个数判断，填写不同的msg，所以返回List
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    List<AccountVO> searchAccountByUserId(Integer userId);

    /**
     * 向数据库的ht_user_transfer表中插入数据
     * @auth sunpeikai
     * @param request 用户转账-发起转账的参数
     * @return
     */
    Boolean insertUserTransfer(CustomerTransferRequest request);

    /**
     * 根据筛选条件查询ht_user_transfer的数据总数
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getUserTransferCount(CustomerTransferListRequest request);

    /**
     * 根据筛选条件查询UserTransfer列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<UserTransferVO> searchUserTransferList(CustomerTransferListRequest request);

    /**
     * 根据transferId查询UserTransfer
     * @auth sunpeikai
     * @param transferId ht_user_transfer表的主键id
     * @return
     */
    UserTransferVO searchUserTransferById(Integer transferId);

    /**
     * 查询用户交易明细的交易类型
     * @return
     */
    List<AccountTradeVO> selectTradeTypes();

    AdminCouponRepayMonitorCustomizeResponse couponRepayMonitorCreatePage(CouponRepayRequest form);

    List<AdminCouponRepayMonitorCustomizeVO> selectInterestSum(CouponRepayRequest form);

    /**
     * 根据筛选条件查询userTransfer列表
     * @param form
     * @return
     */
    UserTransferResponse getRecordList(TransferListRequest form);

    /**
     * 查询汇计划-转让列表
     * @param request
     * @return
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

    /**
     * 查询批次中心-批次放款列表
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowRecoverList(BatchBorrowRecoverRequest request);

    /**
     * 查询CouponRepayMonitorVO
     * @param form
     * @return
     */
    List<AdminCouponRepayMonitorCustomizeVO> selectRecordList(CouponRepayRequest form);

    /**
     * 根据筛选条件查询平台转账count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getPlatformTransferCount(PlatformTransferListRequest request);

    /**
     * 根据筛选条件查询平台转账list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AccountRechargeVO> searchPlatformTransferList(PlatformTransferListRequest request);

    /**
     * 获取项目类型list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BorrowProjectTypeVO> selectBorrowProjectList();

    /**
     * 获取还款方式list,用于筛选条件展示
     * @auth sunpeikai
     * @param
     * @return
     */
    List<BorrowStyleVO> selectBorrowStyleList();

    /**
     * 获取标的列表count,用于前端分页显示条数
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案异常列表
     * @auth sunpeikai
     * @param borrowRegistListRequest 筛选条件
     * @return
     */
    List <BorrowRegistCustomizeVO> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 根据borrowNid查询出来异常标
     * @auth sunpeikai
     * @param borrowNid 借款编号
     * @return
     */
    BorrowVO searchBorrowByBorrowNid(String borrowNid);

    /**
     * 根据受托支付userId查询stAccountId
     * @auth sunpeikai
     * @param entrustedUserId 受托支付userId
     * @return stAccountId
     */
    String getStAccountIdByEntrustedUserId(Integer entrustedUserId);

    /**
     * 更新标
     * @auth sunpeikai
     * @param borrowVO 标信息
     * @param type 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    boolean updateBorrowRegist(BorrowVO borrowVO, Integer type);

    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     * @auth sunpeikai
     * @param borrowVO 标信息
     * @return
     */
    boolean updateBorrowAsset(BorrowVO borrowVO,Integer status);

    /**
     * 转账列表
     * @param form
     * @return
     */
    BankMerchantAccountResponse selectBankMerchantAccount(BankMerchantAccountListRequest form);

    /**
     * 查询红包明细分页
     * @param request
     * @return
     */
    BankMerchantAccountListCustomizeResponse selectBankMerchantAccountList(BankRedPacketAccountListRequest request);

    /**
     * 更新账户信息
     * @auth sunpeikai
     * @param accountVO 账户信息
     * @return
     */
    Integer updateAccount(AccountVO accountVO);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param accountRechargeVO 充值表
     * @return
     */
    Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param accountListVO 收支明细
     * @return
     */
    Integer insertAccountList(AccountListVO accountListVO);

    /**
     * 根据账户id查询BankMerchantAccount
     * @auth sunpeikai
     * @param accountId 账户id
     * @return
     */
    BankMerchantAccountVO searchBankMerchantAccountByAccountId(Integer accountId);

    /**
     * 更新红包账户信息
     * @auth sunpeikai
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     */
    Integer updateBankMerchantAccount(BankMerchantAccountVO bankMerchantAccountVO);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param bankMerchantAccountListVO 红包明细表
     * @return
     */
    Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO);
    /**
     * 获取发起账户分佣所需的详细信息
     * @auth sunpeikai
     * @param
     * @return
     */
    List<SubCommissionListConfigVO> searchSubCommissionListConfig();

    /**
     * 插入数据
     * @auth sunpeikai
     * @param subCommissionVO 平台账户分佣
     * @return
     */
    boolean insertSubCommission(SubCommissionVO subCommissionVO);

    /**
     * 根据订单号查询分佣数据
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    SubCommissionVO searchSubCommissionByOrderId(String orderId);

    /**
     * 更新分佣数据
     * @auth sunpeikai
     * @param subCommissionVO 待更新的数据参数
     * @return
     */
    Integer updateSubCommission(SubCommissionVO subCommissionVO);

    /**
     *  获取银行转账异常列表 jijun 20180710
     * @param request
     * @return
     */
    AdminTransferExceptionLogResponse getAdminTransferExceptionLogCustomizeList(AdminTransferExceptionLogRequest request);

    /**
     *  获取银行转账异常总数 jijun 20180710
     * @param request
     * @return
     */
    Integer getAdminTransferExceptionLogCustomizeCountRecord(AdminTransferExceptionLogRequest request);

    /**
     * 更改银行转账信息
     * @param request
     * @return
     */
    int updateTransferExceptionLogByUUID(AdminTransferExceptionLogRequest request);

    /**
     * 更改银行转账信息
     * @param transferExceptionLog
     * @return
     */
    int updateTransferExceptionLogByUUID(TransferExceptionLogVO transferExceptionLog);

    /**
     * 通过uuid银行转账异常
     * @param uuid
     * @return
     */
    TransferExceptionLogVO getTransferExceptionLogByUUID(String uuid);

    /**
     * 根据筛选条件查询分佣数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getSubCommissionCount(SubCommissionRequest request);

    /**
     * 根据筛选条件查询分佣数据list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<SubCommissionVO> searchSubCommissionList(SubCommissionRequest request);

    Integer deleteFreezeLogById(Integer id);

    BankRepayFreezeLogVO getBankFreezeLogByOrderId(String orderId);

    List<BankRepayFreezeLogVO> getFreezeLogValidAll(Integer limitStart, Integer limitEnd);

    Integer getFreezeLogValidAllCount();

    /**
     * 转账成功后续处理
     * @param jsonObject
     * @return
     */
    boolean transferAfter(JSONObject jsonObject);

    /**
     * 根据主键获取优惠券还款记录
     * @param recoverId
     * @return
     */
    CouponRecoverVO getCouponRecoverByPrimaryKey(Integer recoverId);

    /**
     *取得优惠券投资信息
     * @param nid
     * @return
     */
    BorrowTenderCpnVO getCouponTenderInfoByNid(String nid);
    /**
     * 根据筛选条件查询银行投资撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request);
    /**
     * 根据筛选条件查询银行投资撤销异常list
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<BorrowTenderTmpVO> searchTenderCancelExceptionList(TenderCancelExceptionRequest request);
    /**
     * 根据orderId查询BorrowTender
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    List<BorrowTenderVO> searchBorrowTenderByOrderId(String orderId);
    /**
     * 根据orderId查询BorrowTenderTmp
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    BorrowTenderTmpVO searchBorrowTenderTmpByOrderId(String orderId);
    /**
     * 根据id删除BorrowTenderTmp
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    Integer deleteBorrowTenderTmpById(Integer id);

    /**
     * 插入数据
     * @auth sunpeikai
     * @param freezeHistoryVO 冻结历史
     * @return
     */
    Integer insertFreezeHistory(FreezeHistoryVO freezeHistoryVO);

    /**
     * 查询批次中心的批次列表的求和
     * @param request
     * @return
     */
    BatchBorrowRecoverReponse getBatchBorrowCenterListSum(BatchBorrowRecoverRequest request);


    /**
     * 根据筛选条件查询汇付对账count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getAccountExceptionCount(AccountExceptionRequest request);

    /**
     * 根据筛选条件查询汇付对账列表
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    List<AccountExceptionVO> searchAccountExceptionList(AccountExceptionRequest request);

    /**
     * 根据id查询AccountException
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    AccountExceptionVO searchAccountExceptionById(Integer id);

    /**
     * 更新AccountException
     * @auth sunpeikai
     * @param accountExceptionVO 更新参数
     * @return
     */
    Integer updateAccountException(AccountExceptionVO accountExceptionVO);

    /**
     * 根据id删除AccountException
     * @auth sunpeikai
     * @param id 主键
     * @return
     */
    Integer deleteAccountExceptionById(Integer id);

    /**
     * 获取提现列表数量
     * @param request
     * @return
     */
    int getWithdrawRecordCount(WithdrawBeanRequest request);

    /**
     * 获取提现列表
     * @param request
     * @return
     */
    WithdrawCustomizeResponse getWithdrawRecordList(WithdrawBeanRequest request);

    List<BankCreditEndVO> getCreditEndList(BankCreditEndListRequest requestBean);

    int getCreditEndCount(BankCreditEndListRequest requestBean);

    BankCreditEndVO getCreditEndByOrderId(String orderId);

    int updateBankCreditEnd(BankCreditEndVO requestBean);

    int updateCreditEndForInitial(BankCreditEndVO requestBean);

    /**
     * 根据ID获取放款任务表
     * @param id
     * @return
     */
    BorrowApicronResponse getBorrowApicronByID(String id);


    /**
     * 分页查询平台设置账户列表
     * @return
     */
    public MerchantAccountResponse selectMerchantAccountListByPage(AdminMerchantAccountRequest request);

    /**
     * 根据id查询账户平台设置
     * @return
     */
    public MerchantAccountResponse searchAccountConfigInfo(Integer id);

    /**
     * 添加账户平台设置
     * @return
     */
    public MerchantAccountResponse saveAccountConfig(AdminMerchantAccountRequest request);

    /**
     * 修改账户平台设置
     * @return
     */
    public MerchantAccountResponse updateAccountConfig(AdminMerchantAccountRequest request);


    /**
     * 子账户类型 查询
     * @return
     */
    public List<ParamNameVO> getParamNameList(String code);
    /**
     *
     * 根据子账户名称检索
     * @param subAccountName
     * @return
     */
    public int countAccountListInfoBySubAccountName(String ids, String subAccountName);

    /**
     *
     * 根据子账户代号检索
     * @param subAccountCode
     * @return
     */
    public int countAccountListInfoBySubAccountCode(String ids, String subAccountCode);



    HjhDebtCreditVO selectHjhDebtCreditByCreditNid(String creditNid);

    int updateHjhDebtCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO);

    int requestDebtEnd(HjhDebtCreditVO credit, String sellerUsrcustid, String sellerAuthCode);

    BorrowTenderVO getBorrowTenderByNid(String nid);

    HjhDebtCreditTenderVO getByAssignOrderId(String assignOrderId);

    /**
     * 检索汇计划加入明细列表
     * @param request
     * @return
     */
   AutoTenderExceptionResponse selectAccedeRecordList(AutoTenderExceptionRequest request);
    /**
     * 查询计划加入明细
     * @auther: nxl
     * @date: 2018/7/12
     * @param tenderExceptionSolveRequest
     * @return
     */
    HjhAccedeResponse selectHjhAccedeByParam(TenderExceptionSolveRequest tenderExceptionSolveRequest);
    /**
     * 查询计划加入明细临时表
     * @auther: nxl
     * @date: 2018/7/12
     * @param tenderExceptionSolveRequest
     * @return
     */
    HjhPlanBorrowTmpResponse selectBorrowJoinList(TenderExceptionSolveRequest tenderExceptionSolveRequest);

    /**
     * 更新
     * @auther: nxl
     * @date: 2018/7/12
     * @param status
     * @param accedeId
     * @return
     */
    boolean updateTenderByParam(int status,int accedeId);
    /**
     * 更新投资数据
     *
     * @return
     * @author nxl
     */
    boolean updateBorrowForAutoTender(String borrowNid, String accedeOrderId, BankCallBean bean);

    List<ManualReverseCustomizeVO> getManualReverseList(ManualReverseCustomizeRequest requestBean);

    int getManualReverseCount(ManualReverseCustomizeRequest requestBean);

    Boolean updateManualReverse(ManualReverseAddRequest requestBean);

    /**
     * 计算实际金额 保存creditTenderLog表
     *
     * @return
     * @author nxl
     */
    Map<String, Object> saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast);
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    boolean updateCreditForAutoTender(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, HjhPlanVO hjhPlan, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, Map<String, Object> resultMap);

    /**
     * 根据机构编号获取机构列表
     * @return
     * @author nxl
     */
    List<HjhInstConfigVO> selectInstConfigAll();

    /**
     * 借款初审总条数
     *
     * @param borrowFirstRequest
     * @return
     */
    Integer countBorrowFirst(BorrowFirstRequest borrowFirstRequest);

    /**
     * 借款初审列表
     *
     * @param borrowFirstRequest
     * @return
     */
    List<BorrowFirstCustomizeVO> selectBorrowFirstList(BorrowFirstRequest borrowFirstRequest);

    /**
     * 统计页面值总和
     *
     * @param borrowFirstRequest
     * @return
     */
    String sumBorrowFirstAccount(BorrowFirstRequest borrowFirstRequest);

    /**
     * 根据code获取配置
     *
     * @param configCd
     * @return
     */
    BorrowConfigVO getBorrowConfig(String configCd);

    /**
     * 根据标的编号查询详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowVO selectBorrowByNid(String borrowNid);

    /**
     * 根据标的编号查询borrowInfo
     *
     * @param borrowNid
     * @return
     */
    BorrowInfoVO selectBorrowInfoByNid(String borrowNid);

    /**
     * 交保证金
     *
     * @param borrowNid
     * @return
     */
    boolean insertBorrowBail(String borrowNid, String currUserId);

    /**
     * 更新-发标
     *
     * @param borrowFireRequest
     */
    boolean updateOntimeRecord(BorrowFireRequest borrowFireRequest);

    /**
     * 借款复审总条数
     *
     * @param borrowFullRequest
     * @return
     */
    Integer countBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 借款复审列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomizeVO> selectBorrowFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 借款复审合计
     *
     * @param borrowFullRequest
     * @return
     */
    BorrowFullCustomizeVO sumAccount(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomizeVO getFullInfo(String borrowNid);

    /**
     * 复审详细列表条数
     *
     * @param borrowNid
     * @return
     */
    Integer countFullList(String borrowNid);

    /**
     * 复审详细列表
     *
     * @param borrowFullRequest
     * @return
     */
    List<BorrowFullCustomizeVO> getFullList(BorrowFullRequest borrowFullRequest);

    /**
     * 复审详细列表合计
     *
     * @param borrowNid
     * @return
     */
    BorrowFullCustomizeVO sumAmount(String borrowNid);

    /**
     * 根据UserID查询账户信息
     *
     * @param userId
     * @return
     */
    AccountVO getAccountByUserId(int userId);

    /**
     * 标的复审数据更新
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowFull(BorrowFullRequest borrowFullRequest);

    /**
     * 流标
     *
     * @param borrowFullRequest
     * @return
     */
    AdminResult updateBorrowOver(BorrowFullRequest borrowFullRequest);

    /**
     * 投资明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 投资明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取用户投资协议
     *
     * @param nid
     * @return
     */
    TenderAgreementVO selectTenderAgreement(String nid);

    /**
     * 标的放款记录
     *
     * @param userId
     * @param borrowNid
     * @param nid
     * @return
     */
    BorrowRecoverVO selectBorrowRecover(Integer userId, String borrowNid, String nid);

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowListCustomizeVO> selectBorrowList(String borrowNid);

    /**
     * 标的投资信息
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebUserInvestListCustomizeVO> selectUserInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录分期count
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countProjectRepayPlanRecordTotal(BorrowInvestRequest borrowInvestRequest);

    /**
     * 标的放款记录分期
     *
     * @param borrowInvestRequest
     * @return
     */
    List<WebProjectRepayListCustomizeVO> selectProjectRepayPlanList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 更新标的放款记录
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer updateBorrowRecover(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取标的备案列表count
     *
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistListCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     *
     * @param borrowRegistListRequest
     * @return
     */
    List<BorrowRegistCustomizeVO> selectRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案列表统计
     *
     * @param borrowRegistListRequest
     * @return
     */
    String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案
     * @param request
     * @return
     */
    AdminResult updateBorrowRegist(BorrowRegistUpdateRequest request);

    /**
     * 资产来源
     *
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigList();
    
    /*资产中心 start*/
	/**
	 * 获取资金来源
	 *
	 * @param 
	 * @return List<HjhInstConfigVO>
	 */
    List<HjhInstConfigVO> findHjhInstConfigList();

	/**
	 * 产品类型下拉联动
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
    List<HjhAssetTypeVO> findHjhAssetTypeList(String instCodeSrch);

    /**
     * param
     * @param param
     * @return
     */
    Map<String, String> findParamNameMap(String param);

    /**
     * 查询资产列表
     * @param request
     * @return
     */
    AssetListCustomizeResponse findAssetList(AssetListRequest request);

    /**
     * 详情查询
     * @param assetListRequest
     * @return
     */
    AssetDetailCustomizeVO findDetailById(AssetListRequest assetListRequest);
    
    /**
     * 总计
     * @param request
     * @return
     */
    Integer getRecordCount(AssetListRequest request);
    
    /**列总数
     * @param request
     * @return
     */
    BigDecimal sumAccount(AssetListRequest request);

    /**
     * 处理保证金不足的资产
     * @param assetId
     * @param menuHide
     */
	void updateCashDepositeStatus(String assetId, String menuHide);
	/*资产中心 end*/
	
	/*标签配置中心 start*/
	/**
     * 项目类型
     * @param 
     * @return
     */
    List<BorrowProjectTypeVO> findBorrowProjectTypeList();
    /**
     * 还款方式
     * @param 
     * @return
     */
    List<BorrowStyleVO> findBorrowStyleList();
    
    /**
     * 查询标签配置列表
     * @param request
     * @return
     */
    HjhLabelCustomizeResponse findHjhLabelList(HjhLabelRequest request);
    
    /**
     * 查询标签配置列表
     * @param request
     * @return
     */
    List<HjhLabelCustomizeVO> findHjhLabelListById(HjhLabelRequest request);
    
    /**
     * 查询标签配置列表
     * @param request
     * @return
     */
    List<HjhLabelCustomizeVO> findHjhLabelListLabelName(HjhLabelRequest request);
    
	/**
     * 插入标签配置列表
     * @param request
     */
    int insertHjhLabelRecord(HjhLabelInfoRequest request);
    
	/**
     * 更新标签配置列表
     * @param request
     */
    int updateHjhLabelRecord(HjhLabelInfoRequest request);
    
	/**
     * 更新引擎表
     * @param request
     */
    int updateAllocationRecord(HjhLabelInfoRequest request);
    /*标签配置中心 end*/
    
    /*计划列表 start*/
    /**
     * 获取计划列表
     * @return
     */
 	HjhPlanResponse getHjhPlanListByParam(PlanListRequest form);
 	
 	/**
     * 获取计划列表 sum
     * @return
     */
 	HjhPlanSumVO getCalcSumByParam(PlanListRequest form);
 	
 	/**
     * 获取计划详情列表
     * @return
     */
 	List<HjhPlanDetailVO> getHjhPlanDetailByPlanNid(PlanListRequest form);
 	
 	/**
     * AJax
     * @return
     */
 	HjhPlanResponse getPlanNameAjaxCheck(PlanListRequest form);
 	
 	/**
     * AJax
     * @return
     */
 	HjhPlanResponse getPlanNidAjaxCheck(PlanListRequest form);
 	
 	/**
     * 修改计划状态
     * @return
     */
 	HjhPlanResponse updatePlanStatusByPlanNid(PlanListRequest form);
 	
 	/**
     * 修改计划显示状态
     * @return
     */
 	HjhPlanResponse updatePlanDisplayByPlanNid(PlanListRequest form);
 	
 	/**
 	 * 根据主键判断数据是否存在
 	 * 
 	 * @Title isExistsRecord
 	 * @param planNid
 	 * @return
 	 */
 	boolean isExistsRecord(String planNid);
 	
 	/**
 	 * 根据计划名称查询数量
 	 * 
 	 * @Title isExistsRecord
 	 * @param planName
 	 * @return
 	 */
 	int countByPlanName(String planName);
 	
 	/**
 	 * 根据计划还款方式，锁定期，锁定期类型获取计划数量(月)
 	 * 
 	 * @Title isDebtPlanTypeNameExist
 	 * @param isMonth
 	 * @return
 	 */
 	int isLockPeriodExist(String lockPeriod,String borrowStyle,String isMonth);
 	
 	/**
 	 * 更新操作
 	 * 
 	 * @Title updateRecord
 	 * @param form
 	 * @throws Exception
 	 */
 	int updateRecord(PlanListRequest form);
 	
 	/**
 	 * 插入操作
 	 * 
 	 * @param form
 	 */
 	int insertRecord(PlanListRequest form);
 	/*计划列表 end*/
 	
 	/*加入明细 start*/
	/**
	 * 检索加入明细列表
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	AccedeListResponse getAccedeListByParam(AccedeListRequest form);
	
	/**
	 * 检索加入明细列表不分页
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	List<AccedeListCustomizeVO> getAccedeListByParamWithoutPage(AccedeListRequest form);
	
	/**
	 * 检索加入明细列表列总计
	 * 
	 * @Title selectAccedeRecordList
	 * @param form
	 * @return
	 */
	HjhAccedeSumVO getCalcSumByParam(AccedeListRequest form);
	
	/**
	 * 通过加入订单号查询法大大协议表
	 * 
	 * @Title selectAccedeRecordList
	 * @param planOrderId
	 * @return
	 */
	List<TenderAgreementVO> selectTenderAgreementByNid(String planOrderId);
	
	/**
	 * 更新协议发送状态
	 * 
	 * @Title selectAccedeRecordList
	 * @param request
	 * @return
	 */
	int updateSendStatusByParam(AccedeListRequest request);
	
	/**
	 * 查询用户投资信息
	 * 
	 * @Title selectAccedeRecordList
	 * @param request
	 * @return
	 */
	UserHjhInvistDetailVO selectUserHjhInvistDetail(AccedeListRequest request);
	/*加入明细 end*/
	
	/*承接记录 start*/
	/**
	 * 获取详细列表
	 * 
	 * @param form
	 * @return
	 */
	HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form);
	
	/**
	 * 获取详细列表未分页
	 * 
	 * @param form
	 * @return
	 */
	List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest form);
	
	/**
	 * 传参查询承接债转表
	 * 
	 * @param form
	 * @return
	 */
	HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest form);
	/*承接记录 end*/
	
	/*计划引擎 start*/
	/**
     * 查询计划专区列表
     * @param form
     * @return
     */
	HjhRegionResponse getHjhRegionList(AllocationEngineRuquest form);

    /**
     * 查询汇计划表
     * @param form
     * @return
     */
    String getPlanNameByPlanNid(AllocationEngineRuquest form);
    
    /**
     * 插入计划专区表
     * @param request
     * @return
     */
    int insertRecord(HjhRegionVO request);
    
    /**
     * AJAX
     * @param planNid
     * @return
     */
    HjhRegionResponse getPlanNidAjaxCheck(String planNid);
    
    /**
     * 根据主键获取 HjhRegionVO
     * @param id
     * @return
     */
    HjhRegionVO getHjhRegionVOById(String id);
    
    /**
     * 更新计划专区表的状态
     * @param vo
     * @return
     */
    int updateHjhRegionRecord(HjhRegionVO vo); 
    
    /**
     * 更新引擎表
     * @param vo
     * @return
     */
    HjhRegionResponse updateAllocationEngineRecord(HjhRegionVO vo); 
    
    /**
     * 查询计划专区列表
     * @param request
     * @return
     */
    List<HjhRegionVO> getHjhRegionListWithOutPage(AllocationEngineRuquest request);
    
    /**
     * 根据计划专区列表传入计划编号查询引擎列表
     * @param form
     * @return
     */
    HjhAllocationEngineResponse getHjhAllocationEngineList(AllocationEngineRuquest form);
    
    /**
     * 根据计划专区列表传入计划编号查询引擎列表
     * @param form
     * @return
     */
    List<HjhAllocationEngineVO> getAllocationList(AllocationEngineRuquest form); 
    
    /**
     * 根据主键获取 HjhRegionVO
     * @param engineId
     * @return
     */
    HjhAllocationEngineVO getPlanConfigRecord(Integer engineId);
    
    /**
     * 更新计划引擎表的状态
     * @param vo
     * @return
     */
    int updateHjhAllocationEngineRecord(HjhAllocationEngineVO vo); 
    
    /**
     * 根据参数获取 HjhRegionVO
     * @param form
     * @return
     */
    HjhAllocationEngineVO getPlanConfigRecordByParam(AllocationEngineRuquest form);
    
    /**
     * 验证重复
     * @param planNid
     */
    boolean checkRepeat(String labelName,String planNid);
    
    /** 获取还款方式
     * @param planNid
     */
    String getPlanBorrowStyle(String planNid);
    
    /** 获取计划专区
     * @param planNid
     */
    HjhRegionVO getHjhRegionRecordByPlanNid(String planNid);
    
    /**
	 * 计划引擎配置Info画面入力后插表
	 * 
	 * @param request
	 */
    int insertHjhAllocationEngineRecord(HjhAllocationEngineVO request);
    
    /**
	 * 通过计划编号获取计划列表
	 * 
	 * @param planNid
	 */
    List<HjhPlanVO> getHjhPlanByPlanNid(String planNid);
    
    /**
	 * 通过计划编号获取计划专区列表
	 * 
	 * @param planNid
	 */
    List<HjhRegionVO> getHjhRegioByPlanNid(String planNid);
    /*计划引擎 end*/

    /**
     * @Description 获取admin产品中心-汇直投-放款明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer countBorrowRecover(BorrowRecoverRequest borrowRecoverCustomize);
    /**
     * @Description 获取admin产品中心-汇直投-放款明细列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRecoverCustomizeVO> selectBorrowRecoverList(BorrowRecoverRequest borrowRecoverCustomize);
    /**
     * @Description 获取admin产品中心-汇直投-放款明细统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRecoverCustomizeVO sumBorrowRecoverList(BorrowRecoverRequest borrowRecoverCustomize);
    /**
     * @Description 获取admin借款操作日志列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer countBorrowLog(BorrowLogRequset request);
    /**
     * @Description 获取admin借款操作日志列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowLogCustomizeVO> selectBorrowLogList(BorrowLogRequset request);
    /**
     * @Description 获取admin借款操作日志列表导出
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowLogCustomizeVO> exportBorrowLogList(BorrowLogRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer countBorrowRepayment(BorrowRepaymentRequest request);
    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRepaymentCustomizeVO> selectBorrowRepaymentList(BorrowRepaymentRequest request);
    /**
     * @Description 获取admin产品中心-汇直投-还款信息列表统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRepaymentCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentRequest request);
    /**
     * @Description 获取admin数据导出--还款计划导出
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRepaymentPlanCustomizeVO> exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);
    /**
     * @Description 获取admin查询延期数据
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    AdminRepayDelayCustomizeVO selectBorrowInfo(String borrowNid);
    /**
     * @Description 获取admin查询延期还款计划（不分期）
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRepayVO getBorrowRepayDelay(String borrowNid, String borrowApr, String borrowStyle);
    /**
     * @Description 获取admin查询延期还款计划（分期）
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRepayPlanVO getBorrowRepayPlanDelay(String borrowNid, String borrowApr, String borrowStyle);
    /**
     * @Description 延期操作
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer updateBorrowRepayDelayDays(String borrowNid, String delayDays);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer countBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细查询
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoListForView(BorrowRepaymentInfoRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRepaymentInfoCustomizeVO sumBorrowRepaymentInfo(BorrowRepaymentInfoRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细导出列表
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRepaymentInfoCustomizeVO> selectBorrowRepaymentInfoList(BorrowRepaymentInfoRequset copyForm);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表数量
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    Integer countBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表查询
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRepaymentInfoListCustomizeVO> selectBorrowRepaymentInfoListList(BorrowRepaymentInfoListRequset request);
    /**
     * @Description 获取admin产品中心-汇直投-还款明细列表统计
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    BorrowRepaymentInfoListCustomizeVO sumBorrowRepaymentInfoList(BorrowRepaymentInfoListRequset request);

    /**
     * 获取admin资金中心-资金明细列表
     * @author nixiaoling
     * @param request
     * @return
     */
    AccountDetailResponse findAccountDetailList(AccountDetailRequest request);

    /**
     * 查询交易明细最小的id
     * @param userId
     * @author nixiaoling
     * @return
     */
    AdminAccountDetailDataRepairResponse accountdetailDataRepair(int userId);

    /**
     * 查询出还款后,交易明细有问题的用户ID
     * @author nixiaoling
     * @return
     */
    AdminAccountDetailDataRepairResponse queryAccountDetailErrorUserList();

    /**
     * 根据Id查询此条交易明细
     * @param accountId
     * @author nixiaoling
     * @return
     */
    AccountListResponse selectAccountById(int accountId);

    /**
     * 查询此用户的下一条交易明细
     * @param accountId
     * @author nixiaoling
     * @param userId
     * @return
     */
    AccountListResponse selectNextAccountList(int accountId, int userId);

    /**
     * 根据查询用交易类型查询用户操作金额
     * @param tradeValue
     * @author nixiaoling
     * @return
     */
    AccountTradeResponse selectAccountTradeByValue(String tradeValue);

    /**
     * 更新用户的交易明细
     * @param accountListRequest
     * @author nixiaoling
     * @return
     */
    int updateAccountList(AccountListRequest accountListRequest);
    /**
     * 查找汇付银行开户记录列表
     *
     * @param request
     * @return
     */
    PushMoneyResponse findPushMoneyList(PushMoneyRequest request);



    /**
     * 发提成处理- 计算提成
     *
     * @param apicornId,request
     * @return
     */
    int insertTenderCommissionRecord(Integer apicornId, ActivityListRequest request) ;

    /**
     * 计划退出查询判断标的是否还款
     * @param borrowNid
     * @return
     */
    List<BorrowApicronVO> selectBorrowApicronListByBorrowNid(String borrowNid);

    /**
     * 根据项目编号取得borrowTender表
     * @param nid
     * @return
     */
    List<BorrowTenderVO> getBorrowTenderListByNid(String nid);

    /**
     * 获取计算提成数据
     * @param request
     * @return
     */
    Integer getCountTenderCommissionBybBorrowNid(TenderCommissionRequest request);

    /**
     * 添加提成数据
     * @param request
     * @return
     */
    int saveTenderCommission(TenderCommissionRequest request);


    /**
     * 更新借款API表
     * @param request
     * @return
     */
    int updateByPrimaryKeySelective(BorrowApicronRequest request);

    /**
     * 獲取銀行開戶信息
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    /**
     * 查询数据中心优惠券数据
     * @param requestBean
     * @param type 优惠券类型
     * @return
     */
    DataCenterCouponResponse getDataCenterCouponList(DadaCenterCouponRequestBean requestBean, String type);

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    List<BankAleveVO> queryBankAleveList(BankAleveRequest request);

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    List<BankEveVO> queryBankEveList(BankEveRequest request);


    //董泽杉
    /**
     * 迁移到详细画面
     *
     * @return
     */
    BorrowCommonResponse moveToInfoAction(BorrowCommonRequest borrowCommonRequest);

    /**
     * 添加信息
     *
     * @param borrowCommonRequest
     * @return
     * @throws Exception
     */
    BorrowCommonResponse insertAction(BorrowCommonRequest borrowCommonRequest) throws Exception;


    /**
     * 用户是否存在
     *
     * @param userId
     * @return
     */
    int isExistsUser(String userId);


    /**
     * 获取最新的借款预编码
     *
     * @return
     */
    String getBorrowPreNid();

    /**
     * 获取现金贷的借款预编号
     *
     * @return
     */
    String getXJDBorrowPreNid();

    /**
     * 借款预编码是否存在
     *
     * @param borrowPreNid
     * @return
     */
    boolean isExistsBorrowPreNidRecord(String borrowPreNid);

    /**
     * 获取融资服务费率 & 账户管理费率
     *
     * @param borrowCommonRequest
     * @return
     */
    String getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest);

    /**
     * 根据资产编号查询该资产下面的产品类型
     *
     * @param instCode
     * @return
     */
    BorrowCommonResponse getProductTypeAction(String instCode);

    /**
     * 受托用户是否存在
     *
     * @param userName
     * @return
     */

    int isEntrustedExistsUser(String userName);

    /**
     * 获取加息券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListJX(DataCenterCouponCustomizeVO dataCenterCouponCustomize);
    /**
     * 获取计划列表无分页
     * @return
     */
 	HjhPlanResponse getHjhPlanListByParamWithoutPage(PlanListRequest form);
	public HjhAccedeResponse canCancelAuth(Integer userId);

    /**
     * 获取平台子账户信息
     * @param accountCode
     * @return
     */
    BankMerchantAccountVO getBankMerchantAccount(String accountCode);

    /**
     * 获取代金券回款列表
     * @param dataCenterCouponCustomize
     * @return
     */
    List<DataCenterCouponCustomizeVO> getRecordListDJ(DataCenterCouponCustomizeVO dataCenterCouponCustomize);
    /**
     * 获取子账户信息
     * @param accountCode
     * @return
     */
    BankMerchantAccountInfoVO getBankMerchantAccountInfoByCode(String accountCode);

    /**
     * 更新子账户信息已设置交易密码
     * @param accountId
     * @param flag
     */
    void updateBankMerchantAccountIsSetPassword(String accountId, int flag);
}
