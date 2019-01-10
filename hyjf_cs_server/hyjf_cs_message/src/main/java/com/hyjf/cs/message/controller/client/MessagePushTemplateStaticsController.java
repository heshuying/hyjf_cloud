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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version MessagePushTemplateStaticsController, v0.1 2018/8/14 14:49
 */
@Api(tags = "app端-查询模板消息统计报表")
@RestController
@RequestMapping("/cs-message/messagePushTemplateStatics")
public class MessagePushTemplateStaticsController extends BaseController {
	@Autowired
	private MessagePushTemplateStaticsService staticsService;

	/**
	 * 查询模板消息统计报表
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询模板消息统计报表", notes = "查询模板消息统计报表")
	@PostMapping("/selectTemplateStatics")
	public MessagePushTemplateStaticsResponse selectTemplateStatics(
			@RequestBody MessagePushTemplateStaticsRequest request) {
		MessagePushTemplateStaticsResponse response = new MessagePushTemplateStaticsResponse();
		List<MessagePushTemplateStatics> list = staticsService.selectTemplateStatics(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushTemplateStaticsVO> voList = CommonUtils.convertBeanList(list,
					MessagePushTemplateStaticsVO.class);
			for (MessagePushTemplateStaticsVO vo : voList) {
				if (StringUtils.isNotBlank(vo.getTagId())) {
					String tagName = staticsService.selectTagName(vo.getTagId());
					vo.setTagName(tagName);
				}
			}
			response.setResultList(voList);
		}
		// 查询数量
		int count = staticsService.selectCount(request);
		response.setCount(count);
		return response;
	}
}
