package com.hyjf.cs.user.controller.api.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "api验证码",description = "api端-验证码")
@RestController
@RequestMapping("/hyjf-api/smsCode")
public class ApiSmsCodeController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(ApiSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 * @param param
	 *  @param token
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@ApiOperation(value = " 发送短信验证码",notes = " 发送短信验证码")
	@PostMapping(value = "/send", produces = "application/json; charset=utf-8")
	@ApiImplicitParam(name = "param",value = "{validCodeType:string,mobile:string,platform:String}", dataType = "Map")
	public ApiResult sendSmsCode(@RequestBody Map<String,String> param,
								 @RequestHeader(value = "token", required = false) String token,
								 HttpServletRequest request)
			throws MQException {
		logger.info("API端发送短信验证码接口, param is :{}", JSONObject.toJSONString(param));
		String validCodeType = param.get("validCodeType");
		String mobile = param.get("mobile");
		String platform = param.get("platform");
		ApiResult resultBean = new ApiResult();
		sendSmsCode.sendSmsCodeCheckParam(validCodeType, mobile, token, GetCilentIP.getIpAddr(request));
		sendSmsCode.sendSmsCode(validCodeType, mobile,platform, token, GetCilentIP.getIpAddr(request));
		return resultBean;
	}
}
