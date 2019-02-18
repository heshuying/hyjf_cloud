/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize.hgreportdata.nifa;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalCustomizeMapper, v0.1 2019/1/19 16:40
 */
public interface NifaStatisticalCustomizeMapper {
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
