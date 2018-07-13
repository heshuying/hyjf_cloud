package com.hyjf.admin.controller.productcenter.borrow.borrowcredittender;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowCreditTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "产品中心-汇转让")
@RestController
@RequestMapping("/borrow/creditTender")
public class AdminBorrowCreditTenderController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;



    @ApiOperation(value = "承接信息", notes = "承接信息")
    @PostMapping("/getList")
    @ResponseBody
    public Object  getBorrowCreditTenderList(@RequestBody BorrowCreditTenderRequest request){
        AdminResult result = borrowCreditTenderService.getCreditTenderList(request);
        return result;
    }


}
