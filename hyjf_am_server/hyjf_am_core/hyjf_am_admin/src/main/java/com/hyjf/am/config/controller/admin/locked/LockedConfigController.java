/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.locked;

import javax.servlet.http.HttpServletRequest;

import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.config.service.locked.LockedConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;

/**
 * @author cui
 * @version LockedConfigController, v0.1 2018/9/26 19:10
 */
@Api(tags = "配置中心-登录失败配置")
@RestController
@RequestMapping("/am-config/lockedconfig")
public class LockedConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LockedConfigService lockedConfigService;

    @GetMapping("/webconfig")
    public Response<LockedConfig.Config> getWebConfig(HttpServletRequest request) {

        Response<LockedConfig.Config> Response = new Response<>();

        Response.setResult(LockedConfigManager.getInstance().getWebConfig());

        return Response;

    }

    @GetMapping("/adminconfig")
    @ResponseBody
    public Response<LockedConfig.Config> getAdminConfig(HttpServletRequest request) {

        Response<LockedConfig.Config> Response = new Response<>();

        Response.setResult(LockedConfigManager.getInstance().getAdminConfig());

        return Response;
    }

    @PostMapping("/savewebconfig")
    @ResponseBody
    public BooleanResponse setWebConfig(@RequestBody LockedConfig.Config webConfig) {

            lockedConfigService.saveWebConfig(webConfig);

            return new BooleanResponse(true);
    }


    @PostMapping("/saveadminconfig")
    @ResponseBody
    public BooleanResponse setAdminConfig(@RequestBody LockedConfig.Config adminConfig) {

            lockedConfigService.saveAdminConfig(adminConfig);

            return new BooleanResponse(true);
    }

}
