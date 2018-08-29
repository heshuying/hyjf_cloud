package com.hyjf.admin.controller.productcenter.borrow.borrowcreditpayment;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCreditTenderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

/**
 * admin: 汇转让：还款信息
 * @author zhangyk
 * @date 2018/7/11 13:42
 */
@Api(value = "产品中心-汇转让-还款信息",tags ="产品中心-汇转让-还款信息")
@RestController
@RequestMapping("/borrow/creditPayment")
public class AdminBorrowCreditPaymentController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    public static final String PERMISSIONS = "borrowcreditrepay";

    /**
     * com.hyjf.admin.manager.borrow.borrowcreditrepay.BorrowCreditRepayController.search()
     * @author zhangyk
     * @date 2018/8/29 10:24
     */
    @ApiOperation(value = "还款信息", notes = "还款信息")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditPaymentList(@RequestBody BorrowCreditRepayRequest request){
        AdminResult result = borrowCreditTenderService.getBorrowCreditRepayList(request);
        return result;
    }

    @ApiOperation(value = "还款信息导出", notes = "还款信息导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRepayRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        borrowCreditTenderService.exportBorrowCreditRepayList(request,response);
    }

    @ApiOperation(value = "还款信息明细", notes = "还款信息明细")
    @PostMapping("/infoDetail")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    @ResponseBody
    public Object getCreditRepayInfoDetail(@RequestBody BorrowCreditRepayRequest request){
       AdminResult result =  borrowCreditTenderService.getBorrowCreditRepayInfoList(request);
       return result;
    }

}
