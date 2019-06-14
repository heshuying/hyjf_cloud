/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.safe;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.UserNoticeSetRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.user.UserUtmInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCilentIP;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.result.ContractSetResultBean;
import com.hyjf.cs.user.service.safe.SafeService;
import com.hyjf.cs.user.vo.BindEmailVO;
import com.hyjf.cs.user.vo.UserNoticeSetVO;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
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
import java.util.*;

/**
 * @author zhangqingqing
 * @version SafeController, v0.1 2018/6/11 14:13
 */

@Api(value = "web端-用户账户设置", tags = "web端-用户账户设置")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hyjf-web/user")
public class WebSafeController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebSafeController.class);

    @Autowired
    private SafeService safeService;
    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    SystemConfig systemConfig;

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
        CheckUtil.check(user!=null,MsgEnum.STATUS_ZT000001);
        Map<String, Object> result = safeService.safeInit(user);

        // 合规需求 分享链接增加渠道信息 add by huanghui
        // 合规自查添加
        // 20181205 产品需求, 屏蔽渠道,只保留用户ID
        String inviteLink = null;
        UserUtmInfoCustomizeVO userUtmInfo = safeService.getUserUtmInfo(userId);
//        if (userUtmInfo != null){
//            inviteLink = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId + "&utmId=" + userUtmInfo.getSourceId().toString() + "&utmSource=" + userUtmInfo.getSourceName();
//        }else {
            inviteLink = systemConfig.getWechatQrcodeUrl() + "refferUserId=" + userId+"&action=scan";
//        }
        result.put("inviteLink", inviteLink);

        // 从redis获取是否可修改银行预留手机号
        String isBankMobileModify = RedisConstants.BANK_MOBILE_MODIFY_FLAG;
        try{
            String flag = RedisUtils.get(isBankMobileModify);
            if(StringUtils.isNotBlank(flag)) {
                result.put("bankMobileModifyFlag",flag);
            }
        } catch(Exception e) {
            result.put("bankMobileModifyFlag","0");
            logger.error("获取银行预留手机号修改按钮展示flag失败！");
        }

        if (null == result) {
            response.setStatus(ApiResult.FAIL);
            response.setStatusDesc("账户设置查询失败");
        }
        logger.info("账户设置查询 is {}"+JSONObject.toJSONString(result));
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
    public WebResult<UserVO> userNoticeSettingInit(@RequestHeader(value = "userId") int userId) {
        WebResult<UserVO> result = new WebResult<UserVO>();
        UserVO userVO = safeService.queryUserByUserId(userId);
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
    public WebResult<WebViewUserVO> saveUserNoticeSetting(@RequestHeader(value = "userId") int userId, @RequestBody @Valid UserNoticeSetVO userNoticeSetVO) {
        logger.info("用户通知设置, userNoticeSetVO :{}", JSONObject.toJSONString(userNoticeSetVO));
        WebResult<WebViewUserVO> result = new WebResult<WebViewUserVO>();

        WebViewUserVO user = safeService.getWebViewUserByUserId(userId, BankCallConstant.CHANNEL_PC);

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

        WebViewUserVO webUser = safeService.getWebViewUserByUserId(user.getUserId(),BankCallConstant.CHANNEL_PC);
        if (null != webUser) {
            webUser = safeService.updateUserToCache(webUser);
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
    public WebResult<Object> sendEmailActive(@RequestHeader(value = "userId") Integer userId,
                                             @RequestHeader(value = "token") String token ,
                                             @RequestBody Map<String, String> paraMap, HttpServletRequest request) {
        WebResult<Object> result = new WebResult<Object>();
        String isUpdate = request.getParameter("isUpdate");
        safeService.checkForEmailSend(paraMap.get("email"), userId);
        WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId,BankCallConstant.CHANNEL_PC);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        if(org.apache.commons.lang.StringUtils.isNotEmpty(isUpdate)&&"isUpdate".equals(isUpdate)){
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE9);
        }else {
            userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE8);
        }
        userOperationLogEntity.setIp(GetCilentIP.getIpAddr(request));
        userOperationLogEntity.setPlatform(0);
        userOperationLogEntity.setRemark("");
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(webUser.getUsername());
        userOperationLogEntity.setUserRole(webUser.getRoleId());
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(),  userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        try {
            safeService.sendEmailActive(userId, token, paraMap.get("email"));
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
    @GetMapping(value = "/bindEmail")
    public WebResult<Object> bindEmail(HttpServletRequest request) {
        BindEmailVO bindEmailVO = new BindEmailVO();
        bindEmailVO.setKey(request.getParameter("key"));
        bindEmailVO.setValue(request.getParameter("value"));
        bindEmailVO.setEmail(request.getParameter("email"));

        logger.info("用戶绑定邮箱, bindEmailVO :{}", JSONObject.toJSONString(bindEmailVO));
        WebResult<Object> result = new WebResult<Object>();

        safeService.checkForEmailBind(bindEmailVO);

        try {
            safeService.updateEmail(Integer.parseInt(bindEmailVO.getKey()), bindEmailVO.getEmail());
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(Integer.parseInt(bindEmailVO.getKey()),BankCallConstant.CHANNEL_PC);
            if (null != webUser) {
                webUser = safeService.updateUserToCache(webUser);
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
    public ContractSetResultBean contractInit(@RequestHeader(value = "userId") int userId) {
        logger.info("加载紧急联系人信息开始...");
        return safeService.queryContractInfo(userId);
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
    public WebResult<Object> saveContract(@RequestHeader(value = "userId") int userId, @RequestBody Map<String, String> param) {
        WebResult<Object> result = new WebResult<Object>();
        safeService.checkForContractSave(param.get("relationId"), param.get("rlName"), param.get("rlPhone"));

        try {
            safeService.saveContract(param.get("relationId"), param.get("rlName"), param.get("rlPhone"), userId);
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId,BankCallConstant.CHANNEL_PC);
            if (null != webUser) {
                webUser = safeService.updateUserToCache(webUser);
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
    public WebResult updateSmsConfig(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param, HttpServletRequest request) {
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
        WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId,BankCallConstant.CHANNEL_PC);
        if (null != webUser) {
            webUser = safeService.updateUserToCache(webUser);
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
        }else{
            result.setData(Collections.emptyMap());
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
    public WebResult uploadAvatarAction(@RequestHeader(value = "userId") Integer userId, @RequestBody Map<String, String> param){
        logger.debug("用户上传头像 -> param::[{}]",param);
        logger.info("上传头像开始。。。。。。。。。。。");
        WebResult<Object> result = new WebResult<>();
        CheckUtil.check(userId != null, MsgEnum.STATUS_CE000006);
        String image = param.get("image");
        try {
            UserVO user = safeService.queryUserByUserId(userId);
            String imgFilePath = safeService.uploadAvatar(user, userId, image);
            WebViewUserVO webUser = safeService.getWebViewUserByUserId(userId,BankCallConstant.CHANNEL_PC);
            if (null != webUser) {
                webUser = safeService.updateUserToCache(webUser);
                result.setData(webUser);
            }
            Map<String, String> map = new HashMap<>();
            map.put("iconUrl", imgFilePath);
            result.setData(map);
            logger.info("头像上传成功");
        } catch (Exception e) {
            throw new CheckException(MsgEnum.STATUS_EV000002);
        }
        return result;
    }


}
