package com.hyjf.am.market.controller.admin.activity;

import com.hyjf.am.market.service.ReturnCashActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tyy
 * @version ReturnCashController, v0.1 2018/12/26 15:38
 */

@RestController
@RequestMapping("/am-market/returncash")
public class ReturnCashController {
    private static final Logger logger = LoggerFactory.getLogger(ReturnCashController.class);
    @Autowired
    private ReturnCashActivityService returnCashActivityService;
    @GetMapping("/updatejointime/{borrowNid}/{nowTime}")
    public void getInfoById(@PathVariable String borrowNid,@PathVariable Integer nowTime) {
        returnCashActivityService.updateJoinTime(borrowNid,nowTime);
    }

}
