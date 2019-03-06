/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance.impl;

import com.hyjf.am.admin.mq.base.CommonProducer;
import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.resquest.admin.PlatformTransferListRequest;
import com.hyjf.am.resquest.admin.PlatformTransferRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.admin.finance.PlatformTransferService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.admin.AccountRechargeVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallParamConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author: sunpeikai
 * @version: PlatformTransferServiceImpl, v0.1 2018/7/9 11:11
 */
@Service(value = "tradePlatformTransferServiceImpl")
public class PlatformTransferServiceImpl extends BaseServiceImpl implements PlatformTransferService {

    @Autowired
    private AccountRechargeMapper accountRechargeMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountListMapper accountListMapper;

    @Autowired
    private BankMerchantAccountMapper bankMerchantAccountMapper;

    @Autowired
    private BankMerchantAccountListMapper bankMerchantAccountListMapper;

    @Autowired
    private CommonProducer commonProducer;

    // 充值状态:充值中
    private static final int RECHARGE_STATUS_WAIT = 1;
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;
    // 充值状态:失败
    private static final int RECHARGE_STATUS_FAIL = 3;
    /**
     * 根据筛选条件查询数据count
     *
     * @param request
     * @return
     * @auth sunpeikai
     */
    @Override
    public Integer getPlatformTransferCount(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        return accountRechargeMapper.countByExample(example);
    }

    /**
     * 根据筛选条件查询平台转账list
     *
     * @param request
     * @return
     * @auth sunpeikai
     */
    @Override
    public List<AccountRecharge> searchPlatformTransferList(PlatformTransferListRequest request) {
        AccountRechargeExample example = convertExample(request);
        return accountRechargeMapper.selectByExample(example);
    }

    /**
     * 更新账户信息
     *
     * @param accountVO 账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateAccount(Account accountVO) {
        Account account = CommonUtils.convertBean(accountVO, Account.class);
        return accountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 插入充值表记录
     *
     * @param accountRechargeVO 充值表信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAccountRecharge(AccountRechargeVO accountRechargeVO) {
        AccountRecharge accountRecharge = CommonUtils.convertBean(accountRechargeVO, AccountRecharge.class);
        return accountRechargeMapper.insertSelective(accountRecharge);
    }

    /**
     * 插入收支明细表记录
     *
     * @param accountListVO 收支明细表信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertAccountList(AccountListVO accountListVO) {
        AccountList accountList = CommonUtils.convertBean(accountListVO, AccountList.class);
        return accountListMapper.insertSelective(accountList);
    }

    /**
     * 根据账户id查询BankMerchantAccount
     *
     * @param accountId 账户id
     * @return
     * @auth sunpeikai
     */
    @Override
    public BankMerchantAccount searchBankMerchantAccountByAccountId(String accountId) {
        BankMerchantAccount bankMerchantAccount = new BankMerchantAccount();
        BankMerchantAccountExample example = new BankMerchantAccountExample();
        example.createCriteria().andAccountCodeEqualTo(accountId);
        List<BankMerchantAccount> bankMerchantAccountList = bankMerchantAccountMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(bankMerchantAccountList)){
            bankMerchantAccount = bankMerchantAccountList.get(0);
        }
        return bankMerchantAccount;
    }

    /**
     * 更新红包账户信息
     *
     * @param bankMerchantAccountVO 红包账户信息
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateBankMerchantAccount(BankMerchantAccount bankMerchantAccountVO) {
        return bankMerchantAccountMapper.updateByPrimaryKeySelective(bankMerchantAccountVO);
    }

    /**
     * 插入红包明细数据
     *
     * @param bankMerchantAccountListVO 红包明细
     * @return
     * @auth sunpeikai
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertBankMerchantAccountList(BankMerchantAccountListVO bankMerchantAccountListVO) {
        BankMerchantAccountList bankMerchantAccountList = CommonUtils.convertBean(bankMerchantAccountListVO, BankMerchantAccountList.class);
        return bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateHandRechargeRecord(PlatformTransferRequest platformTransferRequest) {

        BankCallBean bankBean = CommonUtils.convertBean(platformTransferRequest.getBankCallBeanVO(),BankCallBean.class);
        String accountId = platformTransferRequest.getAccountId();
        String loginUserName = platformTransferRequest.getLoginUserName();
        UserInfoCustomizeVO userInfo = platformTransferRequest.getUserInfo();
        UserInfoCustomizeVO userInfoCustomize = platformTransferRequest.getUserInfoCustomizeVO();

        int ret = 0;

        // 增加时间
        Date now = new Date();
        Integer time = GetDate.getMyTimeInMillis();
        // 商户userID
        // Integer merUserId = Integer.valueOf(ShiroUtil.getLoginUserId());
        // 客户userID
        Integer cusUserId = platformTransferRequest.getUserId();

        // 更新账户信息
        List<Account> accountVOList = searchAccountByUserId(cusUserId);
        Account accountVO = new Account();
        if(!CollectionUtils.isEmpty(accountVOList)){
            accountVO = accountVOList.get(0);
        }

        BigDecimal bankBalanceCash = accountVO.getBankBalanceCash() == null ? BigDecimal.ZERO : accountVO.getBankBalanceCash();
        // 充值金额
        BigDecimal money = platformTransferRequest.getMoney();
        accountVO.setBankBalance(accountVO.getBankBalance().add(money));
        // 累加到账户总资产
        accountVO.setBankTotal(accountVO.getBankTotal().add(money));
        accountVO.setBankBalanceCash(bankBalanceCash.add(money));
        ret += updateAccount(accountVO);

        // 写入充值表
        AccountRechargeVO accountRechargeVO = new AccountRechargeVO();
        accountRechargeVO.setNid(bankBean.getLogOrderId());
        accountRechargeVO.setUserId(cusUserId);
        accountRechargeVO.setStatus(RECHARGE_STATUS_SUCCESS);
        accountRechargeVO.setMoney(money);
        accountRechargeVO.setBalance(accountVO.getBankBalance());
        accountRechargeVO.setFee(new BigDecimal(0));
        accountRechargeVO.setGateType("ADMIN");
        // 线下充值
        accountRechargeVO.setType(0);
        accountRechargeVO.setRemark(platformTransferRequest.getRemark());
        accountRechargeVO.setCreateTime(now);
        accountRechargeVO.setOperator(loginUserName);
        //accountRechargeVO.setAddtime(time.toString());
        accountRechargeVO.setAddIp(bankBean.getLogIp());
        accountRechargeVO.setUpdateTime(now);
        //accountRechargeVO.setNok(0);
        //accountRechargeVO.setDianfuFee(new BigDecimal(0));
        //accountRechargeVO.setIsok(0);
        // 0pc 1app
        accountRechargeVO.setClient(0);
        //accountRechargeVO.setIsok11(0);
        //accountRechargeVO.setFlag(0);
        //accountRechargeVO.setActivityFlag(0);
        accountRechargeVO.setUsername(userInfo.getUserName());
        // 资金托管平台 0:汇付,1:江西银行
        accountRechargeVO.setIsBank(1);
        // 交易日期
        accountRechargeVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
        // 交易时间
        accountRechargeVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
        // 交易流水号
        accountRechargeVO.setSeqNo(Integer.parseInt(bankBean.getSeqNo()));
        // 交易日期+交易时间+交易流水号
        accountRechargeVO.setBankSeqNo(bankBean.getTxDate() + bankBean.getTxTime() + bankBean.getSeqNo());
        // 电子账号
        accountRechargeVO.setAccountId(accountId);
        ret += insertAccountRecharge(accountRechargeVO);


        // 写入收支明细
        AccountListVO accountListVO = new AccountListVO();
        accountListVO.setNid(bankBean.getLogOrderId());
        accountListVO.setSeqNo(bankBean.getSeqNo());
        accountListVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
        accountListVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
        accountListVO.setBankSeqNo(bankBean.getTxDate() + bankBean.getTxTime() + bankBean.getSeqNo());
        accountListVO.setCheckStatus(0);
        accountListVO.setTradeStatus(1);
        accountListVO.setUserId(platformTransferRequest.getUserId());
        accountListVO.setAccountId(accountId);
        accountListVO.setAmount(money);
        // 1收入2支出3冻结
        accountListVO.setType(1);
        accountListVO.setTrade("platform_transfer");
        accountListVO.setTradeCode("balance");
        // 银行总资产
        accountListVO.setBankTotal(accountVO.getBankTotal());
        // 银行可用余额
        accountListVO.setBankBalance(accountVO.getBankBalance());
        // 银行冻结金额
        accountListVO.setBankFrost(accountVO.getBankFrost());
        // 银行待还本金
        accountListVO.setBankWaitCapital(accountVO.getBankWaitCapital());
        // 银行待还利息
        accountListVO.setBankWaitInterest(accountVO.getBankWaitInterest());
        // 银行待收本金
        accountListVO.setBankAwaitCapital(accountVO.getBankAwaitCapital());
        // 银行待收利息
        accountListVO.setBankAwaitInterest(accountVO.getBankAwaitInterest());
        // 银行待收总额
        accountListVO.setBankAwait(accountVO.getBankAwait());
        // 银行累计收益
        accountListVO.setBankInterestSum(accountVO.getBankInterestSum());
        // 银行累计出借
        accountListVO.setBankInvestSum(accountVO.getBankInvestSum());
        // 银行待还金额
        accountListVO.setBankWaitRepay(accountVO.getBankWaitRepay());
        //汇计划账户可用余额
        accountListVO.setPlanBalance(accountVO.getPlanBalance());
        accountListVO.setPlanFrost(accountVO.getPlanFrost());
        accountListVO.setTotal(accountVO.getTotal());
        accountListVO.setBalance(accountVO.getBalance());
        accountListVO.setFrost(accountVO.getFrost());
        accountListVO.setAwait(accountVO.getAwait());
        accountListVO.setRepay(accountVO.getRepay());
        accountListVO.setRemark("平台转账");
        accountListVO.setCreateTime(new Date());
        accountListVO.setOperator(loginUserName);
        accountListVO.setIp(bankBean.getLogIp());
        accountListVO.setIsUpdate(0);
        accountListVO.setBaseUpdate(0);
        accountListVO.setInterest(null);
        accountListVO.setWeb(2);
        accountListVO.setIsBank(1);
        ret += insertAccountList(accountListVO);

        // 写入网站收支
        AccountWebListVO accountWebListVO = new AccountWebListVO();
        accountWebListVO.setOrdid(bankBean.getLogOrderId());
        accountWebListVO.setAmount(Double.valueOf(money.toString()));
        // 1收入2支出
        accountWebListVO.setType(2);
        accountWebListVO.setTrade("platform_transfer");
        accountWebListVO.setTradeType("平台转账");
        accountWebListVO.setUserId(platformTransferRequest.getUserId());
        accountWebListVO.setUsrcustid(accountId);
        accountWebListVO.setTruename(userInfo.getTrueName());
        accountWebListVO.setRegionName(userInfo.getRegionName());
        accountWebListVO.setBranchName(userInfo.getBranchName());
        accountWebListVO.setDepartmentName(userInfo.getDepartmentName());
        accountWebListVO.setRemark(platformTransferRequest.getRemark());
        accountWebListVO.setCreateTime(time);
        accountWebListVO.setOperator(loginUserName);
        accountWebListVO.setFlag(1);
        // ret += csMessageClient.insertAccountWebList(accountWebListVO);

        try {
            boolean b = commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebListVO));
            if (b) {
                ret++;
            }
        } catch (MQException e) {
            logger.error(e.getMessage());
            throw new RuntimeException("平台转账 发生异常，用户userId" + platformTransferRequest.getUserId() + ",accountId：" + accountId);
        }



        // 添加红包账户明细
        BankMerchantAccount bankMerchantAccountVO = searchBankMerchantAccountByAccountId(bankBean.getAccountId());
        bankMerchantAccountVO.setAvailableBalance(bankMerchantAccountVO.getAvailableBalance().subtract(money));
        bankMerchantAccountVO.setAccountBalance(bankMerchantAccountVO.getAccountBalance().subtract(money));
        bankMerchantAccountVO.setUpdateTime(GetDate.getNowTime());

        // 更新红包账户信息
        int updateCount = updateBankMerchantAccount(bankMerchantAccountVO);


        if(updateCount > 0){
            //UserInfoCustomizeVO userInfoCustomize = amUserClient.getUserInfoCustomizeByUserId(platformTransferRequest.getUserId());

            // 添加红包明细
            BankMerchantAccountListVO bankMerchantAccountListVO = new BankMerchantAccountListVO();
            bankMerchantAccountListVO.setOrderId(bankBean.getLogOrderId());
            bankMerchantAccountListVO.setUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setAccountId(accountId);
            bankMerchantAccountListVO.setAmount(money);
            bankMerchantAccountListVO.setBankAccountCode(bankBean.getAccountId());
            bankMerchantAccountListVO.setBankAccountBalance(bankMerchantAccountVO.getAccountBalance());
            bankMerchantAccountListVO.setBankAccountFrost(bankMerchantAccountVO.getFrost());
            bankMerchantAccountListVO.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
            bankMerchantAccountListVO.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
            bankMerchantAccountListVO.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
            bankMerchantAccountListVO.setTxDate(Integer.parseInt(bankBean.getTxDate()));
            bankMerchantAccountListVO.setTxTime(Integer.parseInt(bankBean.getTxTime()));
            bankMerchantAccountListVO.setSeqNo(bankBean.getSeqNo());
            bankMerchantAccountListVO.setCreateTime(new Date());
            bankMerchantAccountListVO.setUpdateTime(new Date());
            bankMerchantAccountListVO.setCreateUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setUpdateUserId(platformTransferRequest.getUserId());
            bankMerchantAccountListVO.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountListVO.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountListVO.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountListVO.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountListVO.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountListVO.setRemark("平台转账");

            ret += insertBankMerchantAccountList(bankMerchantAccountListVO);
        }
        return ret;
    }

    private List<Account> searchAccountByUserId(Integer userId){
        AccountExample example = new AccountExample();
        AccountExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        return accountMapper.selectByExample(example);
    }

    private AccountRechargeExample convertExample(PlatformTransferListRequest request) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria criteria = example.createCriteria();
        // 用户名
        if (StringUtils.isNotEmpty(request.getUsernameSearch())) {
            criteria.andUsernameLike("%" + request.getUsernameSearch() + "%");
        }
        // 订单号
        if (StringUtils.isNotEmpty(request.getNidSearch())) {
            criteria.andNidLike("%" + request.getNidSearch() + "%");
        }
        // 转账状态
        if (StringUtils.isNotEmpty(request.getStatusSearch())) {
            criteria.andStatusEqualTo(Integer.valueOf(request.getStatusSearch()));
        }
        // 添加时间开始
        if (StringUtils.isNotEmpty(request.getStartDate())) {
            criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.stringToDate(GetDate.getDayStart(request.getStartDate())));
        }
        //添加时间结束
        if (StringUtils.isNotEmpty(request.getEndDate())) {
            criteria.andCreateTimeLessThan(GetDate.stringToDate(GetDate.getDayEnd(request.getEndDate())));
        }
        criteria.andGateTypeEqualTo("ADMIN");
        example.setOrderByClause("create_time desc");
        if (request.getLimitStart() != -1) {
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }

        return example;
    }

    @Override
    public Integer getBankMerchantAccountListCountByOrderId(String orderId) {
        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BankMerchantAccountList> listAccountWithdraw = this.bankMerchantAccountListMapper.selectByExample(accountWithdrawExample);
        if (CollectionUtils.isEmpty(listAccountWithdraw)) {
            return 0;
        }
        return listAccountWithdraw.size();
    }


    /**
     * 圈存异步回调处理
     * @author zhangyk
     * @date 2018/8/7 18:58
     */
    @Override
    public Boolean updateAccountByRechargeCallback(Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        String orderId = (String) params.get(BankCallParamConstant.PARAM_LOGORDERID);
        String accountId = (String) params.get(BankCallParamConstant.PARAM_ACCOUNTID);
        String txAmount = (String) params.get(BankCallParamConstant.PARAM_TXAMOUNT);

        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BankMerchantAccountList> listAccountWithdraw = this.bankMerchantAccountListMapper.selectByExample(accountWithdrawExample);

        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            BankMerchantAccountList accountWithdraw = listAccountWithdraw.get(0);
            // 如果信息未被处理
            if (CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS.equals(accountWithdraw.getStatus())) {
                return Boolean.TRUE;
            } else {
                BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
                BankMerchantAccountExample.Criteria bankMerchantAccountCriteria = bankMerchantAccountExample.createCriteria();
                bankMerchantAccountCriteria.andAccountCodeEqualTo(accountId);
                BankMerchantAccount bankMerchantAccount = this.bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample).get(0);


                // 提现金额
                BigDecimal transAmt = new BigDecimal(txAmount);

                // 更新账户信息
                bankMerchantAccount.setAccountBalance(bankMerchantAccount.getAccountBalance().add(transAmt));
                bankMerchantAccount.setAvailableBalance(bankMerchantAccount.getAvailableBalance().add(transAmt));
                bankMerchantAccountCriteria.andUpdateTimeEqualTo(bankMerchantAccount.getUpdateTime());
                bankMerchantAccount.setUpdateTime(new Date());
                boolean isAccountUpdateFlag = this.bankMerchantAccountMapper.updateByExampleSelective(bankMerchantAccount, bankMerchantAccountExample) > 0 ? true : false;

                if (!isAccountUpdateFlag) {
                    throw new RuntimeException("圈存成功后,插入交易明细表失败~!, 操作回滚");
                }

                BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
                bankMerchantAccountList.setId(accountWithdraw.getId());
                bankMerchantAccountList.setOrderId(orderId);
                bankMerchantAccountList.setBankAccountBalance(bankMerchantAccount.getAvailableBalance());
                bankMerchantAccountList.setBankAccountFrost(bankMerchantAccount.getFrost());
                bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
                bankMerchantAccountList.setUpdateUserId(userId);
                bankMerchantAccountList.setUpdateTime(new Date());
                boolean isBankMerchantAccountListFlag = bankMerchantAccountListMapper.updateByPrimaryKeySelective(bankMerchantAccountList) > 0;
                if (!isBankMerchantAccountListFlag) {
                    throw new RuntimeException("圈存成功后，查询红包明细表失败,操作回滚");
                }
                return Boolean.TRUE;
            }
        }
        return false;
    }


    /**
     * 更新充值明细为失败状态
     * @author zhangyk
     * @date 2018/8/8 10:30
     */
    @Override
    public Boolean updateMerchantAccountListFail(String orderId) {
        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
        bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_FAIL);
        bankMerchantAccountList.setUpdateUserName("0");
        bankMerchantAccountList.setUpdateTime(new Date());
        return bankMerchantAccountListMapper.updateByExampleSelective(bankMerchantAccountList, accountWithdrawExample) > 0;
    }


    /**
     * 圈提异步回调处理
     * @author zhangyk
     * @date 2018/8/7 18:58
     */
    @Override
    public Boolean updateAccountByWithdrawCallback(Map<String, Object> params) {
        Integer userId = (Integer) params.get("userId");
        String orderId = (String) params.get(BankCallParamConstant.PARAM_LOGORDERID);
        String accountId = (String) params.get(BankCallParamConstant.PARAM_ACCOUNTID);
        String txAmount = (String) params.get(BankCallParamConstant.PARAM_TXAMOUNT);
        String feeAmount = (String) params.get(BankCallParamConstant.PARAM_TXFEE);

        BankMerchantAccountListExample accountWithdrawExample = new BankMerchantAccountListExample();
        accountWithdrawExample.createCriteria().andOrderIdEqualTo(orderId);
        List<BankMerchantAccountList> listAccountWithdraw = this.bankMerchantAccountListMapper.selectByExample(accountWithdrawExample);
        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            BankMerchantAccountList accountWithdraw = listAccountWithdraw.get(0);
            // 如果信息未被处理
            if (CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS.equals(accountWithdraw.getStatus())) {
                return Boolean.TRUE;
            } else {
                BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
                BankMerchantAccountExample.Criteria bankMerchantAccountCriteria = bankMerchantAccountExample.createCriteria();
                bankMerchantAccountCriteria.andAccountCodeEqualTo(accountId);
                BankMerchantAccount bankMerchantAccount = this.bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample).get(0);

                // 提现金额
                BigDecimal transAmt = new BigDecimal(txAmount);
                // 提现手续费
                BigDecimal feeAmt = new BigDecimal(feeAmount);
                // 支出金额
                BigDecimal outAmt = transAmt.add(feeAmt);

                // 更新账户信息
                bankMerchantAccount.setAccountBalance(bankMerchantAccount.getAccountBalance().subtract(outAmt));
                bankMerchantAccount.setAvailableBalance(bankMerchantAccount.getAvailableBalance().subtract(outAmt));
                bankMerchantAccountCriteria.andUpdateTimeEqualTo(bankMerchantAccount.getUpdateTime());
                bankMerchantAccount.setUpdateTime(new Date());
                boolean isAccountUpdateFlag = this.bankMerchantAccountMapper.updateByExampleSelective(bankMerchantAccount, bankMerchantAccountExample) > 0;

                if (!isAccountUpdateFlag) {
                    throw new RuntimeException("圈提成功后,插入交易明细表失败~!, 操作回滚");
                }

                BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
                bankMerchantAccountList.setId(accountWithdraw.getId());
                bankMerchantAccountList.setOrderId(orderId);
                bankMerchantAccountList.setBankAccountBalance(bankMerchantAccount.getAvailableBalance());
                bankMerchantAccountList.setBankAccountFrost(bankMerchantAccount.getFrost());
                bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
                bankMerchantAccountList.setUpdateUserId(userId);
                bankMerchantAccountList.setUpdateTime(new Date());
                boolean isBankMerchantAccountListFlag = bankMerchantAccountListMapper.updateByPrimaryKeySelective(bankMerchantAccountList) > 0;
                if (!isBankMerchantAccountListFlag) {
                    throw new RuntimeException("圈提成功后，查询红包明细表失败,操作回滚");
                }
                return Boolean.TRUE;
            }
        }
        return false;
    }
}
