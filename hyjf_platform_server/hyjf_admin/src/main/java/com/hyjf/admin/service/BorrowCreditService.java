package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;

public interface BorrowCreditService {


    /**
     *  查询会转让数据列表
     * @author zhangyk
     * @date 2018/7/9 15:11
     */
    AdminResult getBorrowCreditList(BorrowCreditRequest request);
}
