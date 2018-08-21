/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.MessagePushTemplateStaticsResponse;
import com.hyjf.am.resquest.message.MessagePushTemplateStaticsRequest;
import com.hyjf.am.vo.admin.MessagePushTemplateStaticsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePushTemplateStatics;
import com.hyjf.cs.message.service.message.MessagePushTemplateStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author fq
 * @version MessagePushTemplateStaticsController, v0.1 2018/8/14 14:49
 */
@RequestMapping("/cs-message/messagepush_template_statics")
public class MessagePushTemplateStaticsController extends BaseController {
	@Autowired
	private MessagePushTemplateStaticsService staticsService;

	/**
	 * 查询模板消息统计报表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/select_template_statics")
	public MessagePushTemplateStaticsResponse selectTemplateStatics(
			@RequestBody MessagePushTemplateStaticsRequest request) {
		MessagePushTemplateStaticsResponse response = new MessagePushTemplateStaticsResponse();
		List<MessagePushTemplateStatics> list = staticsService.selectTemplateStatics(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushTemplateStaticsVO> voList = CommonUtils.convertBeanList(list,
					MessagePushTemplateStaticsVO.class);
			response.setResultList(voList);
		}
		return response;
	}
}
