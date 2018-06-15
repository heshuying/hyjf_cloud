/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.trade.dao.model.auto.BorrowStyle;
import com.hyjf.am.trade.dao.model.auto.BorrowWithBLOBs;

/**
 * @author ${yaoy}
 * @version BorrowService, v0.1 2018/6/14 17:13
 */
public interface BorrowService {
    BorrowWithBLOBs getBobrrow(String borrowNid);

    BorrowStyle getborrowStyleByNid(String borrowStyle);
}
