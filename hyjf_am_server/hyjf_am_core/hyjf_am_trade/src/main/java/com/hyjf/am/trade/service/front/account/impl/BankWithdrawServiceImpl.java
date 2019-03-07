package com.hyjf.am.trade.service.front.account.impl;

import com.hyjf.am.bean.result.CheckResult;
import com.hyjf.am.resquest.trade.AfterCashParamRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.service.front.account.BankWithdrawService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 江西银行提现掉单异常处理Service实现类
 * create by jijun 20180614
 */
@Service
public class BankWithdrawServiceImpl extends BaseServiceImpl implements BankWithdrawService {

    private static final Logger logger = LoggerFactory.getLogger(BankWithdrawServiceImpl.class);

    // 提现状态:提现中
    private static final int WITHDRAW_STATUS_DEFAULT = 0;
    // 提现状态:提现中
    private static final int WITHDRAW_STATUS_WAIT = 1;
    // 提现状态:成功
    private static final int WITHDRAW_STATUS_SUCCESS = 2;
    // 提现状态:失败
    private static final int WITHDRAW_STATUS_FAIL = 3;


    /**
     * 检索处理中的充值订单
     * add by jijun 20180615
     * @return
     */
    @Override
    public List<AccountWithdraw> selectBankWithdrawList() {
        AccountWithdrawExample example = new AccountWithdrawExample();
        AccountWithdrawExample.Criteria cra = example.createCriteria();
        List<Integer> status = new ArrayList<Integer>();
        //mod by nixiaoling 状态为0的不查找 20180428
        status.add(0);
        status.add(1);
        //status.add(3);
        cra.andStatusIn(status);// 提现状态为提现中, 审核中（处理中）, 提现失败
        cra.andBankFlagEqualTo(1);// 提现平台:江西银行
        // 当前时间
        Date nowDate = new Date();
        cra.andCreateTimeGreaterThanOrEqualTo(DateUtils.addDays(nowDate,-2));//  T-2天之后的充值订单
        cra.andCreateTimeLessThanOrEqualTo(DateUtils.addMinutes(nowDate,-30));// 30分钟之前的充值订单
        return this.accountWithdrawMapper.selectByExample(example);
    }

    /**
     * 更新账户信息
     * add by jijun 20180616
     */
	@Override
	public int updateBankWithdraw(AccountVO accountVO) {
		return this.adminAccountCustomizeMapper.updateBankWithdrawSuccess(CommonUtils.convertBean(accountVO, Account.class));
	}


    /**
     * 用户提现回调方法
     * @param para
     * @return
     */
    @Override
    public void updateHandlerAfterCash(AfterCashParamRequest request) throws Exception{

        BankCallBeanVO bean= request.getBankCallBeanVO();
        AccountWithdrawVO accountWithdraw = request.getAccountWithdrawVO();
        String fee = request.getWithdrawFee();

        String ordId = accountWithdraw.getNid();
        int userId = accountWithdraw.getUserId();
        // 当前时间
        int nowTime = GetDate.getNowTime10();

        Date nowDate = new Date();
        if((BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode()))
                && "00".equals(bean.getResult())
                && !"1".equals(bean.getOrFlag())) {
                CheckResult rtCheck = checkCallRetAndHyjf(bean,accountWithdraw);
                if (!rtCheck.isResultBool()) {
                    // 验证失败，异常信息抛出
                    throw new Exception(rtCheck.getResultMsg());
                }

                //3.DB防并发处理
                rtCheck = checkConcurrencyDB(accountWithdraw, userId, ordId);
                if (!rtCheck.isResultBool()) {
                    // 记录被其他进程处理，日志信息输出
                    logger.info(this.getClass().getName(), "handlerAfterCash", rtCheck.getResultMsg());
                    throw new Exception(rtCheck.getResultMsg());
                }

                //4.DB更新操作
                // 提现金额
                BigDecimal transAmt = new BigDecimal(bean.getTxAmount());
                // 提现手续费
                BigDecimal feeAmt = new BigDecimal(fee);
                // 总的交易金额
                BigDecimal total = transAmt.add(feeAmt);
                // 更新订单信息
                accountWithdraw.setFee((CustomUtil.formatAmount(feeAmt.toString()))); // 更新手续费
                accountWithdraw.setCredited(transAmt); // 更新到账金额
                accountWithdraw.setTotal(total); // 更新到总额
                accountWithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功

                accountWithdraw.setUpdateTime(nowDate);
                accountWithdraw.setAccount(bean.getAccountId());
                accountWithdraw.setReason("");

                boolean isAccountwithdrawFlag = this.accountWithdrawMapper.updateByPrimaryKeySelective(CommonUtils.convertBean(accountWithdraw,AccountWithdraw.class)) > 0 ? true : false;
                if (!isAccountwithdrawFlag) {
                    throw new Exception("提现后,更新用户提现记录表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
                }
                Account newAccount = new Account();
                // 更新账户信息
                newAccount.setUserId(userId);// 用户Id
                newAccount.setBankTotal(total); // 累加到账户总资产
                newAccount.setBankBalance(total); // 累加可用余额
                newAccount.setBankBalanceCash(total);// 江西银行可用余额
                boolean isAccountUpdateFlag = this.adminAccountCustomizeMapper.updateBankWithdrawSuccess(newAccount) > 0 ? true : false;
                if (!isAccountUpdateFlag) {
                    throw new Exception("提现后,更新用户Account表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
                }
                // 重新获取用户信息
                Account account = this.getAccount(userId);
                // 写入收支明细
                AccountList accountList = new AccountList();
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
                accountList.setRemark(accountWithdraw.getRemark());
                accountList.setCreateTime(GetDate.getDate(nowTime));
                accountList.setOperator(String.valueOf(userId));
                accountList.setIp(accountWithdraw.getAddIp());
                accountList.setIsBank(1);
                accountList.setWeb(0);
                accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
                accountList.setTradeStatus(1);// 0失败1成功2失败
                boolean isAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                if (!isAccountListFlag) {
                    throw new Exception("提现成功后,插入交易明细表失败~!" + "提现订单号:" + ordId + ",用户ID:" + userId);
                }
            }else {
                // 提现失败,更新处理中订单状态为失败
                AccountWithdrawExample example = new AccountWithdrawExample();
                AccountWithdrawExample.Criteria cra = example.createCriteria();
                cra.andNidEqualTo(ordId);
                List<AccountWithdraw> list = this.accountWithdrawMapper.selectByExample(example);
                if (list != null && list.size() > 0) {
                    AccountWithdraw accountwithdraw = list.get(0);
                    if (WITHDRAW_STATUS_DEFAULT == accountWithdraw.getStatus()
                            || WITHDRAW_STATUS_WAIT == accountWithdraw.getStatus()) {
                        accountwithdraw.setStatus(WITHDRAW_STATUS_FAIL);// 提现失败
                        accountwithdraw.setUpdateTime(GetDate.getDate(nowTime));// 更新时间
                        accountwithdraw.setReason(bean.getRetMsg());// 失败原因

                        //冲正撤销标志为1：已冲正/撤销时
                        //临时按照失败处理
                        if ("1".equals(bean.getOrFlag())) {
                            accountwithdraw.setReason("提现订单："+ bean.getOrFlag() + "：已冲正/撤销");
                        }

                        boolean isUpdateFlag = this.accountWithdrawMapper.updateByExample(accountwithdraw, example) > 0 ? true : false;
                        if (!isUpdateFlag) {
                            throw new Exception("提现失败后,更新提现记录表失败" + "提现订单号:" + ordId + ",用户ID:" + userId);
                        }
                    }
                }
            }
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
     * DB防并发处理
     * @param accountWithdraw
     * @param userId
     * @param ordId
     * @return
     */
    private CheckResult checkConcurrencyDB(AccountWithdrawVO accountWithdraw, int userId, String ordId){
        CheckResult result = new CheckResult();

        Boolean resultBool = true;
        String resultMsg = null;
        String msg = "此笔充提现状态已发生变化,提现订单号:" + ordId + ",用户Id:" + userId;

        //匹配验证
        // 如果信息已被处理
        if (accountWithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
            resultBool = false;
            resultMsg = msg;
        }
        // 查询是否已经处理过
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.createCriteria().andNidEqualTo(ordId).andTradeEqualTo("cash_success");
        int accountlistCnt = this.accountListMapper.countByExample(accountListExample);
        // 如果信息已被处理
        if (accountlistCnt != 0) {
            resultBool = false;
            resultMsg = msg;
        }

        //匹配结果
        result.setResultBool(resultBool);
        result.setResultMsg(resultMsg);
        return result;
    }


    /**
     * 银行接口返回与平台记录匹配验证
     * @param bean
     * @param accountWithdraw
     * @return
     */
    private CheckResult checkCallRetAndHyjf(BankCallBeanVO bean, AccountWithdrawVO accountWithdraw) {
        CheckResult result = new CheckResult();

        Boolean resultBool = true;
        String resultMsg = null;

        //匹配验证
        //提现金额
        BigDecimal txAmount = new BigDecimal(bean.getTxAmount());
        if (txAmount.compareTo(accountWithdraw.getCredited())!=0) {
            resultBool = false;
            resultMsg = "本地记录的提现金额与银行返回的交易金额不一致:本地记录的提现金额:" + accountWithdraw.getTotal() + ",银行返回的提现金额:" + txAmount;
        }

        //匹配结果
        result.setResultBool(resultBool);
        result.setResultMsg(resultMsg);
        return result;
    }


}
