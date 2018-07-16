package com.hyjf.am.trade.service.wdzj;

import com.hyjf.am.trade.dao.model.customize.wdzj.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.PreapysListCustomize;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version BorrowDataService, v0.1 2018/7/16 11:59
 */
public interface BorrowDataService {
    List<BorrowListCustomize> selectBorrowList(Map<String, Object> paraMap);

    int countBorrowList(Map<String, Object> paraMap);

    String sumBorrowAmount(Map<String, Object> paraMap);

    List<PreapysListCustomize> selectPreapysList(Map<String, Object> paraMap);

    int countPreapysList(Map<String, Object> paraMap);
}
