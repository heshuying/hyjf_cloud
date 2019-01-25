/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.bifa;

import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaExceptionDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author jun
 * @version BifaExceptionDataController, v0.1 2019/1/17 16:54
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/bifaExceptionData")
public class BifaExceptionDataController {

    @Autowired
    private BifaExceptionDataService bifaExceptionDataService;

    @GetMapping("/reportAgain")
    public void reportAgain() {
        bifaExceptionDataService.reportAgain();
    }
}
