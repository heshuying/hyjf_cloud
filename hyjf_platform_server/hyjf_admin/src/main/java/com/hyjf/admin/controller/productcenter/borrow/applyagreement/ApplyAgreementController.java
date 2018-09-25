package com.hyjf.admin.controller.productcenter.borrow.applyagreement;

import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ApplyAgreementService;
import com.hyjf.am.resquest.admin.ApplyAgreementRequest;
import com.hyjf.am.resquest.admin.BorrowRepayAgreementRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @version ApplyAgreementController, v0.1 2018/8/9 16:36
 * @Author: Zha Daojian
 */

@Api(value = "Admin端产品中心-汇转让-垫付协议管理",tags="Admin端产品中心-汇转让-垫付协议管理")
@RestController
@RequestMapping("/hyjf-admin/applyagreement")
public class ApplyAgreementController  extends BaseController {

    @Autowired
    private ApplyAgreementService applyAgreementService;



    @ApiOperation(value = "垫付协议申请列表", notes = "垫付协议申请列表")
    @PostMapping("/getList")
    @ResponseBody
    public AdminResult  getApplyAgreementList(@RequestBody ApplyAgreementRequest request){
        AdminResult result = applyAgreementService.getApplyAgreementList(request);
        return result;
    }

    @ApiOperation(value = "垫付协议申请明细列表页", notes = "垫付协议申请明细列表页")
    @PostMapping("/addListDetail")
    @ResponseBody
    public AdminResult getAddApplyAgreementListDetail(@RequestBody BorrowRepayAgreementRequest request){
        AdminResult result =  applyAgreementService.getAddApplyAgreementListDetail(request);
        return result;
    }

    @ApiOperation(value = "批量生成垫付债转协议", notes = "批量生成垫付债转协议")
    @PostMapping("/generateContract")
    @ResponseBody
    public AdminResult generateContract(@RequestBody BorrowRepayAgreementRequest request){
        AdminResult result =  applyAgreementService.generateContract(request);
        return result;
    }
}
