package com.hyjf.am.trade.service.front.borrow;

import com.hyjf.am.trade.dao.model.auto.BorrowUser;
import com.hyjf.am.trade.service.BaseService;

/**
 * 借款人公司信息
 * @Author zhangyk upd by liushouyi
 */
public interface BorrowUserService extends BaseService {

    /**
     * 根据借款编号获取借款人公司信息
     *
     * @param borrowNid
     * @return
     */
    BorrowUser getBorrowUserByNid(String borrowNid);
}
