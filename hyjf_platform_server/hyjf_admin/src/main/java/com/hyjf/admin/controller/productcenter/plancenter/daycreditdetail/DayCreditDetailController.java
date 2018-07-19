package com.hyjf.admin.controller.productcenter.plancenter.daycreditdetail;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.DayCreditDetailService;
import com.hyjf.am.response.admin.DayCreditDetailResponse;
import com.hyjf.am.resquest.admin.DayCreditDetailRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 转让详情
 * @Author : huanghui
 */
@Api(value = "产品中心-汇计划-资金计划")
@RestController
@RequestMapping(value = "/dayCreditDetail")
public class DayCreditDetailController extends BaseController {

    @Autowired
    private DayCreditDetailService dayCreditDetailService;

    /**
     * 资金计划 - 转让详情列表
     * @param request
     * @return
     */
    @ApiOperation(value = "资金计划", notes = "复投债权列表")
    @PostMapping(value = "hjhDayCreditDetailList")
    @ResponseBody
    public AdminResult hjhDayCreditDetail(@RequestBody @Valid DayCreditDetailRequest request){

        DayCreditDetailResponse response = dayCreditDetailService.hjhDayCreditDetailList(request);

        if (response == null){
            return  new AdminResult(FAIL, FAIL_DESC);
        }
        return new AdminResult(response);
    }
}
