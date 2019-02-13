/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch;

import com.hyjf.cs.trade.service.batch.LateAndCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author yaoyong
 * @version LateAndCreditBatchController, v0.1 2018/12/18 16:58
 * 获取逾期标和完全债转标
 */
@RestController
@ApiIgnore
@RequestMapping("/cs-trade/lateandcredit")
public class LateAndCreditBatchController {

    @Autowired
    private LateAndCreditService lateAndCreditService;

    @RequestMapping("/updaterepayinfo")
    public void updateRepayInfo() {
        lateAndCreditService.updateRepayInfo();
    }
}
