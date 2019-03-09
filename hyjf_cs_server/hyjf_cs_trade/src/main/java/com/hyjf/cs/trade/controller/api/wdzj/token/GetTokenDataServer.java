package com.hyjf.cs.trade.controller.api.wdzj.token;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.controller.BaseTradeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 网贷之家token获取
 * @author hesy
 * @version GetTokenDataServer, v0.1 2018/7/16 10:25
 */
@Api(tags = "api端-网贷之家token获取接口")
@RestController
@RequestMapping("/hyjf-api/wdzj/token")
public class GetTokenDataServer extends BaseTradeController {
    @Autowired
    SystemConfig systemConfig;

    @ApiOperation(value = "token值获取", notes = "token值获取")
    @RequestMapping(value = "/getToken")
    public JSONObject getToken(HttpServletRequest request, HttpServletResponse response) {
        JSONObject result;
        String usernameRight = systemConfig.getUserNameWDZJ();
        String passwordRight = systemConfig.getPasswordWDZJ();

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        logger.info("网贷之家 getToken接口请求参数：username=" + username + " password=" + password);

        if (Validator.isNull(username) || Validator.isNull(password)) {
            result = getFailMsg("1", "用户名或密码为空");
            logger.info("网贷之家 getToken接口返回：" + result.toJSONString());
            return result;
        }
        if(!username.equals(usernameRight)){
            result = getFailMsg("1", "用户名不存在");
            logger.info("网贷之家 getToken接口返回：" + result.toJSONString());
            return result;
        }
        if(!password.equals(passwordRight)){
            result = getFailMsg("1", "密码不正确");
            logger.info("网贷之家 getToken接口返回：" + result.toJSONString());
            return result;
        }

//        String token = getTokenInRedis("token_wdzj_" + username);
        String token =  getTokenInRedis(RedisConstants.KEY_WDZJ_KEY + username);
        result = getSuccMsg("0", "成功", token);
        logger.info("网贷之家 getToken接口返回：" + result.toJSONString());

        return result;
    }

    /**
     * redis中获取token值
     * @param key
     * @return
     */
    private String getTokenInRedis(String key){
        if(RedisUtils.exists(key)){
            return RedisUtils.get(key);
        }else{
            RedisUtils.set(key, UUID.randomUUID().toString(), 60*60*3);
            return RedisUtils.get(key);
        }
    }

    private JSONObject getFailMsg(String status, String desc) {
        JSONObject result = new JSONObject();
        JSONObject token = new JSONObject();
        token.put("token", "");

        result.put("status", status);
        result.put("statusDesc", desc);
        result.put("data", token);
        return result;
    }

    private JSONObject getSuccMsg(String status, String desc, String tokenString) {
        JSONObject result = new JSONObject();
        JSONObject token = new JSONObject();
        token.put("token", tokenString);

        result.put("status", status);
        result.put("statusDesc", desc);
        result.put("data", token);
        return result;
    }
}
