/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb;

import com.hyjf.am.resquest.api.WrbInvestRequest;
import com.hyjf.am.trade.dao.model.auto.BorrowTender;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.WrbBorrowTenderCustomize;

import java.util.List;

/**
 * @author fq
 * @version WrbInfoService, v0.1 2018/9/25 11:12
 */
public interface WrbInfoService {
    /**
     * 标的查询
     * @param borrowNid
     * @return
     */
    List<WrbBorrowListCustomize> borrowList(String borrowNid);

    /**
     * 获取某天投资数据
     * @param request
     * @return
     */
    List<BorrowTender> getBorrowTenderList(WrbInvestRequest request);

    /**
     * 查询标的投资情况
     * @return
     */
    List<WrbBorrowTenderCustomize> getBorrowTenderByBorrowNid(WrbInvestRequest request);
}
