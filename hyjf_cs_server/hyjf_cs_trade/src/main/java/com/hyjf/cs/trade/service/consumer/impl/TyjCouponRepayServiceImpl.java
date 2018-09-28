/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.SmsProducer;
import com.hyjf.cs.trade.service.consumer.TyjCouponRepayService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author yaoyong
 * @version TyjCouponRepayServiceImpl, v0.1 2018/8/13 11:57
 */
@Service
public class TyjCouponRepayServiceImpl implements TyjCouponRepayService {
    private static final Logger logger = LoggerFactory.getLogger(TyjCouponRepayServiceImpl.class);

    /**
     * 用户ID
     */
    private static final String USERID = "userId";
    /**
     * 还款金额(优惠券用)
     */
    private static final String VAL_AMOUNT = "val_amount";
    /**
     * 优惠券类型
     */
    private static final String VAL_COUPON_TYPE = "val_coupon_type";
    /**
     * 优惠券还款类别 1：直投类
     */
    private static final Integer RECOVER_TYPE_HZT = 1;
    /**
     * 优惠券还款类别 2：汇添金
     */
    private static final Integer RECOVER_TYPE_HTJ = 2;

    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private CsMessageClient csMessageClient;
    @Autowired
    private SmsProducer smsProducer;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    @Override
    public void updateCouponOnlyRecover(String nid) throws Exception {
        String methodName = "updateCouponOnlyRecover";
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        Map<String, String> msg = new HashMap<String, String>();
        retMsgList.add(msg);
        logger.info("还款开始，投资编号：" + nid);
        Integer nowTime = GetDate.getNowTime10();
        // 当前还款
        CouponRecoverCustomizeVO currentRecover = null;
        // [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
        // [endday: 按天计息, end:按月计息]
        currentRecover = this.getCurrentCouponRecover(nid, 1);
        if (currentRecover == null) {
            logger.info("优惠券还款数据不存在。[优惠券投资编号：" + nid + "]");
            return;
        }
        // 取得优惠券投资信息
        BorrowTenderCpnVO borrowTenderCpn = this.getCouponTenderInfo(nid);
        if (borrowTenderCpn == null) {
            logger.info("投资编号不存在：" + nid);
            return;
        }
        Integer tenderUserId = borrowTenderCpn.getUserId();
        String borrowNid = borrowTenderCpn.getBorrowNid();
        // 投资人在银行存管的账户信息
        BankOpenAccountVO bankOpenAccountInfo = this.getBankOpenAccount(tenderUserId);
        if (bankOpenAccountInfo == null) {
            throw new Exception("投资人未开户。[用户ID：" + tenderUserId + "]，" + "[优惠券投资编号：" + nid + "]");
        }
        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);
        CouponRecoverVO cr = new CouponRecoverVO();
        cr.setId(currentRecover.getId());
        String orderId = GetOrderIdUtils.getOrderId2(borrowTenderCpn.getUserId());
        String seqNo = GetOrderIdUtils.getSeqNo(6);

        UserInfoCustomizeVO userInfoCustomize = this.queryUserInfoByUserId(tenderUserId);
        UserVO user = amUserClient.findUserById(tenderUserId);

        CouponRecoverCustomizeRequest request = new CouponRecoverCustomizeRequest();
        request.setBankOpenAccountVO(bankOpenAccountInfo);
        request.setCouponRecoverVO(cr);
        request.setCurrentRecoverVO(currentRecover);
        request.setBorrowTenderCpn(borrowTenderCpn);
        request.setUser(user);
        request.setUserInfoCustomize(userInfoCustomize);
        request.setOrderId(orderId);
        request.setRecoverAccount(recoverAccount);
        request.setSeqNo(seqNo);
        request.setTenderUserId(tenderUserId);
        request.setRecoverNid(nid);
        request.setRecoverInterest(recoverInterest);
        request.setRecoverCapital(recoverCapital);
        boolean result = amTradeClient.updateCouponOnlyRecover(request);

        if (result) {
            // 插入网站收支明细记录
            AccountWebListVO accountWebList = new AccountWebListVO();
            // 未分期
            accountWebList.setOrdid(borrowTenderCpn.getNid());// 订单号
            // 直投类
            if (borrowTenderCpn.getTenderType() == RECOVER_TYPE_HZT) {
                accountWebList.setBorrowNid(borrowNid); // 投资编号
            }
            accountWebList.setUserId(tenderUserId); // 投资者
            accountWebList.setAmount(Double.valueOf(recoverAccount.toString())); // 优惠券投资受益
            accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出
            String remark = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                remark = "体验金：" + currentRecover.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 2) {
                remark = "加息券：" + currentRecover.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 3) {
                remark = "代金券：" + currentRecover.getCouponUserCode();
            }
            String tradeType = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                // 体验金
                accountWebList.setTrade(CustomConstants.TRADE_COUPON_TYJ);
                // 体验金收益
                tradeType = CustomConstants.TRADE_COUPON_SY;
            } else if (currentRecover.getCouponType() == 2) {
                // 加息券
                accountWebList.setTrade(CustomConstants.TRADE_COUPON_JXQ);
                // 加息券回款
                tradeType = CustomConstants.TRADE_COUPON_HK;
            } else if (currentRecover.getCouponType() == 3) {
                // 代金券
                accountWebList.setTrade(CustomConstants.TRADE_COUPON_DJQ);
                // 代金券回款
                tradeType = CustomConstants.TRADE_COUPON_DJ;
            }
            accountWebList.setTradeType(tradeType); // 加息券回款
            accountWebList.setRemark(remark); // 投资编号
            accountWebList.setCreateTime(nowTime);
            int accountWebListCnt = insertAccountWebList(accountWebList);
            if (accountWebListCnt == 0) {
                throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[投资订单号：" + borrowTenderCpn.getNid() + "]");
            }
        }

    }

    /**
     * 根据订单编号取得该订单的还款列表
     *
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    private CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
        return this.amTradeClient.selectCurrentCouponRecover(couponTenderNid, periodNow);
    }

    /**
     * 取得优惠券投资信息
     *
     * @param couponTenderNid
     * @return
     */
    private BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
        return amTradeClient.getCouponTenderInfo(couponTenderNid);
    }

    /**
     * 根据用户id获取开户信息
     *
     * @param userId
     * @return
     */
    private BankOpenAccountVO getBankOpenAccount(Integer userId) {
        return amUserClient.selectBankAccountById(userId);
    }

    /**
     * 优惠券还款 余额不足 短信通知
     */
    private void sendSmsFail(int couponType) {
        Map<String, String> replaceStrs = new HashMap<String, String>();
        try {
            if (2 == couponType) {
                replaceStrs.put("couponType", "加息券");
            } else if (3 == couponType) {
                replaceStrs.put("couponType", "代金券");
            } else if (1 == couponType) {
                replaceStrs.put("couponType", "体验金");
            }
            SmsMessage smsMessage =
                    new SmsMessage(null, replaceStrs, null, null,
                            "smsSendForManager", null, CustomConstants.PARAM_TPL_COUPON_JIA_YUE, CustomConstants.CHANNEL_TYPE_NORMAL);
            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));

        } catch (Exception e) {
            logger.debug(this.getClass().toString(), "sendSmsFail", e.getMessage());
        }
    }

    /**
     * 查询投资用户详情
     *
     * @param userId
     * @return
     */
    public UserInfoCustomizeVO queryUserInfoByUserId(Integer userId) {
        return amUserClient.queryUserInfoCustomizeByUserId(userId);
    }

    /**
     * 插入网站收支记录
     *
     * @param accountWebList
     * @return
     */
    private int insertAccountWebList(AccountWebListVO accountWebList) {
        // 设置部门信息
        setDepartments(accountWebList);
        // 插入
        return this.csMessageClient.insertAccountWebList(accountWebList);
    }

    /**
     * 设置部门名称
     *
     * @param accountWebList
     */
    private void setDepartments(AccountWebListVO accountWebList) {
        if (accountWebList != null) {
            Integer userId = accountWebList.getUserId();
            UserInfoVO usersInfo = amUserClient.findUsersInfoById(userId);
            if (usersInfo != null) {
                Integer attribute = usersInfo.getAttribute();
                if (attribute != null) {
                    // 查找用户的的推荐人
                    UserVO user = amUserClient.findUserById(userId);
                    SpreadsUserVO spreadsUserVO = amUserClient.querySpreadsUsersByUserId(userId);
                    Integer refUserId = spreadsUserVO.getSpreadsUserId();
                    // 如果是线上员工或线下员工，推荐人的userId和username不插
                    if (user != null && (attribute == 2 || attribute == 3)) {
                        // 查找用户信息
                        EmployeeCustomizeVO employeeCustomize = amUserClient.selectEmployeeByUserId(userId);
                        if (employeeCustomize != null) {
                            accountWebList.setRegionName(employeeCustomize.getRegionName());
                            accountWebList.setBranchName(employeeCustomize.getBranchName());
                            accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                        }
                    }
                    // 如果是无主单，全插
                    else if (user != null && (attribute == 1)) {
                        // 查找用户推荐人
                        EmployeeCustomizeVO employeeCustomize = amUserClient.selectEmployeeByUserId(refUserId);
                        if (employeeCustomize != null) {
                            accountWebList.setRegionName(employeeCustomize.getRegionName());
                            accountWebList.setBranchName(employeeCustomize.getBranchName());
                            accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                        }
                    }
                    // 如果是有主单
                    else if (user != null && (attribute == 0)) {
                        // 查找用户推荐人
                        EmployeeCustomizeVO employeeCustomize = amUserClient.selectEmployeeByUserId(refUserId);
                        if (employeeCustomize != null) {
                            accountWebList.setRegionName(employeeCustomize.getRegionName());
                            accountWebList.setBranchName(employeeCustomize.getBranchName());
                            accountWebList.setDepartmentName(employeeCustomize.getDepartmentName());
                        }
                    }
                }
                accountWebList.setTruename(usersInfo.getTruename());
                accountWebList.setFlag(1);
            }
        }
    }

}
