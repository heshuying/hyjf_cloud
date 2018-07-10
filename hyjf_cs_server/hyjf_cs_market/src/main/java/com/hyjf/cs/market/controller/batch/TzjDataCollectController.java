package com.hyjf.cs.market.controller.batch;

import com.hyjf.cs.market.controller.BaseMarketController;
import com.hyjf.cs.market.service.TzjDataCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiasq
 * @version TzjDataCollectController, v0.1 2018/7/6 10:54
 * 投之家数据统计
 */
@RestController
@RequestMapping("/cs-market/tzj")
public class TzjDataCollectController extends BaseMarketController {
    @Autowired
    TzjDataCollectService tzjDataCollectService;

    @RequestMapping("/day-report-data")
    public String queryTzjDayReport(){
        tzjDataCollectService.queryTzjDayReport();
        return "success";
    }
}
