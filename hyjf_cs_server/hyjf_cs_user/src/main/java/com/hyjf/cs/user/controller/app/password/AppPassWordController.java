/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.password;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.bankopen.BankOpenService;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangc
 */
@Api(value = "app端密码相关服务",tags = "app端-密码相关服务")
@RestController
@RequestMapping("/hyjf-app")
public class AppPassWordController extends BaseUserController {

    @Autowired
    BankOpenService bankOpenService;

    @Autowired
    PassWordService passWordService;

    @Autowired
    SystemConfig systemConfig;
    @Autowired
    private CommonProducer commonProducer;

    private static final Logger logger = LoggerFactory.getLogger(AppPassWordController.class);

    /**
     * 修改密码
     * @param key
     * @param userId
     * @param request
     * @return
     */
    @ApiOperation(value = "修改登陆密码", notes = "修改登陆密码")
    @ResponseBody
    @PostMapping(value = "/appUser/updatePasswordAction")
    public JSONObject updatePasswordAction(@RequestHeader(value = "key") String key, @RequestHeader(value = "userId") Integer userId, HttpServletRequest request) {

        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/updatePasswordAction");
        // 版本号
        String version = request.getParameter("version");
        // 网络状态
        String netStatus = request.getParameter("netStatus");
        // 平台
        String platform = request.getParameter("platform");
        // 唯一标识
        String sign = request.getParameter("sign");
        // token
        String token = request.getParameter("token");
        // 随机字符串
        String randomString = request.getParameter("randomString");
        // Order
        String order = request.getParameter("order");
        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 旧密码
        String oldPassword = request.getParameter("oldPassword");
        UserVO userVO = passWordService.getUsersById(userId);
        CheckUtil.check(null != userVO && null != userVO.getUserId(), MsgEnum.STATUS_CE000006);
        passWordService.appCheckParam(key, userVO, version, netStatus, platform, sign, token, randomString, order, newPassword, oldPassword);
        // 获取登录信息
        newPassword = DES.decodeValue(key, newPassword);
        try {
            boolean success = passWordService.updatePassWd(userVO, newPassword);
            if (success) {
                UserInfoVO userInfoVO =  passWordService.getUserInfo(userId);
                UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
                userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE7);
                userOperationLogEntity.setIp(com.hyjf.cs.user.util.GetCilentIP.getIpAddr(request));
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
                //如果修改密码成功或者重置密码就将登陆密码错误次数的key删除
                RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ALL + userVO.getUserId());
                ret.put("status", "0");
                ret.put("statusDesc", "修改密码成功");
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "修改密码失败");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "修改密码发生错误");
        }
        return ret;
    }

    /**
     * 设置交易密码
     * @param token
     * @param sign
     * @param request
     * @return
     */
    @ApiOperation(value = "设置交易密码", notes = "设置交易密码")
    @PostMapping(value = "/bank/user/transpassword/setPassword")
    public AppResult<Object> setPassword(@RequestHeader(value = "token") String token, @RequestHeader(value = "sign") String sign, HttpServletRequest request) {
        AppResult<Object> result = new AppResult<>();
        String platform = request.getParameter("platform");
        UserVO user = passWordService.checkStatus(token,sign);
        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 1) {
            //已设置交易密码
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"已设置交易密码！");
        }
        int userId = user.getUserId();
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE6);
        userOperationLogEntity.setIp(com.hyjf.cs.user.util.GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(usersInfo.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(),  userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(userId,txcode, ClientConstants.APP_CLIENT);
        // 同步调用路径
        String retUrl = systemConfig.getAppFrontHost() +"/user/setting/bankPassword/result/failed?status=99&statusDesc=&logOrdId="+bean.getLogOrderId();
        retUrl += "&token=1&sign=" +sign;
        // 异步调用路
        String bgRetUrl = "http://CS-USER" + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURN_ASY_PASSWORD_ACTION;

        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl+"?sign=" + sign);
        Map<String,Object> data = passWordService.setAppPassword(bean,user,usersInfo,bankOpenAccount);
        result.setData(data);
        result.setStatus("000");
        return result;
    }

    /**
     * 设置交易密码异步回调
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "设置交易密码异步回调")
    @PostMapping(value = "/bank/user/transpassword/passwordBgreturn")
    public String passwordBgreturn(@RequestBody BankCallBean bean) {
        logger.info("app设置交易密码异步回调"+bean.getLogOrderId());
        BankCallResult result = new BankCallResult();
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user = passWordService.getUsersById(userId);
        // 成功或审核中
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 开户后保存相应的数据以及日志
                passWordService.updateUserIsSetPassword(user.getUserId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        result.setMessage("交易密码设置成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 重置交易密码
     * @param token
     * @param sign
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "重置交易密码")
    @PostMapping(value = "/bank/user/transpassword/resetPassword")
    public AppResult<Object> resetPassword(@RequestHeader(value = "token") String token,@RequestHeader(value = "sign") String sign,HttpServletRequest request) {
        AppResult<Object> result = new AppResult<>();
        String platform = request.getParameter("platform");
        UserVO user = passWordService.checkStatus(token,sign);
        //判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 0) {
            //已设置交易密码
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"未设置交易密码！");
        }
        int userId = user.getUserId();
        BankOpenAccountVO bankOpenAccount = passWordService.getBankOpenAccount(userId);
        UserInfoVO usersInfo= passWordService.getUserInfo(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE6);
        userOperationLogEntity.setIp(com.hyjf.cs.user.util.GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(Integer.valueOf(platform));
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(usersInfo.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(userId,txcode, ClientConstants.APP_CLIENT);
        // 同步调用路径
        String retUrl = systemConfig.getAppFrontHost() +"/user/setting/bankPassword/result/failed?status=99&statusDesc=&logOrdId="+bean.getLogOrderId() ;
        retUrl += "&token=1&sign=" +sign;
        // 异步调用路
        String bgRetUrl = "http://CS-USER" + request.getContextPath() +  CommonConstant.REQUEST_MAPPING
                + CommonConstant.RETURN_ASY_RESETPASSWORD_ACTION+"?sign=" + sign;

        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        Map<String,Object> data = passWordService.resetAppPassword(bean,user,usersInfo,bankOpenAccount);
        result.setData(data);
        result.setStatus("000");
        return result;
    }

    /**
     * 重置交易密码异步回调
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "重置交易密码异步回调")
    @PostMapping(value = "/bank/user/transpassword/resetPasswordBgreturn")
    public String resetPasswordBgreturn(@RequestBody BankCallBean bean) {
        logger.info("app重置交易密码异步回调");
        BankCallResult result = new BankCallResult();
        result.setMessage("交易密码修改成功");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }


    /**
     * 找回密码(重置密码)
     *
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "找回密码",notes = "找回密码")
    @PostMapping(value = "/appUser/getBackPasswordAction")
    public JSONObject getBackPasswordAction(@RequestHeader(value = "key") String key,HttpServletRequest request, HttpServletResponse response) {
        JSONObject ret = new JSONObject();
        ret.put("request", "/hyjf-app/appUser/getBackPasswordAction");

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

        // 新密码
        String newPassword = request.getParameter("newPassword");
        // 验证方式
        String verificationCode = request.getParameter("verificationCode");
        // 手机号
        String mobile = request.getParameter("mobile");

        // 检查参数正确性
        if (Validator.isNull(version) || Validator.isNull(netStatus) || Validator.isNull(platform) || Validator.isNull(sign) || Validator.isNull(randomString) || Validator.isNull(order) || Validator.isNull(mobile) || Validator.isNull(verificationCode) || Validator.isNull(newPassword)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 取得加密用的Key
        if (Validator.isNull(key)) {
            ret.put("status", "1");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }
        // 业务逻辑
        try {
            // 根据手机号取得用户ID
            UserVO user = passWordService.getUsersByMobile(mobile);
            if (user != null) {
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
                if (Validator.isNull(verificationCode)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "验证码不能为空");
                    return ret;
                }
                if (Validator.isNull(newPassword)) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码不能为空");
                    return ret;
                }
                if (newPassword.length() < 8 || newPassword.length() > 16) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "密码长度8-16位");
                    return ret;
                }
                boolean hasNumber = false;

                for (int i = 0; i < newPassword.length(); i++) {
                    if (Validator.isNumber(newPassword.substring(i, i + 1))) {
                        hasNumber = true;
                        break;
                    }
                }
//                if (!hasNumber) {
//                    ret.put("status", "1");
//                    ret.put("statusDesc", "密码必须包含数字");
//                    return ret;
//                }

                String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
                Pattern p = Pattern.compile(regEx);
                Matcher m = p.matcher(newPassword);
                if (!m.matches()) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "必须包含数字、字母、符号至少两种");
                    return ret;
                }
                String verificationType = CommonConstant.PARAM_TPL_ZHAOHUIMIMA;
                int cnt = passWordService.updateCheckMobileCode(mobile, verificationCode, verificationType, platform, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED,true);
                if (cnt == 0) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "验证码无效");
                    return ret;
                }

                boolean success = passWordService.updatePassWd(user, newPassword);

                if (success) {
                    //如果修改密码成功或者重置密码就将登陆密码错误次数的key删除
                    RedisUtils.del(RedisConstants.PASSWORD_ERR_COUNT_ALL+user.getUserId());
                    ret.put("status", "0");
                    ret.put("statusDesc", "找回密码成功");
                } else {
                    ret.put("status", "1");
                    ret.put("statusDesc", "找回密码失败");
                }
            } else {
                ret.put("status", "1");
                ret.put("statusDesc", "用户信息不存在");
            }
        } catch (Exception e) {
            ret.put("status", "1");
            ret.put("statusDesc", "找回密码发生错误");
        }
        return ret;
    }

}