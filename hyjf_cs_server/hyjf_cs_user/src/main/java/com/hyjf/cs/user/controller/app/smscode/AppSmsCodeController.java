package com.hyjf.cs.user.controller.app.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
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
@Api(tags = "app端-验证码")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppSmsCodeController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(AppSmsCodeController.class);

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    AmConfigClient amConfigClient;

    @Autowired
    AmUserClient amUserClient;


    /**
     * 验证验证码
     *
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "验证验证码",notes = "验证验证码")
    @PostMapping(value = "/validateVerificationCodeAction")
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
        int cnt = 0;
        // app4.0 注册验证码单独验证，不失效
        if(verificationType.equals(CommonConstant.PARAM_TPL_ZHUCE)){
            cnt = smsCodeService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, CommonConstant.CKCODE_NEW, CommonConstant.CKCODE_YIYAN,false);
        } else {
            cnt = smsCodeService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, CommonConstant.CKCODE_NEW, CommonConstant.CKCODE_YIYAN,true);
        }
        CheckUtil.check(cnt > 0, MsgEnum.STATUS_ZC000015);

        ret.put("status", "0");
        ret.put("statusDesc", CustomConstants.APP_STATUS_DESC_SUCCESS);
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

    /**
     * 发送验证码
     *
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "发送短信",notes = "发送短信")
    @PostMapping(value = "/sendVerificationCodeAction")
    public JSONObject sendVerificationCodeAction(@RequestHeader(value = "userId", required = false) Integer userId,
                                                 @RequestHeader(value = "key") String key,
                                                 @RequestHeader(value = "version") String version,
                                                 @RequestHeader(value = "platform") String platform,
                                                 HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("bankCode", "");
        ret.put("request", "/hyjf-app/appUser/sendVerificationCodeAction");

        // 验证码类型
        String verificationType = request.getParameter("verificationType");
        // 手机号
        String mobile = request.getParameter("mobile");

        boolean isNeedUPdate = checkForAppUpdate(version, "此版本暂不可用，请更新至最新版本。", "/hyjf-app/appUser/sendVerificationCodeAction", ret);
        if(isNeedUPdate){
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
            ret = smsCodeService.appSendSmsCodeCheckParam(verificationType, mobile, userId, GetCilentIP.getIpAddr(request));
            if(ret.get("status")!=null){
                return ret;
            }

            // PC1.1.3 新增 短信验证码登录
            if(verificationType.equals(CommonConstant.PARAM_TPL_DUANXINDENGLU)){

                UserVO userVO = smsCodeService.getUsersByMobile(mobile);
                CheckUtil.check(userVO!=null,MsgEnum.ERR_USER_NOT_EXISTS);
                smsCodeService.sendSmsCode(verificationType, mobile, platform, GetCilentIP.getIpAddr(request));
                ret.put("status", "0");
                ret.put("statusDesc", "发送验证码成功");

                return ret;
            }
            if(!verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH)){
                //判断用户是否登录
                //UserVO userVO = smsCodeService.getUsersById(userId);
                // 发送短信
                smsCodeService.sendSmsCode(verificationType, mobile, platform, GetCilentIP.getIpAddr(request));
                ret.put("status", "0");
                ret.put("statusDesc", "发送验证码成功");
            }else{
                // 判断是否开户  假如未开户  发送平台的验证码  假如已开户  发送江西银行的验证码
                BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
                if (bankAccount == null) {
                    // 未开户  发送平台验证码
                    // 发送短信
                    smsCodeService.sendSmsCode(verificationType, mobile, platform, GetCilentIP.getIpAddr(request));
                    ret.put("bankCode",  "");
                        ret.put("status", "0");
                        ret.put("statusDesc", "发送验证码成功");
                    return ret;
                }

                // 请求发送短信验证码
                BankCallBean bean = smsCodeService.callSendCode(userId,mobile, BankCallMethodConstant.TXCODE_MOBILE_MODIFY_PLUS, BankCallConstant.CHANNEL_APP,null);
                if(bean == null){
                    ret.put("status", "1");
                    ret.put("statusDesc","发送短信验证码异常");
                    return ret;
                }
                //返回失败
                if(!BankCallConstant.RESPCODE_SUCCESS.equals(bean.getRetCode())){
                    if(!Validator.isNull(bean.getSrvAuthCode())){
                        ret.put("status", "0");
                        ret.put("statusDesc", "发送验证码成功");
                        ret.put("bankCode", bean.getSrvAuthCode());
                        return ret;
                    }
                    ret.put("status", "1");
                    ret.put("statusDesc","发送短信验证码失败，失败原因：" + bean.getRetMsg());
                    return ret;
                }
                //成功返回业务授权码
                ret.put("status", "0");
                ret.put("statusDesc", "发送验证码成功");
                ret.put("bankCode", bean.getSrvAuthCode());
            }

        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", e.getMessage());
        }
        return ret;

    }


}
