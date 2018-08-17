/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.landingpage;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.user.result.BaseResultBean;
import com.hyjf.cs.user.service.landingpage.LandingPageService;
import com.hyjf.cs.user.vo.LandingPageResulltVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version LandingPageController, v0.1 2018/7/30 11:58
 */
@Api(value = "weChat端-着陆页用户信息接口",tags = "weChat端-着陆页用户信息接口")
@RestController
@RequestMapping("/hyjf-wechat/wx/landingPage")
public class WeChatLandingPageController extends BaseController {

    @Autowired
    private LandingPageService landingPageService;

    @ApiOperation(value = "获取着陆页用户信息",notes = "获取着陆页用户信息")
    @GetMapping(value = "/userData")
    public BaseResultBean landingPageUserData(){
        LandingPageResulltVO landingPageResulltVO = landingPageService.getUserData();
        return landingPageResulltVO;
    }
}
