/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.market.service.StatisticsOperationReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Calendar;

/**
 * @author fuqiang
 * @version StatisticsOperationReportController, v0.1 2018/7/18 9:54
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/statisticsoperationreport")
public class StatisticsOperationReportController extends BaseController {
    @Autowired
    private StatisticsOperationReportService statisticsService;

    /**
     * 插入性别，性别 ，区域的统计信息
     */
    @RequestMapping("/insertoperationgroupdata")
    public String insertOperationGroupData() {
        Calendar cal = Calendar.getInstance();
        statisticsService.insertOperationGroupData(cal);
        //TODO 放后再测 yinhui
//        statisticsService.insertOperationData(cal);
        return "success";
    }

}
