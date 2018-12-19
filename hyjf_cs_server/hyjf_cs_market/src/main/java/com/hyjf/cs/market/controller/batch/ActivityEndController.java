package com.hyjf.cs.market.controller.batch;

import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.ActivityListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 进行中的活动列表状态变更定时
 * @Author: yinhui
 * @Date: 2018/12/18 15:17
 * @Version 1.0
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/activityend")
public class ActivityEndController  extends BaseMarketController {

    @Autowired
    private ActivityListService activityListService;

    @RequestMapping("/callActivityEnd")
    public String callActivityEnd() {
        activityListService.callActivityEnd();
        return "success";
    }
}
