/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.dao.model.auto.BankCreditEnd;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.HjhDebtCredit;
import com.hyjf.am.trade.service.BankCreditEndService;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author liubin
 * @version BankCreditEndServiceImpl, v0.1 2018/7/6 18:37
 */
@Service
public class BankCreditEndServiceImpl extends BaseServiceImpl implements BankCreditEndService {


    @Override
    public int insertBankCreditEndForCreditEnd(HjhDebtCredit hjhDebtCredit, String tenderAccountId, String tenderAuthCode) {

        String borrowNid = hjhDebtCredit.getBorrowNid();
        Integer tenderUserId = hjhDebtCredit.getUserId();

        Borrow borrow = this.getBorrow(borrowNid);
        if (borrow == null) {
            throw new RuntimeException("结束债券接口：标的"+borrowNid+"不存在");
        }
        Account account = this.getAccount(borrow.getUserId());
        if (account == null) {
            throw new RuntimeException("结束债券接口：借款人"+borrow.getUserId()+"银行未开户");
        }

        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        logger.info(borrowNid+" 直投还款结束债权  借款人: "+borrow.getUserId()+"-"+account.getAccountId()+" 投资人: "+tenderUserId+"-"+tenderAccountId+" 授权码: "+tenderAuthCode+" 原始订单号: "+hjhDebtCredit.getSellOrderId());

        BankCreditEnd record = new BankCreditEnd();
        record.setUserId(borrow.getUserId());
        record.setTenderUserId(tenderUserId);
        record.setAccountId(account.getAccountId());
        record.setTenderAccountId(tenderAccountId);
        record.setOrderId(orderId);
        record.setBorrowNid(borrowNid);
        record.setAuthCode(tenderAuthCode);
        record.setCreditEndType(2); // 结束债权类型（1:还款，2:散标债转，3:计划债转）
        record.setStatus(0);
        record.setOrgOrderId(hjhDebtCredit.getSellOrderId());

        Date nowDate = GetDate.getDate();
        record.setCreateUser(tenderUserId);
        record.setCreateTime(nowDate);
        record.setUpdateUser(tenderUserId);
        record.setUpdateTime(nowDate);

        return this.bankCreditEndMapper.insertSelective(record);
    }
}
