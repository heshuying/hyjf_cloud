/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.file.UploadFileUtils;
import com.hyjf.common.util.AppUserToken;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.SecretUtil;
import com.hyjf.common.util.SignValue;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.login.LoginService;
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
import java.util.Map;

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
    private LoginService loginService;

    @Autowired
    SystemConfig systemConfig;
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
        try {
            // 解密
            username = DES.decodeValue(key, username);
            password = DES.decodeValue(key, password);
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
            Map<String, String> errorInfo=loginService.insertErrorPassword(username,password,BankCallConstant.CHANNEL_APP);
            if (!errorInfo.isEmpty()){
                ret.put("status", "1");
                ret.put("statusDesc", errorInfo.get("info"));
                return ret;
            }
            //判断用户输入的密码错误次数---结束
            // 执行登录(登录时间，登录ip)
            WebViewUserVO webViewUserVO = loginService.login(username, password, GetCilentIP.getIpAddr(request), BankCallConstant.CHANNEL_APP);
            if (webViewUserVO != null) {
                logger.info("app端登录成功 userId is :{}", webViewUserVO.getUserId());

                this.appAfterLogin(sign, webViewUserVO, username);
                ret.put("status", "0");
                ret.put("statusDesc", "登录成功");
                ret.put("token", webViewUserVO.getToken());
                ret.put("sign", sign);

            } else {
                logger.error("app端登录失败...");
                ret.put("status", "1");
                ret.put("statusDesc", "app端登录失败");
            }
        }catch (Exception e){
            logger.error("app端登录失败...");
            ret.put("status", "1");
            ret.put("statusDesc", e.getMessage());
        }
        return ret;
    }

    /**
     * 用户退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "登出", notes = "登出")
    @PostMapping(value = "/loginOutAction")
    public JSONObject loginOutAction(@RequestHeader(value = "userId") Integer userId,@RequestHeader(value = "version") String version,@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response) {
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
                ret.put("status", "0");
                ret.put("params", userParameters);
                ret.put("statusDesc", "获取用户相关数据成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }

        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "获取用户相关数据发生错误");
        }
        return ret;
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
                String fileUploadTempPath = UploadFileUtils.getDoPath(systemConfig.getFileUpload());

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
     */
    private void appAfterLogin(String sign, WebViewUserVO webViewUserVO, String loginUsername){
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
            RedisUtils.set(RedisConstants.SIGN+sign, JSON.toJSONString(signValue), RedisUtils.signExpireTime);
        } else {
            throw new RuntimeException("参数异常");
        }
    }

}
