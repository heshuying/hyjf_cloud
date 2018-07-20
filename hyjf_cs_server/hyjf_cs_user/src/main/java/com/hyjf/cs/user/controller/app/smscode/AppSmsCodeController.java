package com.hyjf.cs.user.controller.app.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author xiasq
 * @version WebSmsCodeController, v0.1 2018/4/25 9:01
 */
@Api(value = "app端验证码",description = "app端-验证码")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppSmsCodeController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(AppSmsCodeController.class);

    @Autowired
    private SmsCodeService smsCodeService;


    /**
     * 验证验证码
     *
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "app验证验证码",notes = "验证验证码")
    @PostMapping(value = "/validateVerificationCodeAction", produces = "application/json; charset=utf-8")
    public JSONObject validateVerificationCodeAction(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/validateVerificationCodeAction");
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 验证方式
        String verificationType = request.getParameter("verificationType");
        // 验证码
        String verificationCode = request.getParameter("verificationCode");
        // 手机号
        String mobile = request.getParameter("mobile");
        // 取得加密用的Key
        String key = SecretUtil.getKey(sign);
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if (Validator.isNull(verificationType)) {
            ret.put("status", "1");
            ret.put("statusDesc", "验证码类型不能为空");
            return ret;
        }
        if (Validator.isNull(verificationCode)) {
            ret.put("status", "1");
            ret.put("statusDesc", "验证码不能为空");
            return ret;
        }
        if (!(verificationType.equals(CommonConstant.PARAM_TPL_ZHUCE) || verificationType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA) || verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH) || verificationType.equals(CommonConstant.PARAM_TPL_YZYSJH))) {
            ret.put("status", "1");
            ret.put("statusDesc", "无效的验证码类型");
            return ret;
        }
        mobile = DES.decodeValue(key, mobile);
        int cnt = smsCodeService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, CommonConstant.CKCODE_NEW, CommonConstant.CKCODE_YIYAN);
        CheckUtil.check(cnt > 0, MsgEnum.ERR_OBJECT_INVALID,"验证码");
        return ret;
    }

    /**
     * 发送验证码
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "发送验证码",notes = "发送验证码")
    @PostMapping(value = "/sendVerificationCodeAction")
    public JSONObject sendVerificationCodeAction(@RequestHeader(value = "token", required = false) String token,HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        //业务授权码
        ret.put("bankCode", "");
        ret.put("request", "/hyjf-app/appUser/sendVerificationCodeAction");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 验证码类型
        String verificationType = request.getParameter("verificationType");
        // 手机号
        String mobile = request.getParameter("mobile");

        boolean isNeedUPdate = checkForAppUpdate(version, "此版本暂不可用，请更新至最新版本。", "/hyjf-app/appUser/sendVerificationCodeAction", ret);
        if(isNeedUPdate){
            return ret;
        }
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order)) {
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
        // 业务逻辑
        try {
            // 解密
            mobile = DES.decodeValue(key, mobile);
            if (Validator.isNull(verificationType)) {
                ret.put("status", "1");
                ret.put("statusDesc", "验证码类型不能为空");
                return ret;
            }
            if (Validator.isNull(mobile)) {
                ret.put("status", "1");
                ret.put("statusDesc", "手机号不能为空");
                return ret;
            }
            if (!Validator.isMobile(mobile)) {
                ret.put("status", "1");
                ret.put("statusDesc", "请输入您的真实手机号码");
                return ret;
            }
            smsCodeService.appSendSmsCodeCheckParam(verificationType, mobile, token, GetCilentIP.getIpAddr(request));
            smsCodeService.sendSmsCode(verificationType, mobile, platform, token, GetCilentIP.getIpAddr(request));
        }catch (Exception e){
            ret.put("status", "1");
            ret.put("statusDesc", "发送验证码失败");
        }
        return ret;

    }
    /**
     *
     * 校验app是否要提示更新
     * @author hsy
     * @param version
     * @param desc
     * @param requestUri
     * @param info
     * @return
     */
    public static boolean checkForAppUpdate(String version, String desc, String requestUri, JSONObject info){
        if(StringUtils.isEmpty(version)){
            info.put("status", "1");
            info.put("statusDesc", desc);
            info.put(CustomConstants.APP_REQUEST, requestUri);
            return true;
        }

        if(version.length()>=5){
            version = version.substring(0, 5);
        }

        if(version.compareTo("1.4.0")<=0){
            info.put("status", "1");
            info.put("statusDesc", desc);
            info.put(CustomConstants.APP_REQUEST, requestUri);
            return true;
        }

        return false;
    }
}
