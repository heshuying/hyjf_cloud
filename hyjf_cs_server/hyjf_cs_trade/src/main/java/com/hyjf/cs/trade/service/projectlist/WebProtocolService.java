package com.hyjf.cs.trade.service.projectlist;

import com.hyjf.cs.trade.bean.CreditAssignedBean;
import com.hyjf.cs.trade.bean.ProtocolRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface WebProtocolService{

    /**
     * 下载脱敏后居间服务借款协议（原始标的）_计划投资人
     * @author zhangyk
     * @date 2018/10/18 11:34
     */
    public File creditPaymentPlan(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 债转投资协议
     * @author zhangyk
     * @date 2018/11/10 14:03
     */
    public void downloadIntermediaryPdf(ProtocolRequest form, Integer userId, HttpServletRequest request, HttpServletResponse response);

    /**
     * 资产管理-散标-转让记录-查看详情-下载协议
     * @param tenderCreditAssignedBean
     * @param currentUserId
     * @param request
     * @param response
     * @return
     */
    void creditTransferAgreement(CreditAssignedBean tenderCreditAssignedBean, Integer currentUserId, HttpServletRequest request, HttpServletResponse response);


    /**
     *  汇计划投资服务协议
     * @author zhangyk
     * @date 2018/11/15 17:23
     */
    void  newHjhInvestPDF(ProtocolRequest form, HttpServletRequest request, HttpServletResponse response);

    /**
     * 投资协议(实际为散标居间协议)下载
     * @param form
     * @param request
     * @param response
     */
    void intermediaryAgreementPDF(ProtocolRequest form, HttpServletRequest request, HttpServletResponse response);
}
