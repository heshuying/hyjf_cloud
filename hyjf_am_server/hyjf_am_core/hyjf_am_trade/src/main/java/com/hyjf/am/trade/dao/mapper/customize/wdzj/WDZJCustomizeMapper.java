package com.hyjf.am.trade.dao.mapper.customize.wdzj;

import com.hyjf.am.trade.dao.model.customize.wdzj.BorrowListCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.PreapysListCustomize;
import com.hyjf.am.trade.dao.model.customize.wdzj.TenderListCustomize;

import java.util.List;
import java.util.Map;

public interface WDZJCustomizeMapper {

    List<BorrowListCustomize> selectBorrowList(Map<String, Object> paraMap);

    List<TenderListCustomize> selectTenderList(Map<String, Object> paraMap);
    
    int countBorrowList(Map<String, Object> paraMap);
    
    String sumBorrowAmount(Map<String, Object> paraMap);
    
    List<PreapysListCustomize> selectPreapysList(Map<String, Object> paraMap);
    
    int countPreapysList(Map<String, Object> paraMap);
    
}