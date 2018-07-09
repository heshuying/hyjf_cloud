/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.statistics.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteService, v0.1 2018/7/6 10:52
 */
public interface WebsiteService {

    List<AccountTradeVO> selectTradeTypes();

    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    String selectBorrowInvestAccount(AccountWebListVO accountWebList);

    double getCompanyYuE(String companyId);
}
