/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.totalinterest;

import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.trade.controller.BaseController;
import com.hyjf.am.trade.service.TotalInvestAndInterestService;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fq
 * @version TotalInvestAndInterestController, v0.1 2018/7/31 11:21
 */
@RestController
@RequestMapping("/am-trade/totalinvestandinterest")
public class TotalInvestAndInterestController extends BaseController {
    @Autowired
    private TotalInvestAndInterestService totalInvestAndInterestService;

    @RequestMapping("/gettotalinvestandinterest")
    public TotalInvestAndInterestResponse getTotalInvestAndInterest() {
        TotalInvestAndInterestResponse response = new TotalInvestAndInterestResponse();
        TotalInvestAndInterestVO vo = totalInvestAndInterestService.getTotalInvestAndInterest();
        if (vo != null) {
            response.setResult(vo);
        }
        return response;
    }

}
