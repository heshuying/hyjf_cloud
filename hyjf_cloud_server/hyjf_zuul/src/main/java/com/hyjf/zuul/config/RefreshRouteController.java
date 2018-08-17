package com.hyjf.zuul.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version RefreshRouteController, v0.1 2018/4/13 10:08
 * 手动刷新路由配置
 */

@RestController
public class RefreshRouteController {

    @Autowired
    RefreshRouteService refreshRouteService;
    @Autowired
    private Flag flag;

    @RequestMapping("/refreshRoute")
    public String refreshRoute() {
        flag.setFlag(false);
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }
}
