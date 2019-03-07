/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.withdraw.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.withdraw.UserWithdrawService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetterUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: UserWithdrawServiceImpl, v0.1 2018/8/30 15:34
 */
@Service
public class UserWithdrawServiceImpl extends BaseServiceImpl implements UserWithdrawService {

    // 提现状态:提现中
    private static final int WITHDRAW_STATUS_WAIT = 1;
    // 提现状态:失败
    private static final int WITHDRAW_STATUS_FAIL = 3;
    // 提现状态:成功
    private static final int WITHDRAW_STATUS_SUCCESS = 2;

    /**
     * 用户提现更新数据表
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public int updateBeforeCash(ApiUserWithdrawRequest request) {

        BankCallBeanVO bean = request.getBankCallBeanVO();
        Map<String,String> params = request.getParams();
        BankCardVO bankCard = request.getBankCardVO();

        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? bean.getParamMap().get(ChinaPnrConstant.PARAM_ORDID) : bean.getLogOrderId(); // 订单号
        AccountWithdrawExample accountWithdrawExample = new AccountWithdrawExample();
        accountWithdrawExample.createCriteria().andNidEqualTo(ordId);
        List<AccountWithdraw> accountWithdrawList = this.accountWithdrawMapper.selectByExample(accountWithdrawExample);
        if (accountWithdrawList != null && accountWithdrawList.size() > 0) {
            return ret;
        }
        Date nowTime = GetDate.getNowTime(); // 当前时间
        BigDecimal money = new BigDecimal(bean.getTxAmount()); // 提现金额
        BigDecimal fee = BigDecimal.ZERO; // 取得费率
        if (Validator.isNotNull(params.get("fee"))) {
            fee = new BigDecimal(params.get("fee")); // 取得费率
        }
        BigDecimal total = money.add(fee); // 实际出账金额
        Integer userId = GetterUtil.getInteger(params.get("userId")); // 用户ID
        String cardNo = params.get("cardNo"); // 银行卡号
        String bank = "";

        AccountWithdraw record = new AccountWithdraw();
        // 取得银行信息
        if (bankCard != null) {
            bank = bankCard.getBank();
            record.setBankId(bankCard.getId());
        }
        record.setUserId(userId);
        record.setNid(bean.getLogOrderId()); // 订单号
        record.setStatus(WITHDRAW_STATUS_WAIT); // 状态: 0:处理中
        record.setAccount(cardNo);// 提现银行卡号
        record.setBank(bank); // 提现银行

/*        record.setBranch(null);
        record.setProvince(0);
        record.setCity(0);*/
        record.setTotal(total);
        record.setCredited(money);
        record.setBankFlag(1);
        record.setFee(CustomUtil.formatAmount(fee.toString()));
        record.setCreateTime(nowTime);
        record.setAddIp(params.get("ip"));
        record.setAccountId(bean.getAccountId());
        record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
        record.setTxDate(Integer.parseInt(bean.getTxDate()));
        record.setTxTime(Integer.parseInt(bean.getTxTime()));
        record.setSeqNo(Integer.parseInt(bean.getSeqNo()));
        record.setRemark("第三方提现");
        record.setClient(GetterUtil.getInteger(params.get("client"))); // 0pc
        record.setWithdrawType(0); // 提现类型 0主动提现  1代提现
        // 插入用户提现记录表
        ret += this.accountWithdrawMapper.insertSelective(record);
        return ret;
    }

    /**
     * 根据orderId查询出status=2的账户提现信息
     * @auth sunpeikai
     * @param orderId 订单号
     * @return
     */
    @Override
    public AccountWithdraw getAccountWithdrawByOrderId(String orderId) {
        AccountWithdrawExample example = new AccountWithdrawExample();
        AccountWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andNidEqualTo(orderId).andStatusEqualTo(WITHDRAW_STATUS_SUCCESS);
        List<AccountWithdraw> list = this.accountWithdrawMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 执行提现后处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public String handlerAfterCash(ApiUserWithdrawRequest request) throws Exception {
        logger.info("执行提现后处理,请求参数为：=======================【" + JSONObject.toJSONString(request, true) + "】");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status","");
        jsonObject.put("statusDesc","");
        jsonObject.put("flag",false);
        BankCallBeanVO bean = request.getBankCallBeanVO();
        Map<String,String> params = request.getParams();

        // 用户ID
        int userId = Integer.parseInt(params.get("userId"));
        // 查询账户信息
        Account account = new Account();
        // 根据用户ID查询用户银行卡信息
        String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
        Date nowTime = GetDate.getNowTime(); // 当前时间
        AccountWithdrawExample accountWithdrawExample = new AccountWithdrawExample();
        accountWithdrawExample.createCriteria().andNidEqualTo(ordId);
        List<AccountWithdraw> listAccountWithdraw = this.accountWithdrawMapper.selectByExample(accountWithdrawExample);

        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            AccountWithdraw accountWithdraw = listAccountWithdraw.get(0);
            // 返回值=000成功 ,大额提现返回值为 CE999028
            if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode())) {
                // 如果信息未被处理
                if (accountWithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
                    // 如果是已经提现成功了
                    jsonObject.put("status","0");
                    jsonObject.put("statusDesc","提现成功");
                    return jsonObject.toJSONString();
                } else {
                    // 查询是否已经处理过
                    AccountListExample accountListExample = new AccountListExample();
                    accountListExample.createCriteria().andNidEqualTo(ordId).andTradeEqualTo("cash_success");
                    int accountlistCnt = this.accountListMapper.countByExample(accountListExample);
                    // 未被处理
                    if (accountlistCnt == 0) {
                        // 开启事务
                        TransactionStatus txStatus = null;
                        try {
                            txStatus = this.transactionManager.getTransaction(transactionDefinition);
                            // 提现成功后,更新银行联行号
                            // 大额提现返回成功 并且银联行号不为空的情况,将正确的银联行号更新到bankCard表中
                            // 这里涉及到跨库，所以返回参数，抛给组合层处理
                            jsonObject.put("flag",true);

                            // 提现金额
                            BigDecimal transAmt = BigDecimal.ZERO;
                            String txAmont = bean.getTxAmount();
                            if (Validator.isNotNull(txAmont) && NumberUtils.isCreatable(txAmont)) {
                                transAmt = new BigDecimal(txAmont);
                            }
                            // 从数据库中查询提现手续费
                            String fee = accountWithdraw.getFee();
                            // 提现手续费
                            BigDecimal feeAmt = new BigDecimal(fee);
                            // 总的交易金额
                            BigDecimal total = transAmt.add(feeAmt);
                            // 更新订单信息
                            accountWithdraw.setFee((CustomUtil.formatAmount(feeAmt.toString()))); // 更新手续费
                            accountWithdraw.setCredited(transAmt); // 更新到账金额
                            accountWithdraw.setTotal(total); // 更新到总额
                            accountWithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功
                            accountWithdraw.setUpdateTime(nowTime);
                            accountWithdraw.setAccount(bean.getAccountId());
                            accountWithdraw.setReason("");
                            boolean isAccountwithdrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                            if (!isAccountwithdrawFlag) {
                                throw new Exception("提现后,更新用户提现记录表失败!");
                            }
                            Account newAccount = new Account();
                            // 更新账户信息
                            newAccount.setUserId(userId);// 用户Id
                            newAccount.setBankTotal(total); // 累加到账户总资产
                            newAccount.setBankBalance(total); // 累加可用余额
                            newAccount.setBankBalanceCash(total);// 江西银行可用余额
                            boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankWithdrawSuccess(newAccount) > 0 ? true : false;
                            if (!isAccountUpdateFlag) {
                                throw new Exception("提现后,更新用户Account表失败!");
                            }
                            // 写入收支明细
                            AccountList accountList = new AccountList();
                            // 重新查询用户账户信息
                            account = this.getAccount(userId);
                            accountList.setNid(ordId);
                            accountList.setUserId(userId);
                            accountList.setAmount(total);
                            accountList.setType(2);
                            accountList.setTrade("cash_success");
                            accountList.setTradeCode("balance");
                            accountList.setTotal(account.getTotal());
                            accountList.setBalance(account.getBalance());
                            accountList.setFrost(account.getFrost());
                            accountList.setAwait(account.getAwait());
                            accountList.setRepay(account.getRepay());
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
                            accountList.setSeqNo(bean.getSeqNo());
                            accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
                            accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
                            accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
                            accountList.setAccountId(bean.getAccountId());
                            accountList.setRemark("第三方提现");
                            accountList.setCreateTime(nowTime);
                            //accountList.setBaseUpdate(nowTime);
                            accountList.setOperator(params.get("userId"));
                            accountList.setIp(params.get("ip"));
                            /*accountList.setIsUpdate(0);
                            accountList.setBaseUpdate(0);
                            accountList.setInterest(null);*/
                            accountList.setIsBank(1);
                            accountList.setWeb(0);
                            accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
                            accountList.setTradeStatus(1);// 0失败1成功2失败
                            boolean isAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                            if (!isAccountListFlag) {
                                throw new Exception("提现成功后,插入交易明细表失败~!");
                            }
                            // 提交事务
                            this.transactionManager.commit(txStatus);

                            jsonObject.put("status","0");
                            jsonObject.put("statusDesc","提现成功");
                            return jsonObject.toJSONString();
                        } catch (Exception e) {
                            // 回滚事务
                            transactionManager.rollback(txStatus);
                            logger.error(e.getMessage());
                            jsonObject.put("status","1");
                            jsonObject.put("statusDesc","提现失败,请联系客服");
                            return jsonObject.toJSONString();
                        }
                    }
                }
            } else {
                // 提现失败,更新订单状态
                AccountWithdrawExample example = new AccountWithdrawExample();
                AccountWithdrawExample.Criteria cra = example.createCriteria();
                cra.andNidEqualTo(ordId);
                List<AccountWithdraw> list = this.accountWithdrawMapper.selectByExample(example);
                if (list != null && list.size() > 0) {
                    AccountWithdraw accountwithdraw = list.get(0);
                    if (WITHDRAW_STATUS_WAIT == accountWithdraw.getStatus()) {
                        accountwithdraw.setStatus(WITHDRAW_STATUS_FAIL);
                        accountwithdraw.setUpdateTime(nowTime);
                        // 失败原因
                        String reason = request.getErrorMsg();
                        accountwithdraw.setReason(reason);
                        boolean isUpdateFlag = this.accountWithdrawMapper.updateByExample(accountwithdraw, example) > 0 ? true : false;
                        if (!isUpdateFlag) {
                            throw new Exception("提现失败后,更新提现记录表失败");
                        }
                    }
                }
                jsonObject.put("status","1");
                jsonObject.put("statusDesc",bean.getRetMsg() == null ? "" : bean.getRetMsg());
                return jsonObject.toJSONString();
            }
        }
        return null;
    }

    /**
     * 查询某用户 id 的提现记录，带分页
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public List<AccountWithdraw> searchAccountWithdrawPaginate(ApiUserWithdrawRequest request) {
        AccountWithdrawExample example = new AccountWithdrawExample();
        AccountWithdrawExample.Criteria criteria = example.createCriteria();
        criteria.andBankFlagEqualTo(1);
        if(request.getUserId()!=null && request.getUserId()>0){
            criteria.andUserIdEqualTo(request.getUserId());
        }
        if(request.getStatus()!=null){
            criteria.andStatusEqualTo(request.getStatus());
        }
        if(request.getLimitStart()!=-1 && request.getLimitEnd()!=-1){
            example.setLimitStart(request.getLimitStart());
            example.setLimitEnd(request.getLimitEnd());
        }
        return accountWithdrawMapper.selectByExample(example);
    }
}
