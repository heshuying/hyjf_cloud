/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user.web.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.result.MobileModifyResultBean;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.vo.UserNoticeSetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version SafeController, v0.1 2018/6/11 14:13
 */

@Api(value = "web端用户账户设置")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebSafeController {

    private static final Logger logger = LoggerFactory.getLogger(WebSafeController.class);

    @Autowired
    private SafeService safeService;


    /**
     * @Author: zhangqingqing
     * @Desc :账户设置查询
     * @Param: * @param token
     * @Date: 16:43 2018/5/30
     * @Return: java.lang.String
     */
    @ApiOperation(value = "账户设置查询", notes = "账户设置查询")
    @PostMapping(value = "accountSet")
    public ApiResult<Object> accountSet(@RequestHeader(value = "token",required = false) String token) {
        ApiResult<Object> apiResult = new ApiResult<>();
        WebViewUser webViewUser = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        if (null == webViewUser){
            apiResult.setStatus(ApiResult.STATUS_FAIL);
            apiResult.setStatusDesc("账户设置查询失败");
            apiResult.setLoginFlag(ApiResult.STATUS_FAIL);
        }else{
            Map<String,Object> result = safeService.safeInit(webViewUser);
            if (null == result){
                apiResult.setStatus(ApiResult.STATUS_FAIL);
                apiResult.setStatusDesc("账户设置查询失败");
            }
            apiResult.setResult(result);
        }
        return apiResult;
    }

    /**
     * 获取用戶通知配置信息
     *
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "获取用戶通知配置信息", notes = "获取用戶通知配置信息")
    @PostMapping("/userNoticeSettingInit")
    public ApiResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        UserVO userVO = safeService.queryUserByUserId(user.getUserId());
        result.setResult(userVO);

        return result;
    }


    /**
     * 保存用户通知设置
     *
     * @param token
     * @param userVO
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "保存用户通知设置", notes = "保存用户通知设置")
    @PostMapping(value = "/saveUserNoticeSetting", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> saveUserNoticeSetting(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid UserNoticeSetVO userNoticeSetVO, HttpServletRequest request,
                                                   HttpServletResponse response) {
        logger.info("用戶通知設置, userNoticeSetVO :{}", JSONObject.toJSONString(userNoticeSetVO));
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);

        UserNoticeSetRequest noticeSetRequest = new UserNoticeSetRequest();
        BeanUtils.copyProperties(userNoticeSetVO, noticeSetRequest);
        noticeSetRequest.setUserId(user.getUserId());
        int ret = safeService.updateUserNoticeSet(noticeSetRequest);

        if (ret <= 0) {
            logger.error("保存用户通知设置失败");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("保存用户通知设置失败");
        }

        return result;
    }

    /**
     * 用户手机号修改基础信息获取
     * @param token
     * @param request
     * @return
     */
    @PostMapping("/mobileModifyInit")
    public ApiResult<MobileModifyResultBean> mobileModifyInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        ApiResult<MobileModifyResultBean> result = new ApiResult<MobileModifyResultBean>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        MobileModifyResultBean resultBean = safeService.queryForMobileModify(user.getUserId());
        result.setResult(resultBean);

        return result;
    }

    /**
     * 用户手机号码修改
     */
    @ApiOperation(value = "手机号码修改", notes = "手机号码修改")
    @PostMapping(value = "/mobileModify", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> mobileModify(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String newMobile, @RequestParam(required=true) String smsCode, HttpServletRequest request,
                                          HttpServletResponse response) {
        logger.info("用户手机号码修改, newMobile :{}, smsCode:{}", newMobile, smsCode);
        ApiResult<UserVO> result = new ApiResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        boolean checkRet = safeService.checkForMobileModify(newMobile, smsCode);
        if(checkRet) {
            UserVO userVO = new UserVO();
            userVO.setUserId(user.getUserId());
            userVO.setMobile(newMobile);
            safeService.updateUserByUserId(userVO);
        }

        return result;
    }

    /**
     * 发送激活邮件
     */
    @ApiOperation(value = "发送激活邮件", notes = "发送激活邮件")
    @PostMapping(value = "/sendEmailActive", produces = "application/json; charset=utf-8")
    public ApiResult<Object> sendEmailActive(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String email, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<Object>();

        WebViewUser user = new WebViewUser();
        user.setUserId(1);
        safeService.checkForEmailSend(email, user.getUserId());

        try {
            safeService.sendEmailActive(user.getUserId(), email);
        } catch (MQException e) {
            logger.error("发送激活邮件失败", e);
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("发送激活邮件失败");
        }

        return result;
    }

    /**
     * 绑定邮箱
     */
    @ApiOperation(value = "绑定邮箱", notes = "绑定邮箱")
    @PostMapping(value = "/bindEmail", produces = "application/json; charset=utf-8")
    public ApiResult<Object> bindEmail(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String key, @RequestParam(required=true) String value, @RequestParam(required=true) String email, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<Object>();

        WebViewUser user  = new WebViewUser();
        user.setUserId(1);

        safeService.checkForEmailBind(email, key, value, user);

        try {
            safeService.updateEmail(user.getUserId(), email);
        } catch (MQException e) {
            logger.error("邮箱激活失败", e);
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("邮箱激活失败");
        }

        return result;
    }

    /**
     * 添加、修改紧急联系人
     * @param token
     * @param request
     * @return
     */
    @ApiOperation(value = "添加、修改紧急联系人", notes = "添加、修改紧急联系人")
    @PostMapping(value = "/saveContract", produces = "application/json; charset=utf-8")
    public ApiResult<Object> saveContract(@RequestHeader(value = "token", required = true) String token, @RequestParam(required=true) String relationId,
                                          @RequestParam(required=true) String rlName, @RequestParam(required=true) String rlPhone, HttpServletRequest request) {
        ApiResult<Object> result = new ApiResult<Object>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        safeService.checkForContractSave(relationId, rlName, rlPhone, user);

        try {
            safeService.saveContract(relationId, rlName, rlPhone, user);
        } catch (MQException e) {
            logger.error("紧急联系人保存失败", e);
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc("紧急联系人保存失败");
        }

        return result;
    }

}
