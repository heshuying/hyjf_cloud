/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.nifa;

import com.hyjf.am.trade.service.BaseService;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalService, v0.1 2019/1/19 16:52
 */
public interface NifaStatisticalService extends BaseService {
    /**
     * 查询用户借款笔数
     *
     * @param username
     * @return
     */
    Integer selectBorrowUsersCount(String username);

    /**
     * 查询用户借款笔数
     *
     * @param username
     * @return
     */
    Integer selectBorrowManInfoCount(String username);
}
