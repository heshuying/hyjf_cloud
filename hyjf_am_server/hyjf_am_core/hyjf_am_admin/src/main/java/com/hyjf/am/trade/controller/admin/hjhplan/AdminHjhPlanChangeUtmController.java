/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.controller.admin.hjhplan;

import com.hyjf.am.response.trade.HjhPlanAccedeCustomizeResponse;
import com.hyjf.am.trade.service.admin.hjhplan.AdminHjhPlanChangeUtmService;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cui
 * @version AdminHjhPlanChangeUtmController, v0.1 2019/6/18 15:35
 */
@ApiOperation(value = "计划订单修改渠道")
@RestController
@RequestMapping(value = "/am-trade/planutm")
public class AdminHjhPlanChangeUtmController {

    @Autowired
    private AdminHjhPlanChangeUtmService adminHjhPlanChangeUtmService;

    @GetMapping(value = "/plan_tender_info/{planOrderId}")
    public HjhPlanAccedeCustomizeResponse getPlanTenderInfo(@PathVariable(value = "planOrderId") String planOrderId) {

        HjhPlanAccedeCustomizeResponse response=new HjhPlanAccedeCustomizeResponse();

        HjhPlanAccedeCustomizeVO vo=adminHjhPlanChangeUtmService.getHjhPlanTender(planOrderId);

        response.setResult(vo);

        return response;

    }

}
