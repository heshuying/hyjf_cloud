/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.news.config;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.news.config.AppNewsConfigService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author fq
 * @version AppNewsConfigController, v0.1 2018/7/25 15:25
 */
@Api(tags = "app端-开关闭推送服务")
@RequestMapping("/hyjf-app/news/config")
public class AppNewsConfigController extends BaseController {
	@Autowired
	private AppNewsConfigService appNewsConfigService;

	@PostMapping("/updateAppNewsConfig")
	public JSONObject updateAppNoticeConfig(HttpServletRequest request, HttpServletResponse response) {
		JSONObject ret = new JSONObject();
		ret.put("request", "/hyjf-app/news/config/updateAppNewsConfig");

		// 版本号
		String version = request.getParameter("version");
		// 网络状态
		String netStatus = request.getParameter("netStatus");
		// 平台
		String platform = request.getParameter("platform");
		// 唯一标识
		String sign = request.getParameter("sign");
		// 1打开/0关闭推送标识
		String ifReceiveNotice = request.getParameter("isOpen");

		// 检查参数正确性
		if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform)
				|| Validator.isNull(sign) || Validator.isNull(ifReceiveNotice)) {
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

		// 取得用户iD
		Integer userId = SecretUtil.getUserId(sign);
		UserVO users = new UserVO();
		users.setUserId(userId);
		users.setIfReceiveNotice(Integer.valueOf(ifReceiveNotice));

		int result = this.appNewsConfigService.updateAppNewsConfig(users);
		if (result == 1) {
			ret.put("status", 0);
			ret.put("statusDesc", "成功");
		} else {
			ret.put("status", 2);
			ret.put("statusDesc", "开关闭推送服务出现异常，请重新操作！");
		}

		return ret;

	}
}
