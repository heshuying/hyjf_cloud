/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;

/**
 * @author zhangqingqing
 * @version AmDataCollectClient, v0.1 2018/6/25 10:27
 */
public interface AmDataCollectClient {

    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    String selectBorrowInvestAccount(AccountWebListVO accountWebList);
}
