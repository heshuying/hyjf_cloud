package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;

import javax.servlet.http.HttpServletResponse;

public interface BorrowCreditService {


    /**
     *  查询会转让数据列表
     * @author zhangyk
     * @date 2018/7/9 15:11
     */
    AdminResult getBorrowCreditList(BorrowCreditRequest request);

    /**
     * 汇转让导出
     * @author zhangyk
     * @date 2018/7/10 14:09
     */
    void  exportBorrowCreditList(BorrowCreditRequest request, HttpServletResponse response);


    /**
     * 汇转让明细
     * @author zhangyk
     * @date 2018/7/10 16:15
     */
    AdminResult getBorrowInfoList(BorrowCreditRequest request);
}
