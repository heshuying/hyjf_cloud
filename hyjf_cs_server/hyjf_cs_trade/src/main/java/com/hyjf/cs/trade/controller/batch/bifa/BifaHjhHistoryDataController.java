/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.bifa;

import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaHjhHistoryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 智投历史数据上报
 * @author jun
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/bifaHjhHistoryData")
public class BifaHjhHistoryDataController {

    @Autowired
    private BifaHjhHistoryDataService bifaHjhHistoryDataService;

    @GetMapping("/report")
    public void report() {
        bifaHjhHistoryDataService.report();
    }
}
