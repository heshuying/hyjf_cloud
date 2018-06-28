/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.regist;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.util.DES;
import com.hyjf.cs.common.bean.result.WeChatResult;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version RegistController, v0.1 2018/6/11 14:35
 */

@Api(value = "weChat端用户注册接口")
@RestController
@RequestMapping("/wechat/user")
public class WeChatRegistController extends BaseUserController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatRegistController.class);
    @Autowired
    private RegistService registService;
    @Autowired
    private AmUserClient amUserClient;

    /**
     * wechat注册
     * @param key
     * @param registerRequest
     * @param request
     * @param
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping(value = "/register", produces = "application/json; charset=utf-8")
    public WeChatResult register(@RequestHeader String key, @RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        logger.info("微信端注册接口, mobile is :{}", JSONObject.toJSONString(registerRequest));
        WeChatResult resultBean = new WeChatResult();
        String pwd = DES.decodeValue(key, registerRequest.getPassword());
        registerRequest.setPassword(pwd);
        registService.checkParam(registerRequest);
        WebViewUserVO userVO = registService.register(registerRequest, GetCilentIP.getIpAddr(request));
        if (userVO != null) {
            logger.info("register success, userId is :{}", userVO.getUserId());
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("token",userVO.getToken());
            resultBean.setData(resultMap);
        } else {
            logger.error("register failed...");
            resultBean.setStatus("1");
            resultBean.setStatusDesc(MsgEnum.ERR_USER_REGISTER.getMsg());
        }
        return resultBean;
    }

}
