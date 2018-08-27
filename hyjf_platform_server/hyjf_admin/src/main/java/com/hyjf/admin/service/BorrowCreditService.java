package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

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
    void  exportBorrowCreditList(BorrowCreditRequest request, HttpServletResponse response) throws UnsupportedEncodingException;


    /**
     * 汇转让明细
     * @author zhangyk
     * @date 2018/7/10 16:15
     */
    AdminResult getBorrowInfoList(BorrowCreditRequest request);

    /**
     * 取消转让
     * @author zhangyk
     * @date 2018/8/24 16:43
     */
    AdminResult cancelCredit(BorrowCreditRequest request);
}
