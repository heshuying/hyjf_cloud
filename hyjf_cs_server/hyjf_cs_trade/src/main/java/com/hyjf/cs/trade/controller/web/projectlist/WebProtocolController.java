package com.hyjf.cs.trade.controller.web.projectlist;

import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.trade.bean.CreditAssignedBean;
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
    public void creditPaymentPlan(@ModelAttribute ProtocolRequest form, HttpServletRequest request, HttpServletResponse response,@RequestHeader(value = "userId",required = false) Integer userId){
       webProtocolService.creditPaymentPlan(form,userId,request,response);
    }

    /**
     *债转被出借的协议PDF文件下载
     * @author zhangyk
     * 原接口： com.hyjf.web.bank.web.user.repay.RepayController.downloadIntermediaryPdf()
     */
    @ApiOperation(value = "债转出借协议",notes = "债转出借协议")
    @GetMapping(value = "/downloadIntermediaryPdf")
    public void downloadIntermediaryPdf(@ModelAttribute ProtocolRequest form, HttpServletRequest request, HttpServletResponse response, @RequestHeader(value = "userId",required = false) Integer userId){
        webProtocolService.downloadIntermediaryPdf(form,userId,request,response);
    }

    /**
     * 资产管理-散标-转让记录-查看详情-下载协议
     * @param tenderCreditAssignedBean
     * @param request
     * @param response
     * 原接口：com.hyjf.web.agreement.CreateAgreementController.userCreditContractToSealPDF()
     */
    @ApiOperation(value = "资产管理-散标-转让记录-查看详情-下载协议", notes = "资产管理-散标-转让记录-查看详情-下载协议")
    @GetMapping(value = "/creditTransferAgreement")
    public void creditTransferAgreement(@ModelAttribute CreditAssignedBean tenderCreditAssignedBean, HttpServletRequest request, HttpServletResponse response){
        webProtocolService.creditTransferAgreement(tenderCreditAssignedBean, tenderCreditAssignedBean.getRandom(), request, response);
    }


    /**
     *  导出PDF文件 （汇盈金服互联网金融服务平台汇计划智投服务协议）
     * @author zhangyk
     * @date 2018/11/15 17:09
     */
    @ApiOperation(value = "资产管理-智投-智投详情:智投服务协议" , notes = "资产管理-智投-智投详情:智投服务协议")
    @GetMapping(value = "/newHjhInvestPDF")
    public void newHjhInvestPDF(@ModelAttribute ProtocolRequest form, HttpServletRequest request, HttpServletResponse response){
        webProtocolService.newHjhInvestPDF(form,request,response);
    }

    /**
     * pcc
     * 账户中心-资产管理-当前持有-- 出借协议(实际为散标居间协议)下载
     * @param form
     * @param request
     * @param response
     */
    @ApiOperation(value = "账户中心-资产管理-当前持有-- 出借协议(实际为散标居间协议)下载", httpMethod = "GET", notes = "账户中心-资产管理-当前持有-- 出借协议(实际为散标居间协议)下载")
    @GetMapping(value = "/intermediaryAgreementPDF")
    public void intermediaryAgreementPDF(@ModelAttribute ProtocolRequest form, HttpServletRequest request, HttpServletResponse response){
        webProtocolService.intermediaryAgreementPDF(form,request,response);
    }
}
