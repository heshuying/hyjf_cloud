/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.vo.admin.BorrowDeleteConfirmCustomizeVO;

/**
 * 标的删除
 * @author hesy
 */
public interface BorrowDeleteCustomizeMapper {

    /**
     * 标的备案撤销确认页面数据
     * @param borrowNid
     * @return
     */
    BorrowDeleteConfirmCustomizeVO selectDeleteConfirm(String borrowNid);
}
