package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.beans.request.BorrowCreditTenderRequest;
import com.hyjf.admin.common.result.AdminResult;

import javax.servlet.http.HttpServletResponse;

public interface BorrowCreditTenderService {

    /**
     * 查询还款信息列表
     * @author zhangyk
     * @date 2018/7/11 14:34
     */
    AdminResult getBorrowCreditRepayList(BorrowCreditRepayRequest request);

    /**
     * 还款信息列表导出
     * @author zhangyk
     * @date 2018/7/11 20:41
     */
    void exprotBorrowCreditRepayList(BorrowCreditRepayRequest request, HttpServletResponse response);


    /**
     * 还款信息明细
     * @author zhangyk
     * @date 2018/7/12 10:52
     */
    AdminResult getBorrowCreditRepayInfoList(BorrowCreditRepayRequest request);


    /**
     * 承接信息列表
     * @author zhangyk
     * @date 2018/7/12 19:04
     */
    AdminResult getCreditTenderList(BorrowCreditTenderRequest request);


}
