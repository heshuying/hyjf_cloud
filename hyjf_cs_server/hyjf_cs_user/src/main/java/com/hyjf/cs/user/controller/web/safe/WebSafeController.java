/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.safe;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUser;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
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

/**
 * @author zhangqingqing
 * @version SafeController, v0.1 2018/6/11 14:13
 */

@Api(value = "web端用户账户设置")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebSafeController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebSafeController.class);

    @Autowired
    private SafeService safeService;


    /**
     * @Author: zhangqingqing
     * @Desc : 账户设置查询
     * @Param: * @param token
     * @Date: 16:43 2018/5/30
     * @Return: String
     */
    @ApiOperation(value = "账户设置查询", notes = "账户设置查询")
    @PostMapping(value = "accountSet")
    public WebResult<Object> accountSet(@RequestHeader(value = "token",required = false) String token) {
        WebResult<Object> apiResult = new WebResult<>();
        WebViewUser webViewUser = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        if (null == webViewUser){
            apiResult.setStatusInfo(MsgEnum.ERR_USER_NOT_LOGIN);
        }else{
            Map<String,Object> result = safeService.safeInit(webViewUser);
            if (null == result){
                apiResult.setStatus(ApiResult.FAIL);
                apiResult.setStatusDesc("账户设置查询失败");
            }
            apiResult.setData(result);
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
    public WebResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "token", required = true) String token, HttpServletRequest request) {
        WebResult<UserVO> result = new WebResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        UserVO userVO = safeService.queryUserByUserId(user.getUserId());
        result.setData(userVO);

        return result;
    }


    /**
     * 保存用户通知设置
     * @param token
     * @param userNoticeSetVO
     * @param
     * @param
     * @return
     */
    @ApiOperation(value = "保存用户通知设置", notes = "保存用户通知设置")
    @PostMapping(value = "/saveUserNoticeSetting", produces = "application/json; charset=utf-8")
    public WebResult<UserVO> saveUserNoticeSetting(@RequestHeader(value = "token", required = true) String token, @RequestBody @Valid UserNoticeSetVO userNoticeSetVO) {
        logger.info("用户通知设置, userNoticeSetVO :{}", JSONObject.toJSONString(userNoticeSetVO));
        WebResult<UserVO> result = new WebResult<UserVO>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);

        UserNoticeSetRequest noticeSetRequest = new UserNoticeSetRequest();
        BeanUtils.copyProperties(userNoticeSetVO, noticeSetRequest);
        noticeSetRequest.setUserId(user.getUserId());
        int ret = safeService.updateUserNoticeSet(noticeSetRequest);

        if (ret <= 0) {
            logger.error("保存用户通知设置失败");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("保存用户通知设置失败");
        }

        return result;
    }


    /**
     * 发送激活邮件
     */
    @ApiOperation(value = "发送激活邮件", notes = "发送激活邮件")
    @PostMapping(value = "/sendEmailActive", produces = "application/json; charset=utf-8")
    public WebResult<Object> sendEmailActive(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String, String> paraMap, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
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
     */
    @ApiOperation(value = "绑定邮箱", notes = "绑定邮箱")
    @PostMapping(value = "/bindEmail", produces = "application/json; charset=utf-8")
    public WebResult<Object> bindEmail(@RequestHeader(value = "token") String token, @RequestBody BindEmailVO bindEmailVO) {
    	logger.info("用戶绑定邮箱, bindEmailVO :{}", JSONObject.toJSONString(bindEmailVO));
    	WebResult<Object> result = new WebResult<Object>();

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);

        safeService.checkForEmailBind(bindEmailVO, user);

        try {
            safeService.updateEmail(user.getUserId(), bindEmailVO.getEmail());
        } catch (MQException e) {
            logger.error("邮箱激活失败", e);
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("邮箱激活失败");
        }

        return result;
    }

    /**
     * 添加、修改紧急联系人
     * @param token
     * @param
     * @return
     */
    @ApiOperation(value = "加载紧急联系人信息", notes = "加载紧急联系人信息")
    @PostMapping(value = "/contractInit", produces = "application/json; charset=utf-8")
    public ContractSetResultBean contractInit(@RequestHeader(value = "token", required = true) String token) {
    	logger.info("加载紧急联系人信息开始...");

        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);

        return safeService.queryContractInfo(user.getUserId());
    }

    /**
     * 添加、修改紧急联系人
     * @param token
     * @param
     * @return
     */
    @ApiOperation(value = "添加、修改紧急联系人", notes = "添加、修改紧急联系人")
    @PostMapping(value = "/saveContract", produces = "application/json; charset=utf-8")
    @ApiImplicitParam(name = "param",value = "{relationId:int,rlName:string,rlPhone:string}", dataType = "Map")
    public WebResult<Object> saveContract(@RequestHeader(value = "token", required = true) String token, @RequestBody Map<String,String> param) {
    	WebResult<Object> result = new WebResult<Object>();
        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        safeService.checkForContractSave(param.get("relationId"), param.get("rlName"), param.get("rlPhone"), user);

        try {
            safeService.saveContract(param.get("relationId"), param.get("rlName"), param.get("rlPhone"), user);
        } catch (MQException e) {
            logger.error("紧急联系人保存失败", e);
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc("紧急联系人保存失败");
        }
        return result;
    }


}
