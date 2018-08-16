package com.hyjf.am.trade.controller.transactiondemo;

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
