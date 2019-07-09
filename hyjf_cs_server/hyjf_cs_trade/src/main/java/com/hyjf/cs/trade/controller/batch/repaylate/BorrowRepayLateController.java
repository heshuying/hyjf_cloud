/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.controller.batch.repaylate;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.service.batch.BorrowRepayLateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author wangjun
 * @version BorrowRepayController, v0.1 2019/3/19 17:27
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-trade/batch")
public class BorrowRepayLateController extends BaseController {

    @Autowired
    BorrowRepayLateService borrowRepayLateService;

    @GetMapping("/repaylate")
    public void borrowRepayLate(){
        borrowRepayLateService.updateBorrowRepayLateInfo();
    }
}
