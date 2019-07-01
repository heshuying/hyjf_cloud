/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.AutoHjhPlanCapitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author wenxin
 * @version AutoHjhPlanCapitalBatchController, v0.1 2019/04/15
 * 资金计划3.3.0
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/hjhPlanAutoCapital")
public class AutoHjhPlanCapitalBatchController {

    @Autowired
    private AutoHjhPlanCapitalService autoHjhPlanCapitalService;

    @RequestMapping("/autoCapitalPrediction")
    public void autoCapitalPrediction() {
        // 计算预计
        autoHjhPlanCapitalService.autoCapitalPrediction();
    }

    @RequestMapping("/autoCapitalActual")
    public void autoCapitalActual() {
        // 计算当日
        autoHjhPlanCapitalService.autoCapitalActual();
    }
}
