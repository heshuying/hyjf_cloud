package com.hyjf.am.trade.mq.transactionmq;

import com.hyjf.am.trade.controller.demo.ProducerTransactionMessageService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version TransactionCheckScheduler, v0.1 2018/6/28 18:53
 * rocketmq事务回查
 */
@Component
public class TransactionCheckScheduler {
	private Logger logger = LoggerFactory.getLogger(TransactionCheckScheduler.class);

	@Autowired
	private ProducerTransactionMessageService messageService;

	/**
	 * 每分钟执行一次
	 */
	@Scheduled(fixedRate = 60 * 1000)
	public void checkTransactionMessage() {
		try {
			messageService.callBackQuery();
		} catch (MQClientException e) {
			logger.error("rocketmq事务回查失败...", e);
		} catch (InterruptedException e) {
			logger.error("rocketmq事务回查失败...", e);
		} catch (RemotingException e) {
			logger.error("rocketmq事务回查失败...", e);
		} catch (MQBrokerException e) {
			logger.error("rocketmq事务回查失败...", e);
		}
	}
}
