/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.api.wrb;

import com.hyjf.am.trade.dao.model.customize.WrbBorrowListCustomize;

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


}
