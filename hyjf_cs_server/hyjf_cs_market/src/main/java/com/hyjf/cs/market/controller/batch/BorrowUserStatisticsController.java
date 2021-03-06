/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.controller.batch;

import com.hyjf.am.response.StringResponse;
import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.BorrowUserStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author fuqiang
 * @version BorrowUserStatisticsController, v0.1 2018/7/18 15:42
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-market/operationReport")
public class BorrowUserStatisticsController extends BaseMarketController {
    @Autowired
    private BorrowUserStatisticsService statisticsService;

    @RequestMapping("/insertStatistic")
    public StringResponse insertStatistics() {
        StringResponse response = new StringResponse();
        statisticsService.insertStatistics();
        return response;
    }
}
