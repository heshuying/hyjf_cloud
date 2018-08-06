/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.batch;

import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.front.trade.PoundageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangjun
 * @version PoundageController, v0.1 2018/7/28 10:07
 */
@RestController
@RequestMapping("/am-trade/batch")
public class PoundageController extends BaseController {

    @Autowired
    PoundageService poundageService;

    @RequestMapping("/poundage")
    public void downloadFiles() {
        logger.info("手续费分账明细插入定时 开始...");
        poundageService.insertPoundage();
    }
}
