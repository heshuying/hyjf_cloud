package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.HjhSmsNoticeService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 投资调单异常处理controller
 * 
 * @author jun
 * @since 20180623
 */
@ApiIgnore
@RestController
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
	@GetMapping(value = "/overdueSmsNotice")
	public String overdueSmsNotice() {
		logger.info("标的还款逾期短信提醒跑批任务开始start...");
		hjhSmsNoticeService.overdueSmsNotice();
		logger.info("标的还款逾期短信提醒跑批任务结束end...");
		return "SUCCESS";
	}
}
