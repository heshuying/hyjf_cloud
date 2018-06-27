/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.cs.trade.service.RtbBatchService;

/**
 * @author yaoy
 * @version RtbBatchController, v0.1 2018/6/13 11:17
 */
@RestController
@RequestMapping("/batch/rtb")
@Deprecated // 融通宝没有业务了  项目均已经到期
public class RtbBatchController {
	private static final Logger logger = LoggerFactory.getLogger(RtbBatchController.class);

	@Autowired
	RtbBatchService rtbBatchService;

	@RequestMapping("/increaseInterestRepay")
	public void rtbInterestRepay() {
		logger.info("融通宝加息还款开始....");
		rtbBatchService.execute();
	}

}
