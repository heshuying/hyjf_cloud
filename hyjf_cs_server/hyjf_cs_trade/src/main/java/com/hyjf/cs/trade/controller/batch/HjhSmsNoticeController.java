package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.BatchBankInvestAllService;
import com.hyjf.cs.trade.service.BatchBankInvestService;
import com.hyjf.cs.trade.service.HjhSmsNoticeService;
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
@RequestMapping(value = "/cs-trade/hjhSmsNotice")
public class HjhSmsNoticeController {

	Logger logger = LoggerFactory.getLogger(HjhSmsNoticeController.class);

	@Autowired
	private HjhSmsNoticeService hjhSmsNoticeService;

	/**
	 * 处理债转投资异常
	 * 
	 * @return
	 */
	@ApiOperation(value = "标的还款逾期短信提醒", notes = "标的还款逾期短信提醒")
	@RequestMapping(value = "/overdueSmsNotice")
	public void overdueSmsNotice() {
		logger.info("投资异常全部掉单跑批任务开始start...");
		hjhSmsNoticeService.overdueSmsNotice();
		logger.info("投资异常全部掉单跑批任务结束end...");
	}
}
