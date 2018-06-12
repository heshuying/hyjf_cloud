/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.web.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.user.constants.RegisterError;
import com.hyjf.cs.user.result.ApiResult;
import com.hyjf.cs.user.service.regist.RegistService;
import com.hyjf.cs.user.util.GetCilentIP;
import com.hyjf.cs.user.vo.RegisterVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 13:59
 */

@Api(value = "web端用户注册接口")
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/web/user")
public class WebRegistController {

    private static final Logger logger = LoggerFactory.getLogger(WebRegistController.class);
    @Autowired
    private RegistService registService;

    /**
     * @param request
     * @Author: zhangqingqing
     * @Desc :注册
     * @Param: * @param registerVO
     * @Date: 16:39 2018/5/30
     * @Return: com.hyjf.cs.user.result.ApiResult<com.hyjf.am.vo.user.UserVO>
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public ApiResult<UserVO> register(@RequestBody @Valid RegisterVO registerVO, HttpServletRequest request) {
        logger.info("register start, registerVO is :{}", JSONObject.toJSONString(registerVO));
        ApiResult<UserVO> result = new ApiResult<UserVO>();
        UserVO userVO = registService.register(registerVO, GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("register success, userId is :{}", userVO.getUserId());
            result.setResult(userVO);
        } else {
            logger.error("register failed...");
            result.setStatus(ApiResult.STATUS_FAIL);
            result.setStatusDesc(RegisterError.REGISTER_ERROR.getMessage());
        }
        return result;
    }

}
