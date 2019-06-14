/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.resquest.admin.BorrowRegistListRequest;
import com.hyjf.am.trade.dao.model.customize.BorrowRegistCustomize;
import com.hyjf.am.vo.admin.BorrowRegistCancelConfirmCustomizeVO;

import java.util.List;

/**
 * @author wangjun
 * @version BorrowRegistCustomizeMapper, v0.1 2018/7/2 9:14
 */
public interface BorrowRegistCustomizeMapper {
    /**
     * 获取标的列表count
     * @param borrowRegistListRequest
     * @return
     */
    Integer getRegistCount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 获取标的备案列表
     * @param borrowRegistListRequest
     * @return
     */
    List<BorrowRegistCustomize> selectBorrowRegistList(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 统计页面值总和
     * @param borrowRegistListRequest
     * @return
     */
    String sumBorrowRegistAccount(BorrowRegistListRequest borrowRegistListRequest);

    /**
     * 标的备案撤销确认页面数据
     * @param borrowNid
     * @return
     */
    BorrowRegistCancelConfirmCustomizeVO selectRegistCancelConfirm(String borrowNid);
}
