/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.wechat.landingpage;

import com.hyjf.am.response.trade.WeChatLandingPageResponse;
import com.hyjf.am.vo.trade.WeChatLandingPageVO;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.landingpage.LandingPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author wangjun
 * @version LandingPageController, v0.1 2018/7/30 11:58
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/wx/landingPage")
public class WeChatLandingPageController1 extends BaseController {

    @Autowired
    private LandingPageService landingPageService;

    @GetMapping(value = "/selectInterestSum")
    public WeChatLandingPageResponse selectInterestSum(){
        WeChatLandingPageResponse response = new WeChatLandingPageResponse();
        WeChatLandingPageVO vo = new WeChatLandingPageVO();
        vo.setProfitSum(landingPageService.selectInterestSum());
        response.setResult(vo);
        return response;
    }
}
