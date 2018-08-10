package com.hyjf.am.trade.dao.mapper.customize.wdzj;

import com.hyjf.am.trade.dao.model.customize.wdzj.WDZJBorrowListDataCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.WDZJPreapysListCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.WDZJTenderListDataCustomize;

import java.util.List;
import java.util.Map;

public interface WDZJCustomizeMapper {

    List<WDZJBorrowListDataCustomize> selectBorrowList(Map<String, Object> paraMap);

    List<WDZJTenderListDataCustomize> selectTenderList(Map<String, Object> paraMap);
    
    int countBorrowList(Map<String, Object> paraMap);
    
    String sumBorrowAmount(Map<String, Object> paraMap);
    
    List<WDZJPreapysListCustomize> selectPreapysList(Map<String, Object> paraMap);
    
    int countPreapysList(Map<String, Object> paraMap);
    
}