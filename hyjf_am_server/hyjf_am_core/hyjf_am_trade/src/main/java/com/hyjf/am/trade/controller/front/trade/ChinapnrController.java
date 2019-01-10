/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.front.trade;

import com.hyjf.am.response.BooleanResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.trade.service.front.trade.ChinapnrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangqingqing
 * @version ChinapnrController, v0.1 2018/9/8 10:08
 */
@RestController
@RequestMapping("/am-trade/chinapnr")
public class ChinapnrController {

    @Autowired
    ChinapnrService chinapnrService;

    @PostMapping("/handlerAfterCash")
    public BooleanResponse handlerAfterCash(@RequestBody ChinaPnrWithdrawRequest chinaPnrWithdrawRequest) {
        boolean flag = chinapnrService.updateAfterCash(chinaPnrWithdrawRequest);
        return  new BooleanResponse(flag);
    }

    @GetMapping("/updateAccountWithdrawByOrdId/{ordId}/{reason}")
    public IntegerResponse updateAccountWithdrawByOrdId(@PathVariable String ordId,@PathVariable String reason) {
        int cnt = chinapnrService.updateAccountWithdrawByOrdId(ordId,reason);
        return new IntegerResponse(cnt);
    }
}
