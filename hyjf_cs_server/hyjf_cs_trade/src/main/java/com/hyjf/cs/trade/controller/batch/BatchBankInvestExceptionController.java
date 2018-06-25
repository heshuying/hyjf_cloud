package com.hyjf.cs.trade.controller.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.trade.service.BatchBankInvestService;

import io.swagger.annotations.ApiOperation;

/**
 * 投资调单异常处理controller
 * 
 * @author jun
 * @since 20180623
 */
@Controller
@RequestMapping(value = "/cs-trade/investexception")
public class BatchBankInvestExceptionController {

	Logger logger = LoggerFactory.getLogger(BatchBankInvestExceptionController.class);

	@Autowired
	private BatchBankInvestService batchBankInvestService;

	/**
	 * 处理债转投资异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "投资调单异常处理", notes = "投资调单异常处理")
	@RequestMapping(value = "/handle")
	public void handle() {
		logger.info("投资调单异常处理start...");
		batchBankInvestService.handle();
		logger.info("投资调单异常处理end...");
	}
}
