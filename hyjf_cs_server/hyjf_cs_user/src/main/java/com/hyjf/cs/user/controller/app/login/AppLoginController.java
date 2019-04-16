/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.login.LoginService;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.UserParameters;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @author zhangqingqing
 * @version LoginController, v0.1 2018/6/11 14:43
 */

@Api(value = "app端用户登录接口",tags = "app端-用户登录接口")
@RestController
@RequestMapping("/hyjf-app/appUser")
public class AppLoginController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppLoginController.class);

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private LoginService loginService;

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private PassWordService passWordService;
    /**
     * 登录
     *
     * @throws Exception
     */
    @ResponseBody
    @ApiOperation(value = "登录", notes = "登录")
    @PostMapping(value = "/loginInAction")
    public JSONObject loginInAction(@RequestHeader(value = "version") String version,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response){
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/loginInAction");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 用户名
        String username = request.getParameter("username");
        // 密码
        String password = request.getParameter("password");
        // 神策预置属性
        String presetProps = request.getParameter("presetProps");
        // 唯一标识
        String sign = request.getParameter("sign");
        loginService.checkForApp(version,platform,netStatus);
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "709");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
       // try {
            // 解密
            //logger.info("APP登录 ---> 解密前 key：{}，username：{}，password：{}", key, username, password);
            username = DES.decodeValue(key, username);
            password = DES.decodeValue(key, password);
            //logger.info("APP登录 ---> 解密后 username：{}，password：{}", username, password);
            if (Validator.isNull(username)) {
                ret.put("status", "1");
                ret.put("statusDesc", "用户名不能为空");
                return ret;
            }
            if (Validator.isNull(password)) {
                ret.put("status", "1");
                ret.put("statusDesc", "密码不能为空");
                return ret;
            }
            //判断用户输入的密码错误次数---开始
            UserVO userVO = loginService.getUser(username);
            Map<String, String> errorInfo=loginService.insertErrorPassword(username,password,BankCallConstant.CHANNEL_APP,userVO);
            if (!errorInfo.isEmpty()){
                ret.put("status", "1");
                ret.put("statusDesc", errorInfo.get("info"));
                return ret;
            }
            //判断用户输入的密码错误次数---结束
            // 执行登录(登录时间，登录ip)
            WebViewUserVO webViewUserVO = loginService.login(username, password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP,userVO);
            if (webViewUserVO != null) {
                logger.info("app端登录成功 userId is :{}", webViewUserVO.getUserId());
                //登录成功发送mq
                sign = doLoginAfter(version, request, platform, username, presetProps, sign, webViewUserVO);
                ret.put("status", "0");
                ret.put("statusDesc", "登录成功");
                ret.put("token", webViewUserVO.getToken());
                ret.put("sign", sign);

            } else {
                logger.error("app端登录失败...");
                ret.put("status", "1");
                ret.put("statusDesc", "app端登录失败");
            }
//        }catch (Exception e){
//            logger.error("app端登录失败...");
//            ret.put("status", "1");
//            ret.put("statusDesc", e.getMessage());
//        }
        return ret;
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "/loginOutAction")
    public JSONObject loginOutAction(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "version") String version,@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,@RequestHeader(value = "key") String key,HttpServletRequest request) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/loginOutAction");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(token) || Validator.isNull(randomString) || Validator.isNull(order)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
        try {
            if (userId != null) {
                clearMobileCode(userId,sign);
                UserInfoVO userInfoVO =  loginService.getUserInfo(userId);
                UserVO userVO = loginService.getUsersById(userId);
                UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
                userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE2);
                userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
                userOperationLogEntity.setPlatform(Integer.valueOf(platform));
                userOperationLogEntity.setRemark("");
                userOperationLogEntity.setOperationTime(new Date());
                userOperationLogEntity.setUserName(userVO.getUsername());
                userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
                } catch (MQException e) {
                    logger.error("保存用户日志失败", e);
                }
                // 移除sign
                if(version.substring(0,5).equals(systemConfig.getNewVersion()) && "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604".equals(sign)){
                    logger.info("sign不做移除");
                }else{
                    SecretUtil.clearToken(sign);
                }
                ret.put("status", "0");
                ret.put("statusDesc", "退出登录成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "退出登录发生错误");
        }
        return ret;
    }

    /**
     * 获取用户相关数据
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/getUserinfoAction")
    @ApiOperation(value = "获取用户相关数据",notes = "获取用户相关数据")
    public JSONObject getUserinfoAction(HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/getUserinfoAction");

        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // token
        String token = request.getParameter("token");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(token) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order)) {
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
            // 取得用户ID
            Integer userId = SecretUtil.getUserId(sign);
            if (userId != null) {
                UserParameters userParameters = loginService.getUserParameters(userId,platform, request);
                if (StringUtils.isBlank(userParameters.getIdcard()) || userParameters.getIdcard().length() < 15) {
                    userParameters.setIdcard("000000000000000000");
                }
                //用户角色 1、出借人 2、借款人 3、担保机构
                //1、出借人 -->自动投标、自动债转、服务费授权；
                if(userParameters.getRoleId().equals("1")){
                    //跳转授权须知页面的路径
                    userParameters.setPaymentAuthUrl(systemConfig.getAppFrontHost() + "/needs/authorization"+packageStr(request)+"&usertype="+0);
                    //用来兼容之前的版本
                    userParameters.setMergeAuthUrl(systemConfig.getAppFrontHost() + "/needs/authorization"+packageStr(request)+"&usertype="+0);
                    //跳转授权须知页面之前的弹框信息
                    userParameters.setPaymentAuthDesc("应合规要求，出借、提现等交易前需进行以下授权：\n自动投标，自动债转，服务费授权。");
                }else if (userParameters.getRoleId().equals("2")) {
                    //2、借款人 -->服务费授权、还款授权
                    userParameters.setRepayAuthUrl(systemConfig.getAppFrontHost() + "/needs/authorization"+packageStr(request)+"&usertype="+1);
                    userParameters.setPaymentAuthUrl(systemConfig.getAppFrontHost() + "/needs/authorization"+packageStr(request)+"&usertype="+1);
                    userParameters.setPaymentAuthDesc("应合规要求，借款、提现等交易前需进行以下授权：\n服务费授权，还款授权。");
                }else {
                    //3、担保机构 -->服务费授权
                    userParameters.setPaymentAuthUrl(systemConfig.getAppFrontHost() + "/needs/authorization"+packageStr(request)+"&usertype="+2);
                    userParameters.setPaymentAuthDesc("应合规要求，借款、提现等交易前需进行以下授权：\n服务费授权");
                }
                ret.put("status", "0");
                ret.put("params", userParameters);
                ret.put("statusDesc", "获取用户相关数据成功");

            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }

        } catch (Exception e) {
            logger.error("app授权获取用户相关数据异常",
                    "methodName："+ this.getClass().getName() + "methodPath："+ "/hyjf-app/appUser/getUserinfoAction",
                    e);
            ret.put("status", "1");
            ret.put("statusDesc", "获取用户相关数据发生错误");
        }
        return ret;
    }

    /**
     * 组装url
     * @param request
     * @return
     */
    private String packageStr(HttpServletRequest request) {
        StringBuffer sbUrl = new StringBuffer();
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // token
        String token = request.getParameter("token");
        // 唯一标识
        String sign = request.getParameter("sign");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");
        sbUrl.append("?").append("version").append("=").append(version);
        sbUrl.append("&").append("netStatus").append("=").append(netStatus);
        sbUrl.append("&").append("platform").append("=").append(platform);
        sbUrl.append("&").append("randomString").append("=").append(randomString);
        sbUrl.append("&").append("sign").append("=").append(sign);
        sbUrl.append("&").append("token").append("=").append(strEncode(token));
        sbUrl.append("&").append("order").append("=").append(strEncode(order));
        return sbUrl.toString();
    }

    /**
     * 上传头像 - 使用form-data提交方式的类似这个方法处理，网关有特殊处理
     *
     * @param
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "/uploadAvatarAction")
    @ApiOperation(value = "上传头像",notes = "上传头像")
    public JSONObject uploadAvatarAction(@RequestParam String sign,
                                         @RequestParam(value = "iconImg", required = false) MultipartFile iconImg) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/uploadAvatarAction");

        // 业务逻辑
        try {
            // 单位字节
            Long allowFileLength = 5000000L;

            // 取得用户ID
            Integer userId = SecretUtil.getUserId(sign);

            if (userId != null) {
                // 从配置文件获取上传文件的各个URL
                // 上传文件的CDNURL
                // 实际物理路径前缀1
                String filePhysicalPath = UploadFileUtils.getDoPath(systemConfig.getPhysicalPath());
                // 实际物理路径前缀2
                String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload(ClientConstants.APP_CLIENT));

                // 如果文件夹(前缀+后缀)不存在,则新建文件夹
                String logoRealPathDir = filePhysicalPath + fileUploadTempPath;
                File logoSaveFile = new File(logoRealPathDir);
                if (!logoSaveFile.exists()) {
                    logoSaveFile.mkdirs();
                }

                // 生成图片文件名
                String fileRealName = String.valueOf(System.currentTimeMillis());
                fileRealName = "appIconImg_" + userId + fileRealName + UploadFileUtils.getSuffix(iconImg.getOriginalFilename());

                // 上传至服务器
                String returnMessage = UploadFileUtils.upload4Stream(fileRealName, logoRealPathDir, iconImg.getInputStream(), allowFileLength);
                if (!"上传文件成功！".equals(returnMessage)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", returnMessage);
                    return ret;
                }
                // 保存到数据库的路径=上传文件的CDNURL+图片的文件名
                String iconUrl = fileRealName;
                // 保存到数据库
                loginService.updateUserIconImg(userId, iconUrl);
                String imghost = UploadFileUtils.getDoPath(systemConfig.getFileDomainUrl());
                imghost = imghost.substring(0, imghost.length() - 1);
                ret.put("iconUrl", imghost + fileUploadTempPath + fileRealName);
                ret.put("status", "0");
                ret.put("statusDesc", "头像上传成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "上传头像发生错误");
        }
        return ret;
    }

    private void clearMobileCode(Integer userId, String sign) {
        loginService.clearMobileCode(userId,sign);

    }


    /**
     * app登录成功后重置sign
     * @param sign
     * @param webViewUserVO
     * @param loginUsername
     * @param version
     */
    private String appAfterLogin(String sign, WebViewUserVO webViewUserVO, String loginUsername, String version){
        // 加密后的token
        String encryptValue;
        // 获取sign对应的加密key
        String value = RedisUtils.get(RedisConstants.SIGN+sign);
        SignValue signValue;
        if (StringUtils.isNotBlank(value)) {
            signValue = JSON.parseObject(value, SignValue.class);
            AppUserToken token = new AppUserToken(webViewUserVO.getUserId(), loginUsername);
            String encryptString = JSON.toJSONString(token);
            encryptValue = DES.encryptDES_ECB(encryptString, signValue.getKey());
            signValue.setToken(encryptValue);
            // 重新获取一个新sign(保证ios审核跳转最优服务器后可多用户登陆)
            if(version.substring(0,5).equals(systemConfig.getNewVersion()) && "6bcbd50a-27c4-4aac-b448-ea6b1b9228f43GYE604".equals(sign)) {
                sign = SecretUtil.createSign();
            }
            RedisUtils.set(RedisConstants.SIGN+sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);
            value =RedisUtils.get(RedisConstants.SIGN+sign);
            logger.info("更新sign:"+JSON.toJSONString(value));
        } else {
            throw new RuntimeException("参数异常");
        }
        return sign;
    }

    /**
     * 短信验证码登录  PC1.1.3 需求新增
     * @param version
     * @param key
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "短信验证码登录", notes = "登录")
    @PostMapping(value = "/mobileCodeLogin")
    public JSONObject mobileCodeLogin(@RequestHeader(value = "version") String version,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response){
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/mobileCodeLogin");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 用户名
        String username = request.getParameter("username");
        // 密码
        String smsCode = request.getParameter("smsCode");
        // 神策预置属性
        String presetProps = request.getParameter("presetProps");
        // 唯一标识
        String sign = request.getParameter("sign");
        loginService.checkForApp(version,platform,netStatus);
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(username) ) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "709");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        // try {
        // 解密
        logger.info("APP登录 ---> 解密前 key：{}，username：{}，smsCode：{}", key, username, smsCode);
        username = DES.decodeValue(key, username);
        smsCode = DES.decodeValue(key, smsCode);
        logger.info("APP登录 ---> 解密后 username：{}，smsCode：{}", username, smsCode);
        if (Validator.isNull(username)) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户名不能为空");
            return ret;
        }
        if(!Validator.isMobile(username)){
            ret.put("status", "1");
            ret.put("statusDesc", "手机号格式错误");
            return ret;
        }
        if (Validator.isNull(smsCode)) {
            ret.put("status", "1");
            ret.put("statusDesc", "验证码不能为空");
            return ret;
        }
        UserVO userVO = loginService.getUsersByMobile(username);
        Map<String, String> errorInfo=loginService.checkMobileCodeLogin(smsCode,BankCallConstant.CHANNEL_APP,userVO);
        if (!errorInfo.isEmpty()){
            ret.put("status", "1");
            ret.put("statusDesc", errorInfo.get("info"));
            return ret;
        }

        // 执行登录(登录时间，登录ip)
        WebViewUserVO webViewUserVO = loginService.loginByCode(username, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP,userVO);
        if (webViewUserVO != null) {
            logger.info("app端短信登录成功 userId is :{}", webViewUserVO.getUserId());
            sign = doLoginAfter(version, request, platform, username, presetProps, sign, webViewUserVO);
            // 一个用户用同一个值   如果已经存在  就用以前的
            String codeLoginKey = "";
            if(RedisUtils.exists(RedisConstants.APP_SMS_LOGIN_KEY+userVO.getUserId())){
                codeLoginKey = RedisUtils.get(RedisConstants.APP_SMS_LOGIN_KEY+userVO.getUserId());
            }else{
                codeLoginKey =  SecretUtil.createSign();
            }
            // 设置十三天内有效
            RedisUtils.set(RedisConstants.APP_SMS_LOGIN_KEY+userVO.getUserId(),codeLoginKey,60*60*24*13);
            ret.put("status", "0");
            ret.put("statusDesc", "登录成功");
            ret.put("token", webViewUserVO.getToken());
            ret.put("sign", sign);
            ret.put("codeLoginKey", codeLoginKey);

            // pc1.1.3 新增 如果短信登录成功 就解锁帐号锁定
            passWordService.unlockUser(userVO.getUserId());

        } else {
            logger.error("app端登录失败...");
            ret.put("status", "1");
            ret.put("statusDesc", "app端登录失败");
        }
        return ret;
    }

    /**
     * 登录成功后操作
     * @param version
     * @param request
     * @param platform
     * @param username
     * @param presetProps
     * @param sign
     * @param webViewUserVO
     * @return
     */
    private String doLoginAfter(String version, HttpServletRequest request, String platform, String username, String presetProps, String sign, WebViewUserVO webViewUserVO) {
        //登录成功发送mq
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE1);
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(webViewUserVO.getUsername());
        userOperationLogEntity.setUserRole(webViewUserVO.getRoleId());
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        logger.info("appAfterLogin:"+sign);
        // ios审核时跳转最优服务器的场景sign值重新获取
        sign = this.appAfterLogin(sign, webViewUserVO, username,version);

        if (StringUtils.isNotEmpty(presetProps)){
            SensorsDataBean sensorsDataBean = new SensorsDataBean();
            // 将json串转换成Bean
            try {
                Map<String, Object> sensorsDataMap = JSONObject.parseObject(presetProps, new TypeReference<Map<String, Object>>() {
                });
                sensorsDataBean.setPresetProps(sensorsDataMap);
                sensorsDataBean.setUserId(webViewUserVO.getUserId());
                sensorsDataBean.setEventCode("login");
                // 发送神策数据统计MQ
                this.loginService.sendSensorsDataMQ(sensorsDataBean);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return sign;
    }

    /**
     * 短信验证码登录之后  自动登陆用
     */

    @ResponseBody
    @ApiOperation(value = "短信验证码登录后自动登录", notes = "登录")
    @PostMapping(value = "/autoLoginByCode")
    public JSONObject autoLoginByCode(@RequestHeader(value = "version") String version,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response){
        JSONObject ret = new JSONObject();
        ret.put("request", "/appUser/mobileCodeLogin");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 用户名
        String username = request.getParameter("username");
        /**
         * 自动登陆用
         */
        String codeLoginKey = request.getParameter("codeLoginKey");

        // 神策预置属性
        String presetProps = request.getParameter("presetProps");
        // 唯一标识
        String sign = request.getParameter("sign");
        loginService.checkForApp(version,platform,netStatus);
        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || !Validator.isMobile(username) || Validator.isNull(codeLoginKey)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "709");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        // 业务逻辑
        // try {
        // 解密
        logger.info("APP登录 ---> 解密前 key：{}，username：{}，codeLoginKey：{}", key, username, codeLoginKey);
        username = DES.decodeValue(key, username);
        codeLoginKey = DES.decodeValue(key, codeLoginKey);
        logger.info("APP登录 ---> 解密后 username：{}，smsCode：{}", username, codeLoginKey);
        if (Validator.isNull(username)) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户名不能为空");
            return ret;
        }

        UserVO userVO = loginService.getUser(username);
        CheckUtil.check(userVO!=null,MsgEnum.ERR_USER_NOT_EXISTS);
        // 是否禁用
        if (userVO.getStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        String redisUserId = RedisUtils.get(RedisConstants.APP_SMS_LOGIN_KEY+userVO.getUserId());
        if(redisUserId==null || !(userVO.getUserId()+"").equals(redisUserId)){
            // 自动登录失败
            ret.put("status", "1");
            ret.put("statusDesc", "自动登录失败");
            return ret;
        }

        // 执行登录(登录时间，登录ip)
        WebViewUserVO webViewUserVO = loginService.loginByCode(username, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP,userVO);
        if (webViewUserVO != null) {
            logger.info("app端短信登录成功 userId is :{}", webViewUserVO.getUserId());
            sign = doLoginAfter(version, request, platform, username, presetProps, sign, webViewUserVO);
            // 设置七天内有效
            RedisUtils.set(RedisConstants.APP_SMS_LOGIN_KEY+userVO.getUserId(),codeLoginKey,60*60*24*13);
            ret.put("status", "0");
            ret.put("statusDesc", "登录成功");
            ret.put("token", webViewUserVO.getToken());
            ret.put("sign", sign);

        } else {
            logger.error("app端登录失败...");
            ret.put("status", "1");
            ret.put("statusDesc", "app端登录失败");
        }
        return ret;
    }
}
