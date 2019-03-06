/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.msgpush;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.http.HtmlUtil;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.MsgPushVO;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistory;
import com.hyjf.cs.message.bean.mc.MessagePushMsgHistoryNew;
import com.hyjf.cs.message.service.msgpush.MsgPushService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fq
 * @version MessagePushController, v0.1 2018/7/25 9:06
 */
@Api(tags = "app端-消息推送")
@RestController
@RequestMapping("/hyjf-app/msgpush")
public class MessagePushController extends BaseController {
	@Value("${hyjf.web.host}")
	private String webHost;
	@Autowired
	private MsgPushService msgPushService;

	@ApiOperation(value = "获取提醒列表", notes = "获取提醒列表")
	@PostMapping("/getTagListAction")
	public JSONObject getTagListAction(@RequestHeader(value = "userId") Integer userId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
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

		ret.put("status", "0");
		ret.put("statusDesc", "成功");

		int count = msgPushService.countMsgHistoryRecord(1, userId, null);
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
				// 取前三位版本号
				String vers[] = version.split("\\.");
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
			logger.error(e.getMessage());
			ret.put("status", "1");
			ret.put("statusDesc", "获取消息失败");
			return ret;
		}
		ret.put("msgTypeList", msgTypeList);
		return ret;
	}

	@ApiOperation(value = "获取通知列表", notes = "获取通知列表")
	@PostMapping("/getMsgListAction")
	public JSONObject getMsgListAction(
			@RequestHeader(value = "userId") Integer userId,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize, HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/msgpush/getMsgListAction");
		// 版本号
		String version = request.getParameter("version");
		// 平台
		String platform = request.getParameter("platform");
		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(platform)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}

		ret.put("status", "0");
		ret.put("statusDesc", "成功");
		// 获取标签信息
		// 查询列表数量
		//int count = msgPushService.countMsgHistoryRecord(0, userId, null);

		// 返回列表
		List<MsgPushVO> msgPushList = new ArrayList<MsgPushVO>();
		int limitStart = pageSize * (page - 1);
		try {
			List<MessagePushMsgHistory> list = msgPushService.getMsgHistoryList(0, userId, null, limitStart, pageSize);
			boolean firstFlag = false;
			if (page <= 1) {
				firstFlag = true;
			}
			for (MessagePushMsgHistory msg : list) {
				MsgPushVO msgPushVO = new MsgPushVO();
				msgPushVO.setMsgTitle(msg.getMsgTitle());
				msgPushVO.setMsgContent(msg.getMsgContent().replaceAll("</?[^>]+>", ""));
				msgPushVO.setActionUrl(msg.getMsgActionUrl());
				msgPushVO.setMsgId(String.valueOf(msg.getId()));
				msgPushVO.setMsgAction(String.valueOf(msg.getMsgAction()));
				// 如果发送时间大于 时间戳 则为今天的数据
				msgPushVO.setTime(GetDate.timestamptoStrYYYYMMDDHHMM(msg.getSendTime().toString()));
				msgPushVO.setMsgType(String.valueOf(msg.getMsgDestinationType()));

				// 标识第一条通知数据返回大图片
				if (firstFlag) {
					String webhost = UploadFileUtils.getDoPath(webHost) + "/hyjf-app".substring(1, "/hyjf-app".length())
							+ "/";
					webhost = webhost.substring(0, webhost.length() - 1);
					msgPushVO.setImgUrl(webhost + msg.getMsgImageUrl());
					firstFlag = false;
				}
				// 屏蔽了原始代码，这里统一设置已读标志为已读
				msgPushVO.setReadFlag("1");
				// if(msg.getMsgReadCountAndroid() > 0){
				// msgbean.setReadFlag("1");
				// }else if(msg.getMsgReadCountIos() > 0){
				// msgbean.setReadFlag("1");
				// }else{
				// msgbean.setReadFlag("0");
				// }
				msgPushList.add(msgPushVO);
			}
		} catch (Exception e) {
			ret.put("status", "1");
			ret.put("statusDesc", "获取通知列表失败");
			return ret;
		}
		// 暂时屏蔽等产品需求修改完成后返回正确数据
		// ret.put("count", count);
		// ret.put("msgList", msgPushList);
		ret.put("count", 0);
		ret.put("msgList", new ArrayList<MsgPushVO>());
		return ret;
	}

	@ApiOperation(value = "消息标识已读", notes = "消息标识已读")
	@PostMapping("/alreadyReadAction")
	public JSONObject alreadyReadAction(HttpServletRequest request, @RequestHeader(value = "userId")Integer userId) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/msgpush/alreadyReadAction");
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
		ret.put("status", "0");
		ret.put("statusDesc", "成功");
		this.msgPushService.updateAllMsgPushMsgHistory(userId, platform);
		return ret;
	}

	@ApiOperation(value = "消息及消息推送已读", notes = "消息及消息推送已读")
	@GetMapping("/msgReadAction")
	public JSONObject msgReadAction(HttpServletRequest request, HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/msgpush/getMsgListAction");
		// 版本号
		String version = request.getParameter("version");
		// 平台
		String platform = request.getParameter("platform");
		// 唯一标识
		String sign = request.getParameter("sign");
		//消息ID
		String msgIdStr = request.getParameter("msgId");
		logger.info("version:{},platform:{},sign{},msgIdStr:{}",version,platform,sign,msgIdStr);
		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(platform) ||  Validator.isNull(sign)||  Validator.isNull(msgIdStr)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}

		ret.put("status", "0");
		ret.put("statusDesc", "成功");
		//更新记录信息
		MessagePushMsgHistory msgHistory = this.msgPushService.getMsgPushMsgHistoryById(msgIdStr);
		if(msgHistory != null){
			//第一次阅读
			if(msgHistory.getMsgReadCountAndroid() == 0 && msgHistory.getMsgReadCountIos() == 0){
				msgHistory.setMsgFirstreadPlat(Integer.valueOf(platform));
			}
			if(platform.equals(CustomConstants.CLIENT_ANDROID)){
				msgHistory.setMsgReadCountAndroid(msgHistory.getMsgReadCountAndroid() + 1);

			}else if(platform.equals(CustomConstants.CLIENT_IOS)){
				msgHistory.setMsgReadCountIos(msgHistory.getMsgReadCountIos() + 1);
			}
			this.msgPushService.updateMsgPushMsgHistory(msgHistory);
		}
		return ret;
	}

	@ApiOperation(value = "通知详情页", notes = "通知详情页")
	@GetMapping("/msgDetailAction")
	public JSONObject msgDetailAction(HttpServletRequest request) {
		JSONObject ret = new JSONObject();
		// 唯一标识
		String sign = request.getParameter("sign");
		// 消息ID
		String msgIdStr = request.getParameter("msgId");

		// 检查参数正确性
		if (Validator.isNull(sign) || Validator.isNull(msgIdStr)) {
			ret.put("status", "1");
			ret.put("statusDesc", "请求参数非法");
			return ret;
		}
		// 获取记录信息
		MessagePushMsgHistory msgHistory = this.msgPushService.getMsgPushMsgHistoryById(msgIdStr);
		ret.put("msgHistory", msgHistory);
		ret.put("status", "0");
		ret.put("statusDesc", "成功");
		return ret;
	}

}
