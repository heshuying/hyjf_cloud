/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.task.IncreaseInterestRepayService;
import com.hyjf.am.trade.service.task.IncreaseinterestLoansService;

/**
 * @author dxj
 * @version IncreaseInterestController, v0.1 2018/7/5 10:00
 */
@RestController
@RequestMapping("/am-trade/batch")
public class IncreaseInterestController extends BaseController {
	
	@Autowired
	IncreaseinterestLoansService increaseinterestLoansService;
	
	@Autowired
	IncreaseInterestRepayService increaseInterestRepayService;

	@GetMapping("/increaseinterestLoans")
	public Object increaseinterestLoans() {
		logger.info("请求原子层加息放款");

		return increaseinterestLoansService.loans();
	}

	@GetMapping("/increaseInterestRepay")
	public Object increaseInterestRepay() {
		logger.info("请求原子层加息还款");

		return increaseInterestRepayService.repay();
	}

}
