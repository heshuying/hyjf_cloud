/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.controller.BaseUserController;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version RegisterController, v0.1 2018/6/11 14:27
 */
@Api(value = "api端用户注册接口")
@RestController
@RequestMapping("/api/user")
public class ApiRegisterController extends BaseUserController {
    private static final Logger logger = LoggerFactory.getLogger(ApiRegisterController.class);

    @Autowired
    RegistService registService;

    /**
     * @param request
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:44 2018/5/30
     * @Return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        logger.info("api端注册接口, registerVO is :{}", JSONObject.toJSONString(registerRequest));
        ApiResult<UserVO> result = new ApiResult<UserVO>();
        registService.registerCheckParam(ClientConstants.API_CLIENT,registerRequest);
        UserVO userVO = registService.apiRegister(registerRequest, GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("api端注册成功, userId is :{}", userVO.getUserId());
            result.setData(userVO);
        } else {
            logger.error("api端注册失败...");
            result.setStatus(ApiResult.FAIL);
            result.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return result;
    }

}
