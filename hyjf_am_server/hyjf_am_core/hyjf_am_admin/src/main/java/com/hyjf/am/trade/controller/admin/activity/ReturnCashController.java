package com.hyjf.am.trade.controller.admin.activity;

import com.hyjf.am.resquest.admin.ReturnCashRequest;
import com.hyjf.am.resquest.market.InviterReturnCashCustomize;
import com.hyjf.am.trade.service.admin.ReturnCashActivityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/saveReturnCash")
    public void saveReturnCash(@RequestBody ReturnCashRequest request) {
        //查询用户提出来
        InviterReturnCashCustomize inviterReturnCashCustomize = returnCashActivityService.selectReturnCashList(request.getUserId());
        returnCashActivityService.saveReturnCash(request.getUserId(),request.getOrderId(),request.getProductType(),request.getInvestMoney(),inviterReturnCashCustomize);
    }
}
