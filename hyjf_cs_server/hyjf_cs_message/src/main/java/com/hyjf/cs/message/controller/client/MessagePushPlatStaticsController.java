/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.am.vo.admin.MessagePushPlatStaticsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.service.message.MessagePushPlatStaticsService;
import com.hyjf.cs.message.service.message.MessagePushTemplateStaticsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author fq
 * @version MessagePlatStaticsController, v0.1 2018/8/14 16:21
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/messagePlatStatics")
public class MessagePushPlatStaticsController extends BaseController {
    @Autowired
    private MessagePushPlatStaticsService service;
    @Autowired
	private MessagePushTemplateStaticsService templateStaticsService;

    /**
     * 根据条件查询平台消息统计报表
     * @param request
     * @return
     */
	@RequestMapping("/selectPlatPtatics")
	public MessagePushPlatStaticsResponse selectPlatStatics(@RequestBody MessagePushPlatStaticsRequest request) {
		MessagePushPlatStaticsResponse response = new MessagePushPlatStaticsResponse();
		List<MessagePushPlatStatics> list = service.selectPlatStatics(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushPlatStaticsVO> voList = CommonUtils.convertBeanList(list, MessagePushPlatStaticsVO.class);
			for (MessagePushPlatStaticsVO vo : voList) {
				if (StringUtils.isNotBlank(vo.getTagId())) {
					String tagName = templateStaticsService.selectTagName(vo.getTagId());
					vo.setTagName(tagName);
				}
				vo.setStaDate(vo.getStaDate().substring(0, 10));
			}
			response.setResultList(voList);
		}
		// 查询数量
		int count = service.selectCount(request);
		response.setCount(count);
		return response;
	}
}
