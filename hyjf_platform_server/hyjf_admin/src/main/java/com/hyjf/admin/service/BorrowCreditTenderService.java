package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.BorrowCreditRepayRequest;
import com.hyjf.admin.beans.request.BorrowCreditRequest;
import com.hyjf.admin.common.result.AdminResult;

public interface BorrowCreditTenderService {

    /**
     * 查询还款信息列表
     * @author zhangyk
     * @date 2018/7/11 14:34
     */
    AdminResult getBorrowCreditRepayList(BorrowCreditRepayRequest request);


}
