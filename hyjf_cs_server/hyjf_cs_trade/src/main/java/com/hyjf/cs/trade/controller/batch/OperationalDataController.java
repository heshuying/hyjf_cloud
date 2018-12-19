/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.OperationalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version OperationalDataController, v0.1 2018/12/18 17:10
 * 资金变化统计
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/batch")
public class OperationalDataController {

    @Autowired
    private OperationalDataService operationalDataService;

    @RequestMapping("/countRechargeMoney")
    public void countRechargeMoney() {
        operationalDataService.countRechargeMoney();
    }
}
