package com.hyjf.am.trade.mq.transactionmq;

import com.hyjf.am.trade.dao.model.auto.Account;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.service.AccountService;
import com.hyjf.common.exception.MQException;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version UsereProducer, v0.1 2018/6/26 14:59
 */
@Component
public class UserTProducer extends TransactionProducer {
	private Logger logger = LoggerFactory.getLogger(UserTProducer.class);

	@Autowired
	AccountService accountService;

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup("user_transaction_group_test");
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		return super.messageSend(messageContent, (msg, arg) -> {

			Integer userId = JSON.parseObject(msg.getBody(), Integer.class);
			if (userId == null) {
				logger.error("更新user投资标志失败， 参数解析错误...");
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}

			// 本地事务
			try {
				Account account = accountService.getAccount(userId);
				if (account == null)
					throw new RuntimeException("找不到用户账户信息，userId is : " + userId);
				account.setBankBalance(new BigDecimal(1000000));
				accountService.updateOfRTBLoansTender(account);
			} catch (Exception e) {
				logger.error("更新user投资标志失败....", e);
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			logger.info("事务消息提交...");
			return LocalTransactionState.COMMIT_MESSAGE;
		});
	}

}
