/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.hyjf.am.response.admin.MessagePushPlatStaticsResponse;
import com.hyjf.am.resquest.config.MessagePushPlatStaticsRequest;
import com.hyjf.am.vo.admin.MessagePushPlatStaticsVO;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePushPlatStatics;
import com.hyjf.cs.message.service.message.MessagePushPlatStaticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fq
 * @version MessagePlatStaticsController, v0.1 2018/8/14 16:21
 */
@RestController
@RequestMapping("/cs-message/messageplat_statics")
public class MessagePushPlatStaticsController extends BaseController {
    @Autowired
    private MessagePushPlatStaticsService service;

    /**
     * 根据条件查询平台消息统计报表
     * @param request
     * @return
     */
	@RequestMapping("/select_plat_statics")
	public MessagePushPlatStaticsResponse selectPlatStatics(@RequestBody MessagePushPlatStaticsRequest request) {
		MessagePushPlatStaticsResponse response = new MessagePushPlatStaticsResponse();
		List<MessagePushPlatStatics> list = service.selectPlatStatics(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<MessagePushPlatStaticsVO> voList = CommonUtils.convertBeanList(list, MessagePushPlatStaticsVO.class);
			response.setResultList(voList);
		}
		return response;
	}
}
