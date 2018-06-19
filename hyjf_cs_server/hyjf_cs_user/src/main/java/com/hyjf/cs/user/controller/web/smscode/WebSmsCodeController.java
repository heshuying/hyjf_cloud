package com.hyjf.cs.user.controller.web.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author xiasq
 * @version WebSmsCodeController, v0.1 2018/4/25 9:01
 */
@Api(value = "验证码")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/smsCode")
public class WebSmsCodeController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 * @param param
	 * @param token
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@PostMapping(value = "/send", produces = "application/json; charset=utf-8")
	@ApiImplicitParam(name = "param",value = "{validCodeType:string,mobile:string}", dataType = "Map")
	public WebResult sendSmsCode(@RequestBody Map<String,String> param,
								 @RequestHeader(value = "token", required = false)String token,
								 HttpServletRequest request)
			throws MQException {
		logger.info("web端发送短信验证码接口, param is :{}", JSONObject.toJSONString(param));
		String validCodeType = param.get("validCodeType");
		String mobile = param.get("mobile");
		WebResult resultBean = new WebResult();
		sendSmsCode.sendSmsCode(validCodeType, mobile, token, GetCilentIP.getIpAddr(request));
		return resultBean;
	}
}
