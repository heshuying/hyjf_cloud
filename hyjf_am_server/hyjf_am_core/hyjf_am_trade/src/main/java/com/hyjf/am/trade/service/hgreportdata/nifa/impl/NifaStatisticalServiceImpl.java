/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.hgreportdata.nifa.impl;

import com.hyjf.am.trade.service.hgreportdata.nifa.NifaStatisticalService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author PC-LIUSHOUYI
 * @version NifaStatisticalServiceImpl, v0.1 2019/1/19 16:52
 */
@Service
public class NifaStatisticalServiceImpl extends BaseServiceImpl implements NifaStatisticalService {

    /**
     * 查询用户借款笔数
     *
     * @param username
     * @return
     */
    @Override
    public Integer selectBorrowUsersCount(String username) {
        Integer count = this.nifaStatisticalCustomizeMapper.selectBorrowUsersCount(username);
        return count;
    }

    /**
     * 查询用户借款笔数
     *
     * @param username
     * @return
     */
    @Override
    public Integer selectBorrowManInfoCount(String username) {
        Integer count = this.nifaStatisticalCustomizeMapper.selectBorrowManInfoCount(username);
        return count;
    }
}
