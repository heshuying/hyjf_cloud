package com.hyjf.admin.controller.productcenter.plancenter.reinvestdetail;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.HjhReInvestDetailService;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划",description ="产品中心-汇计划-资金计划-复投详情")
@RestController
@RequestMapping(value = "/hjhReInvestDetail")
public class HjhReInvestDetailController extends BaseController {

    @Autowired
    private HjhReInvestDetailService hjhReInvestDetailService;

    @ApiOperation(value = "资金计划", notes = "复投详情列表")
    @PostMapping(value = "/hjhReInvest")
    @ResponseBody
    public AdminResult hjhReInvestList(@RequestBody @Valid HjhReInvestDetailRequest request){
        HjhReInvestDetailResponse response = hjhReInvestDetailService.hjhReInvestDetailList(request);

        if (response == null){
            return new AdminResult<>(FAIL, FAIL_DESC);
        }

        return new AdminResult(response);
    }

}
