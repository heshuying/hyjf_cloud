/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.admin.AccountWebListResponse;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.trade.AccountTradeVO;

import java.util.List;

/**
 * @author zhangqingqing
 * @version WebsiteService, v0.1 2018/7/6 10:52
 */
public interface WebsiteService {

    /**
     * 查询交易类型列表
     * @return
     */
    List<AccountTradeVO> selectTradeTypes();

    /**
     * 查询网站收支列表
     * @param accountWebList
     * @return
     */
    AccountWebListResponse queryAccountWebList(AccountWebListVO accountWebList);

    /**
     * 交易金额总计
     * @param accountWebList
     * @return
     */
    String selectBorrowInvestAccount(AccountWebListVO accountWebList);

    /**
     * 取得公司账户余额
     * @param companyId
     * @return
     */
    double getCompanyYuE(String companyId);
}
