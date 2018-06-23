/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import java.util.List;

import com.hyjf.am.trade.dao.model.auto.BatchBorrowTenderCustomize;

/**
 * 江西银行投资调单处理自动任务
 * @author jun
 * @since 20180623
 */
public interface BankInvestExceptionService {


    List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList();

    void insertAuthCode(List<BatchBorrowTenderCustomize> list);
}
