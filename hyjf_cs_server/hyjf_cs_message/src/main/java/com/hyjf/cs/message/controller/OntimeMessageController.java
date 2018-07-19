/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.SmsOntime;
import com.hyjf.cs.message.service.message.MessageService;

/**
 * @author fuqiang
 * @version OntimeMessageController, v0.1 2018/6/22 10:32
 */
@RestController
@RequestMapping("/cs-message/message")
public class OntimeMessageController extends BaseController {

	/** 任务状态:未执行 */
	private static final Integer STATUS_WAIT = 0;

	@Autowired
	private MessageService messageService;

	/**
	 * 定时发短信
	 */
	@RequestMapping("/ontimeMessage")
	public void ontimeMessage() {
		List<SmsOntime> listApicron = messageService.getOntimeList(STATUS_WAIT);
		if (!CollectionUtils.isEmpty(listApicron)) {
			for (SmsOntime apicron : listApicron) {
				try {
					messageService.sendMessage(apicron);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
