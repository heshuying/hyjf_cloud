/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.InvestorDebtBean;
import com.hyjf.admin.beans.request.InvestorRequest;
import com.hyjf.admin.beans.response.BorrowInvestResponseBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.am.response.Response;
import com.hyjf.am.resquest.admin.BorrowInvestRequest;
import com.hyjf.am.vo.admin.BorrowInvestCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowInvestService, v0.1 2018/7/10 9:17
 */
public interface BorrowInvestService {

    Integer countBorrowInvest(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细列表
     *
     * @param borrowInvestRequest
     * @return
     */
    BorrowInvestResponseBean getBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借明细导出列表
     *
     * @param borrowInvestRequest
     * @return
     */
    List<BorrowInvestCustomizeVO> getExportBorrowInvestList(BorrowInvestRequest borrowInvestRequest);

    /**
     * 出借人债权明细
     *
     * @param investorDebtBean
     * @return
     */
    AdminResult debtInfo(InvestorDebtBean investorDebtBean);

    /**
     * PDF脱敏图片预览
     *
     * @param nid
     * @return
     */
    AdminResult pdfPreview(String nid);

    /**
     * PDF签署
     *
     * @param investorDebtBean
     * @return
     */
    AdminResult pdfSign(InvestorDebtBean investorDebtBean);

    /**
     * 发送协议
     *
     * @param investorRequest
     * @return
     */
    AdminResult sendAgreement(InvestorRequest investorRequest);

    /**
     * 重发协议
     *
     * @param investorRequest
     * @return
     */
    AdminResult resendAgreement(InvestorRequest investorRequest);

    Response doCreditEnd(String orderId);
}
