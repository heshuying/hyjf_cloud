/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.DES;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:42
 */
@Api(value = "app端用户注册接口")
@RestController
@RequestMapping("/app/bank/user/")
public class AppRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(AppRegistController.class);

    @Autowired
    private RegistService registService;

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 注册
     * @param key
     * @param register
     * @param request
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> register(@RequestHeader String key, @RequestBody RegisterVO register, HttpServletRequest request) {
        logger.info("web端注册接口, register is :{}", JSONObject.toJSONString(register));
        ApiResult<UserVO> result = new ApiResult<>();
        String mobilephone = DES.decodeValue(key, register.getMobilephone());
        String smsCode = DES.decodeValue(key,register.getSmsCode());
        String pwd = DES.decodeValue(key, register.getPassword());
        String reffer = DES.decodeValue(key, register.getReffer());
        if (StringUtils.isNotBlank(reffer)) {
            int count = amUserClient.countUserByRecommendName(reffer);
            if (count == 0) {
                result.setStatus(ApiResult.STATUS_FAIL);
                result.setStatusDesc(LoginError.REFFER_INVALID_ERROR.getMsg());
            }
        }
        RegisterVO registerVO = new RegisterVO();
        registerVO.setMobilephone(mobilephone);
        registerVO.setPassword(pwd);
        registerVO.setReffer(reffer);
        registerVO.setSmsCode(smsCode);
        registService.registerCheckParam(ClientConstants.APP_CLIENT,registerVO);
        UserVO userVO = registService.register(registerVO, GetCilentIP.getIpAddr(request));
        result.setResult(userVO);
        if (userVO != null) {
            logger.info("web端注册成功, userId is :{}", userVO.getUserId());
        } else {
            logger.error("web端注册失败...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMsg());
        }
        return result;
    }
}
