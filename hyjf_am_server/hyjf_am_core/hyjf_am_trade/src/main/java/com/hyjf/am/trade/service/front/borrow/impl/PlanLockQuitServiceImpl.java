/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.borrow.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.CommisionComputeService;
import com.hyjf.am.trade.service.front.borrow.PlanLockQuitService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.HjhLockVo;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author wangjun
 * @version PlanLockQuitServiceImpl, v0.1 2018/7/17 16:24
 */
@Service
public class PlanLockQuitServiceImpl extends BaseServiceImpl implements PlanLockQuitService {
    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private CommisionComputeService commisionComputeService;
    /**
     * 用户ID
     */
    private static final String VAL_USER_ID = "userId";
    /**
     * 汇计划名称
     */
    private static final String VAL_HJH_TITLE = "val_hjh_title";
    /**
     * 预期收益
     */
    private static final String VAL_INTEREST = "val_interest";
    /**
     * 预期退出时间
     */
    private static final String VAL_DATE = "val_date";
    /**
     * 用户名
     */
    private static final String VAL_NAME = "val_name";
    /**
     * 性别
     */
    private static final String VAL_SEX = "val_sex";
    /**
     * 放款金额
     */
    private static final String VAL_AMOUNT = "val_amount";

    @Override
    public void updateQuitRepayInfo(String accedeOrderId) {
        HjhAccedeExample accedeExample = new HjhAccedeExample();
        accedeExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
        boolean isLastBorrow = true;
        HjhAccede hjhAccede = null;
        if (accedeList != null && accedeList.size() > 0) {
            hjhAccede = accedeList.get(0);
        } else {
            logger.info("-----------订单号" + accedeOrderId + ",未查询到匹配加入订单!");
            return;
        }
        //重新获取是否清算完成标识 汇计划三期
        Integer completeFlag = hjhAccede.getCreditCompleteFlag();
        if(1 != completeFlag){
            logger.info("-----------订单号" + accedeOrderId + ",未清算完成，暂不退出计划!");
            return;
        }
        boolean isQuit = checkIsRepayQuit(hjhAccede);
        if (!isQuit) {
            logger.info("-----------订单号" + accedeOrderId + ",不符合退出条件,暂不退出计划!");
            return;
        }
        //根据计划订单号判断是否为计划最后一次放款
        BorrowTenderExample example = new BorrowTenderExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<BorrowTender> tenderList = this.borrowTenderMapper.selectByExample(example);
        //根据计划订单号获得所有出借标的并且出借总额 = 计划加入总额
        //判断是否除本标的外还存在复审中的标的,如果没有则确定是最后一笔放款
        List<String> borrowList = new ArrayList<>();
        if (tenderList != null && tenderList.size() > 0) {
            for (int i = 0; i < tenderList.size(); i++) {
                BorrowTender borrowTender = tenderList.get(i);
                borrowList.add(borrowTender.getBorrowNid());
            }
        }
        //判断原始出借是否还款或被清算
        for (int i = 0; i < borrowList.size(); i++) {
            String borrowNid = borrowList.get(i);
            BorrowExample borrowExample = new BorrowExample();
            borrowExample.createCriteria().andBorrowNidEqualTo(borrowNid);
            List<Borrow> borrowLists = this.borrowMapper.selectByExample(borrowExample);
            if (borrowLists != null && borrowLists.size() > 0) {
                Borrow borrow = borrowLists.get(0);
                //获得标的状态
                Integer status = borrow.getStatus();
                //存在其他标的未还款
                if (status < 5) {
                    //判断剩余待清算金额是否全部债转
                    boolean result = isDebtCredite(borrowNid, accedeOrderId);
                    if (!result) {
                        isLastBorrow = false;
                    }

                }
            }
        }
        //判断订单内债权是否被完全承接
        boolean isEnd = isDebtEnd(accedeOrderId);
        if (!isEnd) {
            isLastBorrow = false;
        }
        //最后一次还款,退出计划
        if (isLastBorrow) {
            //退出计划更新还款信息表
            HjhRepay hjhRepay = updateHjhLastRepayInfo(hjhAccede);
            //计划相关金额更新
            updatePlanData(hjhAccede, hjhRepay);
            //更新计划账户金额
            updatePlanTenderAccount(hjhAccede, hjhRepay);
            // 重新获取hjhRepay
            hjhRepay = this.hjhRepayMapper.selectByPrimaryKey(hjhRepay.getId());
            //发送计划还款短信
            sendSms(hjhRepay);
            //发送计划退出通知
            sendMessage(hjhRepay);
            try {
                couponRepay(hjhAccede);
            } catch (Exception e) {
                logger.info("============优惠券还款请求失败：",e);
            }
        }
    }

    /**
     * 订单内债权是否全部退出
     *
     * @param accedeOrderId
     * @return
     */
    private boolean isDebtEnd(String accedeOrderId) {
        HjhDebtDetailExample example = new HjhDebtDetailExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId);
        List<HjhDebtDetail> detailList = this.hjhDebtDetailMapper.selectByExample(example);
        if (detailList != null && detailList.size() > 0) {
            for (int i = 0; i < detailList.size(); i++) {
                Integer delFlag = detailList.get(i).getDelFlag();
                String orderId = detailList.get(i).getOrderId();
                //存在未清算的债权
                if (0 == delFlag) {
                    logger.info("--------------订单号:" + accedeOrderId + ",存在未清算的债权,债权订单号:" + orderId);
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 判断剩余待清算金额是否全部债转
     *
     * @param borrowNid
     * @param accedeOrderId
     * @return
     */
    private boolean isDebtCredite(String borrowNid, String accedeOrderId) {
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId).andBorrowNidEqualTo(borrowNid).andCreditStatusNotEqualTo(3);
        List<HjhDebtCredit> debtCreditList = this.hjhDebtCreditMapper.selectByExample(example);
        if (debtCreditList != null && debtCreditList.size() > 0) {
            HjhDebtCredit hjhDebtCredit = debtCreditList.get(0);
            Integer creditStatus = hjhDebtCredit.getCreditStatus();
            //全部承接
            if (2 == creditStatus) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验计划订单是否符合退出计划的条件
     *
     * @param hjhAccede
     * @return
     */
    private boolean checkIsRepayQuit(HjhAccede hjhAccede) {
        //判断是否到清算时间
        Date endDate = hjhAccede.getEndDate();
        long endTime = endDate.getTime();
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        Date nowDate = new Date();
        long nowTime = nowDate.getTime();
        if (nowTime < endTime) {
            logger.info("-------------订单号:" + accedeOrderId + "未到清算时间!");
            return false;
        }

        //判断是否存在债转未承接的项目
        HjhDebtCreditExample example = new HjhDebtCreditExample();
        example.createCriteria().andPlanOrderIdEqualTo(accedeOrderId);
        List<HjhDebtCredit> debtCreditList = this.hjhDebtCreditMapper.selectByExample(example);
        if (debtCreditList != null && debtCreditList.size() > 0) {
            for (int i = 0; i < debtCreditList.size(); i++) {
                HjhDebtCredit debtCredit = debtCreditList.get(i);
                String borrowNid = debtCredit.getBorrowNid();
                boolean flag = isBorrowRepay(borrowNid);
                if (flag) {
                    return false;
                }
                Integer creditStatus = debtCredit.getCreditStatus();
                //存在债转未被全部承接
                if (1 == creditStatus || 0 == creditStatus) {
                    logger.info("-------------订单号:" + accedeOrderId + ",存在未被全部承接的债转!,债转编号:" + debtCredit.getCreditNid());
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 退出计划更新还款信息表
     *
     * @param hjhAccede
     */
    private HjhRepay updateHjhLastRepayInfo(HjhAccede hjhAccede) {
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        HjhRepayExample example = new HjhRepayExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhRepay> repayList = this.hjhRepayMapper.selectByExample(example);
        if (repayList != null && repayList.size() > 0) {
            HjhRepay hjhRepay = repayList.get(0);
            //已回款
            hjhRepay.setRepayStatus(2);
            //已退出
            hjhRepay.setOrderStatus(7);
            hjhRepay.setRepayActualTime(GetDate.getNowTime10());
            hjhRepay.setUpdateTime(new Date());
            hjhRepay.setWaitTotal(BigDecimal.ZERO);
            int count = this.hjhRepayMapper.updateByPrimaryKey(hjhRepay);
            if (count > 0) {
                logger.info("============= 汇计划退出还款信息更新成功!,加入订单号:" + accedeOrderId + ",还款信息总还款金额:" + hjhRepay.getRepayTotal());
            }
            return hjhRepay;
        }
        return null;
    }

    /**
     * 计划相关金额更新
     *
     * @param hjhAccede
     * @param hjhRepay
     */
    private void updatePlanData(HjhAccede hjhAccede, HjhRepay hjhRepay) {
        //计划加入待收总额
        BigDecimal waitTotal = hjhAccede.getShouldPayTotal();
        //计划加入待收本金
        BigDecimal waitCaptical = hjhAccede.getWaitCaptical();
        //计划加入待收利息
        BigDecimal waitInterest = hjhAccede.getWaitInterest();
        //计划实收金额
        BigDecimal repayTotal = hjhRepay.getRepayTotal();
        BigDecimal repayCapital = hjhRepay.getPlanRepayCapital();
        BigDecimal repayInterest = hjhRepay.getPlanRepayInterest();
        String planNid = hjhAccede.getPlanNid();
        HjhPlan hjhPlan = new HjhPlan();
        hjhPlan.setPlanNid(planNid);
        hjhPlan.setRepayWaitAll(waitTotal);
        hjhPlan.setPlanWaitCaptical(waitCaptical);
        hjhPlan.setPlanWaitInterest(waitInterest);
        hjhPlan.setRepayTotal(repayTotal);
        hjhPlan.setPlanRepayCapital(repayCapital);
        hjhPlan.setPlanRepayInterest(repayInterest);
        int count = this.adminAccountCustomizeMapper.updateHjhPlanForQuit(hjhPlan);
        if (count > 0) {
            logger.info("===============计划加入订单:" + hjhAccede.getAccedeOrderId() + "对应的计划:" + planNid + "相关待还应收金额维护成功!待还减少:" + waitTotal + ",应收增加:" + repayTotal);
        }
    }

    /**
     * 变更计划出借账户金额
     *
     * @param hjhAccede
     * @param hjhRepay
     */
    private void updatePlanTenderAccount(HjhAccede hjhAccede, HjhRepay hjhRepay) {
        BigDecimal repayTotal = hjhRepay.getRepayTotal();
        //计划加入待收总额
        BigDecimal waitTotal = hjhAccede.getWaitTotal();
        //计划加入待收本金
        BigDecimal waitCaptical = hjhAccede.getWaitCaptical();
        //计划加入待收利息
        BigDecimal waitInterest = hjhAccede.getWaitInterest();
        //账户实际收到冻结金额
        BigDecimal accountForst = hjhAccede.getFrostAccount();
        //剩余订单可用金额
        BigDecimal availableInvestAccount = hjhAccede.getAvailableInvestAccount();
        //剩余订单可用金额也放到账户金额中
        accountForst = accountForst.add(availableInvestAccount);
        //计算账户订单利息
        BigDecimal accountInterest = accountForst.subtract(hjhAccede.getAccedeAccount());

        //实际年化收益率
        BigDecimal reallyApr = getAccedeReallyApr(accountForst,hjhAccede.getAccedeAccount(),hjhAccede.getPlanNid(),hjhAccede.getLockPeriod(),hjhAccede.getAccedeOrderId());
        BigDecimal lqdServiceFee = hjhAccede.getLqdServiceFee();//清算服务费
        //获得出借服务费率
        BigDecimal tenderFeeRate = getTenderFeeRate(lqdServiceFee,hjhAccede.getAccedeAccount());
        //计划加入明细金额变更
        hjhAccede.setActualApr(reallyApr);
        //计划加入明细金额变更
        hjhAccede.setWaitTotal(new BigDecimal(0));
        hjhAccede.setWaitCaptical(new BigDecimal(0));
        hjhAccede.setWaitInterest(new BigDecimal(0));
        //已退出
        hjhAccede.setOrderStatus(7);
        //已收总额
        hjhAccede.setReceivedTotal(accountForst);
        //已收利息
        hjhAccede.setReceivedInterest(accountInterest);
        //已收本金
        hjhAccede.setReceivedCapital(hjhAccede.getAccedeAccount());
        //实际回款时间
        hjhAccede.setAcctualPaymentTime(GetDate.getNowTime10());
        //计划订单冻结金额更新
        hjhAccede.setFrostAccount(BigDecimal.ZERO);
        //计划订单可用金额
        hjhAccede.setAvailableInvestAccount(BigDecimal.ZERO);
        hjhAccede.setFairValue(accountForst);//订单退出时重新计算公允价值
        hjhAccede.setInvestServiceApr(tenderFeeRate);//更新出借服务费率
        hjhAccede.setLqdProgress(BigDecimal.ONE);//已退出时更新清算进度为 100%
        int count = this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
        if (count > 0) {
            logger.info("================ 计划退出更新计划加入明细成功!计划加入订单号: " + hjhAccede.getAccedeOrderId());
        }

        HjhRepay newRepay = hjhRepayMapper.selectByPrimaryKey(hjhRepay.getId());
        newRepay.setRepayTotal(accountForst);
        newRepay.setRepayAlready(accountForst);
        newRepay.setActualRevenue(accountInterest);//实际收益
        newRepay.setActualPayTotal(accountForst);//实际回款总额
        newRepay.setPlanRepayCapital(hjhAccede.getAccedeAccount());
        newRepay.setPlanRepayInterest(accountInterest);
        this.hjhRepayMapper.updateByPrimaryKey(newRepay);
        //账户金额变更
        Integer userId = hjhAccede.getUserId();
        Account Account = this.getAccountByUserId(userId);
        AccountExample example = new AccountExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<Account> accountLists = this.accountMapper.selectByExample(example);
        if (accountLists != null && accountLists.size() > 0) {
            Account account = accountLists.get(0);
            //冻结金额 - 已还总额
            BigDecimal total = accountForst.subtract(waitTotal);
            repayTotal = accountForst;
            //明细数据取值
            BigDecimal detailTotal = account.getBankTotal().add(total);
            BigDecimal detailBalance = account.getBankBalance().add(repayTotal);
            BigDecimal detailPlanBalance = account.getPlanBalance().subtract(availableInvestAccount);
            BigDecimal detailPlanFrost = account.getPlanFrost().subtract(repayTotal.subtract(availableInvestAccount));
            account.setBankTotal(total);
            account.setBankBalance(repayTotal);
            account.setPlanAccountWait(waitTotal);
            account.setPlanCapitalWait(waitCaptical);
            account.setPlanInterestWait(waitInterest);
            account.setBankInterestSum(repayTotal.subtract(waitCaptical));
            account.setPlanFrost(repayTotal.subtract(availableInvestAccount));
            account.setPlanBalance(availableInvestAccount);
            boolean investaccountFlag = this.adminAccountCustomizeMapper.updateOfPlanRepayAccount(account) > 0 ? true : false;
            if (investaccountFlag) {
                logger.info("==================== 计划退出更新账户资金成功,计划加入订单号: " + hjhAccede.getAccedeOrderId());
            }
            AccountList accountList = new AccountList();
            // 出借人银行相关
            accountList.setBankAwait(account.getBankAwait());
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());
            accountList.setBankBalance(detailBalance);
            accountList.setBankFrost(account.getBankFrost());
            accountList.setBankInterestSum(account.getBankInterestSum());
            accountList.setBankInvestSum(account.getBankInvestSum());
            accountList.setBankTotal(detailTotal);
            accountList.setBankWaitCapital(account.getBankWaitCapital());
            accountList.setBankWaitInterest(account.getBankWaitInterest());
            accountList.setBankWaitRepay(account.getBankWaitRepay());
            accountList.setAccountId(Account.getAccountId());
            accountList.setCheckStatus(1);
            accountList.setTradeStatus(1);
            accountList.setIsBank(1);
            accountList.setTxDate(getTxDate());
            accountList.setTxTime(getTxTime());
            accountList.setAccedeOrderId(hjhAccede.getAccedeOrderId());
            //出借人非银行相关
            // 出借人
            accountList.setUserId(userId);
            // 出借本金
            accountList.setAmount(repayTotal);
            // 1收入
            accountList.setType(1);
            // 退出计划
            accountList.setTrade("hjh_quit");
            // 余额操作
            accountList.setTradeCode("balance");
            // 出借人资金总额
            accountList.setTotal(account.getTotal());
            // 出借人银行可用金额
            accountList.setBalance(BigDecimal.ZERO);
            // 出借人冻结金额
            accountList.setFrost(account.getFrost());
            // 出借人待收金额
            accountList.setAwait(account.getAwait());
            // 操作者
            accountList.setOperator(CustomConstants.OPERATOR_AUTO_LOANS);
            accountList.setRemark("计划结束");
            accountList.setPlanBalance(detailPlanBalance);
            accountList.setPlanFrost(detailPlanFrost);
            // PC
            accountList.setWeb(0);
            int insertCount = this.accountListMapper.insertSelective(accountList);
            if (insertCount > 0) {
                logger.info("=================== 插入汇计划加入明细成功!加入订单号:" + hjhAccede.getAccedeOrderId());
            }

        }
        // 退出计划累加统计数据
        logger.info("退出计划累加统计数据...");
        JSONObject params1 = new JSONObject();
        params1.put("interestSum", accountInterest);
        try {
            commonProducer
                    .messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC,
                            MQConstant.STATISTICS_CALCULATE_INTEREST_SUM_TAG, UUID.randomUUID().toString(),
                            params1));
        } catch (MQException e) {
            logger.error("退出计划累加统计数", e);
        }
        // 更新运营数据计划收益
        logger.info("退出计划更新运营数据计划收益...");
        JSONObject params = new JSONObject();
        // 计划收益
        params.put("type", 2);
        params.put("recoverInterestAmount", accountInterest);
        //运营数据队列
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), params));
        }catch (MQException e){
            logger.error("=================发送运营数据更新MQ失败,出借订单号:" + hjhAccede.getAccedeOrderId());
        }
    }



    /**
     * 获得出借服务费率
     *
     * @param lqdServiceFee
     * 清算服务费
     * @param accedeAccount
     * 订单加入金额
     * @return
     */
    private BigDecimal getTenderFeeRate(BigDecimal lqdServiceFee, BigDecimal accedeAccount) {
        if(lqdServiceFee.compareTo(BigDecimal.ZERO) > 0){
            BigDecimal lqdRate = lqdServiceFee.divide(accedeAccount, 4, BigDecimal.ROUND_DOWN);
            return lqdRate;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 获得订单的实际年化收益率
     * @param accountForst
     * @param accedeAccount
     * @param planNid
     * @param lockPeriod
     * @param accedeOrderId
     * @return
     */
    private BigDecimal getAccedeReallyApr(BigDecimal accountForst, BigDecimal accedeAccount, String planNid, Integer lockPeriod, String accedeOrderId) {
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> hjhPlans = this.hjhPlanMapper.selectByExample(example);
        BigDecimal reallyApr = new BigDecimal(0);
        if(hjhPlans != null && hjhPlans.size() == 1){
            HjhPlan hjhPlan = hjhPlans.get(0);
            Integer isMonth = hjhPlan.getIsMonth();
            if(1 == isMonth){//按月计息
                reallyApr = (accountForst.subtract(accedeAccount)).divide(accedeAccount,8,BigDecimal.ROUND_DOWN)
                        .divide(new BigDecimal(lockPeriod),8,BigDecimal.ROUND_DOWN)
                        .multiply(new BigDecimal(12)).multiply(new BigDecimal(100));
            }else if(0 == isMonth){//按日计息
                reallyApr = (accountForst.subtract(accedeAccount)).divide(accedeAccount,8,BigDecimal.ROUND_DOWN)
                        .divide(new BigDecimal(lockPeriod),8,BigDecimal.ROUND_DOWN)
                        .multiply(new BigDecimal(360)).multiply(new BigDecimal(100));
            }
        }
        reallyApr = reallyApr.setScale(4,BigDecimal.ROUND_DOWN);
        logger.info("--------------------开始计算订单实际年化收益率，订单号:" + accedeOrderId + ",accountForst：" + accountForst
                + ",accedeAccount:" + accedeAccount + ",lockperiod:" + lockPeriod + ",实际年化收益率：" + reallyApr.toString());
        return reallyApr;
    }

    /**
     * 发送短信(计划还款成功)
     *
     * @param hjhRepay
     */
    private void sendSms(HjhRepay hjhRepay) {
        int userId = hjhRepay.getUserId();
        String planNid = hjhRepay.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        HjhPlan hjhPlan = planList.get(0);
        String planName = hjhPlan.getPlanName();
        String userName = hjhRepay.getUserName();
        BigDecimal repayTotal = hjhRepay.getRepayTotal();
        Map<String, String> msg = new HashMap<String, String>();

        msg.put(VAL_NAME, userName);
        msg.put(VAL_AMOUNT, repayTotal.toString());
        msg.put(VAL_USER_ID, String.valueOf(userId));
        msg.put(VAL_HJH_TITLE, planName);

        logger.info("userId=" + msg.get(VAL_USER_ID) + ";开始发送短信,待收金额" + msg.get(VAL_AMOUNT));
        SmsMessage smsMessage = null;
        smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USER_ID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_REPAY_HJH_SUCCESS,
                CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                    smsMessage));
        } catch (MQException e) {
            logger.error("计划还款成功发送短信通知失败...", e);
        }
    }

    /**
     * 计划退出推送消息
     *
     * @param hjhRepay
     * @author Administrator
     */
    private void sendMessage(HjhRepay hjhRepay) {
        int userId = hjhRepay.getUserId();
        BigDecimal amount = hjhRepay.getRepayTotal();
        String planNid = hjhRepay.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        HjhPlan hjhPlan = planList.get(0);
        String planName = hjhPlan.getPlanName();
        Map<String, String> msg = new HashMap<String, String>();
        // 待收金额
        msg.put(VAL_AMOUNT, amount.toString());
        msg.put(VAL_USER_ID, String.valueOf(userId));
        msg.put(VAL_HJH_TITLE, planName);

        if (Validator.isNotNull(msg.get(VAL_USER_ID)) && Validator.isNotNull(msg.get(VAL_AMOUNT)) && new BigDecimal(msg.get(VAL_AMOUNT)).compareTo(BigDecimal.ZERO) > 0) {
            RUser users = rUserMapper.selectByPrimaryKey(Integer.valueOf(msg.get(VAL_USER_ID)));
            if (users == null) {
                return;
            } else {
                if (StringUtils.isEmpty(users.getTruename())) {
                    msg.put(VAL_NAME, users.getUsername());
                } else if (users.getTruename().length() > 1) {
                    msg.put(VAL_NAME, users.getTruename().substring(0, 1));
                } else {
                    msg.put(VAL_NAME, users.getTruename());
                }
                AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USER_ID)), msg, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_PLAN_REPAY_SUCCESS);
                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(),
                            smsMessage));
                } catch (MQException e) {
                    logger.error("计划退出推送消息通知失败...", e);
                }
            }
        }
    }

    private Account getAccountByUserId(Integer userId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return new Account();
    }

    private int getTxDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        //调整相应格式 yyyy-年 MM-月  dd-日 HH-时 mm-分 ss-秒
        format.applyPattern("yyyyMMdd");
        String day = format.format(date);
        return Integer.parseInt(day);
    }

    private int getTxTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        //调整相应格式 yyyy-年 MM-月  dd-日 HH-时 mm-分 ss-秒
        format.applyPattern("HHmmss");
        String day = format.format(date);
        return Integer.parseInt(day);
    }


    /**
     * 判断标的是否还款
     *
     * @param borrowNid
     * @return
     */
    private boolean isBorrowRepay(String borrowNid) {

        BorrowApicronExample example = new BorrowApicronExample();
        example.createCriteria().andBorrowNidEqualTo(borrowNid).andApiTypeEqualTo(1).andStatusNotEqualTo(6);
        List<BorrowApicron> list = this.borrowApicronMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void updateLockRepayInfo(HjhLockVo hjhLockVo) {
        String accedeOrderId = hjhLockVo.getAccedeOrderId();
        //判断是否为计划最后一次复审
        //根据计划订单号判断是否为计划最后一次放款
        BorrowTenderExample example = new BorrowTenderExample();
        // 汇计划二期 复投的标的不进行进入锁定期操作
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId).andTenderTypeEqualTo(0);
        List<BorrowTender> tenderList = this.borrowTenderMapper.selectByExample(example);
        //根据计划订单号获得所有出借标的并且出借总额 = 计划加入总额
        //判断是否除本标的外还存在复审中的标的,如果没有则确定是最后一笔放款
        List<String> borrowList = new ArrayList<>();
        if (tenderList != null && tenderList.size() > 0) {
            for (int i = 0; i < tenderList.size(); i++) {
                borrowList.add(tenderList.get(i).getBorrowNid());
            }
        }
        HjhAccedeExample accedeExample = new HjhAccedeExample();
        accedeExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(accedeExample);
        boolean isLastBorrow = true;
        HjhAccede hjhAccede = null;
        //汇计划最小出借额
        BigDecimal tenderMin = CustomConstants.HJH_RETENDER_MIN_ACCOUNT;
        if (accedeList != null && accedeList.size() > 0) {
            hjhAccede = accedeList.get(0);
            logger.info("============== 订单号:" + hjhAccede.getAccedeOrderId() + "的计划金额为:" + hjhAccede.getAccedeAccount());
            logger.info("============== 订单号:" + hjhAccede.getAccedeOrderId() + "的已投金额为:" + hjhAccede.getAlreadyInvest());
            //计划未投满
            if (hjhAccede.getAccedeAccount().compareTo(hjhAccede.getAlreadyInvest()) > 0) {
                isLastBorrow = false;
                logger.info("================= 订单号:" + hjhAccede.getAccedeOrderId() + "计划未投满!");
                // 获得计划可用金额 如果计划可用金额小于一个阀值的话,通过可用余额+已投金额如果 = 加入金额的话则计划加入金额用光,可以进入计划锁定
                //订单可用金额
                BigDecimal availableInvestAccount = hjhAccede.getAvailableInvestAccount();
                if (availableInvestAccount.compareTo(tenderMin) < 0) {
                    if (availableInvestAccount.add(hjhAccede.getAlreadyInvest()).compareTo(hjhAccede.getAccedeAccount()) == 0) {
                        isLastBorrow = true;
                        logger.info("================= 订单号:" + hjhAccede.getAccedeOrderId() + "可用金额小于阀值,可以准备进入锁定期!");
                    }
                }
            }
            for (int i = 0; i < borrowList.size(); i++) {
                String borrowNid = borrowList.get(i);
                Borrow borrow = getBorrowByNid(borrowNid);
                Integer status = borrow.getStatus();
                if (4 > status) {
                    isLastBorrow = false;
                    logger.info("================= 订单号:" + hjhAccede.getAccedeOrderId() + "出借标的未放款，标的号："+ borrowNid );
                }
            }
        }
        //是最后一次放款复审,订单进入锁定期
        if (isLastBorrow) {
            logger.info("-----------订单:" + hjhAccede.getAccedeOrderId() + ",进入锁定期!");
            // 更新预期收益
            updateHjhAccedeInterest(hjhAccede);
            //获得变更数据后的订单信息
            hjhAccede = getNewAccede(hjhAccede);
            //更新加入明细表的相关时间
            updateAccedeDate(hjhAccede);
            //更新还款信息表
            updateHjhPlanRepayInfo(hjhAccede);
            //推送消息
            loanSendMessage(hjhAccede);
            //汇计划二期修改清算时间
            updateEndDate(hjhAccede);
            // 更新资金明细   Account+AccountList
            updateUserAccount(hjhAccede);
            //推送消息
            sendHjhLockMessage(hjhAccede);
            //发送短信
            sendSms(hjhAccede);
            try {
                //开始计算提成 add by cwyang 2018-5-24 汇计划3期由batch工程挪至此处处理
                commisionCompute(hjhAccede,hjhLockVo);
            }catch (Exception e){
                logger.error("=================提成发放失败,计划加入订单号:" + hjhAccede.getAccedeOrderId(), e);
            }
            try {
                //生成并签署加入计划出借服务协议
                sendPlanContract(hjhAccede.getUserId(), hjhAccede.getAccedeOrderId(), hjhAccede.getQuitTime(), hjhAccede.getCountInterestTime(), hjhAccede.getWaitTotal());
                //优惠券放款
                couponLoan(hjhAccede);
                // 双十二活动删除
                //actBalloonTender(hjhAccede);
            } catch (Exception e) {
                logger.error("=================优惠券放款失败,出借订单号:" + hjhAccede.getAccedeOrderId(), e);
            }
        }
    }

    /**
     * 开始计算订单提成
     * @param hjhAccede
     */
    private void commisionCompute(HjhAccede hjhAccede,HjhLockVo hjhLockVo) {
        logger.info("----------开始生成计划订单的提成计算，订单号：" + hjhAccede.getAccedeOrderId());
        commisionComputeService.commisionCompute(hjhAccede,hjhLockVo);
        logger.info("----------生成计划订单的提成计算完毕，订单号：" + hjhAccede.getAccedeOrderId());

    }

    /**
     * 计算预期收益
     *
     * @param hjhAccede
     * @return
     */
    private BigDecimal updateHjhAccedeInterest(HjhAccede hjhAccede) {
        // 计算需要的参数
        // 计划加入金额
        BigDecimal account = hjhAccede.getAccedeAccount();
        // 锁定期
        Integer borrowPeriod = hjhAccede.getLockPeriod();
        // 年利率
        BigDecimal apr = BigDecimal.ZERO;
        // 返回值
        BigDecimal interest = BigDecimal.ZERO;

        String planNid = hjhAccede.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        if (planList != null && planList.size() > 0) {
            HjhPlan hjhPlan = planList.get(0);
            apr = hjhPlan.getExpectApr().divide(new BigDecimal(100));
            // 锁定期为日
            if (hjhPlan.getIsMonth() - 0 == 0) {
                interest = account.multiply(apr).multiply(new BigDecimal(borrowPeriod)).divide(new BigDecimal(360), 16, BigDecimal.ROUND_DOWN);
            } else {
                interest = account.multiply(apr).multiply(new BigDecimal(borrowPeriod)).divide(new BigDecimal(12), 16, BigDecimal.ROUND_DOWN);
            }
        }
        interest = interest.setScale(2, BigDecimal.ROUND_DOWN);

        // 待收收益
        hjhAccede.setWaitInterest(interest);
        // 待收本金
        hjhAccede.setWaitCaptical(account);
        // 待收总额
        hjhAccede.setWaitTotal(interest.add(account));
        boolean tenderFlag = this.batchHjhAccedeCustomizeMapper.updateInterest(hjhAccede) > 0 ? true : false;
        if (!tenderFlag) {
            throw new RuntimeException("更新(hyjf_hjh_accede)写入失败!" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        }
        logger.info("============更新(hyjf_hjh_accede)待收收益，待收本金，待收总额成功:interest：[" + interest + "]，account：[" + account + "]   WaitTotal：[" + interest.add(account) + "][出借人ID：" + hjhAccede.getUserId() + "]，" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        return interest;
    }

    /**
     * 获得更新后的计划订单信息
     *
     * @param hjhAccede
     * @return
     */
    private HjhAccede getNewAccede(HjhAccede hjhAccede) {

        HjhAccedeExample example = new HjhAccedeExample();
        example.createCriteria().andIdEqualTo(hjhAccede.getId());
        List<HjhAccede> accedeList = this.hjhAccedeMapper.selectByExample(example);
        return accedeList.get(0);
    }

    /**
     * 更新加入明细表的相关时间
     *
     * @param hjhAccede
     */
    private void updateAccedeDate(HjhAccede hjhAccede) {
        int nowTime = GetDate.getNowTime10();
        //锁定期
        int lockTime = 0;
        int lastPaymentTime = 0;
        String planNid = hjhAccede.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        if (planList != null && planList.size() > 0) {
            HjhPlan hjhPlan = planList.get(0);
            if ("endday".equals(hjhPlan.getBorrowStyle())) {//锁定期为日
                lockTime = GetDate.getDayStart10(GetDate.countDate(new Date(), 5, hjhPlan.getLockPeriod() - 1));
                lastPaymentTime = GetDate.getDayStart10(GetDate.countDate(new Date(), 5, hjhPlan.getLockPeriod() + 2));
            } else {
                lockTime = GetDate.getDayStart10(GetDate.countDate(new Date(), 2, hjhPlan.getLockPeriod()));
                lastPaymentTime = GetDate.getDayStart10(GetDate.countDate(GetDate.countDate(new Date(), 2, hjhPlan.getLockPeriod()), 5, +3));
            }
        }
        hjhAccede.setQuitTime(lockTime);
        hjhAccede.setLastPaymentTime(lastPaymentTime);
        //锁定中
        hjhAccede.setOrderStatus(3);
        hjhAccede.setCountInterestTime(nowTime);
        int count = this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
        if (count > 0) {
            logger.info("============ 更新退出时间和最后还款时间成功,计划加入订单号:" + hjhAccede.getAccedeOrderId());
        }
    }

    /**
     * 更新还款信息表
     *
     * @param hjhAccede
     */
    private void updateHjhPlanRepayInfo(HjhAccede hjhAccede) {
//		BigDecimal planWaitCaptical = new BigDecimal(0);
//		BigDecimal planWaitInterest = new BigDecimal(0);
//		BigDecimal waitTotal = new BigDecimal(0);
        BigDecimal serviceFee = new BigDecimal(0);
        //计划加入订单号
        String accedeOrderId = hjhAccede.getAccedeOrderId();
        logger.info("============= 开始更新汇计划相关还款信息,加入订单号:" + accedeOrderId);
        BorrowRecoverExample example = new BorrowRecoverExample();
        example.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<BorrowRecover> borrowRecoverList = this.borrowRecoverMapper.selectByExample(example);

        if (borrowRecoverList != null && borrowRecoverList.size() > 0) {
            for (int i = 0; i < borrowRecoverList.size(); i++) {
                BorrowRecover borrowRecover = borrowRecoverList.get(i);
//				planWaitCaptical = planWaitCaptical.add(borrowRecover.getRecoverCapital());
//				planWaitInterest = planWaitInterest.add(borrowRecover.getRecoverInterest());
//				waitTotal = waitTotal.add(borrowRecover.getRecoverAccount());
                logger.info("============= 开始更新汇计划相关还款信息,加入订单号:" + accedeOrderId + ",第" + i + "次出借金额: " + borrowRecover.getRecoverAccount());
                serviceFee = serviceFee.add(borrowRecover.getRecoverServiceFee());
            }
        }
        logger.info("============= 开始更新汇计划相关还款信息,加入订单号:" + accedeOrderId + ",待还款金额: " + hjhAccede.getShouldPayTotal());
        HjhRepay repay = new HjhRepay();
        repay.setAccedeOrderId(accedeOrderId);
        repay.setPlanNid(hjhAccede.getPlanNid());
        repay.setLockPeriod(hjhAccede.getLockPeriod());
        repay.setUserId(hjhAccede.getUserId());
        repay.setUserName(hjhAccede.getUserName());
        repay.setUserAttribute(hjhAccede.getUserAttribute());
        repay.setAccedeAccount(hjhAccede.getAccedeAccount());
        repay.setRepayInterest(hjhAccede.getShouldPayInterest());
        repay.setRepayCapital(hjhAccede.getShouldPayCapital());
        //未回款
        repay.setRepayStatus(0);
        //待回款
        repay.setRepayWait(hjhAccede.getShouldPayTotal());
        repay.setRepayAlready(new BigDecimal(0));
        repay.setPlanRepayCapital(new BigDecimal(0));
        repay.setPlanRepayInterest(new BigDecimal(0));
        repay.setRepayTotal(new BigDecimal(0));
        //应还金额
        repay.setRepayShould(hjhAccede.getShouldPayTotal());
        repay.setOrderStatus(3);
        //计划应还时间
        repay.setRepayShouldTime(hjhAccede.getQuitTime());
        //待还本金
        repay.setPlanWaitCaptical(hjhAccede.getShouldPayCapital());
        //待还利息
        repay.setPlanWaitInterest(hjhAccede.getShouldPayInterest());
        //待还总额
        repay.setWaitTotal(hjhAccede.getShouldPayTotal());
        //服务费
        repay.setServiceFee(serviceFee);
        repay.setCreateUser(hjhAccede.getUserId());
        HjhRepayExample repayExample = new HjhRepayExample();
        repayExample.createCriteria().andAccedeOrderIdEqualTo(accedeOrderId);
        List<HjhRepay> repayList = hjhRepayMapper.selectByExample(repayExample);
        if (repayList != null && repayList.size() > 0) {
            logger.info("=============== 汇计划还款信息表已插入!");
        } else {
            int count = this.hjhRepayMapper.insertSelective(repay);
            if (count > 0) {
                logger.info("============ 汇计划还款信息表插入完成!计划加入订单号:" + accedeOrderId);
            }
        }
    }

    /**
     * 推送消息
     *
     * @param hjhAccede
     * @author Administrator
     */
    private void loanSendMessage(HjhAccede hjhAccede) {
        int userId = hjhAccede.getUserId();
        BigDecimal amount = hjhAccede.getWaitTotal();
        Map<String, String> msg = new HashMap<String, String>();
        msg.put(VAL_AMOUNT, amount.toString());// 待收金额
        msg.put(VAL_USER_ID, String.valueOf(userId));
        // 推送消息队列
        AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USER_ID)), msg, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_PLAN_TOUZI_SUCCESS);
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
                    smsMessage));
        } catch (MQException e) {
            logger.error("发送app消息失败..", e);
        }
    }

    /**
     * 修改清算时间
     *
     * @param hjhAccede
     * @author sunss
     */
    private void updateEndDate(HjhAccede hjhAccede) {
        logger.info("============ 开始更新清算时间,计划加入订单号:" + hjhAccede.getAccedeOrderId());
        // 汇计划二期
        Date end_date = null;
        //锁定期
        String planNid = hjhAccede.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        if (planList != null && planList.size() > 0) {
            HjhPlan hjhPlan = planList.get(0);
            if (hjhPlan.getIsMonth() - 0 == 0) {//锁定期为日
                // 清算日期 10.1加入  7天计划 存的是10.7  清算日期为10.7  8  9
                end_date = GetDate.countDate(new Date(), 5, (hjhPlan.getLockPeriod() - 1));
            } else {
                // 清算日期  2.1加入的  1个月计划  存的是3.1   清算日期是3.1  3.2  3.3
                end_date = GetDate.countDate(new Date(), 2, hjhPlan.getLockPeriod());
            }
        }
        hjhAccede.setEndDate(end_date);
        int count = this.hjhAccedeMapper.updateByPrimaryKey(hjhAccede);
        if (count > 0) {
            logger.info("============ 更新清算时间成功,计划加入订单号:" + hjhAccede.getAccedeOrderId());
        } else {
            logger.info("============ 更新清算时间失败,计划加入订单号:" + hjhAccede.getAccedeOrderId());
        }
    }

    /**
     * 更新资金明细
     *
     * @param hjhAccede
     */
    private void updateUserAccount(HjhAccede hjhAccede) {
        logger.info("============开始更新资金明细WaitInterest:[" + hjhAccede.getWaitInterest() + "]，[出借人ID：" + hjhAccede.getUserId() + "]，" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        Account accountTender = new Account();
        accountTender.setUserId(hjhAccede.getUserId());
        accountTender.setBankTotal(hjhAccede.getWaitInterest());
        accountTender.setPlanAccountWait(hjhAccede.getWaitTotal());
        accountTender.setPlanInterestWait(hjhAccede.getWaitInterest());
        accountTender.setPlanCapitalWait(hjhAccede.getWaitCaptical());
        boolean investaccountFlag = this.adminAccountCustomizeMapper.updateBankTotalForLockPlan(accountTender) > 0 ? true : false;
        if (!investaccountFlag) {
            throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败!" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        }
        // 取得账户信息(出借人)
        accountTender = this.getAccountByUserId(hjhAccede.getUserId());
        if (Validator.isNull(accountTender)) {
            throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + hjhAccede.getUserId() + "]，" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        }
        //牵扯到跨库问题 用account表中的account_id代替原先bank_open_account的account
//        Account tenderOpenAccount = this.getAccountByUserId(hjhAccede.getUserId());
        String txDate = GetOrderIdUtils.getTxDate();
        String txTime = GetOrderIdUtils.getTxTime();
        int nowTime = GetDate.getNowTime10();
        // 写入收支明细
        AccountList accountList = new AccountList();
        // 出借人银行相关
        accountList.setBankAwait(accountTender.getBankAwait());
        accountList.setBankAwaitCapital(accountTender.getBankAwaitCapital());
        accountList.setBankAwaitInterest(accountTender.getBankAwaitInterest());
        accountList.setBankBalance(accountTender.getBankBalance());
        accountList.setBankFrost(accountTender.getBankFrost());
        accountList.setBankInvestSum(accountTender.getBankInvestSum());
        accountList.setBankTotal(accountTender.getBankTotal());
        accountList.setBankWaitCapital(accountTender.getBankWaitCapital());
        accountList.setBankWaitInterest(accountTender.getBankWaitInterest());
        accountList.setBankWaitRepay(accountTender.getBankWaitRepay());
        //牵扯到跨库问题 用account表中的account_id代替原先bank_open_account的account
        accountList.setAccountId(accountTender.getAccountId());
        accountList.setCheckStatus(0);
        accountList.setTradeStatus(1);
        accountList.setIsBank(1);
        accountList.setTxDate(Integer.parseInt(txDate));
        accountList.setTxTime(Integer.parseInt(txTime));
        // 出借人非银行相关
        // 出借订单号
        accountList.setNid(hjhAccede.getAccedeOrderId());
        // 出借人
        accountList.setUserId(hjhAccede.getUserId());
        //accountList.setAmount(tenderAccount); // 出借本金
        // 收支类型1收入2支出3冻结
        accountList.setType(1);
        // 投标成功
        accountList.setTrade("hjh_lock");
        // 余额操作
        accountList.setTradeCode("balance");
        // 出借人资金总额
        accountList.setTotal(accountTender.getTotal());
        // 出借人可用金额
        accountList.setBalance(accountTender.getBalance());
        // 出借人冻结金额
        accountList.setFrost(accountTender.getFrost());
        // 出借人待收金额
        accountList.setAwait(accountTender.getAwait());
//        accountList.setCreateTime(nowTime); // 创建时间
//        accountList.setBaseUpdate(nowTime); // 更新时间
        // 操作者
        accountList.setOperator(CustomConstants.OPERATOR_AUTO_LOANS);
        accountList.setRemark(hjhAccede.getAccedeOrderId());
//        accountList.setIsUpdate(0);
//        accountList.setBaseUpdate(0);
//        accountList.setInterest(BigDecimal.ZERO); // 利息
        accountList.setWeb(0); // PC
        accountList.setPlanFrost(accountTender.getPlanFrost());
        accountList.setPlanBalance(accountTender.getPlanBalance());
        accountList.setIsShow(1);
        boolean tenderAccountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
        if (!tenderAccountListFlag) {
            throw new RuntimeException("出借人收支明细(huiyingdai_account_list)写入失败!" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
        }
        logger.info("============结束更新资金明细bank_total:[" + accountTender.getBankTotal() + "]，[出借人ID：" + hjhAccede.getUserId() + "]，" + "[出借订单号：" + hjhAccede.getAccedeOrderId() + "]");
    }

    /**
     * 生成并签署计划加入协议
     *
     * @param userId
     * @param accedeOrderId
     * @param waitTotal
     */
    private void sendPlanContract(Integer userId, String accedeOrderId, Integer quitTime, Integer countInterestTime, BigDecimal waitTotal) {

        logger.info("-------------加入订单号:" + accedeOrderId + ",开始生成计划加入协议！----------");
        try {
            FddGenerateContractBean bean = new FddGenerateContractBean();
            bean.setOrdid(accedeOrderId);
            bean.setTransType(FddGenerateContractConstant.PROTOCOL_TYPE_PLAN);
            bean.setTenderUserId(userId);
            //签署日期
            bean.setSignDate(GetDate.getDataString(GetDate.date_sdf));
            bean.setPlanStartDate(GetDate.getDateMyTimeInMillis(countInterestTime));
            bean.setPlanEndDate(GetDate.getDateMyTimeInMillis(quitTime));
            bean.setTenderInterestFmt(waitTotal.toString());
            // 法大大生成合同MQ
            commonProducer.messageSend(new MessageContent(MQConstant.FDD_TOPIC,
                    MQConstant.FDD_GENERATE_CONTRACT_TAG, UUID.randomUUID().toString(), bean));

        } catch (Exception e) {
            logger.info("-------------userid:" + userId + ",生成计划加入协议失败！----------",e);
        }
    }

    /**
     * 计划锁定推送消息
     *
     * @param hjhAccede
     */
    private void sendHjhLockMessage(HjhAccede hjhAccede) {
        int userId = hjhAccede.getUserId();
        BigDecimal amount = hjhAccede.getAccedeAccount();
        BigDecimal waitInterest = hjhAccede.getWaitInterest();
        Date endDate = hjhAccede.getEndDate();
        //最迟退出时间
        Date countDate = GetDate.countDate(endDate, 5, 3);
        String endDateStr = GetDate.dateToString2(countDate);
        String planNid = hjhAccede.getPlanNid();
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        HjhPlan hjhPlan = planList.get(0);
        String planName = hjhPlan.getPlanName();
        Map<String, String> msg = new HashMap<String, String>();
        msg.put(VAL_AMOUNT, amount.toString());// 待收金额
        msg.put(VAL_USER_ID, String.valueOf(userId));
        msg.put(VAL_HJH_TITLE, planName);
        msg.put(VAL_INTEREST, waitInterest.toString());
        msg.put(VAL_DATE, endDateStr);

        //发送app消息队列，需要根据userid取真实用户
        AppMsMessage smsMessage = new AppMsMessage(Integer.valueOf(msg.get(VAL_USER_ID)), msg, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_PLAN_LOCK_SUCCESS);
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(userId),
                    smsMessage));
        } catch (MQException e) {
            logger.error("发送app消息失败..", e);
        }

    }

    /**
     * 优惠券还款队列
     *
     * @param hjhAccede
     */
    private void couponRepay(HjhAccede hjhAccede) throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        // 借款项目编号
        params.put("orderId", hjhAccede.getAccedeOrderId());
        //优惠券还款队列
        commonProducer.messageSend(new MessageContent(MQConstant.HJH_COUPON_REPAY_TOPIC, UUID.randomUUID().toString(), params));
    }

    /**
     * 优惠券放款队列
     *
     * @param hjhAccede
     */
    private void couponLoan(HjhAccede hjhAccede) throws Exception{
        Map<String, String> params = new HashMap<String, String>();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        // 借款项目编号
        params.put("orderId", hjhAccede.getAccedeOrderId());
        //优惠券放款队列
        commonProducer.messageSend(new MessageContent(MQConstant.HJH_COUPON_LOAN_TOPIC, UUID.randomUUID().toString(), params));
    }

    /**
     * 发送短信(计划投标成功)
     *
     * @param hjhAccede
     */
    private void sendSms(HjhAccede hjhAccede) {
        int userId = hjhAccede.getUserId();
        String planNid = hjhAccede.getPlanNid();
        BigDecimal waitInterest = hjhAccede.getWaitInterest();
        Date endDate = hjhAccede.getEndDate();
        Date countDate = GetDate.countDate(endDate, 5, 3);//最迟退出时间
        String endDateStr = GetDate.dateToString2(countDate);
        HjhPlanExample example = new HjhPlanExample();
        example.createCriteria().andPlanNidEqualTo(planNid);
        List<HjhPlan> planList = this.hjhPlanMapper.selectByExample(example);
        HjhPlan hjhPlan = planList.get(0);
        String planName = hjhPlan.getPlanName();
        BigDecimal waitTotal = hjhAccede.getAccedeAccount();
        Map<String, String> msg = new HashMap<String, String>();
        msg.put(VAL_AMOUNT, waitTotal.toString());
        msg.put(VAL_USER_ID, String.valueOf(userId));
        msg.put(VAL_HJH_TITLE, planName);
        msg.put(VAL_INTEREST, waitInterest.toString());
        msg.put(VAL_DATE, endDateStr);
        logger.info("userid=" + msg.get(VAL_USER_ID) + ";开始发送短信,待收金额" + msg.get(VAL_AMOUNT));
        SmsMessage smsMessage = null;
        smsMessage = new SmsMessage(Integer.valueOf(msg.get(VAL_USER_ID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_TOUZI_HJH_SUCCESS,
                CustomConstants.CHANNEL_TYPE_NORMAL);
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, String.valueOf(userId), smsMessage));
        } catch (MQException e2) {
            logger.error("发送邮件失败..", e2);
        }
    }
}
