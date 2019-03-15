/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.api.websocket;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.cs.common.bean.result.ApiResult;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.controller.BaseUserController;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author: tanyy
 * @version: ApiSocketMessageController, v0.1 2019/3/13 10:37
 */
@Api(value = "api端-用户画像大屏幕显示",tags = "api端-用户画像大屏幕显示")
@Controller
@RequestMapping(value = "/hyjf-api/api/user_picture")
public class ApiSocketMessageController extends BaseUserController {


    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid,HttpServletRequest request) {
        ModelAndView mav=new ModelAndView("/socket/user_picture");
        StringBuffer bf = new StringBuffer();
        bf.append("ws://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/hyjf-api/websocket/").append(cid);
        logger.info(bf.toString());
        mav.addObject("url",bf.toString());
        mav.addObject("cid", cid);
        return mav;
    }
    //推送数据接口
    @ResponseBody
    @GetMapping("/socket/push/{cid}")
    public ApiResult pushToWeb(@PathVariable String cid,String message) {
        ApiResult result = new ApiResult();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",1);
        result.setData(jsonObject.toJSONString());
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(),cid);
        } catch (IOException e) {
            logger.info("推送数据接口出现异常"+e.getMessage());
            return new ApiResult(e.getMessage());
        }
        return result;
    }

}
