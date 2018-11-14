/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.app.common;

import com.hyjf.cs.user.config.SystemConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhangqingqing
 * @version UpdateVersion, v0.1 2018/11/13 17:32
 */
@Api(tags = "app端-版本更新")
@Controller
@RequestMapping("/hyjf-app/jsp/update")
public class UpdateVersionController {

    @Autowired
    SystemConfig systemConfig;

    /**
     * 获取最新版本号下载地址
     * @param request
     * @param
     * @return
     */
    @ApiOperation("版本更新")
    @GetMapping(value = "/hjh-update-android.jsp")
    public ModelAndView hjhUpdateAndroid(HttpServletRequest request) {
        String sign = request.getParameter("sign");
        String url = systemConfig.getAppFrontHost()+"/update/package?sign="+sign;
        ModelAndView modelAndView = new ModelAndView("redirect:"+url);
        return modelAndView;
    }
}
