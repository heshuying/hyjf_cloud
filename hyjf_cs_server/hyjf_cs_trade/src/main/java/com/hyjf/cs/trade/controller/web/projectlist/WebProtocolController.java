package com.hyjf.cs.trade.controller.web.projectlist;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.ProtocolRequest;
import com.hyjf.cs.trade.service.projectlist.WebProtocolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * web协议
 * @author zhangyk
 * @date 2018/10/18 10:05
 */
@Api(tags = "web端-协议相关")
@Controller
@RequestMapping("/hyjf-web/createAgreementPDF")
public class WebProtocolController extends BaseController {


    @Autowired
    private WebProtocolService webProtocolService;

    /**
     *
     * @param request
     * 原接口：com.hyjf.web.agreement.CreateAgreementController.createAgreementPDFFile()
     */
    @ApiOperation(value = "协议下载", notes = "协议下载")
    @GetMapping(value = "/creditPaymentPlan")
    public File homeBorrowProjectList(@ModelAttribute ProtocolRequest form, HttpServletRequest request, HttpServletResponse response,@RequestHeader(value = "userId",required = false) Integer userId){
        File  file = webProtocolService.creditPaymentPlan(form,userId,request,response);
        if (null == file){
            logger.info("get result file is null");
        }
        return file;
    }


}
