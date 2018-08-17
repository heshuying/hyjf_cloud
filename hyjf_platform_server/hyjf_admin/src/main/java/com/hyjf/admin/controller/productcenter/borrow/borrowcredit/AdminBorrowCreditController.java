package com.hyjf.admin.controller.productcenter.borrow.borrowcredit;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.service.BorrowCreditService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * admin: 汇直投：债权转让
 * @author zhangyk
 * @date 2018/7/9 14:59
 */

@Api(value = "产品中心-汇转让-债权转让", tags = "产品中心-汇转让-债权转让")
@RestController
@RequestMapping("/borrow/credit")
public class AdminBorrowCreditController {


    @Autowired
    private BorrowCreditService borrowCreditService;


    /**
     * 债权转让列表
     * @author zhangyk
     * 原接口：com.hyjf.admin.manager.borrow.borrowcredit.BorrowCreditController.search()
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让", notes = "债权转让")
    @PostMapping("/getList")
    @ResponseBody
    public Object  getBorrowCreditList(@RequestBody BorrowCreditRequest request){
        AdminResult result = borrowCreditService.getBorrowCreditList(request);
        return result;
    }

    @ApiOperation(value = "债权转让导出", notes = "债权转让导出")
    @PostMapping("/exportData")
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        borrowCreditService.exportBorrowCreditList(request,response);
    }

    @ApiOperation(value = "债权转让明细", notes = "债权转让明细")
    @PostMapping("/infoDetail")
    @ResponseBody
    public Object getCreditInfoDetail(@RequestBody BorrowCreditRequest request){
       AdminResult result =  borrowCreditService.getBorrowInfoList(request);
       return result;
    }

}
