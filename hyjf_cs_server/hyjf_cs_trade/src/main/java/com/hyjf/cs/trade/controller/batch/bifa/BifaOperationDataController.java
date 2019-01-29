/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.bifa;

import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaOperationDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author jun
 * @version BifaOperationDataController, v0.1 2019/1/21 16:11
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/bifaOperationData")
public class BifaOperationDataController {

    @Autowired
    BifaOperationDataService bifaOperationDataService;

    /**
     * 索引文件上报
     */
    @GetMapping("/report")
    public void report() {
        bifaOperationDataService.report();
    }
}
