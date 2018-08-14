package com.hyjf.am.trade.controller.transactiondemo;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.mq.transactionmq.AccountTProducer;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.common.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @author xiasq
 * @version TransactionServiceImpl, v0.1 2018/6/27 14:06
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	private Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Autowired
	AccountTProducer accountTProducer;
    @Autowired
    AccountService accountService;

	@Override
	public void commitAmount(int userId) throws MQException {
		boolean commitFlag = accountTProducer
				.messageSend(new AccountTProducer.MassageContent("userTransationTest1" , UUID.randomUUID().toString(), JSON.toJSONBytes(userId)));
		if (!commitFlag) {
			throw new RuntimeException("事务消息发送失败...");
		}
	}

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAmount(int userId) {
        Account account = accountService.getAccount(userId);
        if (account == null) {
            throw new RuntimeException("找不到用户账户信息，userId is : " + userId);
        }
        account.setBankBalance(new BigDecimal(1000000));
        accountService.updateOfRTBLoansTender(account);
    }
}
