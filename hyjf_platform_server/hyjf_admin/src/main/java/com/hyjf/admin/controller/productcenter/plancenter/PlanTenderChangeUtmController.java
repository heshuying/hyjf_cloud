/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.productcenter.plancenter;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanTenderChangeUtmService;
import com.hyjf.am.vo.trade.hjh.HjhAccedeCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAccedeCustomizeVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cui
 * @version PlanTenderChangeUtmController, v0.1 2019/6/18 14:44
 */
@ApiOperation(value = "产品中心-汇计划-加入明细2",notes = "产品中心-汇计划-加入明细2")
@RestController
@RequestMapping(value = "/hyjf-admin/planutm")
public class PlanTenderChangeUtmController extends BaseController {

    @Autowired
    private PlanTenderChangeUtmService planTenderChangeUtmService;

    @ApiOperation(value = "加入计划订单详情",notes = "加入计划订单详情")
    @GetMapping(value = "/plan_tender_info/{planOrderId}")
    public AdminResult<HjhPlanAccedeCustomizeVO> getPlanTenderInfo(@PathVariable(value = "planOrderId") String planOrderId){

        HjhPlanAccedeCustomizeVO vo=planTenderChangeUtmService.getPlanTenderInfo(planOrderId);

        return new AdminResult<>(vo);

    }
}
