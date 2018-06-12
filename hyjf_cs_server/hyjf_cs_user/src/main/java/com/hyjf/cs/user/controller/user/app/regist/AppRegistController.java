/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.user.app.regist;

import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.DES;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.constants.LoginError;
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
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:42
 */
@Api(value = "app端用户注册接口")
@RestController
@RequestMapping("/app/bank/user/")
public class AppRegistController {

    private static final Logger logger = LoggerFactory.getLogger(AppRegistController.class);

    @Autowired
    private RegistService registService;

    @Autowired
    private AmUserClient amUserClient;

    /**
     * 注册
     * @param key
     * @param mobile
     * @param verificationCode
     * @param password
     * @param reffer
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> register(@RequestHeader String key, @RequestParam String mobile,
                                      @RequestParam String verificationCode, @RequestParam String password,
                                      @RequestParam(required = false) String reffer, HttpServletRequest request, HttpServletResponse response) {
        logger.info("register start, mobile is :{}", mobile);
        ApiResult<UserVO> result = new ApiResult<>();
        String mobilephone = DES.decodeValue(key, mobile);
        String smsCode = DES.decodeValue(key, verificationCode);
        String pwd = DES.decodeValue(key, password);
        reffer = DES.decodeValue(key, reffer);
        if (StringUtils.isNotBlank(reffer)) {
            int count = amUserClient.countUserByRecommendName(reffer);
            if (count == 0) {
                result.setStatus(ApiResult.STATUS_FAIL);
                result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
            }
        }
        RegisterVO registerVO = new RegisterVO();
        registerVO.setMobilephone(mobilephone);
        registerVO.setPassword(pwd);
        registerVO.setReffer(reffer);
        registerVO.setSmsCode(smsCode);
        UserVO userVO = registService.register(registerVO, GetCilentIP.getIpAddr(request));
        result.setResult(userVO);
        if (userVO != null) {
            logger.info("register success, userId is :{}", userVO.getUserId());
        } else {
            logger.error("register failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(LoginError.USER_LOGIN_ERROR.getMessage());
        }
        return result;
    }
}
