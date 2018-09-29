package com.hyjf.am.trade.dao.mapper.customize;

import com.hyjf.am.trade.dao.model.customize.WDZJBorrowListDataCustomize;
import com.hyjf.am.trade.dao.model.customize.WDZJPreapysListCustomize;
import com.hyjf.am.trade.dao.model.customize.WDZJTenderListDataCustomize;

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