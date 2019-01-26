/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.bifa;

import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaIndexDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author jun
 * @version BifaIndexDataController, v0.1 2019/1/21 9:22
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/bifaIndexData")
public class BifaIndexDataController {

    @Autowired
    BifaIndexDataService bifaIndexDataService;

    /**
     * 索引文件上报
     */
    @GetMapping("/report")
    public void report() {
        bifaIndexDataService.report();
    }

    /**
     * 索引历史数据上报
     */
    @GetMapping("/historydatareport")
    public void historydatareport(){
        bifaIndexDataService.historydatareport();
    }
}
