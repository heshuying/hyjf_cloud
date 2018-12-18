/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.cs.trade.service.batch.TenderMatchDaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version TenderMatchDaysBatchController, v0.1 2018/12/18 16:30
 * 计算自动投资的匹配期(每日)
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/tenderMatchDays/batch")
public class TenderMatchDaysBatchController {

    @Autowired
    private TenderMatchDaysService tenderMatchDaysService;

    @RequestMapping("/tenderMatchDays")
    public BooleanResponse tenderMatchDays() {
        BooleanResponse response = new BooleanResponse();
        response = tenderMatchDaysService.tenderMatchDays();
        return response;
    }
}
