/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.account;

import com.hyjf.am.trade.dao.model.customize.BatchBorrowTenderCustomize;

import java.util.List;

/**
 * 江西银行出借调单处理自动任务
 * @author jun
 * @since 20180623
 */
public interface BankInvestService {


    List<BatchBorrowTenderCustomize> queryAuthCodeBorrowTenderList();

    void insertAuthCode(List<BatchBorrowTenderCustomize> list);
}
