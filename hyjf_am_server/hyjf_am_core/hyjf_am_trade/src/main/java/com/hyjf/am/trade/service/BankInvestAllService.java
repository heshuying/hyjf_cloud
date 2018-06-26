/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.resquest.trade.BorrowTenderTmpRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTenderTmp;

import java.util.List;

/**
 * 江西银行投资全部掉单处理自动任务
 * @author jun
 * @since 20180623
 */
public interface BankInvestAllService {

    List<BorrowTenderTmp> getBorrowTenderTmpList();

    void updateTenderStart(BorrowTenderTmpRequest request) throws Exception;
}
