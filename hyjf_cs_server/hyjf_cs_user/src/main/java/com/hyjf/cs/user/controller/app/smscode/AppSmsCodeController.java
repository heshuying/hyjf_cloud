package com.hyjf.cs.user.controller.app.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.SmsRequest;
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
@Api(value = "验证码")
@RestController
@RequestMapping("/app/usr/sms")
public class AppSmsCodeController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(AppSmsCodeController.class);

    @Autowired
    private SmsCodeService smsCodeService;

    /**
     * 发送短信验证码 范围：注册，修改手机号(验证原手机，修改新手机)
     *
     * @param param
     * @param token
     * @param request
     * @return
     * @throws MQException
     */
    @PostMapping(value = "/send", produces = "application/json; charset=utf-8")
    @ApiImplicitParam(name = "param", value = "{validCodeType:string,mobile:string,platform:String}", dataType = "Map")
    public AppResult sendSmsCode(@RequestBody Map<String, String> param,
                                 @RequestHeader(value = "token", required = false) String token,
                                 HttpServletRequest request)
            throws MQException {
        logger.info("app端发送短信验证码接口, param is :{}", JSONObject.toJSONString(param));
        String validCodeType = param.get("validCodeType");
        String mobile = param.get("mobile");
        String platform = param.get("platform");
        AppResult resultBean = new AppResult();
        smsCodeService.sendSmsCode(validCodeType, mobile,platform, token, GetCilentIP.getIpAddr(request));
        return resultBean;
    }

    /**
     * 验证验证码
     *
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "app验证验证码",notes = "验证验证码")
    @PostMapping(value = "/verification", produces = "application/json; charset=utf-8")
    public JSONObject validateVerificationCodeAction(@RequestHeader String key, @RequestBody SmsRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/user/smsCode/verification");
        // 验证方式
        String verificationType = request.getVerificationType();
        // 验证码
        String verificationCode = request.getVerificationCode();
        // 手机号
        String mobile = request.getMobile();
       smsCodeService.appCheckParam(request, verificationType, verificationCode, mobile, key);
        int cnt = smsCodeService.updateCheckMobileCode(mobile, verificationCode, verificationType, request.getPlatform(), CommonConstant.CKCODE_NEW, CommonConstant.CKCODE_YIYAN);
        CheckUtil.check(cnt > 0, MsgEnum.ERR_OBJECT_INVALID,"验证码");
        return ret;
    }
}
