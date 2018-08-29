package com.hyjf.admin.controller.productcenter.borrow.borrowcredit;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
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

    public static final String PERMISSIONS = "borrowcredit";


    /**
     * 债权转让列表
     * @author zhangyk
     * 原接口：com.hyjf.admin.manager.borrow.borrowcredit.BorrowCreditController.search()
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让", notes = "债权转让")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditList(@RequestBody BorrowCreditRequest request){
        AdminResult result = borrowCreditService.getBorrowCreditList(request);
        return result;
    }

    /**
     * 债权转让导出
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让导出", notes = "债权转让导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void  exportBorrowCreditList(@RequestBody BorrowCreditRequest request,HttpServletResponse response) throws UnsupportedEncodingException {
        borrowCreditService.exportBorrowCreditList(request,response);
    }

    /**
     * 债权转让明细
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "债权转让明细", notes = "债权转让明细")
    @PostMapping("/infoDetail")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    @ResponseBody
    public Object getCreditInfoDetail(@RequestBody BorrowCreditRequest request){
       AdminResult result =  borrowCreditService.getBorrowInfoList(request);
       return result;
    }

    /**
     * 取消债权转让
     * @author zhangyk
     * @date 2018/8/7 9:36
     */
    @ApiOperation(value = "取消债权转让", notes = "取消债权转让")
    @PostMapping("/cancel")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_CANCEL)
    @ResponseBody
    public Object creditCalcel(@RequestBody BorrowCreditRequest request){
        AdminResult result =  borrowCreditService.cancelCredit(request);
        return result;
    }


    @ApiOperation(value = "转让状态下拉选" , notes = "转让状态下拉选")
    @PostMapping("/creditStatusList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_VIEW)
    @ResponseBody
    public Object getCreditStatusList(){
        AdminResult result =  borrowCreditService.getCreditStatusList();
        return result;
    }



}
