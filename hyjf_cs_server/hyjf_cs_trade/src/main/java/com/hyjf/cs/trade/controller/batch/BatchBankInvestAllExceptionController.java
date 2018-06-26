package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.BatchBankInvestAllService;
import com.hyjf.cs.trade.service.BatchBankInvestService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 投资调单异常处理controller
 * 
 * @author jun
 * @since 20180623
 */
@Controller
@RequestMapping(value = "/cs-trade/investallexception")
public class BatchBankInvestAllExceptionController {

	Logger logger = LoggerFactory.getLogger(BatchBankInvestAllExceptionController.class);

	@Autowired
	private BatchBankInvestAllService batchBankInvestAllService;

	/**
	 * 处理债转投资异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "投资调单异常处理", notes = "投资调单异常处理")
	@RequestMapping(value = "/handle")
	public void handle() {
		logger.info("投资异常全部掉单跑批任务开始start...");
		batchBankInvestAllService.updateTender();
		logger.info("投资异常全部掉单跑批任务结束end...");
	}
}
