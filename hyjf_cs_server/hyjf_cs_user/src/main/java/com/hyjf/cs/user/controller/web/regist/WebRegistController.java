/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.SmsCodeVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.RandomValidateCode;
import com.hyjf.common.validator.Validator;
import com.hyjf.common.validator.ValidatorCheckUtil;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 13:59
 */

@Api(value = "web端用户注册接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WebRegistController.class);
    @Autowired
    private RegistService registService;

    /**
     * @param request
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:39 2018/5/30
     * @Return: ApiResult
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public WebResult<Map<String,Object>> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        logger.info("Web端用户注册接口, registerVO is :{}", JSONObject.toJSONString(registerRequest));
        WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
        // 1. 参数检查
        registerRequest.setPlatform(CommonConstant.CLIENT_PC);
        registService.checkParam(registerRequest);
        WebViewUserVO webViewUserVO = registService.register(registerRequest, GetCilentIP.getIpAddr(request));
        if (webViewUserVO != null) {
            logger.info("Web端用户注册成功, userId is :{}", webViewUserVO.getUserId());
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("token",webViewUserVO.getToken());
            result.setData(resultMap);
        } else {
            logger.error("Web端用户注册失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return result;
    }


    /**
     * 短信验证码校验
     *
     * 用户注册数据提交（获取session数据并保存） 1.校验验证码
     * 2.若验证码正确，则获取session数据，并将相应的注册数据写入数据库（三张表），跳转相应的注册成功界面
     */
    @ApiOperation(value = "短信验证码校验", notes = "短信验证码校验")
    @PostMapping(value = "/checkcode", produces = "application/json; charset=utf-8")
    public boolean checkcode(@RequestBody SmsCodeVO request) {
        logger.info("Web端短信验证码校验接口,SmsCodeVO  is :{}",JSONObject.toJSONString(request));
        JSONObject info = new JSONObject();
        String validCodeType = request.getVerificationType();
        if (StringUtils.isBlank(validCodeType)) {
            return false;
        }
        if (!validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE) && !validCodeType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA) && !validCodeType.equals( CommonConstant.PARAM_TPL_YZYSJH)
                && !validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
            return false;
        }
        // 手机号码(必须,数字,最大长度)
        String mobile = request.getMobile();
        if (!ValidatorCheckUtil.validateRequired(info, null, null, mobile)) {
            return false;
        } else if (!ValidatorCheckUtil.validateMobile(info, null, null, mobile, 11, false)) {
            return false;
        }
        // 短信验证码
        String code = request.getVerificationCode();
        if (!ValidatorCheckUtil.validateRequired(info, null, null, code)) {
            return false;
        }
        int cnt = this.registService.updateCheckMobileCode(mobile, code, validCodeType, CustomConstants.CLIENT_PC, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_YIYAN);
        if (cnt > 0) {
            logger.info("Web端短信验证码校验接口返回结果为：true");
            return true;
        } else {
            logger.info("Web端短信验证码校验接口返回结果为：false");
            return false;
        }
    }

    /**
     * @Author: zhangqingqing
     * @Desc : 检查手机号是否已存在
     * @Param: * @param mobile
     * @Date: 17:00 2018/6/13
     * @Return: boolean
     */
    @ResponseBody
    @ApiOperation(value = "检查手机号是否已存在", notes = "检查手机号是否已存在")
    @ApiImplicitParam(name = "param",value = "{mobile: string}", dataType = "Map")
    @PostMapping(value = "/checkPhone", produces = "application/json; charset=utf-8")
    public boolean checkPhone(@RequestBody Map<String,String> param) {
        logger.info("Web端检查手机号是否已存在, mobile is :{}",JSONObject.toJSONString(param));
        if (param.get("mobile") != null && !"".equals(param.get("mobile").trim())) {
            if (Validator.isMobile(param.get("mobile"))) {
                if (registService.existUser(param.get("mobile"))) {
                    // 存在用户,返回false
                    return false;
                } else {
                    return true;
                }

            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    /**
     * 检查手机号码或用户名唯一性
     *
     * @param
     * @return
     */
    @ResponseBody
    @ApiOperation(value = "检查手机号码或用户名唯一性", notes = "检查手机号码或用户名唯一性")
    @ApiImplicitParam(name = "param",value = "{name:string,param:string}", dataType = "Map")
    @PostMapping(value = "/checkaction", produces = "application/json; charset=utf-8")
    public boolean checkAction(@RequestBody Map<String,String> param) {
        logger.info("Web端检查手机号码或用户名唯一性, param is :{}",JSONObject.toJSONString(param));
        String name = param.get("name");
        String mobile = param.get("param");
        if ("userName".equals(name)) {
            if (registService.existUser(mobile)) {
                // 存在用户,返回false
                return false;
            } else {
                JSONObject info = new JSONObject();
                return ValidatorCheckUtil.validateMobile(info, null, null, mobile, 11, false);
            }
        } else {
            return false;
        }
    }

    /**
     * 检查图片验证码
     *
     * @param request
     * @param
     */
    @ResponseBody
    @PostMapping(value = "checkcaptcha", produces = "application/json; charset=utf-8")
    public boolean checkcaptcha(HttpServletRequest request) {
        RandomValidateCode randomValidateCode = new RandomValidateCode();
        return randomValidateCode.checkRandomCode(request, request.getParameter("newRegVerifyCode"));
    }

    /**
     * 判断推荐人是否存在 如果存在返回true，如果不存在返回false;
     *
     * @param param
     * @param
     * @return
     */
    @ResponseBody
    @PostMapping(value = "checkRecommend", produces = "application/json; charset=utf-8")
    public boolean checkRecommend(@RequestBody Map<String,String> param) {
        logger.info("Web端判断推荐人是否存在, param is :{}",JSONObject.toJSONString(param));
        String recommend = param.get("newRegReferree");
        if (registService.countUserByRecommendName(recommend) <= 0) {
            return false;
        }
        return true;
    }

}
