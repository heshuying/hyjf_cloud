/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.msgpush;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistoryNew;
import com.hyjf.cs.message.service.msgpush.MsgPushService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fq
 * @version MessagePushController, v0.1 2018/7/25 9:06
 */
@Api(value = "消息推送", description = "消息推送")
@RestController
@RequestMapping("/hyjf-app/msgpush")
public class MessagePushController extends BaseController {
	@Autowired
	private MsgPushService msgPushService;

	@ApiOperation(value = "获取提醒列表", notes = "获取提醒列表")
	@RequestMapping("/getTagListAction")
	public JSONObject getTagListAction(@RequestHeader(value = "userId") Integer userId,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "pageSize", defaultValue = "10") int pageSize, HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/msgpush/getTagListAction");
		// 版本号
		String version = request.getParameter("version");
		// 平台
		String platform = request.getParameter("platform");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(platform) || Validator.isNull(sign)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		// 取得加密用的Key
		String key = SecretUtil.getKey(sign);
		if (Validator.isNull(key)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		ret.put("status", "0");
		ret.put("statusDesc", "成功");

		page = Integer.valueOf(page);
		pageSize = Integer.valueOf(pageSize);
		int count = msgPushService.countMsgHistoryRecord(1, userId, null);
		// int count = this.msgPushService.countMsgPushTagRecord(userId);
		// ret.put("title", "消息盒子");
		ret.put("count", count);
		int limitStart = pageSize * (page - 1);

		List<MessagePushMsgHistoryNew> msgTypeList = new ArrayList<MessagePushMsgHistoryNew>();
		try {
			List<MessagePushMsgHistory> list = msgPushService.getMsgHistoryList(1, userId, null, limitStart, pageSize);
			for (MessagePushMsgHistory msg : list) {
				MessagePushMsgHistoryNew tagbean = new MessagePushMsgHistoryNew();
				tagbean.setTitle(msg.getMsgTitle());
				tagbean.setId(msg.getId());
				tagbean.setTime(GetDate.timestamptoStrYYYYMMDDHHMM(msg.getSendTime().toString()));
				int versionInt = 0;
				String vers[] = version.split("\\."); // 取前三位版本号
				if (vers != null && vers.length > 0) {
					versionInt = Integer.parseInt(vers[0] + vers[1] + vers[2]);
				}
				String text = HtmlUtil.getTextFromHtml(msg.getMsgContent());
				if (versionInt < 135) {
					if (text.length() > 13) {
						text = text.substring(0, 11) + "…";
					}
				}
				// 消息内容
				tagbean.setIntroduction(text);
				msgTypeList.add(tagbean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			ret.put("status", "1");
			ret.put("statusDesc", "获取消息失败");
			return ret;
		}
		ret.put("msgTypeList", msgTypeList);
		return ret;
	}
}
