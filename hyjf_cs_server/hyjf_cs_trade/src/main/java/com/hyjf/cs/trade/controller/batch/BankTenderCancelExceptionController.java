package com.hyjf.cs.trade.controller.batch;

import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyjf.cs.trade.service.BankTenderCancelExceptionService;

import org.slf4j.Logger;
/**
 * 投资撤销异常定时任务controller
 * @author jijun
 * @date 20180625
 */
@Controller
@RequestMapping(value = "/cs-trade/tendercancelexception")
public class BankTenderCancelExceptionController {
	Logger logger = LoggerFactory.getLogger(BankTenderCancelExceptionController.class);
	@Autowired
	private BankTenderCancelExceptionService bankTenderCancelExceptionService;
	/**
	 * 处理债转投资异常
	 * @return
	 */
	@ApiOperation(value = "投资撤销异常处理", notes = "投资撤销异常处理")
	@RequestMapping(value = "/handle")
	public void handle() {
		logger.info("投资撤销异常处理start...");
		bankTenderCancelExceptionService.handle();
		logger.info("投资撤销异常处理end...");
	}
}

