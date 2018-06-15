package com.hyjf.cs.market.controller.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version DemoController, v0.1 2018/6/14 16:35
 */
@RestController
@RequestMapping("/batch")
public class DemoController {
	Logger logger = LoggerFactory.getLogger(DemoController.class);

	@RequestMapping("/demo")
	public void demo() throws InterruptedException {
		logger.info("DemoController exe..");
		Thread.sleep(6000 * 1000L);
        logger.info("DemoController end..");
	}
}
