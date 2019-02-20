package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.InterestInfo;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.CouponLoansService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 10:34
 * @Description: CouponLoansServiceImpl
 */
@Service
public class CouponLoansServiceImpl implements CouponLoansService {
    private static final Logger logger = LoggerFactory.getLogger(CouponLoansServiceImpl.class);

    @Autowired
    private CommonProducer commonProducer;
    @Resource
    private AmUserClient amUserClient;
    @Resource
    private AmTradeClient borrowClient;

    /** 预期收益 */
    private static final String VAL_PROFIT = "val_profit";
    /** 用户ID */
    private static final String USERID = "userId";
    /** 出借金额 */
    private static final String VAL_AMOUNT = "val_amount";
    /** 优惠券出借 */
    private static final String COUPON_TYPE = "coupon_type";
    /** 优惠券出借订单编号 */
    private static final String TENDER_NID = "tender_nid";
    /** 优惠券面值 */
    private static final String VAL_COUPON_BALANCE = "val_coupon_balance";
    /** Y优惠券类型 */
    private static final String VAL_COUPON_TYPE = "val_coupon_type";
    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhList(String orderId) {
        if(StringUtils.isEmpty(orderId)){
            return new ArrayList<BorrowTenderCpnVO>();
        }
        return borrowClient.getBorrowTenderCpnHjhList(orderId);
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnHjhCouponOnlyList(String couponOrderId) {
        if(StringUtils.isEmpty(couponOrderId)){
            return new ArrayList<BorrowTenderCpnVO>();
        }
        return borrowClient.getBorrowTenderCpnHjhCouponOnlyList(couponOrderId);
    }

    @Override
    public Integer updateBorrowTenderCpn(BorrowTenderCpnVO borrowTenderCpn) {
        return  borrowClient.updateBorrowTenderCpn(borrowTenderCpn);
    }

    @Override
    public List<Map<String, String>> updateCouponRecoverHjh(BorrowTenderCpnVO borrowTenderCpn, String realOrderId) throws Exception{
        logger.info("优惠券自动放款开始！----标的编号：" + borrowTenderCpn.getBorrowNid());

        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();

        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 计划编号
        String planNid = borrowTenderCpn.getBorrowNid();
        /** 标的基本数据 */
        // 取得标的详情
//		BorrowWithBLOBs borrow = getBorrow(borrowNid);

        Integer borrowSuccessTime = GetDate.getNowTime10();
        HjhPlanVO hjhPlan = borrowClient.getHjhPlan(planNid);
        if(!StringUtils.isEmpty(realOrderId)){
            HjhAccedeVO hjhAccede = borrowClient.getHjhAccede(realOrderId);
            // 借款成功时间
            borrowSuccessTime = hjhAccede.getCountInterestTime() == 0 ? GetDate.getNowTime10() : hjhAccede.getCountInterestTime();
        }

        Map<String, String> msg = null;
        // 借款期数
        Integer borrowPeriod = Validator.isNull(hjhPlan.getLockPeriod()) ? 1 : hjhPlan.getLockPeriod();
        // 还款方式
        String borrowStyle = hjhPlan.getBorrowStyle();

        //汇计划只支持按天和按月
        if(!"endday".equals(borrowStyle)){
            borrowStyle = "end";
        }
        // 年利率
        BigDecimal borrowApr = hjhPlan.getExpectApr();
        // 项目类型
        Integer projectType = 0;
        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

        // 利息
        BigDecimal interestTender = BigDecimal.ZERO;
        // 本息总额
        BigDecimal allAccount = BigDecimal.ZERO;
        // 本金
        BigDecimal allCapital = BigDecimal.ZERO;

        BigDecimal sumInterest = BigDecimal.ZERO;
        Integer recoverPeriod = 1;
        Integer recoverTime = null;
        msg = new HashMap<String, String>();
        retMsgList.add(msg);

        // 出借订单号
        String ordId = borrowTenderCpn.getNid();
        CouponConfigVO couponConfig = borrowClient.getCouponConfig(ordId);
        if (couponConfig == null) {
            throw new RuntimeException("优惠券出借放款失败" + "[出借订单号：" + ordId + "]");
        }
        InterestInfo interestInfo = null;
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = BigDecimal.ZERO;
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = BigDecimal.ZERO;
        // 差异费率
        BigDecimal differentialRate = BigDecimal.ZERO;
        // 初审时间
        int borrowVerifyTime = 0;
        // 检查优惠券是否重复放款
        if (checkCouponRecoverFirst(ordId)) {
            return null;
        }

        // 体验金
        if (couponConfig.getCouponType() == 1) {
            String tenderNid = borrowTenderCpn.getNid();
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("nid", tenderNid);
            // 取得体验金收益期限
            Integer couponProfitTime = borrowClient.getCouponProfitTime(tenderNid);
            // 计算体验金收益
            BigDecimal interest = this.getInterestTYJ(borrowTenderCpn.getAccount(), borrowApr,couponProfitTime);
            // 体验金按项目期限还款
            if(couponConfig.getRepayTimeConfig()==1){
                // 计算利息
                interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, borrowApr, borrowStyle, borrowSuccessTime,
                        borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);

                // 体验金的项目如果是分期
                if(isMonth){
                    List<InterestInfo> listMonthly = interestInfo.getListMonthly();
                    // 取得最后一次分期的还款时间作为体验金的还款时间
                    interestInfo.setRepayTime(listMonthly.get(listMonthly.size()-1).getRepayTime());
                    // 体验金的还款期数是最后一期
                    recoverPeriod = listMonthly.size();
                }
            }else{
                // 体验金按收益期限还款
                interestInfo = new InterestInfo();
                Integer repayTime = GetDate.countDate(borrowSuccessTime, 5, couponProfitTime);
                interestInfo.setRepayTime(repayTime);
            }

            // 体验金收益
            interestInfo.setRepayAccountInterest(interest);
            interestTender = interestInfo.getRepayAccountInterest(); // 利息

        } else if (couponConfig.getCouponType() == 2) {
            // 加息券
            // 计算利息
            interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, couponConfig.getCouponQuota(), borrowStyle,
                    borrowSuccessTime, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);
            interestTender = interestInfo.getRepayAccountInterest(); // 利息
        } else if (couponConfig.getCouponType() == 3) {
            // 代金券
            // 计算利息
            interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, borrowApr, borrowStyle, borrowSuccessTime,
                    borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);
            // 本息总额
            allAccount = interestInfo.getRepayAccount();
            // 本金
            allCapital = interestInfo.getRepayAccountCapital();

            interestTender = borrowTenderCpn.getAccount().add(interestInfo.getRepayAccountInterest()); // 利息
        }
        if (interestInfo != null) {
            allAccount = allAccount.compareTo(BigDecimal.ZERO) == 0 ? interestTender : allAccount;

            if(StringUtils.isEmpty(realOrderId)){
                recoverTime = interestInfo.getRepayTime();
            }else if(couponConfig.getCouponType() == 1 && couponConfig.getRepayTimeConfig()==2){
                recoverTime = interestInfo.getRepayTime();
            }else{
                recoverTime = interestInfo.getRepayTime() ; // 估计还款时间
            }
        }

        int borrowTenderCnt2 = updateBorrowTender(borrowTenderCpn, allAccount, interestTender, allCapital);
        if (borrowTenderCnt2 == 0) {
            throw new RuntimeException("出借详情(huiyingdai_borrow_tender)更新失败!" + "[出借订单号：" + ordId + "]");
        }
        // [principal: 等额本金, month:等额本息, month:等额本息, end:先息后本]
        if (isMonth && couponConfig.getCouponType() != 1) {
            // 作成分期还款计划
            if (interestInfo != null && interestInfo.getListMonthly() != null) {
                InterestInfo monthly = null;
                for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
                    monthly = interestInfo.getListMonthly().get(j);
                    if(StringUtils.isEmpty(realOrderId)){
                        recoverTime = monthly.getRepayTime();
                    }else if(couponConfig.getCouponType() == 1 && couponConfig.getRepayTimeConfig()==2){
                        recoverTime = monthly.getRepayTime();
                    }else{
                        recoverTime = monthly.getRepayTime() ; // 估计还款时间
                    }
                    CouponRecoverVO cr = new CouponRecoverVO();
                    // 出借订单编号
                    cr.setTenderId(borrowTenderCpn.getNid());
                    // 还款状态（0:未还款，1：已还款）
                    cr.setRecoverStatus(0);
                    // 收益领取状态（1：未回款，2：未领取，4：转账失败，5：已领取，6：已过期）
                    cr.setReceivedFlg(1);
                    // 还款期数
                    cr.setRecoverPeriod(String.valueOf(j + 1));
                    // 估计还款时间
                    cr.setRecoverTime(monthly.getRepayTime());
                    // 应还利息
                    cr.setRecoverInterest(monthly.getRepayAccountInterest());
                    if (couponConfig.getCouponType() == 3) {
                        // 代金券
                        // 应还本息
                        cr.setRecoverAccount(monthly.getRepayAccount());
                        // 应还本金
                        cr.setRecoverCapital(monthly.getRepayAccountCapital());
                        sumInterest = sumInterest.add(monthly.getRepayAccount());
                    } else {
                        // 体验金和加息券
                        // 应还本息
                        cr.setRecoverAccount(monthly.getRepayAccountInterest());
                        // 应还本金
                        cr.setRecoverCapital(BigDecimal.ZERO);
                        sumInterest = sumInterest.add(monthly.getRepayAccountInterest());
                    }

                    // 是否已通知用户
                    cr.setNoticeFlg(0);
                    // 作成时间
                    cr.setCreateTime(new Date());
                    // 作成用户，系统自动（system）
                    cr.setCreateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
                    // 更新时间
                    cr.setUpdateTime(new Date());
                    // 更新用户 系统自动（system）
                    cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
                    // 删除标识
                    cr.setDelFlag(0);
                    borrowClient.insertSelective(cr);
                }
            }
        } else {
            // 不分期还款的场合
            CouponRecoverVO cr = new CouponRecoverVO();
            // 出借订单编号
            cr.setTenderId(borrowTenderCpn.getNid());
            // 还款状态（0:未还款，1：已还款）
            cr.setRecoverStatus(0);
            // 收益领取状态（1：未回款，2：未领取，3：转账中,4：转账失败，5：已领取，6：已过期）
            cr.setReceivedFlg(1);
            // 还款期数
            cr.setRecoverPeriod(String.valueOf(recoverPeriod));
            // 估计还款时间
            cr.setRecoverTime(recoverTime);
            // 应还利息
            cr.setRecoverInterest(interestTender);
            // 应还本息
            cr.setRecoverAccount(allAccount);
            sumInterest = allAccount;
            if (couponConfig.getCouponType() == 3) {
                // 代金券
                // 应还本金
                cr.setRecoverCapital(allCapital);
            } else {
                // 体验金和加息券
                // 应还本金
                cr.setRecoverCapital(BigDecimal.ZERO);
            }
            // 是否已通知用户
            cr.setNoticeFlg(0);
            // 作成时间
            cr.setCreateTime(new Date());
            // 作成用户，系统自动（system）
            cr.setCreateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
            // 更新时间
            cr.setUpdateTime(new Date());
            // 更新用户 系统自动（system）
            cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
            // 删除标识
            cr.setDelFlag(0);
            cr.setCurrentRecoverFlg(1);
            // 还款类别：1：直投类，2：汇添金
            cr.setRecoverType(1);
            this.borrowClient.insertSelective(cr);
        }
        borrowClient.crRecoverPeriod(borrowTenderCpn.getNid(), 1, 1);
        // 更新账户信息(出借人)
        AccountVO account = new AccountVO();

        account.setUserId(borrowTenderCpn.getUserId());
        account.setBankTotal(allAccount);// 出借人资金总额 +利息
        // 本金
        account.setPlanInterestWait(interestTender);
        account.setPlanAccountWait(allAccount);

        int accountCnt = this.borrowClient.updateOfLoansTenderHjh(account);
        if (accountCnt == 0) {
            throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败!" + "[出借订单号：" + ordId + "]");
        }
        // 取得账户信息(出借人)
        account = borrowClient.getAccountByUserId(borrowTenderCpn.getUserId());
        if (account == null) {
            throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + borrowTenderCpn.getUserId() + "]，" + "[出借订单号：" + ordId + "]");
        }

        // 出借人编号
        msg.put(USERID, borrowTenderCpn.getUserId().toString());
        // 出借额
        msg.put(VAL_AMOUNT, allAccount.toString());
        // 代收收益
        msg.put(VAL_PROFIT, sumInterest.toString());
        // 优惠券类型
        msg.put(VAL_COUPON_TYPE, couponConfig.getCouponType() == 1 ? "体验金" : couponConfig.getCouponType() == 2 ? "加息券" : "代金券");
        // 优惠券面值
        msg.put(VAL_COUPON_BALANCE, couponConfig.getCouponType() == 1 ? couponConfig.getCouponQuota() + "元"
                : couponConfig.getCouponType() == 2 ? couponConfig.getCouponQuota() + "%" : couponConfig.getCouponQuota() + "元");
        // 出借订单编号
        msg.put(TENDER_NID, borrowTenderCpn.getNid());
        // 优惠券
        msg.put(COUPON_TYPE, "1");
        logger.info("优惠券自动放款结束！----标的编号：" + borrowTenderCpn.getBorrowNid());
        return retMsgList;
    }

    @Override
    public void sendSmsCoupon(List<Map<String, String>> msgList) {
        logger.info("优惠券放款,短信发送开始："+ msgList.size());
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))
                        && new BigDecimal(msg.get(VAL_AMOUNT)).compareTo(BigDecimal.ZERO) > 0) {
                    UserVO users = amUserClient.findUserById(Integer.valueOf(msg.get(USERID)));
                    if (users == null || Validator.isNull(users.getMobile()) || (users.getInvestSms() != null && users.getInvestSms() == 1)) {
                        return;
                    }
                    SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(USERID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                            CustomConstants.PARAM_TPL_COUPON_TENDER, CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),
                                smsMessage));
                    } catch (MQException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        logger.info("优惠券放款,短信发送结束："+ msgList.size());
    }

    @Override
    public void sendAppMSCoupon(List<Map<String, String>> msgList) {
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_PROFIT))
                        && new BigDecimal(msg.get(VAL_PROFIT)).compareTo(BigDecimal.ZERO) > 0) {
                    UserVO users = amUserClient.findUserById(Integer.valueOf(msg.get(USERID)));
                    if (users == null) {
                        System.out.println("不满足发送push消息条件，推送失败");
                        return;
                    }
                    System.out.println("开始调用推送消息接口");
                    AppMsMessage appMsMessage = new AppMsMessage(users.getUserId(), msg, null, MessageConstant.APP_MS_SEND_FOR_USER,
                            CustomConstants.JYTZ_COUPON_TENDER);
                    try{
                        commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
                                UUID.randomUUID().toString(), appMsMessage));
                    }catch (Exception e){
                        logger.error("优惠券投标成功推送消息异常！",e);
                    }
                }
            }
        }
    }

    @Override
    public List<BorrowTenderCpnVO> getBorrowTenderCpnList(String borrowNid) {
        return borrowClient.getBorrowTenderCpnList(borrowNid);
    }

    @Override
    public List<Map<String, String>> updateCouponRecover(BorrowTenderCpnVO borrowTenderCpn) {
        logger.info("优惠券自动放款开始！----标的编号：" + borrowTenderCpn.getBorrowNid());

        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();

        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 借款编号
        String borrowNid = borrowTenderCpn.getBorrowNid();
        /** 标的基本数据 */
        // 取得标的详情
        BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);

        Map<String, String> msg = null;
        // 借款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 年利率
        BigDecimal borrowApr = borrow.getBorrowApr();
        // 借款成功时间
        Integer borrowSuccessTime = borrow.getReverifyTime();
        logger.info("标的："+borrowNid + "放款开始，借款满标时间："+borrow.getVerifyTimeInteger());
        // 项目类型
        Integer projectType = borrow.getProjectType();
        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);

        // 利息
        BigDecimal interestTender = BigDecimal.ZERO;
        // 本息总额
        BigDecimal allAccount = BigDecimal.ZERO;
        // 本金
        BigDecimal allCapital = BigDecimal.ZERO;

        BigDecimal sumInterest = BigDecimal.ZERO;
        Integer recoverPeriod = 1;
        Integer recoverTime = null;
        msg = new HashMap<String, String>();
        retMsgList.add(msg);

        // 出借订单号
        String ordId = borrowTenderCpn.getNid();
        CouponConfigVO couponConfig = borrowClient.getCouponConfig(ordId);
        if (couponConfig == null) {
            throw new RuntimeException("优惠券出借放款失败" + "[出借订单号：" + ordId + "]");
        }
        InterestInfo interestInfo = null;
        // 月利率(算出管理费用[上限])
        BigDecimal borrowMonthRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
        // 月利率(算出管理费用[下限])
        BigDecimal borrowManagerScaleEnd = Validator.isNull(borrow.getBorrowManagerScaleEnd()) ? BigDecimal.ZERO : new BigDecimal(
                borrow.getBorrowManagerScaleEnd());
        // 差异费率
        BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
        // 初审时间
        int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
        // 检查优惠券是否重复放款
        if (checkCouponRecoverFirst(ordId)) {
            return null;
        }

        // 体验金
        if (couponConfig.getCouponType() == 1) {
            String tenderNid = borrowTenderCpn.getNid();
            // 取得体验金收益期限
            Integer couponProfitTime = this.borrowClient.getCouponProfitTime(tenderNid);
            // 计算体验金收益
            BigDecimal interest = this.getInterestTYJ(borrowTenderCpn.getAccount(), borrowApr,couponProfitTime);
            // 体验金按项目期限还款
            if(couponConfig.getRepayTimeConfig()==1){
                // 计算利息
                interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, borrowApr, borrowStyle, borrowSuccessTime,
                        borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);

                // 体验金的项目如果是分期
                if(isMonth){
                    List<InterestInfo> listMonthly = interestInfo.getListMonthly();
                    // 取得最后一次分期的还款时间作为体验金的还款时间
                    interestInfo.setRepayTime(listMonthly.get(listMonthly.size()-1).getRepayTime());
                    // 体验金的还款期数是最后一期
                    recoverPeriod = listMonthly.size();
                }
            }else{
                // 体验金按收益期限还款
                interestInfo = new InterestInfo();
                Integer repayTime = GetDate.countDate(borrowSuccessTime, 5, couponProfitTime);
                interestInfo.setRepayTime(repayTime);
            }

            // 体验金收益
            interestInfo.setRepayAccountInterest(interest);
            interestTender = interestInfo.getRepayAccountInterest(); // 利息

        } else if (couponConfig.getCouponType() == 2) {
            // 加息券
            // 计算利息
            interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, couponConfig.getCouponQuota(), borrowStyle,
                    borrowSuccessTime, borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);
            interestTender = interestInfo.getRepayAccountInterest(); // 利息
        } else if (couponConfig.getCouponType() == 3) {
            // 代金券
            // 计算利息
            interestInfo = CalculatesUtil.getInterestInfo(borrowTenderCpn.getAccount(), borrowPeriod, borrowApr, borrowStyle, borrowSuccessTime,
                    borrowMonthRate, borrowManagerScaleEnd, projectType, differentialRate, borrowVerifyTime);
            // 本息总额
            allAccount = interestInfo.getRepayAccount();
            // 本金
            allCapital = interestInfo.getRepayAccountCapital();

            interestTender = borrowTenderCpn.getAccount().add(interestInfo.getRepayAccountInterest()); // 利息
        }
        if (interestInfo != null) {
            allAccount = allAccount.compareTo(BigDecimal.ZERO) == 0 ? interestTender : allAccount;
            recoverTime = interestInfo.getRepayTime(); // 估计还款时间
        }

        // 更新出借详情表
        BorrowTenderCpnVO newBorrowTender = new BorrowTenderCpnVO();
        newBorrowTender.setId(borrowTenderCpn.getId());
        // 待收总额（优惠券利息）
        newBorrowTender.setRecoverAccountWait(allAccount);
        // 收款总额（优惠券利息）
        newBorrowTender.setRecoverAccountAll(allAccount);
        // 待收利息（优惠券利息）
        newBorrowTender.setRecoverAccountInterestWait(interestTender);
        // 收款总利息（优惠券利息）
        newBorrowTender.setRecoverAccountInterest(interestTender);
        // 待收本金 (优惠券没有本金，设为0)
        newBorrowTender.setRecoverAccountCapitalWait(allCapital);
        // 放款金额（优惠券利息）
        newBorrowTender.setLoanAmount(allAccount);
        // 服务费（优惠券收益没有服务费）
        newBorrowTender.setLoanFee(BigDecimal.valueOf(0));
        // 状态 0，未放款，1，已放款
        newBorrowTender.setStatus(1);
        // 出借状态 0，未放款，1，已放款
        newBorrowTender.setTenderStatus(1);
        // 放款状态 0，未放款，1，已放款
        newBorrowTender.setApiStatus(1);
        // 放款订单号
        newBorrowTender.setLoanOrdid(borrowTenderCpn.getLoanOrdid());
        // 写入网站收支明细
        newBorrowTender.setWeb(2);
        int borrowTenderCnt2 = this.updateBorrowTenderCpn(newBorrowTender);
        if (borrowTenderCnt2 == 0) {
            throw new RuntimeException("出借详情(huiyingdai_borrow_tender)更新失败!" + "[出借订单号：" + ordId + "]");
        }
        // [principal: 等额本金, month:等额本息, month:等额本息, end:先息后本]
        if (isMonth && couponConfig.getCouponType() != 1) {
            // 作成分期还款计划
            if (interestInfo != null && interestInfo.getListMonthly() != null) {
                InterestInfo monthly = null;
                for (int j = 0; j < interestInfo.getListMonthly().size(); j++) {
                    monthly = interestInfo.getListMonthly().get(j);
                    CouponRecoverVO cr = new CouponRecoverVO();
                    // 出借订单编号
                    cr.setTenderId(borrowTenderCpn.getNid());
                    // 还款状态（0:未还款，1：已还款）
                    cr.setRecoverStatus(0);
                    // 收益领取状态（1：未回款，2：未领取，4：转账失败，5：已领取，6：已过期）
                    cr.setReceivedFlg(1);
                    // 还款期数
                    cr.setRecoverPeriod(String.valueOf(j + 1));
                    // 估计还款时间
                    cr.setRecoverTime(monthly.getRepayTime());
                    // 应还利息
                    cr.setRecoverInterest(monthly.getRepayAccountInterest());
                    if (couponConfig.getCouponType() == 3) {
                        // 代金券
                        // 应还本息
                        cr.setRecoverAccount(monthly.getRepayAccount());
                        // 应还本金
                        cr.setRecoverCapital(monthly.getRepayAccountCapital());
                        sumInterest = sumInterest.add(monthly.getRepayAccount());
                    } else {
                        // 体验金和加息券
                        // 应还本息
                        cr.setRecoverAccount(monthly.getRepayAccountInterest());
                        // 应还本金
                        cr.setRecoverCapital(BigDecimal.ZERO);
                        sumInterest = sumInterest.add(monthly.getRepayAccountInterest());
                    }

                    // 是否已通知用户
                    cr.setNoticeFlg(0);
                    // 作成时间
                    cr.setCreateTime(new Date());
                    // 作成用户，系统自动（system）
                    cr.setCreateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
                    // 更新时间
                    cr.setUpdateTime(new Date());
                    // 更新用户 系统自动（system）
                    cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
                    // 删除标识
                    cr.setDelFlag(0);
                    this.borrowClient.insertSelective(cr);
                }
            }
        } else {
            // 不分期还款的场合
            CouponRecoverVO cr = new CouponRecoverVO();
            // 出借订单编号
            cr.setTenderId(borrowTenderCpn.getNid());
            // 还款状态（0:未还款，1：已还款）
            cr.setRecoverStatus(0);
            // 收益领取状态（1：未回款，2：未领取，3：转账中,4：转账失败，5：已领取，6：已过期）
            cr.setReceivedFlg(1);
            // 还款期数
            cr.setRecoverPeriod(String.valueOf(recoverPeriod));
            // 估计还款时间
            cr.setRecoverTime(recoverTime);
            // 应还利息
            cr.setRecoverInterest(interestTender);
            // 应还本息
            cr.setRecoverAccount(allAccount);
            sumInterest = allAccount;
            if (couponConfig.getCouponType() == 3) {
                // 代金券
                // 应还本金
                cr.setRecoverCapital(allCapital);
            } else {
                // 体验金和加息券
                // 应还本金
                cr.setRecoverCapital(BigDecimal.ZERO);
            }
            // 是否已通知用户
            cr.setNoticeFlg(0);
            cr.setCreateTime(new Date());
            // 作成用户，系统自动（system）
            cr.setCreateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
            // 更新时间
            cr.setUpdateTime(new Date());
            // 更新用户 系统自动（system）
            cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_LOANS));
            // 删除标识
            cr.setDelFlag(0);
            cr.setCurrentRecoverFlg(1);
            // 还款类别：1：直投类，2：汇添金
            cr.setRecoverType(1);
            this.borrowClient.insertSelective(cr);
        }
        this.borrowClient.crRecoverPeriod(borrowTenderCpn.getNid(), 1, 1);
        // 更新账户信息(出借人)
        AccountVO account = new AccountVO();

        account.setUserId(borrowTenderCpn.getUserId());
        account.setBankTotal(allAccount);// 出借人资金总额 +利息
        account.setBankFrost(BigDecimal.ZERO);// 出借人冻结金额+出借金额(等额本金时出借金额可能会大于计算出的本金之和)
        account.setBankAwait(allAccount);// 出借人待收金额+利息+
        // 本金
        account.setBankAwaitCapital(BigDecimal.ZERO);// 出借人待收本金
        account.setBankAwaitInterest(interestTender);// 出借人待收利息
        account.setBankInvestSum(BigDecimal.ZERO);// 出借人累计出借
        account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额

        logger.info("更新用户账户："+account.getUserId());
        int accountCnt = this.borrowClient.updateOfLoansTender(account);
        if (accountCnt == 0) {
            throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败!" + "[出借订单号：" + ordId + "]");
        }
        // 取得账户信息(出借人)
        account = borrowClient.getAccountByUserId(borrowTenderCpn.getUserId());
        if (account == null) {
            throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + borrowTenderCpn.getUserId() + "]，" + "[出借订单号：" + ordId + "]");
        }

        // 出借人编号
        msg.put(USERID, borrowTenderCpn.getUserId().toString());
        // 出借额
        msg.put(VAL_AMOUNT, allAccount.toString());
        // 代收收益
        msg.put(VAL_PROFIT, sumInterest.toString());
        // 优惠券类型
        msg.put(VAL_COUPON_TYPE, couponConfig.getCouponType() == 1 ? "体验金" : couponConfig.getCouponType() == 2 ? "加息券" : "代金券");
        // 优惠券面值
        msg.put(VAL_COUPON_BALANCE, couponConfig.getCouponType() == 1 ? couponConfig.getCouponQuota() + "元"
                : couponConfig.getCouponType() == 2 ? couponConfig.getCouponQuota() + "%" : couponConfig.getCouponQuota() + "元");
        // 出借订单编号
        msg.put(TENDER_NID, borrowTenderCpn.getNid());
        // 优惠券
        msg.put(COUPON_TYPE, "1");
        logger.info("优惠券自动放款结束！----标的编号：" + borrowTenderCpn.getBorrowNid());
        return retMsgList;
    }

    /**
     * @Author walter.limeng
     * @Description  更新BorrowTender表
     * @Date 15:03 2018/7/17
     * @Param
     * @return
     */
    public int updateBorrowTender(BorrowTenderCpnVO borrowTenderCpn,BigDecimal allAccount,BigDecimal interestTender,BigDecimal allCapital){
        // 更新出借详情表
        BorrowTenderCpnVO newBorrowTender = new BorrowTenderCpnVO();
        newBorrowTender.setId(borrowTenderCpn.getId());
        // 待收总额（优惠券利息）
        newBorrowTender.setRecoverAccountWait(allAccount);
        // 收款总额（优惠券利息）
        newBorrowTender.setRecoverAccountAll(allAccount);
        // 待收利息（优惠券利息）
        newBorrowTender.setRecoverAccountInterestWait(interestTender);
        // 收款总利息（优惠券利息）
        newBorrowTender.setRecoverAccountInterest(interestTender);
        // 待收本金 (优惠券没有本金，设为0)
        newBorrowTender.setRecoverAccountCapitalWait(allCapital);
        // 放款金额（优惠券利息）
        newBorrowTender.setLoanAmount(allAccount);
        // 服务费（优惠券收益没有服务费）
        newBorrowTender.setLoanFee(BigDecimal.valueOf(0));
        // 状态 0，未放款，1，已放款
        newBorrowTender.setStatus(1);
        // 出借状态 0，未放款，1，已放款
        newBorrowTender.setTenderStatus(1);
        // 放款状态 0，未放款，1，已放款
        newBorrowTender.setApiStatus(1);
        // 放款订单号
        newBorrowTender.setLoanOrdid(borrowTenderCpn.getLoanOrdid());
        // 写入网站收支明细
        newBorrowTender.setWeb(2);
        int borrowTenderCnt2 = this.updateBorrowTenderCpn(newBorrowTender);
        return borrowTenderCnt2;
    }

    /**
     * 计算体验金收益
     * @param account
     * @param borrowApr
     * @param couponProfitTime
     * @return
     */
    private BigDecimal getInterestTYJ(BigDecimal account, BigDecimal borrowApr,Integer couponProfitTime) {
        BigDecimal interest = BigDecimal.ZERO;
        // 保留两位小数（去位）
        // 应回款=体验金面值*出借标的年化/365*收益期限（体验金发行配置）
        interest = account.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP))
                .divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(couponProfitTime))
                .setScale(2, BigDecimal.ROUND_DOWN);
        return interest;
    }

    /**
     * 校验是否重复放款
     *
     * @param tenderNid
     * @return
     */
    private boolean checkCouponRecoverFirst(String tenderNid) {
        int count = this.borrowClient.countByExample(tenderNid);
        return count > 0 ? true : false;
    }
}
