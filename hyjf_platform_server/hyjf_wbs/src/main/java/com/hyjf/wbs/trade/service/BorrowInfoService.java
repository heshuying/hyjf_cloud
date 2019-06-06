package com.hyjf.wbs.trade.service;

import com.hyjf.wbs.trade.dao.model.customize.BorrowCustomize;

/**
 * @Auther: wxd
 * @Date: 2019-04-30 10:36
 * @Description:
 */
public interface BorrowInfoService extends BaseService {
    public BorrowCustomize selectByNid(String nid);
}
