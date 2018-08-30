package com.hyjf.am.trade.service.front.repay;

import com.hyjf.am.resquest.trade.ApiBorrowRepaymentInfoRequest;
import com.hyjf.am.trade.dao.model.customize.ApiBorrowRepaymentInfoCustomize;

import java.util.List;

/**
 * @version ApiBorrowRepaymentInfoService, v0.1 2018/8/28 14:48
 * @Author: Zha Daojian
 */
public interface ApiBorrowRepaymentInfoService {

    List<ApiBorrowRepaymentInfoCustomize> selectBorrowRepaymentInfoList(
            ApiBorrowRepaymentInfoRequest request);
}
