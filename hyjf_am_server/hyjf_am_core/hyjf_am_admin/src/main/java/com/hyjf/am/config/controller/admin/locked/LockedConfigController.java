/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.locked;

import com.hyjf.am.bean.admin.LockedConfig;
import com.hyjf.am.config.service.locked.LockedConfigService;
import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.admin.locked.LockedConfigResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cui
 * @version LockedConfigController, v0.1 2018/9/26 19:10
 */
@RestController
@RequestMapping(value = "/am-admin/lockedconfig")
public class LockedConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private LockedConfigService lockedConfigService;

    @GetMapping(value = "/webconfig")
    public LockedConfigResponse getWebConfig(HttpServletRequest request) {

        LockedConfigResponse Response = new LockedConfigResponse();

        Response.setData(LockedConfigManager.getInstance().getWebConfig());

        return Response;

    }

    @GetMapping(value = "/adminconfig")
    @ResponseBody
    public LockedConfigResponse getAdminConfig(HttpServletRequest request) {

        LockedConfigResponse response = new LockedConfigResponse();

        response.setData(LockedConfigManager.getInstance().getAdminConfig());

        return response;
    }

    @PostMapping(value = "/savewebconfig")
    @ResponseBody
    public BooleanResponse setWebConfig(@RequestBody LockedConfig.Config webConfig) {

            lockedConfigService.saveWebConfig(webConfig);

            return new BooleanResponse(true);
    }


    @PostMapping(value = "/saveadminconfig")
    @ResponseBody
    public BooleanResponse setAdminConfig(@RequestBody LockedConfig.Config adminConfig) {

            lockedConfigService.saveAdminConfig(adminConfig);

            return new BooleanResponse(true);
    }

}
