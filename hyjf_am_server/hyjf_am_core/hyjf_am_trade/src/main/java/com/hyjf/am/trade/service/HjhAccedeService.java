/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.HjhAccede;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhAccedeService, v0.1 2018/6/25 10:24
 */
public interface HjhAccedeService {

    /**
     * 查询退出中和准备进入锁定期的计划
     * @return
     */
    List<HjhAccede> selectWaitQuitHjhList();
    /**
     * 判断用户是否有持有中的计划。如果有，则不能解除投资授权和债转授权
     * @param userId
     * @return
     */
	public boolean canCancelAuth(int userId);
}
