/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.borrow.dao.model.auto.HjhInstConfig;

/**
 * @author zhangqingqing
 * @version UserService, v0.1 2018/5/28 12:53
 */
public interface UserService {

    /**
     * 根据机构编号检索机构信息
     *
     * @param instCode
     * @return
     */
    HjhInstConfig selectInstConfigByInstCode(String instCode);
}
