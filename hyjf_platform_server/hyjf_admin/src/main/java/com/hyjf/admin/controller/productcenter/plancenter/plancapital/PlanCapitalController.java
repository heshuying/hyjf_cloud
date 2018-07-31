package com.hyjf.admin.controller.productcenter.plancenter.plancapital;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.PlanCapitalService;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 产品中心 --> 汇计划 --> 资金计划
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",description ="产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/plancapital")
public class PlanCapitalController extends BaseController {

    @Autowired
    private PlanCapitalService planCapitalService;

    /**
     * 计划资金 列表
     * @param hjhPlanCapitalRequest
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "资金计划列表")
    @PostMapping(value = "planCapitalList")
    @ResponseBody
    public AdminResult planCapitalList(@RequestBody @Valid HjhPlanCapitalRequest hjhPlanCapitalRequest){

        HjhPlanCapitalResponse response = planCapitalService.getPlanCapitalList(hjhPlanCapitalRequest);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult(response);
    }
    

}
