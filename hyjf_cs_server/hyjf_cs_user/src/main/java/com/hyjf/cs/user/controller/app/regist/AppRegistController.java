/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.DES;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public AppResult<WebViewUserVO> register(@RequestHeader String key, @RequestBody RegisterRequest register, HttpServletRequest request) {
        logger.info("app端注册接口, register is :{}", JSONObject.toJSONString(register));
        AppResult<WebViewUserVO> result = new AppResult<>();
        String mobilephone = DES.decodeValue(key, register.getMobile());
        String smsCode = DES.decodeValue(key,register.getSmsCode());
        String pwd = DES.decodeValue(key, register.getPassword());
        String reffer = DES.decodeValue(key, register.getReffer());
        register = new RegisterRequest();
        register.setMobile(mobilephone);
        register.setPassword(pwd);
        register.setReffer(reffer);
        register.setSmsCode(smsCode);
        registService.checkParam(register);
        WebViewUserVO userVO = registService.register(register, GetCilentIP.getIpAddr(request));
        result.setData(userVO);
        if (userVO != null) {
            logger.info("app端注册成功, userId is :{}", userVO.getUserId());
        } else {
            logger.error("app端注册失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN.getMsg());
        }
        return result;
    }
}
