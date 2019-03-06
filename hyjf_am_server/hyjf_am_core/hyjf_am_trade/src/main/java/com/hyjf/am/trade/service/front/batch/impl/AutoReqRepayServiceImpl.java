/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.batch.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.bean.repay.RepayBean;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.AutoReqRepayBorrowCustomize;
import com.hyjf.am.trade.service.front.batch.AutoReqRepayService;
import com.hyjf.am.trade.service.front.repay.RepayManageService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;

/**
 * @author liubin
 * @version AutoReqRepayServiceImpl, v0.1 2018/7/13 10:58
 */
@Service
public class AutoReqRepayServiceImpl extends BaseServiceImpl implements AutoReqRepayService {
    @Autowired
    private RepayManageService repayManageService;

    /** 还款时的校验信息 */
    public static final String REPAY_ERROR = "WEBPAY0001";

    /**
     * 取得本日应还款标的列表
     * @return
     * @author lb
     */
    @Override
    public List<AutoReqRepayBorrowCustomize> getAutoReqRepayBorrow() {
        List<AutoReqRepayBorrowCustomize> list = this.autoReqRepayBorrowCustomizeMapper.getAutoReqRepayBorrow();
        return list;
    }

    /**
     * 标的请求还款
     * @return
     * @author lb
     * @throws ParseException
     */
    @Override
    public boolean repayUserBorrowProject(AutoReqRepayBorrowCustomize autoReqRepayBorrow) throws Exception {
        JSONObject info = new JSONObject();

        // 还款用参数设定
        Integer userId = null;
        String roleId = null;
        String userName = null;
        String password = null;
        String borrowNid = autoReqRepayBorrow.getBorrowNid();
        if ("1".equals(autoReqRepayBorrow.getRepayerType())) {
            // 担保机构还款
            userId = autoReqRepayBorrow.getRepayOrgUserId();
            userName = autoReqRepayBorrow.getRepayOrgUsername();
            roleId = "3";
            if (userId == null || userId <= 0 ) {
                logger.info("该还款表的没有担保机构");
                return false;
            }
        }else {
            // 借款人还款
            userId = autoReqRepayBorrow.getUserId();
            userName = autoReqRepayBorrow.getUsername();
            roleId = "2";
        }

        // 获取用户在银行的客户号
        Account account = this.getAccount(userId);
        if (account == null || StringUtils.isEmpty(account.getAccountId())) {
            logger.info("用户在银行未开户");
            return false;
        }
        // 获取还款的标的信息
        Borrow borrow = this.repayManageService.searchRepayProject(userId, userName, roleId, borrowNid);
        /** redis 锁 */
        boolean reslut = RedisUtils.tranactionSet(RedisConstants.CONCURRENCE_REPAY_REQUEST + borrowNid, 60);
        if(!reslut){
            logger.info("项目正在还款中...");
            return false;
        }
        // 校验用户/垫付机构的还款
        RepayBean repay = null;
        if (StringUtils.isNotEmpty(roleId) && "3".equals(roleId)) {// 垫付机构还款校验
            Integer repayUserId = borrow.getUserId();
            repay = this.validatorFieldCheckRepay_org(info, userId, password, borrow, repayUserId,0);
        } else { // 借款人还款校验
            repay = this.validatorFieldCheckRepay(info, userId, password, borrow);
            repay.setRepayUserId(borrow.getUserId());
        }

        if (!ValidatorCheckUtil.hasValidateError(info) && repay != null) {

            //防止汇计划还款时正在发生债转操作
            int errflag = repay.getFlag();
            if (1 == errflag) {
                logger.info(repay.getMessage());
                return false;
            }
            // batch服务器的Ip
            String ip = "10.139.40.205";
            repay.setIp(ip);
            BigDecimal repayTotal = repay.getRepayAccountAll();
            // 用户还款
            try {
                String orderId = GetOrderIdUtils.getOrderId2(userId);
                String accountId = account.getAccountId();
                //add by cwyang 2017-07-25 还款去重
                boolean result = repayManageService.checkRepayInfo(userId,borrowNid);
                if (!result) {
                    logger.info("项目正在还款中...");
                    return false;
                }
                //插入冻结信息日志表 add by cwyang 2017-07-08
                repayManageService.insertRepayFreezeLog(userId,orderId,accountId,borrowNid,repayTotal,userName);
                // 申请还款冻结资金
                // 调用江西银行还款申请冻结资金
                BankCallBean bean = new BankCallBean();
                bean.setVersion(BankCallConstant.VERSION_10);// 版本号
                bean.setTxCode(BankCallMethodConstant.TXCODE_BALANCE_FREEZE);// 交易代码
                bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
                bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
                bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
                bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
                bean.setAccountId(accountId);// 电子账号
                bean.setOrderId(orderId); // 订单号
                bean.setTxAmount(String.valueOf(repayTotal));// 交易金额
                bean.setProductId(borrowNid);
                bean.setFrzType("0");
                bean.setLogOrderId(orderId);// 订单号
                bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
                bean.setLogUserId(String.valueOf(userId));
                bean.setLogUserName(userName);
                bean.setLogClient(0);
                bean.setLogIp(ip);
                bean.setProductId(borrowNid);
                BankCallBean callBackBean = BankCallUtils.callApiBg(bean);
                String respCode = callBackBean == null ? "" : callBackBean.getRetCode();
                // 申请冻结资金失败
                if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
                    if (!"".equals(respCode)) {
                        this.repayManageService.deleteFreezeTempLogs(orderId);
                    }
                    logger.info("调用还款申请冻结资金接口失败:" + callBackBean.getRetMsg() + "订单号:" + callBackBean.getOrderId());
                    logger.info("还款失败，请稍后再试！");
                    return false;
                }
                BorrowRepayExample example = new BorrowRepayExample();
                example.createCriteria().andBorrowNidEqualTo(borrowNid);
                List<BorrowRepay> repayList = this.borrowRepayMapper.selectByExample(example );
                if (repayList != null && repayList.size() > 0) {
                    BorrowRepay borrowRepay = repayList.get(0);
                    Integer borrowPeriod = borrow.getBorrowPeriod();//总期数
                    Integer repayPeriod = borrowRepay.getRepayPeriod();//剩余应还期数
                    Integer nowPeriod = borrowPeriod - repayPeriod + 1;//当前还款期数

                    BorrowRepayPlanExample planExample = new BorrowRepayPlanExample();
                    planExample.createCriteria().andBorrowNidEqualTo(borrowNid).andRepayPeriodEqualTo(nowPeriod);
                    List<BorrowRepayPlan> repayPlanList = this.borrowRepayPlanMapper.selectByExample(planExample );
                    if (repayPlanList != null && repayPlanList.size() > 0) {
                        BorrowRepayPlan borrowRepayPlan = repayPlanList.get(0);
                        borrowRepayPlan.setAutoRepay(1);
                        this.borrowRepayPlanMapper.updateByPrimaryKey(borrowRepayPlan);
                        borrowRepay.setAutoRepay(-1);
                    }else{
                        borrowRepay.setAutoRepay(1);//自动还款
                    }
                    int flag = this.borrowRepayMapper.updateByPrimaryKey(borrowRepay);
                    if (flag > 0) {
                        logger.info("------------------cwyang 变更自动还款成功!----------------------");
                    }
                }
                boolean flag = this.repayManageService.updateRepayMoney(repay, callBackBean, false);
                if (flag) {
                    String planNid = borrow.getPlanNid();
                    if(StringUtils.isNotBlank(planNid)){//计划编号
                        // 如果有正在出让的债权,先去把出让状态停止
                        this.repayManageService.updateBorrowCreditStautus(borrow.getBorrowNid());
                    }
                    return true;
                } else {
                    logger.info("还款失败，请稍后再试！");
                    return false;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            logger.info("还款失败，请稍后再试！");
            return false;

        } else {
            logger.info(info.getString(REPAY_ERROR));
            return false;
        }
    }

    /**
     * 校验垫付机构还款信息(计算结果形成中间结果值)
     *
     * @param info
     * @param userId
     *            垫付机构id
     * @param password
     * @param borrow
     * @param repayUserId
     *            借款人id
     * @param flag
     * @throws ParseException
     */
    private RepayBean validatorFieldCheckRepay_org(JSONObject info, int userId, String password, Borrow borrow, Integer repayUserId, int flag) throws Exception {
        // 获取当前垫付机构
        RUser user = this.getRUser(userId);
        // 检查垫付机构是否存在
        if (user == null) {
        }
        // 画面需要密码验证，定时任务不需要密码验证
//		String sort = user.getSalt();
//		String mdPassword = MD5.toMD5Code(password + sort);
//		// 检查垫付机构输入的密码信息同当前的用户密码信息是否对应
//		if (!mdPassword.equals(user.getPassword())) {
//			logger.info("REPAY_ERROR2);
//		}
        // 获取垫付机构的账户余额信息
        Account account = this.getAccount(userId);
        // 查询垫付机构的账户余额信息
        if (account == null) {
            info.put(REPAY_ERROR,"未查询到账户余额信息");
            return null;
        }
        // 获取用户在平台的账户余额
        BigDecimal balance = account.getBankBalance();
        // 获取当前垫付机构要还款的项目
        borrow = this.repayManageService.searchRepayProject(repayUserId, null, null, borrow.getBorrowNid());
        // 判断用户当前还款的项目是否存在
        if (borrow == null) {
            info.put(REPAY_ERROR,"未查询到项目信息");
            return null;
        }
        // 获取项目还款方式
        String borrowStyle = StringUtils.isNotEmpty(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
        // 获取年化利率
        BigDecimal borrowApr = borrow.getBorrowApr();
        // 判断项目的还款方式是否为空
        if (StringUtils.isEmpty(borrowStyle)) {
            info.put(REPAY_ERROR,"未查询到项目的还款方式");
        }
        String planNid = borrow.getPlanNid();
        if(StringUtils.isNotBlank(planNid)){//计划还款
            //设置防债转并发锁
            boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
            if (!tranactionSetFlag) {//设置失败
                RepayBean repayByTerm = new RepayBean();
                repayByTerm = new RepayBean();
                repayByTerm.setFlag(1);//校验失败
                repayByTerm.setMessage("系统繁忙,请5分钟后重试.......");
                return repayByTerm;
            }
        }
        // 一次性还款
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
            BigDecimal repayTotal = this.repayManageService.searchRepayTotal(repayUserId, borrow);
            if (repayTotal.compareTo(balance) == 0 || repayTotal.compareTo(balance) < 0) {
                // ** 垫付机构符合还款条件，可以还款 *//*
                // 获取用户在银行的电子账户余额
                if (flag == 1) {//垫付机构批量还款
                    RepayBean repayByTerm = this.repayManageService.calculateRepay(repayUserId, borrow);
                    repayByTerm.setRepayUserId(userId);// 垫付机构id
                    return repayByTerm;
                }else{
                    BigDecimal userBankBalance = this.getBankBalance(userId, account.getAccountId());
                    if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) < 0) {
                        // ** 垫付机构符合还款条件，可以还款 *//*
                        RepayBean repayByTerm = this.repayManageService.calculateRepay(repayUserId, borrow);
                        repayByTerm.setRepayUserId(userId);// 垫付机构id
                        return repayByTerm;
                    } else {
                        info.put(REPAY_ERROR,"您的银行账户余额不足，请充值");
                    }
                }

            } else {
                info.put(REPAY_ERROR,"您的余额不足，请充值");
            }
        } // 分期还款
        else {
            BigDecimal repayTotal = this.repayManageService.searchRepayByTermTotal(repayUserId, borrow, borrowApr, borrowStyle, borrow.getBorrowPeriod());
            if (repayTotal.compareTo(balance) == 0 || repayTotal.compareTo(balance) < 0) {
                // ** 垫付机构符合还款条件，可以还款 *//*
                // 获取用户在银行的电子账户余额
                if (flag ==1) {//垫付机构批量还款
                    // ** 用户符合还款条件，可以还款 *//*
                    RepayBean repayByTerm = this.repayManageService.calculateRepayByTerm(repayUserId, borrow);
                    repayByTerm.setRepayUserId(userId);// 垫付机构id
                    return repayByTerm;
                }else{
                    BigDecimal userBankBalance = this.getBankBalance(userId, account.getAccountId());
                    if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) < 0) {
                        // ** 用户符合还款条件，可以还款 *//*
                        RepayBean repayByTerm = this.repayManageService.calculateRepayByTerm(repayUserId, borrow);
                        repayByTerm.setRepayUserId(userId);// 垫付机构id
                        return repayByTerm;
                    } else {
                        info.put(REPAY_ERROR,"您的银行账户余额不足，请充值");
                    }
                }

            } else {
                info.put(REPAY_ERROR,"您的余额不足，请充值");
            }
        }

        return null;
    }

    /**
     * 校验用户还款信息(计算结果形成中间结果值)
     *
     * @param info
     * @param userId
     * @param password
     * @param borrow
     * @throws ParseException
     */
    private RepayBean validatorFieldCheckRepay(JSONObject info, int userId, String password, Borrow borrow) throws Exception {
        // 获取当前用户
        RUser user = this.getRUser(userId);
        // 检查用户是否存在
        if (user == null) {
            info.put(REPAY_ERROR,"未查询到相应的用户信息");
            return null;
        }
        // 画面需要密码验证，定时任务不需要密码验证
//		String sort = user.getSalt();
//		String mdPassword = MD5.toMD5Code(password + sort);
//		// 检查用户输入的密码信息同当前的用户密码信息是否对应
//		if (!mdPassword.equals(user.getPassword())) {
//			info.put(REPAY_ERROR,"REPAY_ERROR2);
//		}
        // 获取用户的账户余额信息
        Account account = this.getAccount(userId);
        // 查询用户的账户余额信息
        if (account == null) {
            info.put(REPAY_ERROR,"未查询到账户余额信息");
            return null;
        }
        // 获取用户的余额
        BigDecimal balance = account.getBankBalance();
        // 获取当前的用户还款的项目
        borrow = this.repayManageService.searchRepayProject(userId, user.getUsername(), "2", borrow.getBorrowNid());
        // 判断用户当前还款的项目是否存在
        if (borrow == null) {
            info.put(REPAY_ERROR,"未查询到项目信息");
            return null;
        }
        // 获取项目还款方式
        String borrowStyle = StringUtils.isNotEmpty(borrow.getBorrowStyle()) ? borrow.getBorrowStyle() : null;
        // 获取年化利率
        BigDecimal borrowApr = borrow.getBorrowApr();
        // 判断项目的还款方式是否为空
        if (StringUtils.isEmpty(borrowStyle)) {
            info.put(REPAY_ERROR,"未查询到项目的还款方式");
        }
        String planNid = borrow.getPlanNid();
        if(StringUtils.isNotBlank(planNid)){//计划还款
            //设置防债转并发锁
            boolean tranactionSetFlag = RedisUtils.tranactionSet(RedisConstants.HJH_DEBT_SWAPING + borrow.getBorrowNid(),300);
            if (!tranactionSetFlag) {//设置失败
                RepayBean repayByTerm = new RepayBean();
                repayByTerm = new RepayBean();
                repayByTerm.setFlag(1);//校验失败
                repayByTerm.setMessage("系统繁忙,请5分钟后重试.......");
                return repayByTerm;
            }
        }
        // 一次性还款
        if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
            BigDecimal repayTotal = this.repayManageService.searchRepayTotal(userId, borrow);
            if (repayTotal.compareTo(balance) == 0 || repayTotal.compareTo(balance) < 0) {
                // ** 用户符合还款条件，可以还款 *//*
                // 查询用户在银行电子账户的余额
                BigDecimal userBankBalance = this.getBankBalance(userId, account.getAccountId());
                if (repayTotal.compareTo(userBankBalance) == 0 || repayTotal.compareTo(userBankBalance) < 0) {
                    // ** 用户符合还款条件，可以还款 *//*
                    RepayBean repayByTerm = this.repayManageService.calculateRepay(userId, borrow);
                    return repayByTerm;
                } else {
                    info.put(REPAY_ERROR,"您的银行账户余额不足，请充值");
                }
            } else {
                info.put(REPAY_ERROR,"您的余额不足，请充值");
            }
        } // 分期还款
        else {
            BigDecimal repayTotal = this.repayManageService.searchRepayByTermTotal(userId, borrow, borrowApr, borrowStyle, borrow.getBorrowPeriod());
            if (repayTotal.compareTo(balance) == 0 || repayTotal.compareTo(balance) < 0) {
                // 查询用户在银行电子账户的可用余额
                BigDecimal userBalance = this.getBankBalance(userId, account.getAccountId());
                if (repayTotal.compareTo(userBalance) == 0 || repayTotal.compareTo(userBalance) < 0) {
                    // ** 用户符合还款条件，可以还款 *//*
                    RepayBean repayByTerm = this.repayManageService.calculateRepayByTerm(userId, borrow);
                    return repayByTerm;
                } else {
                    info.put(REPAY_ERROR,"您的银行账户余额不足，请充值");
                }
            } else {
                info.put(REPAY_ERROR,"您的余额不足，请充值");
            }
        }

        return null;
    }
}
