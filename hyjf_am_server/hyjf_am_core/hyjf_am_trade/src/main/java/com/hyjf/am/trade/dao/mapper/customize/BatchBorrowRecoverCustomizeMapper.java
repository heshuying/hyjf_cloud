package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.auto.BorrowRecover;

/**
 * @author wgx
 * @date 2019/3/11
 */
public interface BatchBorrowRecoverCustomizeMapper {

    /**
     * 更新还款表还款金额
     *
     * @param borrowRecover
     * @return
     */
    int updateRepayOfBorrowRecover(BorrowRecover borrowRecover);
}
