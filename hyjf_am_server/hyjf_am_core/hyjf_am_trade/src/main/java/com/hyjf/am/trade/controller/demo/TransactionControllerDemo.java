package com.hyjf.am.trade.controller.demo;

import com.hyjf.common.exception.MQException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version TransactionControllerDemo, v0.1 2018/6/27 13:46 分布式事务测试类 todo 定期清理
 *
 *          测试场景： 假设account表增加余额1000， 同时修改user表
 *
 *          解决方案： 在保证消费端幂等前提下，
 *          只要保证生产端事务消息一定推送成功即可，rocketmq能保证消息不丢失，但是不能保证不重复（概率很小），所以消费端要幂等
 *          增加本地事务消息推送表，推送成功修改状态，投递失败定时查询出来重新推送
 */
@RestController
@RequestMapping("/transaction")
public class TransactionControllerDemo {
	@Autowired
	TransactionService transactionService;

	@RequestMapping("/update/{userId}")
	public String testTransaction(@PathVariable int userId) {
		try {
			transactionService.commitAmount(userId);
		} catch (MQException e) {
			e.printStackTrace();
		}
		return "success";
	}
}
