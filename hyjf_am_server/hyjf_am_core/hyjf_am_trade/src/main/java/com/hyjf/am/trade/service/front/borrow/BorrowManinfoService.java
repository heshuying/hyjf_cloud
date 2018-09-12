package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowManinfo;
import com.hyjf.am.trade.service.BaseService;

/**
 * 页面录入借款人信息
 * @author zhangyk upd by liushouyi
 */
public interface BorrowManinfoService extends BaseService {

    /**
     * 根据借款编号获取借款人信息
     *
     * @param borrowNid
     * @return
     */
    BorrowManinfo getBorrowManinfoByNid(String borrowNid);
}
