/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.websocket;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * @author: tanyy
 * @version: ApiSocketMessageController, v0.1 2019/3/13 10:37
 */
@Api(value = "api端-用户画像大屏幕显示",tags = "api端-用户画像大屏幕显示")
@Controller
@RequestMapping(value = "/hyjf-api/api/user_picture")
public class ApiSocketMessageController{
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
/*    //页面请求
    @GetMapping("/socket/{cid}")
    public ModelAndView socket(@PathVariable String cid,HttpServletRequest request) {
        logger.info("进入接口=====");
        ModelAndView mav=new ModelAndView("socket/user_picture");
        StringBuffer bf = new StringBuffer();
        bf.append("ws://").append(request.getServerName()).append(":").append(request.getServerPort()).append("/hyjf-api/websocket/").append(cid);
        logger.info(bf.toString());
        mav.addObject("url",bf.toString());
        mav.addObject("cid", cid);
        return mav;
    }*/
    //推送数据接口
    @ResponseBody
    @GetMapping("/socket/push/{cid}")
    public Object pushToWeb(@PathVariable String cid,String message) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("a",1);
        try {
            WebSocketServer.sendInfo(jsonObject.toJSONString(),cid);
        } catch (IOException e) {
            logger.info("推送数据接口出现异常"+e.getMessage());
        }
        return jsonObject;
    }

}
