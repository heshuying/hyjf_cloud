package com.hyjf.cs.message.controller.batch;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.access.AppChannelStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("/cs-message/appchannelstatistics")
public class AppChannelStatisticsJobController extends BaseController {

    @Autowired
    private AppChannelStatisticsService statisticsService;

    @RequestMapping("/insertStatistics")
    public String insertStatistics() {
        statisticsService.insertStatistics();
        return "success";
    }
}
