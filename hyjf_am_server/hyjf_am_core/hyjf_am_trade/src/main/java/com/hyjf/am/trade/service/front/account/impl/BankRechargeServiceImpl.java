package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.account.BankRechargeService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 江西银行充值掉单异常处理Service实现类
 * create by jijun 20180612
 */
@Service
public class BankRechargeServiceImpl extends BaseServiceImpl implements BankRechargeService {

    // 充值状态:失败
    private static final int RECHARGE_STATUS_FAIL = 3;
    // 充值状态:成功
    private static final int RECHARGE_STATUS_SUCCESS = 2;
    
    /**
     * 检索充值中的充值记录
     * @return
     */
    @Override
    public void recharge() {
        List<AccountRecharge> rechargeList = this.selectBankRechargeList();
        if (rechargeList != null && rechargeList.size() > 0) {
            // 循环处理中的充值订单
            for (AccountRecharge accountRecharge : rechargeList) {
                this.updateRecharge(accountRecharge);
            }
        }

    }

    /**
     * 更新处理中的充值记录
     * bankOpenAccount    getBankOpenAccount
     * @param accountRecharge
     */
    private void updateRecharge(AccountRecharge accountRecharge) {

        // 当前时间
    	Date nowTime = GetDate.getDate();
        // 交易日期
        Integer txDate = accountRecharge.getTxDate();
        // 交易时间
        Integer txTime = accountRecharge.getTxTime();
        // 交易流水号
        Integer seqNo = accountRecharge.getSeqNo();
        // 充值用户的用户Id
        Integer userId = accountRecharge.getUserId();
        // 充值订单号
        String orderId = accountRecharge.getNid();
        // 根据用户Id获取用户开户信息
        String accountId = accountRecharge.getAccountId();
        // 根据 交易日期,交易时间 ,交易流水号查询此笔充值订单是否成功
        BankCallBean bean = new BankCallBean();
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallConstant.TXCODE_FUND_TRANS_QUERY);// 消息类型
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setChannel(BankCallConstant.CHANNEL_PC);
        bean.setAccountId(accountId);// 电子账号
        bean.setOrgTxDate(String.valueOf(txDate));// 原交易日期
        // modify  by liubin 原交易时间 补全6位 start
        bean.setOrgTxTime(String.format("%06d", txTime));// 原交易时间
        // modify  by liubin 原交易时间 补全6位 end
        bean.setOrgSeqNo(String.valueOf(seqNo));// 原交易流水号
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        bean.setLogRemark("单笔资金类业务交易查询（充值Batch）");
        try {
            // 调用江西银行
            BankCallBean resultBean = BankCallUtils.callApiBg(bean);
            if (Validator.isNotNull(resultBean)) {
                String retCode = StringUtils.isNotBlank(resultBean.getRetCode()) ? resultBean.getRetCode() : "";
                if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    // 调用银行接口返回成功
                    String result = StringUtils.isNotBlank(resultBean.getResult()) ? resultBean.getResult() : "";
                    logger.info("=====银行充值异常batch修复请求银行接口返回:"+result);
                    if ("00".equals(result)) {
                        // 交易处理结果:00 成功
                        // 根据充值订单号重新获取
                        AccountRecharge newAccountRechage = this.getAccountRecharge(orderId, userId);
                        if (newAccountRechage != null) {
                            // 充值记录状态:充值中,充值失败
                            if (newAccountRechage.getStatus() == 1 || newAccountRechage.getStatus() == 0
                                    // add by liubin 江西银行充值掉单异常处理修正 start
                                    || newAccountRechage.getStatus() == 3
                                // add by liubin 江西银行充值掉单异常处理修正 end
                                    ) {
                                // 交易金额
                                BigDecimal txAmount = new BigDecimal(resultBean.getTxAmount());
                                if (txAmount.compareTo(newAccountRechage.getMoney()) == 0) {
                                    // 更新用户充值记录
                                    AccountRechargeExample accountRechargeExample = new AccountRechargeExample();
                                    accountRechargeExample.createCriteria().andNidEqualTo(orderId).andStatusEqualTo(newAccountRechage.getStatus());
                                    newAccountRechage.setUpdateTime(nowTime);// 更新时间
                                    newAccountRechage.setStatus(RECHARGE_STATUS_SUCCESS);// 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
                                    newAccountRechage.setRemark("后台batch执行,修复充值状态为成功");
                                    boolean isAccountRechargeFlag = this.accountRechargeMapper.updateByExampleSelective(newAccountRechage, accountRechargeExample) > 0 ? true : false;
                                    if (!isAccountRechargeFlag) {
                                        throw new Exception("充值后,回调更新充值记录表失败!" + "充值订单号:" + orderId + ",用户ID:" + userId);
                                    }
                                    // 更新账户信息前,检索用户的交易明细表是否有相同流水号的订单
                                    if (!countAccountList(orderId, userId, txDate, txTime, seqNo)) {
                                        // 更新用户账户信息
                                        Account newAccount = new Account();
                                        newAccount.setUserId(userId);// 用户Id
                                        newAccount.setBankTotal(txAmount); // 累加到账户总资产
                                        newAccount.setBankBalance(txAmount); // 累加可用余额
                                        newAccount.setBankBalanceCash(txAmount);// 银行账户可用户
                                        boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankRechargeSuccess(newAccount) > 0 ? true : false;
                                        if (!isAccountUpdateFlag) {
                                            throw new Exception("充值成功后,更新用户账户信息表(huiyingdai_account)失败,充值订单号:" + orderId + ",用户Id:" + userId);
                                        }
                                        // 重新获取用户账户信息
                                        Account account = this.getAccount(userId);
                                        // 生成交易明细
                                        AccountList accountList = new AccountList();
                                        accountList.setNid(orderId);
                                        accountList.setUserId(userId);
                                        accountList.setAmount(txAmount);
                                        accountList.setTxDate(txDate);// 交易日期
                                        accountList.setTxTime(txTime);// 交易时间
                                        accountList.setSeqNo(String.valueOf(seqNo));// 交易流水号
                                        accountList.setBankSeqNo(newAccountRechage.getBankSeqNo());
                                        accountList.setType(1);
                                        accountList.setTrade("recharge");
                                        accountList.setTradeCode("balance");
                                        accountList.setAccountId(accountId);
                                        accountList.setBankTotal(account.getBankTotal()); // 银行总资产
                                        accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
                                        accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
                                        accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
                                        accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
                                        accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
                                        accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
                                        accountList.setBankAwait(account.getBankAwait());// 银行待收总额
                                        accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
                                        accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计出借
                                        accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
                                        accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
                                        accountList.setPlanFrost(account.getPlanFrost());
                                        accountList.setTotal(account.getTotal());
                                        accountList.setBalance(account.getBalance());
                                        accountList.setFrost(account.getFrost());
                                        accountList.setAwait(account.getAwait());
                                        accountList.setRepay(account.getRepay());
                                        accountList.setRemark("快捷充值");
                                        accountList.setCreateTime(nowTime);
                                        accountList.setOperator(String.valueOf(userId));
                                        accountList.setIp(newAccountRechage.getAddIp());
                                        accountList.setWeb(0);
                                        accountList.setIsBank(1);// 是否是银行的交易记录
                                        accountList.setCheckStatus(1);// 对账状态0：未对账
                                        accountList.setTradeStatus(1);// 成功状态
                                        // 插入交易明细
                                        boolean isAccountListUpdateFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                                        if (!isAccountListUpdateFlag) {
                                            throw new Exception("充值成功后,插入交易明细失败~~~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                                        }
                                    }
                                } else {
                                    throw new Exception("本地记录的充值金额与银行返回的交易金额不一致:本地记录的充值金额:" + newAccountRechage.getMoney() + ",银行返回的充值金额:" + txAmount);
                                }
                            }
                        } else {
                            throw new Exception("根据订单号获取充值记录失败~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                        }
                    } else {
                        // 其它:无该交易或者处理失败
                        // 根据订单号重新获取充值记录
                        AccountRecharge newAccountRechage = this.getAccountRecharge(orderId, userId);
                        if (newAccountRechage != null) {
                            // 充值记录状态:充值中
                            if (newAccountRechage.getStatus() == 1 || newAccountRechage.getStatus() == 0) {
                                // 更新充值订单为:失败
                                newAccountRechage.setStatus(RECHARGE_STATUS_FAIL);
                                newAccountRechage.setUpdateTime(nowTime);
                                boolean isUpdateFlag = this.accountRechargeMapper.updateByPrimaryKeySelective(newAccountRechage) > 0 ? true : false;
                                if (!isUpdateFlag) {
                                    throw new Exception("充值记录表状态更新失败~~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                                }
                            }
                        } else {
                            throw new Exception("根据订单号获取充值记录失败~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                        }
                    }
                } else {
                    // 返回retcode的错误码和result返回其他这两个都是有可能的，具体的是哪个和银行内部的操作有关，所以retcode和result这个你们都需要判断下
                    // 其它:无该交易或者处理失败
                    // 根据订单号重新获取充值记录
                    AccountRecharge newAccountRechage = this.getAccountRecharge(orderId, userId);
                    if (newAccountRechage != null) {
                        // 充值记录状态:充值中
                        if (newAccountRechage.getStatus() == 1 || newAccountRechage.getStatus() == 0) {
                            // 更新充值订单为:失败
                            newAccountRechage.setStatus(RECHARGE_STATUS_FAIL);
                            newAccountRechage.setUpdateTime(nowTime);
                            boolean isUpdateFlag = this.accountRechargeMapper.updateByPrimaryKeySelective(newAccountRechage) > 0 ? true : false;
                            if (!isUpdateFlag) {
                                throw new Exception("充值记录表状态更新失败~~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                            }
                        }
                    } else {
                        throw new Exception("根据订单号获取充值记录失败~~~,充值订单号:" + orderId + ",用户Id:" + userId);
                    }
                }
            } else {
                throw new Exception("调用银行接口,银行返回NULL,充值订单号:" + orderId + ",用户Id:" + userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 根据订单号,用户ID查询用户的充值记录
     *	am-trade/trad/selectByOrderIdAndUserId/{orderId}/{userId}
     * @param orderId
     * @param userId
     * @return
     */
    private AccountRecharge getAccountRecharge(String orderId, Integer userId) {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        cra.andNidEqualTo(orderId);
        cra.andUserIdEqualTo(userId);
        List<AccountRecharge> rechargeList = this.accountRechargeMapper.selectByExample(example);
        if (rechargeList != null && rechargeList.size() == 1) {
            return rechargeList.get(0);
        }
        return null;
    }


    /**
     * 检索充值中的充值记录
     * @return
     */
    private List<AccountRecharge> selectBankRechargeList() {
        AccountRechargeExample example = new AccountRechargeExample();
        AccountRechargeExample.Criteria cra = example.createCriteria();
        List<Integer> status = new ArrayList<Integer>();
        status.add(0);
        status.add(1);
        // add by liubin 江西银行充值掉单异常处理修正 start
        status.add(3);
        // add by liubin 江西银行充值掉单异常处理修正 end
        // 充值状态:0:初始,1:充值中,2:充值成功,3:充值失败
        cra.andStatusIn(status);// 充值状态为充值中和失败的
        cra.andIsBankEqualTo(1);// 充值平台:江西银行
        // 当前时间
        
        cra.andCreateTimeGreaterThanOrEqualTo(GetDate.getTodayBeforeOrAfter(-1));// 一天之前
        // modify by liubin 江西银行充值掉单异常处理修正 start
        cra.andCreateTimeLessThanOrEqualTo(GetDate.getSomeTimeBeforeOrAfterMin(new Date(), -30));// 30分钟之前的充值订单
        // modify by liubin 江西银行充值掉单异常处理修正 end
        return this.accountRechargeMapper.selectByExample(example);
    }

    /**
     * 根据用户Id查询用户的账户信息
     *
     * @param userId
     * @return
     */
    @Override
    public Account getAccount(Integer userId) {
        AccountExample example = new AccountExample();
        AccountExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        List<Account> list = this.accountMapper.selectByExample(example);
        if (list != null && list.size() == 1) {
            return list.get(0);
        }
        return null;
    }


    /**
     * 查询交易明细是否存在
     *
     * @param orderId
     * @param userId
     * @param txDate
     * @param txTime
     * @param seqNo
     * @return
     */
    private boolean countAccountList(String orderId, Integer userId, Integer txDate, Integer txTime, Integer seqNo) {
        AccountListExample example = new AccountListExample();
        AccountListExample.Criteria cra = example.createCriteria();
        cra.andUserIdEqualTo(userId);
        cra.andNidEqualTo(orderId);
        cra.andTxDateEqualTo(txDate);
        cra.andTxTimeEqualTo(txTime);
        cra.andSeqNoEqualTo(String.valueOf(seqNo));
        List<AccountList> accountList = this.accountListMapper.selectByExample(example);
        if (accountList != null && accountList.size() == 1) {
            return true;
        }
        return false;
    }


}
