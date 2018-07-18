/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.BorrowUserStatisticsService;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsController, v0.1 2018/7/18 15:42
 */
@RestController
@RequestMapping("/cs-market/statisticsoperationreport")
public class BorrowUserStatisticsController extends BaseMarketController {
    @Autowired
    private BorrowUserStatisticsService statisticsService;

    @RequestMapping("/insertstatistics")
    public String insertStatistics() {
        statisticsService.insertStatistics();
        return "success";
    }
}
