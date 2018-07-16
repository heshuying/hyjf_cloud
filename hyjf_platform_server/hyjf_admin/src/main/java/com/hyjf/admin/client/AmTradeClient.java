/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.trade.BorrowApicronResponse;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhPlanBorrowTmpResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.finance.withdraw.WithdrawCustomizeVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
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
     * 查询关联记录列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    Integer getAssociatedRecordsCount(AssociatedRecordListRequest request);
    /**
     * 根据筛选条件查询关联记录list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<AssociatedRecordListVo> getAssociatedRecordList(AssociatedRecordListRequest request);
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
     * 插入数据
     * @auth sunpeikai
     * @param accountWebListVO 网站收支表
     * @return
     */
    Integer insertAccountWebList(AccountWebListVO accountWebListVO);

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
     * 根据订单号查询是否存在重复的AccountWebList数据
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    Integer accountWebListByOrderId(String orderId);

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
    boolean updateBorrowForAutoTender(BorrowVO borrow, HjhAccedeVO hjhAccede, BankCallBean bean);

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
     * 加入计划
     *
     * @param borrowFireRequest
     */
    boolean sendToMQ(BorrowFireRequest borrowFireRequest);

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
     * 查询信托信息
     *
     * @param instCode
     * @param entrustedAccountId
     * @return
     */
    STZHWhiteListVO selectStzfWhiteList(String instCode, String entrustedAccountId);

    /**
     * 备案-更新标的信息
     *
     * @param borrowRegistRequest
     * @return
     */
    int updateBorrowRegist(BorrowRegistRequest borrowRegistRequest);

    /**
     * 更新标的信息(受托支付备案)
     *
     * @param borrowRegistRequest
     * @return
     */
    int updateEntrustedBorrowRegist(BorrowRegistRequest borrowRegistRequest);

    /**
     * 资产来源
     *
     * @return
     */
    List<HjhInstConfigVO> selectHjhInstConfigList();
}
