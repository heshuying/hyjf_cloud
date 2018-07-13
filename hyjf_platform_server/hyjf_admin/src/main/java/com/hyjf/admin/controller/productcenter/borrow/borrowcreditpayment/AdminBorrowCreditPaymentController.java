package com.hyjf.admin.controller.productcenter.borrow.borrowcreditpayment;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowCreditTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * admin: 汇转让：还款信息
 * @author zhangyk
 * @date 2018/7/11 13:42
 */
@Api(value = "产品中心-汇转让")
@RestController
@RequestMapping("/borrow/creditPayment")
public class AdminBorrowCreditPaymentController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;


    @ApiOperation(value = "还款信息", notes = "还款信息")
    @PostMapping("/getList")
    @ResponseBody
    public Object  getBorrowCreditPaymentList(@RequestBody BorrowCreditRepayRequest request){
        AdminResult result = borrowCreditTenderService.getBorrowCreditRepayList(request);
        return result;
    }

    @ApiOperation(value = "还款信息导出", notes = "还款信息导出")
    @PostMapping("/exportData")
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRepayRequest request,HttpServletResponse response){
        borrowCreditTenderService.exprotBorrowCreditRepayList(request,response);
    }

    @ApiOperation(value = "还款信息明细", notes = "还款信息明细")
    @PostMapping("/infoDetail")
    @ResponseBody
    public Object getCreditRepayInfoDetail(@RequestBody BorrowCreditRepayRequest request){
       AdminResult result =  borrowCreditTenderService.getBorrowCreditRepayInfoList(request);
       return result;
    }

}
