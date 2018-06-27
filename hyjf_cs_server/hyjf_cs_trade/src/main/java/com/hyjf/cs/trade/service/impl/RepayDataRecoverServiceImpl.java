/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.CouponTenderCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.service.RepayDataRecoverService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yaoy
 * @version RepayDatarecoverServiceImpl, v0.1 2018/6/25 11:25
 */
@Service
public class RepayDataRecoverServiceImpl implements RepayDataRecoverService {
    private static final Logger logger = LoggerFactory.getLogger(RepayDataRecoverServiceImpl.class);

    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    BorrowClient borrowClient;
    @Autowired
    BorrowTenderCpnClient borrowTenderCpnClient;
    @Autowired
    BankOpenClient bankOpenClient;
    @Autowired
    AccountClient accountClient;
    @Autowired
    AccountListClient accountListClient;

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid) {
        // 1 项目到期还款  2 收益期限到期还款
        int repayTimeConfig = 1;
        return amTradeClient.selectCouponRecoverAll(borrowNid,repayTimeConfig);
    }

    @Override
    public void couponRepayDataRecover(String borrowNid, int periodNow, CouponTenderCustomizeVO ct) {
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        Map<String, String> msg = new HashMap<String, String>();
        retMsgList.add(msg);
        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        /** 标的基本数据 */
        // 取得借款详情
        BorrowVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        // 还款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 剩余还款期数
        Integer periodNext = borrowPeriod - periodNow;
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 投资人用户ID
        Integer tenderUserId = null;
        // 投资人用户ID
        tenderUserId = Integer.valueOf(ct.getUserId());
        String couponTenderNid = ct.getOrderId();
        // 取得优惠券投资信息
        BorrowTenderCpnVO borrowTenderCpn = borrowTenderCpnClient.getCouponTenderInfo(couponTenderNid);
        // 投资人在银行存管的账户信息
        BankOpenAccountVO bankOpenAccountInfo = bankOpenClient.selectById(tenderUserId);
        if (bankOpenAccountInfo == null || StringUtils.isBlank(bankOpenAccountInfo.getAccount())) {
            throw new RuntimeException("投资人未开户。[用户ID：" + tenderUserId + "]，" + "[优惠券投资编号：" + couponTenderNid + "]");
        }

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        // 当前还款
        CouponRecoverCustomizeVO currentRecover = null;
        // [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
        if (isMonth) {
            // 取得分期还款
            currentRecover = amTradeClient.getCurrentCouponRecover(couponTenderNid, periodNow);

        } else {// [endday: 按天计息, end:按月计息]
            currentRecover = amTradeClient.getCurrentCouponRecover(couponTenderNid, 1);

        }
        if (currentRecover == null) {
            logger.info("优惠券还款数据不存在。[借款编号：" + borrowNid + "]，" + "[优惠券投资编号：" + couponTenderNid + "]");
            //throw new Exception("优惠券还款数据不存在。[借款编号：" + borrowNid + "]，" + "[优惠券投资编号：" + couponTenderNid + "]");
            return;
        }

        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        String seqNo = GetOrderIdUtils.getSeqNo(6);

        // 取得账户信息(投资人)
        AccountVO account = new AccountVO();
        account = accountClient.getAccountByUserId(tenderUserId);
        if (account == null) {
            throw new RuntimeException("投资人账户信息不存在。[投资人ID：" + borrowTenderCpn.getUserId() + "]，" + "[优惠券投资编号：" + couponTenderNid + "]");
        }
        //判断该收支明细是否存在(加息券)
        int count = accountListClient.countAccountListByNidCoupon(orderId);
        // 判断该收支明细是否存在时,跳出本次循环
        if (count == 0) {
            this.amTradeClient.repayDataRecover(currentRecover,bankOpenAccountInfo,ct.getUserId(),ct.getCouponUserCode(),borrow.getAddip(),count);
        }
        this.amTradeClient.updateRepayDataRecover(currentRecover,borrowTenderCpn,borrowNid,ct.getCouponUserCode(),ct.getUserId(),borrowStyle,periodNow);
    }
}
