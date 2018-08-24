package com.hyjf.admin.controller.productcenter.plancenter.reinvestdebt;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDebtService;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投承接债权
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",tags ="产品中心-汇计划-资金计划-复投承接债权")
@RestController
@RequestMapping(value = "/hjhReInvestDebt")
public class HjhReInvestDebtController extends BaseController {

    @Autowired
    private HjhReInvestDebtService hjhReInvestDebtService;

    /**
     * 资金计划 - 复投债权列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "复投债权列表")
    @PostMapping(value = "hjhReInvestDebtList")
    @ResponseBody
    public AdminResult hjhReInvestDebtList(@RequestBody @Valid HjhReInvestDebtRequest request){

        HjhReInvestDebtResponse response = hjhReInvestDebtService.hjhReInvestDebtList(request);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }
        return new AdminResult(response);
    }

}
