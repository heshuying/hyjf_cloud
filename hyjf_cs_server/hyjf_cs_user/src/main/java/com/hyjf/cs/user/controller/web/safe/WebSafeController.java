/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.ContractSetResultBean;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.vo.BindEmailVO;
import com.hyjf.cs.user.vo.UserNoticeSetVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version SafeController, v0.1 2018/6/11 14:13
 */

@Api(value = "web端-用户账户设置", description = "web端-用户账户设置")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebSafeController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebSafeController.class);

    @Autowired
    private SafeService safeService;


    /**
     * 账户设置查询
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "账户设置查询", notes = "账户设置查询")
    @PostMapping(value = "/accountSet")
    public WebResult<Object> accountSet(@RequestHeader(value = "userId", required = false) Integer userId) {
        logger.info("web端-账户设置查询, param is :{}",userId);
        WebResult<Object> response = new WebResult<>();
        UserVO user = safeService.getUsersById(userId);
        Map<String, Object> result = safeService.safeInit(user);
        if (null == result) {
            response.setStatus(ApiResult.FAIL);
            response.setStatusDesc("账户设置查询失败");
        }
        response.setData(result);
        return response;
    }

    /**
     * 获取用戶通知配置信息
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "获取用戶通知配置信息", notes = "获取用戶通知配置信息")
    @PostMapping("/userNoticeSettingInit")
    public WebResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        WebResult<UserVO> result = new WebResult<UserVO>();

        WebViewUserVO user = safeService.getUsersByToken(token);
        UserVO userVO = safeService.queryUserByUserId(user.getUserId());
        result.setData(userVO);

        return result;
    }


    /**
     * 保存用户通知设置
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "保存用户通知设置", notes = "保存用户通知设置")
    @PostMapping(value = "/saveUserNoticeSetting", produces = "application/json; charset=utf-8")
    public WebResult<WebViewUserVO> saveUserNoticeSetting(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid UserNoticeSetVO userNoticeSetVO) {
        logger.info("用户通知设置, userNoticeSetVO :{}", JSONObject.toJSONString(userNoticeSetVO));
        WebResult<WebViewUserVO> result = new WebResult<WebViewUserVO>();

        WebViewUserVO user = safeService.getUsersByToken(token);

        UserNoticeSetRequest noticeSetRequest = new UserNoticeSetRequest();
        BeanUtils.copyProperties(userNoticeSetVO, noticeSetRequest);
        noticeSetRequest.setUserId(user.getUserId());
        int ret = safeService.updateUserNoticeSet(noticeSetRequest);
        if (ret <= 0) {
            logger.error("保存用户通知设置失败");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("保存用户通知设置失败");
            return result;
        }

        WebViewUserVO webUser = safeService.getWebViewUserByUserId(user.getUserId());
        if (null != webUser) {
            webUser = safeService.setToken(webUser);
            result.setData(webUser);
        }

        return result;
    }


    /**
     * 发送激活邮件
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "发送激活邮件", notes = "发送激活邮件")
    @ApiImplicitParam(name = "paraMap", value = "{email:string}", dataType = "Map")
    @PostMapping(value = "/sendEmailActive", produces = "application/json; charset=utf-8")
    public WebResult<Object> sendEmailActive(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String, String> paraMap, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = safeService.getUsersByToken(token);
        safeService.checkForEmailSend(paraMap.get("email"), user.getUserId());

        try {
            safeService.sendEmailActive(user.getUserId(), paraMap.get("email"));
        } catch (MQException e) {
            logger.error("发送激活邮件失败", e);
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("发送激活邮件失败");
        }

        return result;
    }

    /**
     * 绑定邮箱
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "绑定邮箱", notes = "绑定邮箱")
    @PostMapping(value = "/bindEmail", produces = "application/json; charset=utf-8")
    public WebResult<Object> bindEmail(@RequestHeader(value = "token") String token, @RequestBody BindEmailVO bindEmailVO) {
        logger.info("用戶绑定邮箱, bindEmailVO :{}", JSONObject.toJSONString(bindEmailVO));
        WebResult<Object> result = new WebResult<Object>();

        WebViewUserVO user = safeService.getUsersByToken(token);

        safeService.checkForEmailBind(bindEmailVO, user);

        try {
            safeService.updateEmail(user.getUserId(), bindEmailVO.getEmail());
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(user.getUserId());
            if (null != webUser) {
                webUser = safeService.setToken(webUser);
                result.setData(webUser);
            }
        } catch (MQException e) {
            logger.error("邮箱激活失败", e);
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("邮箱激活失败");
        }

        return result;
    }

    /**
     * 添加、修改紧急联系人
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "加载紧急联系人信息", notes = "加载紧急联系人信息")
    @PostMapping(value = "/contractInit", produces = "application/json; charset=utf-8")
    public ContractSetResultBean contractInit(@RequestHeader(value = "token", required = true) String token) {
        logger.info("加载紧急联系人信息开始...");

        WebViewUserVO user = safeService.getUsersByToken(token);

        return safeService.queryContractInfo(user.getUserId());
    }

    /**
     * 添加、修改紧急联系人
     *
     * @auther: hesy
     * @date: 2018/6/20
     */
    @ApiOperation(value = "添加、修改紧急联系人", notes = "添加、修改紧急联系人")
    @PostMapping(value = "/saveContract", produces = "application/json; charset=utf-8")
    @ApiImplicitParam(name = "param", value = "{relationId:int,rlName:string,rlPhone:string}", dataType = "Map")
    public WebResult<Object> saveContract(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String, String> param) {
        WebResult<Object> result = new WebResult<Object>();
        WebViewUserVO user = safeService.getUsersByToken(token);
        safeService.checkForContractSave(param.get("relationId"), param.get("rlName"), param.get("rlPhone"), user);

        try {
            safeService.saveContract(param.get("relationId"), param.get("rlName"), param.get("rlPhone"), user);
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(user.getUserId());
            if (null != webUser) {
                webUser = safeService.setToken(webUser);
                result.setData(webUser);
            }
        } catch (MQException e) {
            logger.error("紧急联系人保存失败", e);
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("紧急联系人保存失败");
        }
        return result;
    }

    /**
     * 更新sms配置
     *
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "更新sms配置", notes = "更新sms配置")
    @ApiImplicitParam(name = "param", value = "{key:String,value:String}", dataType = "Map")
    @PostMapping(value = "/updateSmsConfig", produces = "application/json; charset=utf-8")
    public WebResult updateSmsConfig(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param) {
        WebResult<Object> result = new WebResult<>();
        String key = param.get("key");
        String value = param.get("value");
        // 加入验证
        CheckUtil.check(StringUtils.isNotBlank(key), MsgEnum.STATUS_CE000001);
        CheckUtil.check("rechargeSms".equals(key) || "withdrawSms".equals(key) || "investSms".equals(key) || "recieveSms".equals(key), MsgEnum.STATUS_CE000001);
        CheckUtil.check("0".equals(value) || "1".equals(value), MsgEnum.STATUS_CE000001);
        UserVO user = safeService.getUsersById(userId);
        safeService.updateSmsConfig(userId, key, Integer.parseInt(value), user);
        /**
         * 调用重新登录接口
         */
        WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId);
        if (null != webUser) {
            webUser = safeService.setToken(webUser);
            result.setData(webUser);
        }
        return result;
    }

    /**
     * 头像上传页面初始化
     *
     * @param userId
     * @return
     */
    @ApiOperation(value = "头像上传页面初始化", notes = "头像上传页面初始化")
    @GetMapping(value = "/avatar/init")
    public WebResult uploadAvatarInitAction(@RequestHeader(value = "userId") Integer userId) {
        WebResult<Object> result = new WebResult<>();
        UserVO user = safeService.queryUserByUserId(userId);
        if (StringUtils.isNotEmpty(user.getIconUrl())) {
            Map<String, String> map = new HashMap<>();
            map.put("iconUrl", user.getIconUrl());
            result.setData(map);
        }
        return result;
    }

    /**
     * 上传头像
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "上传头像", notes = "上传头像")
    @ApiImplicitParam(name = "param", value = "{image:String}", dataType = "Map")
    @PostMapping(value = "/avatar", produces = "application/json; charset=utf-8")
    public WebResult uploadAvatarAction(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param) {
        WebResult<Object> result = new WebResult<>();
        CheckUtil.check(userId != null, MsgEnum.STATUS_CE000006);
        String image = param.get("image");
        try {
            UserVO user = safeService.queryUserByUserId(userId);
            String imgFilePath = safeService.uploadAvatar(user, userId, image);
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId);
            if (null != webUser) {
                webUser = safeService.setToken(webUser);
                result.setData(webUser);
            }
            Map<String, String> map = new HashMap<>();
            map.put("iconUrl", imgFilePath);
            result.setData(map);
        } catch (Exception e) {
            throw new CheckException(MsgEnum.STATUS_EV000002);
        }
        return result;
    }


}
