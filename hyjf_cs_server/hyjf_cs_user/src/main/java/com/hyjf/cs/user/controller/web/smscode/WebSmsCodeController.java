package com.hyjf.cs.user.controller.web.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.BaseResult;
import com.hyjf.am.vo.user.SmsCodeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
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
@Api(tags = "web端-验证码")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user/sms")
public class WebSmsCodeController extends BaseUserController {
	private static final Logger logger = LoggerFactory.getLogger(WebSmsCodeController.class);

	@Autowired
	private SmsCodeService sendSmsCode;
	@Autowired
	SystemConfig systemConfig;

	/**
	 * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
	 * @param param 修改手机号码验证原手机号：validCodeType=TPL_YZYSJH  绑定新手机号：validCodeType=TPL_BDYSJH
	 * @param
	 * @param request
	 * @return
	 * @throws MQException
	 */
	@ApiOperation(value = "发送验证码",notes = "发送验证码")
	@PostMapping(value = "/send", produces = "application/json; charset=utf-8")
	@ApiImplicitParam(name = "param",value = "{validCodeType:string,mobile:string,platform:String}", dataType = "Map")
	public WebResult sendSmsCode(@RequestBody Map<String,String> param,
								 @RequestHeader(value = "wjtClient",required = false) String wjtClient,
								 @RequestHeader(value = "userId", required = false) Integer userId,
								 HttpServletRequest request){
		logger.info("web端发送短信验证码接口, param is :{}", JSONObject.toJSONString(param));
		String validCodeType = param.get("validCodeType");
		String mobile = param.get("mobile");
		String platform = param.get("platform");
		WebResult resultBean = new WebResult();
		sendSmsCode.sendSmsCodeCheckParam(validCodeType, mobile, userId, GetCilentIP.getIpAddr(request));
		if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA)) {
			UserVO user = sendSmsCode.getUsersByMobile(mobile);
			if(user==null){
				throw new CheckException(MsgEnum.ERR_USER_NOT_EXISTS);
			}
			// 汇盈的用户不能登录温金投
			if(wjtClient!=null && (wjtClient.equals(ClientConstants.WJT_PC_CLIENT+"") || wjtClient.equals(ClientConstants.WJT_WEI_CLIENT+""))
					&& !user.getInstCode().equals(systemConfig.getWjtInstCode())){
				throw new CheckException(MsgEnum.ERR_USER_WJT_OPT_ERR);
			}
		}
		try {
			sendSmsCode.sendSmsCode(validCodeType, mobile,platform, GetCilentIP.getIpAddr(request));
		} catch (Exception e) {
			logger.error("web端发送短信验证码失败，失败原因: ", e);
			resultBean.setStatus(BaseResult.FAIL);
			resultBean.setStatusDesc(BaseResult.FAIL_DESC);
		}
		return resultBean;
	}

	/**
	 * 短信验证码校验
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
	@PostMapping(value = "/check", produces = "application/json; charset=utf-8")
	public WebResult checkCode(@RequestBody SmsCodeVO request) {
		logger.info("Web端短信验证码校验接口,SmsCodeVO  is :{}",JSONObject.toJSONString(request));
		WebResult result = new WebResult();
		String verificationType = request.getVerificationType();
		// 短信验证码
		String code = request.getVerificationCode();
		// 手机号码(必须,数字,最大长度)
		String mobile = request.getMobile();
		sendSmsCode.checkParam(verificationType,code,mobile);
		int cnt = sendSmsCode.updateCheckMobileCode(mobile, code, verificationType, CustomConstants.CLIENT_PC, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN,true);
		CheckUtil.check(cnt > 0,MsgEnum.STATUS_ZC000015);
		return  result;
	}
}
