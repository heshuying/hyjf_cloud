/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.repaybean.RepayBean;
import com.hyjf.admin.beans.request.*;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.admin.*;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountListResponse;
import com.hyjf.am.response.trade.account.AccountRechargeCustomizeResponse;
import com.hyjf.am.response.trade.account.AccountTradeResponse;
import com.hyjf.am.response.trade.account.MerchantTransferResponse;
import com.hyjf.am.response.user.ChannelStatisticsDetailResponse;
import com.hyjf.am.resquest.admin.*;
import com.hyjf.am.resquest.config.STZHWhiteListRequestBean;
import com.hyjf.am.resquest.trade.BankCreditEndListRequest;
import com.hyjf.am.resquest.trade.BorrowProjectTypeRequest;
import com.hyjf.am.resquest.trade.BorrowTenderUpdRequest;
import com.hyjf.am.resquest.trade.RepayRequestUpdateRequest;
import com.hyjf.am.resquest.user.ChannelStatisticsDetailRequest;
import com.hyjf.am.vo.admin.*;
import com.hyjf.am.vo.admin.BorrowCreditVO;
import com.hyjf.am.vo.admin.HjhAccountBalanceVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.admin.coupon.CouponBackMoneyCustomize;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.config.ParamNameVO;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.hjh.*;
import com.hyjf.am.vo.trade.hjh.calculate.HjhCreditCalcResultVO;
import com.hyjf.am.vo.trade.repay.BankRepayFreezeLogVO;
import com.hyjf.am.vo.user.ApplyAgreementInfoVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.EnumMap;
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
     * 查询汇计划-转让列表
     * @param request
     * @return
     */
    HjhDebtCreditReponse queryHjhDebtCreditList(HjhDebtCreditListRequest request);

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
     * @param borrowNid 项目编号
     * @return
     */
    BorrowAndInfoVO searchBorrowByBorrowNid(String borrowNid);

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
     * @param registUpdateRequest 标信息 1更新标的备案 2更新受托支付标的备案
     * @return
     */
    boolean updateBorrowRegistException(BorrowRegistUpdateRequest registUpdateRequest);

    /**
     * 备案成功看标的是否关联计划，如果关联则更新对应资产表
     * @auth sunpeikai
     * @param borrowVO 标信息
     * @return
     */
    boolean updateBorrowAsset(BorrowAndInfoVO borrowVO,Integer status);

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
    BankMerchantAccountVO searchBankMerchantAccountByAccountId(String accountId);

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
/*
    @Deprecated
    Integer updateSubCommission(SubCommissionVO subCommissionVO);
*/

    /**
     *  获取银行转账异常列表 jijun 20180710
     * @param request
     * @return
     */
    AdminTransferExceptionLogResponse getAdminTransferExceptionLogCustomizeList(TransferExceptionLogVO request);

    /**
     *  获取银行转账异常总数 jijun 20180710
     * @param request
     * @return
     */
    Integer getAdminTransferExceptionLogCustomizeCountRecord(TransferExceptionLogVO request);

    /**
     * 更改银行转账信息
     * @param request
     * @return
     */
    int updateTransferExceptionLogByUUID(TransferExceptionLogVO request);

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
     *取得优惠券出借信息
     * @param nid
     * @return
     */
    BorrowTenderCpnVO getCouponTenderInfoByNid(String nid);
    /**
     * 根据筛选条件查询银行出借撤销异常的数据count
     * @auth sunpeikai
     * @param request 筛选条件
     * @return
     */
    Integer getTenderCancelExceptionCount(TenderCancelExceptionRequest request);
    /**
     * 根据筛选条件查询银行出借撤销异常list
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
     * 根据orderId查询BorrowTender
     *
     * @param borrowNid 订单号
     * @return
     * @auth zdj
     */
    List<BorrowTenderVO> searchBorrowTenderByBorrowNid(String borrowNid);
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

    HjhDebtCreditVO doSelectHjhDebtCreditByCreditNid(String creditNid);

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
     * 查询计划加入明细
     * @auther: nxl
     * @date: 2018/7/12
     * @param tenderExceptionSolveRequest
     * @return
     */
    HjhAccedeResponse doSelectHjhAccedeByParam(TenderExceptionSolveRequest tenderExceptionSolveRequest);

    int updateHjhDebtCreditForEnd(HjhDebtCreditVO hjhDebtCreditVO);

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
     * 更新出借数据
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
    HjhCreditCalcResultVO saveCreditTenderLogNoSave(HjhDebtCreditVO credit, HjhAccedeVO hjhAccede, String orderId, String orderDate, BigDecimal yujiAmoust, boolean isLast);
    /**
     * 汇计划自动承接成功后数据库更新操作
     *
     * @return
     * @author nxl
     */
    boolean updateCreditForAutoTender(String creditNid, String accedeOrderId, String planNid, BankCallBean bean,String tenderUsrcustid, String sellerUsrcustid, HjhCreditCalcResultVO resultVO);
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
    BorrowAndInfoVO selectBorrowByNid(String borrowNid);

    /**
     * 根据标的编号查询详细信息
     *
     * @param borrowNid
     * @return
     */
    BorrowAndInfoVO doSelectBorrowByNid(String borrowNid);

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
    AdminResult updateOntimeRecord(BorrowFireRequest borrowFireRequest);

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
     * 出借明细记录 总数COUNT
     *
     * @param borrowInvestRequest
     * @return
     */
    Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> selectBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细列表合计
     *
     * @param borrowInvestRequest
     * @return
     */
    String selectBorrowInvestAccount(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 获取用户出借协议
     *
     * @param nid
     * @return
     */
    TenderAgreementVO selectTenderAgreement(String nid);

    /**
     * 获取用户出借协议-担保机构
     *
     * @param request
     * @return
     */
    List<TenderAgreementVO> selectLikeByExample(DownloadAgreementRequest request);

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
     * 协议申请明细列表页
    * @author Zha Daojian
    * @date 2019/5/8 15:35
    * @param borrowNid
    * @return com.hyjf.am.vo.trade.borrow.BorrowRecoverVO
    **/
    ApplyBorrowInfoVO selectApplyBorrowInfoDetail(String borrowNid);

    /**
     * 标的放款记录列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRecoverVO> selectBorrowRecoverList(String borrowNid);

    /**
     * 标的放款记录列表,分期
     *
     * @param borrowNid
     * @return
     */
    List<BorrowRecoverPlanVO> selectBorrowRecoverPlanList(String borrowNid,int repayPeriod);

    /**
     * 获取用户债转还款列表
     *
     * @param borrowNid
     * @return
     */
    List<CreditRepayVO> selectCreditRepayList(String borrowNid,int repayPeriod);

    /**
     * 获取用户汇计划债转还款列表
     *
     * @param borrowNid
     * @return
     */
    List<HjhDebtCreditRepayVO> selectHjhDebtCreditRepayList(String borrowNid,int repayPeriod);

    /**
     * 获取借款列表
     *
     * @param borrowNid
     * @return
     */
    List<BorrowListCustomizeVO> selectBorrowList(String borrowNid);

    /**
     * 标的出借信息
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

    AccedeListResponse getAccedeListByParamCount(AccedeListRequest form);

    AccedeListResponse getAccedeListByParamList(AccedeListRequest form);

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
     * 查询用户出借信息
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
    int checkRepeat(AllocationEngineRuquest form);

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
     * @Description 获取admin产品中心-汇直投-放款明细列表导出
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    List<BorrowRecoverCustomizeVO> exportBorrowRecoverList(BorrowRecoverRequest borrowRecoverCustomize);

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
    AdminBorrowRepaymentResponse exportRepayClkActBorrowRepaymentInfoList(BorrowRepaymentPlanRequest request);
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

    /*** 取得提成配置
    * @author Zha Daojian
    * @date 2018/11/7 11:23
    * @param     request
    * @return java.util.List<com.hyjf.am.vo.trade.PushMoneyVO>
    **/
    List<PushMoneyVO> getPushMoney(PushMoneyRequest request);




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
    Integer getCountTenderCommissionByTenderIdAndTenderType(TenderCommissionRequest request);

    /**
     * 更新借款API表
     * @param apicornId
     * @return
     */
    Integer  updateBorrowApicronByPrimaryKeySelective(String apicornId);

    /**
     * 添加提成数据
     * @param request
     * @return
     */
    int saveTenderCommission(TenderCommissionRequest request);

    /**
     * 獲取銀行開戶信息
     * @param userId
     * @return
     */
    BankOpenAccountVO getBankOpenAccount(Integer userId);

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    List<BankAleveVO> queryBankAleveList(BankAleveRequest request);


    /**
     * 根据筛选条件查询银行账务明细count
     * @param
     * @return
     */
    Integer queryBankAleveCount(BankAleveRequest request);

    /**
     * 根据筛选条件查询银行账务明细list
     * @param
     * @return
     */
    List<BankEveVO> queryBankEveList(BankEveRequest request);

    Integer queryBankEveCount(BankEveRequest request);


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
     * 获取放款服务费率 & 还款服务费率
     *
     * @param borrowCommonRequest
     * @return
     */
    BorrowCommonVO getBorrowServiceScale(BorrowCommonRequest borrowCommonRequest);

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


    /**
     * 获取汇计划 -  计划还款(计划退出)列表
     * @param request
     * @return
     * @Author : huanghui
     */
    HjhRepayResponse selectHjhRepayList(HjhRepayRequest request);

    /**
     * 汇计划 -> 资金计划 -> 复投原始标的 列表
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest requestBean);

    /**
     * 汇计划按天转让记录
     * @param request
     * @return
     * @Author : huanghui
     */
    DayCreditDetailResponse hjhDayCreditDetailList(DayCreditDetailRequest request);

    /**
     * 资金中心 - 充值管理
     * @param request
     * @return
     * @Author : huanghui
     */
    AccountRechargeCustomizeResponse queryRechargeList(AccountRechargeRequest request);

    /**
     * 更新充值状态
     * @param userId
     * @param nid
     * @return
     * @Author : huanghui
     */
    boolean updateRechargeStatus(Integer userId, String nid);

    /**
     * 充值掉单后,更新用户的账户信息
     * @param request
     * @return
     * @Author : huanghui
     */
    AccountRechargeCustomizeResponse updateAccountAfterRecharge(AccountRechargeRequest request);

    /**
     * 子账户类型 查询
     * @param nameClass
     * @return
     */
    List<ParamNameVO> selectParamNameList(String nameClass);

    /**
     * 获取线下充值类型列表
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    UnderLineRechargeResponse selectUnderLineList(UnderLineRechargeRequestBean requestBean);

    /**
     * 添加线下充值类型
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    UnderLineRechargeResponse insterUnderRechargeCode(UnderLineRechargeRequestBean requestBean);

    /**
     * 获取当前code 是否存在
     * @param code
     * @return
     * @Author : huanghui
     */
    boolean getUnderLineRecharge(String code);

    /**
     * 更新指定线下数据类型
     * @param requestBean
     * @return
     * @Author : huanghui
     */
    boolean updateUnderLineRecharge(UnderLineRechargeRequestBean requestBean);

    /**
     * 删除指定充值类型数据
     * @param id
     * @return
     * @Author : huanghui
     */
    boolean deleteUnderLineRecharge(Integer id);

    /**
     *
     * @author zhangyk
     * @date 2018/8/7 16:37
     */
    Integer getBankMerchantAccountListByOrderId(String orderId);


    CouponConfigCustomizeResponse getRecordList(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse getCouponConfig(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse saveCouponConfig(CouponConfigRequest request);

    CouponConfigResponse insertAction(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse deleteAction(CouponConfigRequest couponConfigRequest);

    CouponConfigResponse getAuditInfo(CouponConfigRequest ccfr);

    CouponConfigResponse updateAuditInfo(CouponConfigRequest couponConfigRequest);

    CouponUserResponse getIssueNumber(String couponCode);

    CouponTenderResponse countRecordHzt(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse queryInvestTotalHzt(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse getRecordListHzt(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse getCouponTenderDetailCustomize(Map<String, Object> paramMap);

    CouponTenderResponse getCouponRecoverCustomize(Map<String, Object> paramMap);

    CouponTenderResponse countRecordHjh(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse queryInvestTotalHjh(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse getRecordListHjh(CouponTenderRequest couponTenderRequest);

    CouponTenderResponse getHjhCouponTenderDetailCustomize(Map<String, Object> paramMap);

    CouponTenderResponse getHjhCouponRecoverCustomize(Map<String, Object> paramMap);

    CouponTenderResponse countRecordHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHztDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse queryHztInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse queryHztRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse countRecordHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHztTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse countRecordHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHztJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse countRecordHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHjhDJ(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse queryHjhInvestTotal(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse queryHjhRecoverInterestTotle(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse countRecordHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHjhTY(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse countRecordHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    CouponTenderResponse getRecordListHjhJX(CouponBackMoneyCustomize couponBackMoneyCustomize);

    /**
     * 圈存异步回调业务处理
     * @author zhangyk
     * @date 2018/8/7 18:53
     */
    Boolean updateAccountCallbackRecharge(Map<String,Object> params);

    /**
     * 圈提异步回调业务处理
     * @author zhangyk
     * @date 2018/8/8 16:41
     */
    Boolean updateAccountCallbackWithdraw(Map<String,Object> params);

    /**
     * 更新明细表为失败状态
     * @author zhangyk
     * @date 2018/8/8 10:22
     */
    Boolean updateBankAccountListFailByOrderId(String orderId);

    /**
     * 汇计划提成列表查询
     *
     * @param
     * @param form
     * @return HjhCommissionResponse
     */
    HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form);

    /**
     * 更新BankMerchantAccount
     * @param bankMerchantAccount
     * @return
     */
    int updateBankMerchantAccountByCode(BankMerchantAccountVO bankMerchantAccount);

    /**
     * 查询金额总计
     * @author libin
     * @param id
     * @return
     */
    HjhCommissionResponse selecthjhCommissionTotal(HjhCommissionRequest form);

    /**
     * 发起平台账户分佣
     * @auth sunpeikai
     * @param
     * @return
     */
    JSONObject subCommission(SubCommissionRequest request);

    /**
     * 校验发提成状态是不是已经发放
     * @author libin
     * @param id
     * @return
     */
    TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids);

    /**
     * 汇计划 -- 复投承接债权列表
     * @param request
     * @return
     * @Author : huanghui
     */
    HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request);

    List<BorrowCreditVO> getBorrowCreditList(BorrowCreditAmRequest request);

    Integer getBorrowCreditCount(BorrowCreditAmRequest request);

    BorrowCreditSumVO getBorrwoCreditTotalSum(BorrowCreditAmRequest request);

    BorrowCreditInfoSumVO sumBorrowCreditInfoData(BorrowCreditAmRequest request);

    /**
     * 更新用户账户信息
     * @param accountVO
     * @return
     */
    Integer updateAccountManage(AccountVO accountVO);

    /**
     * 手动银行对账
     * @param userId
     * @param startTime
     * @param endTime
     * @param ip
     * @param payment
     * @param cardId
     * @return
     */
    String updateAccountCheck(Integer userId, String startTime, String endTime, String ip, String payment, String cardId);

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse selectAccountBalanceMonitoringByPage(AdminAccountBalanceMonitoringRequest adminRequest);

    /**
     * 编辑画面检索列表
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse selectaccountBalanceMonitoringById(AdminAccountBalanceMonitoringRequest adminRequest);

    /**
     * 查询配置中心平台账户配置 余额监控
     * @param adminRequest
     * @return
     */
    List<AccountBalanceMonitoringRequestBean> searchMerchantAccountList(AdminAccountBalanceMonitoringRequest adminRequest);
    /**
     * 数据更新 平台账户设置
     * @param adminRequest
     * @return
     */
    AdminAccountBalanceMonitoringResponse updateMerchantAccountList(List<AccountBalanceMonitoringRequestBean> adminRequest);

    /**
     * 项目类型
     *
     * @return
     */
    public List<BorrowProjectTypeVO> borrowProjectTypeList(String borrowTypeCd);

    /**
     * 资金来源
     * @param instCode
     * @return
     */
    public List<HjhInstConfigVO> hjhInstConfigList(String instCode);

    /**
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author liubin
     * @param instCode assetType
     * @return
     */
    public int countRecordByPK(String instCode, Integer assetType);
    /**
     * 根据资金来源查询产品类型
     * @param instCode
     * @return
     */
    public List<HjhAssetTypeVO> hjhAssetTypeList(String instCode);
    /**
     * 分页查询
     * @param adminRequest
     * @return
     */
    AdminBorrowFlowResponse selectBorrowFlowList(AdminBorrowFlowRequest adminRequest);
    /**
     * 详情
     * @param adminRequest
     * @return
     */
    public AdminBorrowFlowResponse selectBorrowFlowInfo(AdminBorrowFlowRequest adminRequest);

    /**
     * 添加
     * @param adminRequest
     * @return
     */
    AdminBorrowFlowResponse insertRecord(AdminBorrowFlowRequest adminRequest);

    /**
     * 修改
     * @param adminRequest
     * @return
     */
    AdminBorrowFlowResponse updateRecord(AdminBorrowFlowRequest adminRequest);

    /**
     * 删除
     * @param adminRequest
     * @return
     */
    AdminBorrowFlowResponse deleteRecord(AdminBorrowFlowRequest adminRequest);

    /**
     * 删除配置信息
     *
     * @param ids
     * @return
     */
    PushMoneyResponse deleteRecord(List<Integer> ids);

    /**
     *（条件）列表查询
     * @param adminRequest
     * @return
     */
    BorrowProjectTypeResponse selectProjectTypeList(BorrowProjectTypeRequest adminRequest);

    /**
     * 画面迁移
     * @param adminRequest
     * @return
     */
    BorrowProjectTypeResponse selectProjectTypeRecord(BorrowProjectTypeRequest adminRequest);
    /**
     * 根据主键判断汇直投项目类型维护中数据是否存在
     * @return
     */
    public boolean isExistsRecord(BorrowProjectTypeVO record);

    /**
     * 获取单个汇直投项目类型维护
     * @return
     */
    public BorrowProjectTypeVO getRecord(BorrowProjectTypeVO record);

    /**
     * 根据项目编号查询还款方式
     * @param str
     */
    public List<BorrowProjectRepayVO> selectRepay(String str);
    /**
     * 查询类型表
     */
    public List<BorrowStyleVO> selectStyles();

    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    public BorrowProjectTypeResponse insertRecord(BorrowProjectTypeRequest record);
    /**
     * 汇直投项目类型维护插入
     *
     * @param record
     */
    public BorrowProjectTypeResponse updateRecord(BorrowProjectTypeRequest record);
    /**
     *  汇直投项目类型维护删除
     * @param adminRequest
     */
    public BorrowProjectTypeResponse deleteProjectType( BorrowProjectTypeRequest adminRequest);
    /**
     * 检查项目名称唯一性
     * @param borrowCd
     * @return
     */
    public int borrowCdIsExists(BorrowProjectTypeRequest borrowCd);

    /**
     * 查询配置中心还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse borrowStyelInit(AdminBorrowStyleRequest adminRequest);

    /**
     * 根据id查询还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse searchBorrowStyleInfo(AdminBorrowStyleRequest adminRequest);
    /**
     * 保存还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse insertBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse updateBorrowStyle(AdminBorrowStyleRequest adminRequest);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse deleteBorrowStyle(Integer id);
    /**
     * 修改还款方式
     *
     * @return
     */
    public AdminBorrowStyleResponse modifyBorrowStyle(Integer id);
    /**
     * 根据主键判断权限维护中权限是否存在
     *
     * @return
     */

    public boolean validatorFieldCheck(AdminBorrowStyleRequest adminRequest);

    List<HjhInstConfigVO> selectHjhInstConfigByInstCode(String instCode);

//    HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request);

    /**
     * 查询配置中心保证金配置
     *
     * @return
     */
    public AdminInstConfigDetailResponse instConfigInit(AdminInstConfigListRequest adminRequest);

    /**
     * 查询详情
     * @param adminRequest
     * @return
     */
    public AdminInstConfigDetailResponse searchInstConfigInfo(AdminInstConfigListRequest adminRequest);

    /**
     * 编辑保存保证金配置
     * @return
     */
    public AdminInstConfigListResponse saveInstConfig(AdminInstConfigListRequest req);

    /**
     * 修改保证金配置
     * @return
     */
    public AdminInstConfigListResponse updateInstConfig(AdminInstConfigListRequest req);

    /**
     * 删除保证金配置
     * @return
     */
    public AdminInstConfigListResponse deleteInstConfig(AdminInstConfigListRequest req);

    /**
     * 查询协议列表
     *
     * @param request
     * @return
     */
    FddTempletCustomizeResponse selectFddTempletList(ProtocolsRequestBean request);

    /**
     * 添加协议列表
     *
     * @param requestBean
     * @return
     */
    FddTempletCustomizeResponse insertAction(ProtocolsRequestBean requestBean);

    /**
     * 修改协议列表
     *
     * @param requestBean
     * @return
     */
    FddTempletCustomizeResponse updateAction(ProtocolsRequestBean requestBean);

    /**
     * 获取提成配置列表
     *
     * @return
     */
    PushMoneyResponse getRecordList(PushMoneyRequest requestBean);

    /**
     * 添加提成配置
     *
     * @param requestBean
     * @return
     */
    PushMoneyResponse insertPushMoney(PushMoneyRequestBean requestBean);

    /**
     * 修改提成配置
     *
     * @param requestBean
     * @return
     */
    PushMoneyResponse updatePushMoney(PushMoneyRequestBean requestBean);

    /**
     * 查询流程配置中的发标/复审
     * @param adminRequest
     * @return
     */
    public BorrowSendTypeResponse selectBorrowSendList(BorrowSendTypeRequest adminRequest);
    /**
     * 查询流程配置中的发标/复审页面
     * @param sendCd
     * @return
     */
    public BorrowSendTypeVO getBorrowSendInfo(String sendCd);

    /**
     * 数据插入
     * @param adminRequest
     */
    public BorrowSendTypeResponse insertBorrowSend(BorrowSendTypeRequest adminRequest);

    /**
     * 数据修改
     * @param adminRequest
     */
    public BorrowSendTypeResponse updateBorrowSend(BorrowSendTypeRequest adminRequest);
    /**
     * 删除
     * @param sendCd
     */
    public BorrowSendTypeResponse daleteBorrowSend(String sendCd);

    /**
     * 受托支付白名单列表
     *
     * @return
     */
    STZHWhiteListResponse selectSTZHWhiteList(STZHWhiteListRequestBean requestBean);

    /**
     * 添加受托支付白名
     *
     * @param requestBean
     * @return
     */
    STZHWhiteListResponse insertSTZHWhiteList(STZHWhiteListRequestBean requestBean);

    /**
     * 修改受托支付白名
     *
     * @param requestBean
     * @return
     */
    STZHWhiteListResponse updateSTZHWhiteList(STZHWhiteListRequestBean requestBean);

    /**
     * 获取机构信息
     * @param instcode
     * @return
     */
    HjhInstConfigVO selectHjhInstConfig(String instcode);

    /**
     * 批量审核优惠券
     * @param params
     * @param params
     * @return
     */
    JSONObject getBatchCoupons(Map<String, String> params);

    /**
     * 根据条件查询PC统计明细
     *
     * @param request
     * @return
     */
    ChannelStatisticsDetailResponse searchChannelStatisticsDetail(ChannelStatisticsDetailRequest request);

    /**
     * 根据订单号查询提现订单
     * @param nid
     * @param userId
     * @return
     */
    AccountWithdrawVO queryAccountwithdrawByNid(String nid, Integer userId);

    /**
     * 提现成功后,更新用户账户信息,提现记录
     * @param param
     * @return
     */
    boolean updateAccountAfterWithdraw(Map<String,String> param);

    /**
     * 提现失败后,更新用户的提现记录
     * @param userId
     * @param nid
     * @return
     */
    boolean updateAccountAfterWithdrawFail(Integer userId, String nid);

    /**
     * 获取部门列表
     * 此方法后期可以做成基类的方法
     * @return
     */
    OADepartmentResponse getCrmDepartmentList(HjhCommissionRequest form);

    /**
     * 直投提成列表count
     * @auth sunpeikai
     * @param
     * @return
     */
    int getPushMoneyListCount(PushMoneyRequest request);

    /**
     * 直投提成列表list
     * @auth sunpeikai
     * @param
     * @return
     */
    List<PushMoneyVO> searchPushMoneyList(PushMoneyRequest request);

    /**
     * 直投提成列表查询总金额
     * @auth sunpeikai
     * @param
     * @return
     */
    Map<String,Object> queryPushMoneyTotle(PushMoneyRequest request);

    /**
     * 根据userid查询 crm  cuttype
     * @auth sunpeikai
     * @param
     * @return
     */
/*
    int queryCrmCuttype(Integer userId);
*/

    /**
     * 发提成包含参数：TenderCommissionVO tenderCommissionVO, BankCallBean resultBean
     * @auth sunpeikai
     * @param
     * @return
     */
    int updateTenderCommissionRecord(PushMoneyRequest pushMoneyRequest);
    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigListByParam(AdminSubConfigRequest adminRequest);

    /**
     * 页面详情
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse selectSubConfigInfo(AdminSubConfigRequest adminRequest);
    /**
     *  分账名单配置添加
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse insertSubConfig(AdminSubConfigRequest adminRequest);
    /**
     * 分账名单配置修改
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse updateSubConfig(AdminSubConfigRequest adminRequest);
    /**
     * 分账名单配置删除
     * @param adminRequest
     * @return
     */
    AdminSubConfigResponse deleteSubConfig(AdminSubConfigRequest adminRequest);

    /**
     * 查询配置中心操作日志配置
     * @param adminRequest
     * @return
     */
    public List<FeerateModifyLogVO> selectInstAndAssertType(AdminOperationLogRequest adminRequest);
    /**
     * 产品类型   asset_type  asset_type_name资产类型名称
     *
     * @param
     * @return List<HjhAssetTypeVO>
     */
    List<HjhAssetTypeVO> getHjhAssetType();



    /**
     * 查询用户充值记录
     * @param userId
     * @return
     */
    List<AccountRechargeVO> getAccountRecharge(int userId);

    /**
     * 根据userId获取提成方式
     * 此方法后期可以做成基类的方法
     * @return
     */
    Integer queryCrmCuttype(Integer userId);

    /**
     * 发提成
     * @auth libin
     * @param accountVO 账户信息
     * @return
     */
    Integer updateTenderCommissionRecord(CommissionComboRequest request);

    CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest);

    CouponUserCustomizeResponse deleteById(CouponUserBeanRequest couponUserBeanRequest);

    List<CouponConfigCustomizeVO> getCouponConfigCustomize(CouponConfigRequest request);

    CouponConfigResponse selectCouponConfig(String couponCode);

    CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest);

    CouponUserResponse getCouponUserByCouponCode(String couponCode);

    CouponUserCustomizeResponse selectCouponUserById(Integer couponUserId);

    CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean);

    /**
     * 查询优惠券已发行数量
     * @param param
     * @return
     */
    CouponRecoverCustomizeResponse checkCouponSendExcess(String couponCode);
    BorrowCustomizeResponse selectBorrowAllList(BorrowBeanRequest form);

    /**
     * 查询列表
     * @param adminRequest
     * @return
     */
    FinmanChargeNewResponse selectFinmanChargeList(FinmanChargeNewRequest adminRequest);
    /**
     * 根据manChargeCd查询费率配置 详情
     * @author xiehuili
     * @param manChargeCd
     * @return
     */
    FinmanChargeNewResponse getRecordInfo(String manChargeCd);
    /**
     * 插入费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public FinmanChargeNewResponse insertFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest);
    /**
     * 修改费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public FinmanChargeNewResponse updateFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest);
    /**
     * 删除费率配置
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public FinmanChargeNewResponse deleteFinmanChargeNewRecord(FinmanChargeNewRequest adminRequest);
    /**
     *
     * 根据表的类型,期数,项目类型检索管理费件数
     * @author xiehuili
     * @param adminRequest
     * @return
     */
    public int countRecordByProjectType(FinmanChargeNewRequest adminRequest);

    /**
     * 还款方式下拉列表
     *
     * @param
     * @return
     * @author wangjun
     */
    List<BorrowStyleVO> selectCommonBorrowStyleList();

    /**
     * 资产来源下拉列表
     *
     * @param
     * @return
     * @author wangjun
     */
    List<HjhInstConfigVO> selectCommonHjhInstConfigList();
    /**
     * 添加互金字段定义
     * @param request
     * @return
     * @auth nxl
     */
    Boolean insertNifaFieldDefinition(NifaFieldDefinitionAddRequest request);

    /**
     * 查找互金字段定义列表
     * @param request
     * @auth nxl
     * @return
     */
    NifaFieldDefinitionResponse selectFieldDefinitionList(NifaFieldDefinitionRequest request);
    /**
     * 根据id查找互金定义
     * @param nifaId
     * @auth nxl
     * @return
     */
    NifaFieldDefinitionResponse selectFieldDefinitionById(String nifaId);
    /**
     * 修改互金字段定义
     * @param request
     * @return
     * @auth nxl
     */
    Boolean updateNifaFieldDefinition(NifaFieldDefinitionAddRequest request);

    /**
     * 添加合同模版约定条款表
     * @param request
     * @return
     * @auth nxl
     */
    Boolean insertNifaContractTemplate(NifaContractTemplateAddRequest request);
    /**
     * 查找合同模板id
     * @return
     */
    FddTempletResponse selectFddTempletId();
    /**
     * 修改合同模版约定条款表
     * @param request
     * @return
     * @auth nxl
     */
    Boolean updateNifaContractTemplate(NifaContractTemplateAddRequest request);
    /**
     * 根据id查找合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    NifaContractTemplateResponse selectNifaContractTemplateById(String nifaId);
    /**
     * 根据id删除合同模版约定条款表
     * @param nifaId
     * @auth nxl
     * @return
     */
    Boolean deleteNifaContractTemplateById(int nifaId);
    /**
     * 查找互金字段定义列表
     * @param request
     * @return
     * @auth nxl
     */
    NifaContractTemplateResponse selectNifaContractTemplateList(NifaContractTemplateRequest request);

    /**
     * 互金协会报送日志列表
     * @param request
     * @return
     * @auth nxl
     */
    NifaReportLogResponse selectNifaReportLogList(NifaReportLogRequest request);

    /**
     * 行账户管理页面查询件数
     *
     * @param bankAccountManageRequest
     * @return
     */
    Integer queryAccountCount(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 账户管理页面查询列表
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountInfos(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 资金明细（列表）
     *
     * @param bankAccountManageRequest
     * @return
     */
    List<BankAccountManageCustomizeVO> queryAccountDetails(BankAccountManageRequest bankAccountManageRequest);

    /**
     * 传参查询承接债转表列总计
     * @auth libin
     * @param DebtCreditCustomize
     * @return
     */
    HjhCreditTenderSumVO getHjhCreditTenderCalcSumByParam(HjhCreditTenderRequest form);

    /**
     * 查询合作机构配置列表
     * @param adminRequest
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse partnerConfigInit(AdminPartnerConfigListRequest adminRequest);
    /**
     * 查询合作机构配置详情页面
     * @param adminRequest
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse searchPartnerConfigInfo(AdminPartnerConfigListRequest adminRequest);

    /**
     * 编辑保存合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse savePartnerConfig(AdminPartnerConfigListRequest req);

    /**
     * 修改合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse updatePartnerConfig(AdminPartnerConfigListRequest req);

    /**
     * 删除合作机构配置
     * @param req
     * @author xiehuili
     * @return
     */
    public AdminPartnerConfigDetailResponse deletePartnerConfig(AdminPartnerConfigListRequest req);
    /**
     * 合作机构配置资产编号校验
     * @param req
     * @author xiehuili
     * @return
     */
    public IntegerResponse isExistsCheckAction(AdminPartnerConfigListRequest req);
    /**
     * 查询固定时间间隔的用户出借列表
     * @param repairStartDate
     * @param repairEndDate
     * @auth nxl
     * @return
     */
    List<BorrowTenderVO> selectBorrowTenderListByDate(String repairStartDate, String repairEndDate);

    /**
     * 更新borrowTender表
     * @auth nxl
     * @return
     */
    Boolean updateBorrowTender(BorrowTenderUpdRequest request);

    BorrowCustomizeResponse exportBorrowList(BorrowBeanRequest borrowCommonCustomize);

    /**
     * 获取债转状态为0的数据
     * @return
     */
    List<com.hyjf.am.vo.trade.BorrowCreditVO> selectBorrowCreditList();

    /**
     * 更新债转状态
     * @return
     */
    Integer updateBorrowCredit(com.hyjf.am.vo.trade.BorrowCreditVO borrowCreditVO);


    /**
     * 保存垫付协议申请-协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 15:38
     * @param applyAgreementInfoVO
     * @return com.hyjf.am.response.admin.ApplyAgreementInfoResponse
     **/
    ApplyAgreementInfoResponse saveApplyAgreementInfo(ApplyAgreementInfoVO applyAgreementInfoVO);


    /**
     * 保存垫付协议申请-协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 15:38
     * @param applyAgreementVO
     * @return com.hyjf.am.response.admin.ApplyAgreementInfoResponse
     **/
    ApplyAgreementResponse saveApplyAgreement(ApplyAgreementVO applyAgreementVO);
    /**
     * 根据contract_id查询垫付协议生成详情
     * @author Zha Daojian
     * @date 2018/8/23 15:47
     * @param contractId
     * @return com.hyjf.am.response.admin.ApplyAgreementInfoResponse
     **/
    ApplyAgreementInfoVO selectApplyAgreementInfoByContractId(String contractId);
    /**
     * 单期还款数据
     * @param borrowNid
     * @param borrowApr
     * @param borrowStyle
     * @return
     */
    BorrowRepayBeanVO getBorrowRepayInfo(String borrowNid, String borrowApr, String borrowStyle);
    /**
     * 多期还款数据
     * @param borrowNid
     * @param borrowApr
     * @param borrowStyle
     * @return
     */
    BorrowRepayPlanBeanVO getBorrowRepayPlanInfo(String borrowNid, String borrowApr, String borrowStyle);

    /**
     * 统计总数
     * @return
     */
    AdminMerchantAccountSumCustomizeVO searchAccountSum();
    /**
     * 根据id查找互金协会报送日志
     * @param logId
     * @auth nxl
     * @return
     */
    NifaReportLogResponse selectNifaReportLogById(int logId);

    /**
     * 查询账户信息
     * @param status
     * @return
     */
    MerchantAccountResponse selectMerchantAccountList(Integer status);

    /**
     * 获取转账列表
     * @param form
     * @return
     */
    MerchantTransferResponse selectMerchantTransfer(MerchantTransferListRequest form);

    /**
     * 根据id获取转账信息
     * @param id
     * @return
     */
    MerchantAccountVO selectMerchantAccountById(Integer id);

    /**
     * 插入转账信息
     * @param form
     * @return
     */
    boolean insertTransfer(MerchantTransferListRequest form);

    /**
     * 更新转账信息
     * @param orderId
     * @param status
     * @param message
     * @return
     */
    int updateMerchantTransfer(String orderId, int status, String message);

    /**
     * 还款计划数量查询
     * @param request
     * @return
     */
    Integer countBorrowRepaymentPlan(BorrowRepaymentRequest request);

    /**
     * 还款计划列表查询
     * @param request
     * @return
     */
    List<BorrowRepaymentPlanCustomizeVO> selectBorrowRepaymentPlanList(BorrowRepaymentRequest request);

    /**
     * 还款计划统计查询
     * @param request
     * @return
     */
    BorrowRepaymentPlanCustomizeVO sumBorrowRepaymentPlanInfo(BorrowRepaymentRequest request);

    /**
     * 根据id查询受托支付白名单详情
     * @param id
     * @return
     */
    STZHWhiteListResponse selectSTZHWhiteById(Integer id);

    /**
     * 加载优惠券配置列表
     * @param request
     * @return
     */
    CouponConfigCustomizeResponse getConfigCustomizeList(CouponConfigRequest request);

    /**
     * 根据参数获取 HjhRegionVO
     * @param form
     * @return
     */
    HjhAllocationEngineVO getPlanConfigRecordByPlanNidLabelName(AllocationEngineRuquest form);

    /**
     * 根据债转编号和出让人id查询assignPay
     * @author zhangyk
     * @date 2018/9/4 10:30
     */
    String selectTenderCreditAssignPay(Map<String,String> map);

    /**
     * 取得新规的模板编号
     * @param protocolType
     * @return
     */
    String getNewTempletId(Integer protocolType);

    /**
     * 协议管理-画面迁移
     *
     * @param id
     * @return
     */
    FddTempletCustomizeResponse getRecordInfoById(Integer id);

    List<ProtocolLogVO> getProtocolLogVOAll(ProtocolLogRequest request);

    List<TenderAgreementVO> getTenderAgreementByBorrowNid(String borrowId);

    /**
     * 保存协议申请
    * @author Zha Daojian
    * @date 2019/5/8 17:56
    * @param applyBorrowAgreementVO
    * @return com.hyjf.am.response.trade.ApplyBorrowAgreementResponse
    **/
    ApplyBorrowAgreementResponse saveApplyBorrowAgreement( ApplyBorrowAgreementVO applyBorrowAgreementVO);

    Integer countRecordLog(ProtocolLogRequest request);

    /**
     * 统计全部个数
     *
     * @return
     */
    Integer countRecord(AdminProtocolRequest request);

    List<ProtocolTemplateCommonVO> getRecordList(AdminProtocolRequest request);

    /**
     * 根据协议id查询协议和版本
     *
     * @return
     */
    ProtocolTemplateCommonVO getProtocolTemplateById(AdminProtocolRequest request);

    /**
     * 查询协议模板数量
     *
     * @return
     */
    Integer getProtocolTemplateNum(AdminProtocolRequest request);

    /**
     * 判断删除的协议中是否存在当前协议模板名称Agreement006
     *
     * @return
     */
    ProtocolTemplateVO getProtocolTemplateByProtocolName(AdminProtocolRequest request);

    /**
     * 保存 协议模板、协议版本、协议日志
     *
     * @return
     */
    Integer insert(AdminProtocolRequest request);

    /**
     * 修改 协议模板
     *
     * @return
     */
    Integer updateProtocolTemplate(AdminProtocolRequest request);

    /**
     * 修改 之前的版本的启用状态改成不启用
     *
     * @return
     */
    Integer updateDisplayFlag(AdminProtocolRequest request);

    /**
     * 删除协议模板
     *
     * @return
     */
    AdminProtocolResponse deleteProtocolTemplate(AdminProtocolRequest request);

    List<ProtocolTemplateVO> getNewInfo();

    ProtocolVersionVO byIdProtocolVersion(Integer id);

    ProtocolTemplateVO byIdTemplateBy(String protocolId);

    int getProtocolVersionSize(AdminProtocolRequest adminProtocolRequest);

    boolean startUseExistProtocol(AdminProtocolRequest adminProtocolRequest);

    Map<String, Object> validatorFieldCheckClient(AdminProtocolRequest adminProtocolRequest);

    /**
     * VIP中心-优惠券发行 查询导出列表总数
     * @param request
     * @return
     */
    int getCouponConfigCountForExport(CouponConfigRequest request);

    /**
     * 查询优惠券发行导出列表
     * @param request
     * @return
     */
    CouponConfigExportCustomizeResponse getExportConfigList(CouponConfigRequest request);

    /**
     * 画面迁移(含有id更新，不含有id添加)
     *
     * @param id
     * @return
     */
    PushMoneyResponse getInfoAction(Integer id);

    Boolean updateBorrowCreditStautus(String borrowNid);

    Boolean repayRequestUpdate(RepayRequestUpdateRequest requestBean);

    RepayBean getRepayBean(Map<String, String> paraMap);

    boolean getFailCredit(String borrowNid);

    /**
     * 保证金不足列表
     * @param request
     * @return
     */
    AssetListCustomizeResponse findBZJBZList(AssetListRequest request);

    /**
     * 开户成功  修改trade的account
     * @param userId
     * @param accountId
     * @return
     */
    boolean updateAccountNumberByUserId(Integer userId, String accountId);

    /**
     * 查询导出总数
     * @param copyForm
     * @return
     */
    Integer countBorrowRepaymentInfoExport(BorrowRepaymentInfoRequset copyForm);

    /**
     * 获取标的风险投资等级
     *
     * @param borrowLevel
     * @return
     */
    String getBorrowLevelAction(@Valid String borrowLevel);


    /** 加息接口开始*/
    /** 枚举类型 */
    enum IncreaseProperty {VO,STR,STR1}
    /**
     * 产品中心-加息出借明细（总计）
     * @param request
     * @auth wenxin
     * @return
     */
    int getIncreaseInterestInvestDetaiCount(IncreaseInterestInvestDetailRequest request);
    /**
     * 产品中心-加息出借明细（列表/导出）
     * @param request
     * @auth wenxin
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> getIncreaseInterestInvestDetaiList(IncreaseInterestInvestDetailRequest request);
    /**
     * 产品中心-加息出借明细（合计）
     * @param request
     * @auth wenxin
     * @return
     */
    String getSumAccount(IncreaseInterestInvestDetailRequest request);

    /**
     * 产品中心-加息还款信息（总计）
     * @param request
     * @auth wenxin
     * @return
     */
    int getIncreaseInterestRepayCount(IncreaseInterestRepayRequest request);
    /**
     * 产品中心-加息还款信息（列表/导出）
     * @param request
     * @auth wenxin
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> getIncreaseInterestRepayList(IncreaseInterestRepayRequest request);
    /**
     * 产品中心-加息还款信息（合计）
     * @param request
     * @auth wenxin
     * @return
     */
    String getSumAccount(IncreaseInterestRepayRequest request);
    /**
     * 产品中心-加息还款明细（总计）
     * @param request
     * @auth wenxin
     * @return
     */

    int getIncreaseInterestRepayDetailCount(IncreaseInterestRepayDetailRequest request);
    /**
     * 产品中心-加息还款明细（列表/导出）
     * @param request
     * @auth wenxin
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> getIncreaseInterestRepayDetailList(IncreaseInterestRepayDetailRequest request);
    /**
     * 产品中心-加息还款明细（合计）
     * @param request
     * @auth wenxin
     * @return
     */
    AdminIncreaseInterestRepayCustomizeVO getSumBorrowRepaymentInfo(IncreaseInterestRepayDetailRequest request);

    /**
     * 产品中心-加息还款明细详情（总计）
     * @param request
     * @auth wenxin
     * @return
     */
    int getIncreaseInterestRepayInfoListCount(IncreaseInterestRepayInfoListRequest request);
    /**
     * 产品中心-加息还款明细详情（列表导出）
     * @param request
     * @auth wenxin
     * @return
     */
    EnumMap<AmTradeClient.IncreaseProperty,Object> getIncreaseInterestRepayInfoListList(IncreaseInterestRepayInfoListRequest request);
    /**
     * 产品中心-加息还款明细详情（合计）
     * @param request
     * @auth wenxin
     * @return
     */
    AdminIncreaseInterestRepayCustomizeVO getSumBorrowLoanmentInfo(IncreaseInterestRepayInfoListRequest request);

    /**
     *  产品中心-加息还款计划（总计）
     * @param request
     * @auth wenxin
     * @return
     */
    int getIncreaseInterestRepayPlanCount(IncreaseInterestRepayPlanRequest request);

    /**
     *  产品中心-加息还款计划（列表）
     * @param request
     * @auth wenxin
     * @return
     */
    List<IncreaseInterestRepayDetailVO> getIncreaseInterestRepayPlanList(IncreaseInterestRepayPlanRequest request);
    /** 加息接口结束*/

    /**
     * 查询资产列表不分页
     * @param request
     * @return
     */
    AssetListCustomizeResponse findAssetListWithoutPage(AssetListRequest request);
    /**
     * 删除 自动出借临时表
     * @auther: nxl
     * @date: 2018/7/10
     */
   Boolean deleteBorrowTmp(String borrowNid, String accedeOrderId);

    /**
     * 获取保证金信息
     * @param borrowNid
     * @return
     */
    AdminResult getBailInfo(String borrowNid);
    
    /**
     * 汇计划提成列表查询
     *
     * @param
     * @param form
     * @return HjhCommissionResponse
     */
    HjhCommissionResponse selectHjhCommissionListWithOutPage(HjhCommissionRequest form);

    /**
     * 资金中心-汇计划提成导出记录总数
     * @param request
     * @return
     */
    int getHjhCommissionCountForExport(HjhCommissionRequest request);

    /**
     * 配置中心-风险测评开关配置（列表）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationCheckResponse getEvaluationCheckList(EvaluationCheckRequest request);

    /**
     * 配置中心-风险测评开关配置（修改详情）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param id
     **/
    EvaluationCheckConfigVO getEvaluationCheckById(Integer id);

    /**
     * 配置中心-风险测评开关配置（修改）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationCheckResponse updateEvaluationCheck(EvaluationCheckRequest request);

    /**
     * 配置中心-风险测评限额配置（列表）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationMoneyResponse getEvaluationMoneyList(EvaluationMoneyRequest request);

    /**
     * 配置中心-风险测评限额配置（修改详情）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param id
     **/
    EvaluationMoneyConfigVO getEvaluationMoneyById(Integer id);

    /**
     * 配置中心-风险测评限额配置（修改）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationMoneyResponse updateEvaluationMoney(EvaluationMoneyRequest request);

    /**
     * 配置中心-风险测评限额配置（日志列表）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationMoneyLogResponse getEvaluationMoneyLogList(EvaluationMoneyLogRequest request);

    /**
     * 配置中心-风险测评限额配置（日志列表）
     * @author Zha Daojian
     * @date 2018/12/20 17:35
     * @param request
     **/
    EvaluationCheckLogResponse getEvaluationCheckLogList(EvaluationCheckLogRequest request);


    /**
     * 风险测评配置-风险测评等级配置
     *
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigResponse getEvaluationBorrowLevelConfigList(EvaluationBorrowLevelConfigRequest requestBean);


    /**
     * 风险测评配置-风险测评等级配置
     * @param id
     * @return
     */
    EvaluationBorrowLevelConfigVO getEvaluationBorrowLevelConfigById(Integer id);


    /**
     * 更新风险测评配置-风险测评等级配置
     *
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigResponse updateBorrowLevelConfig(EvaluationBorrowLevelConfigRequest requestBean);


    /**
     * 配置中心-风险测评等级配置（日志列表）
     *
     * @param requestBean
     * @return
     */
    EvaluationBorrowLevelConfigLogResponse getBorrowLevelConfigLogList(EvaluationBorrowLevelConfigLogRequest requestBean);
    
    /**
     * 更新标签配置列表ByIdAndLabelState
     * @param request
     */
    int updateHjhLabelRecordByIdAndLabelState(HjhLabelInfoRequest request);

    /**
     * 未开户用户销户成功后,删除用户Account表
     * @param userId
     * @return
     */
    int deleteUserAccountAction(String userId);


}

