package com.hyjf.admin.controller.productcenter.borrow.borrowcredittender;

import com.hyjf.admin.beans.request.BorrowCreditTenderInfoRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderPDFSignReq;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.util.ShiroConstants;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.interceptor.AuthorityAnnotation;
import com.hyjf.admin.service.BorrowCreditTenderService;
import com.hyjf.am.vo.config.AdminSystemVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Api(value = "产品中心-汇转让-承接信息" , tags = "产品中心-汇转让-承接信息")
@RestController
@RequestMapping("/borrow/creditTender")
public class AdminBorrowCreditTenderController extends BaseController {


    @Autowired
    private BorrowCreditTenderService borrowCreditTenderService;

    public static final String PERMISSIONS = "credittender";


    /**
     * com.hyjf.admin.manager.borrow.credittender.CreditTenderController.searchAction()
     * @author zhangyk
     * @date 2018/8/29 10:17
     */
    @ApiOperation(value = "承接信息", notes = "承接信息")
    @PostMapping("/getList")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_SEARCH)
    @ResponseBody
    public Object  getBorrowCreditTenderList(@RequestBody BorrowCreditTenderRequest request){
        AdminResult result = borrowCreditTenderService.getCreditTenderList(request);
        return result;
    }


    @ApiOperation(value = "承接信息导出", notes = "承接信息导出")
    @PostMapping("/exportData")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_EXPORT)
    @ResponseBody
    public void exportBorrowCreditTender( @RequestBody BorrowCreditTenderRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        borrowCreditTenderService.exportCreditTenderList(request,response);
    }



    @ApiOperation(value = "查看债权人债权信息", notes = "查看债权人债权信息")
    @PostMapping("/getCreditUserInfo")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_INFO)
    @ResponseBody
    public Object  getCreditUserInfo(@RequestBody BorrowCreditTenderInfoRequest request){
        AdminResult result =  borrowCreditTenderService.getCreditUserInfo(request);
        return result;
    }


    /**
     * 原接口：com.hyjf.admin.manager.borrow.credittender.CreditTenderController.pdfSignAction()
     * @author zhangyk
     * @date 2018/8/31 10:08
     */
    @ApiOperation(value = "PDF签署", notes = "PDF签署")
    @PostMapping("/pdfSign")
    @AuthorityAnnotation(key = PERMISSIONS, value = ShiroConstants.PERMISSION_PDF_SIGN)
    @ResponseBody
    public Object  pdfSign(@RequestBody BorrowCreditTenderPDFSignReq reqBean,HttpServletRequest req){
        AdminSystemVO adminSystemVO = this.getUser(req);
        AdminResult result =  borrowCreditTenderService.pdfSign(reqBean,adminSystemVO);
        return result;
    }





}
