package com.hyjf.am.trade.service.front.wdzj.impl;

import com.hyjf.am.trade.dao.model.customize.WDZJBorrowListDataCustomize;
import com.hyjf.am.trade.dao.model.customize.WDZJPreapysListCustomize;
import com.hyjf.am.trade.service.front.wdzj.BorrowDataService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hesy
 * @version BorrowDataServiceImpl, v0.1 2018/7/16 11:50
 */
@Service
public class BorrowDataServiceImpl extends BaseServiceImpl implements BorrowDataService {
    /**
     * 查询标的列表数据
     */
    @Override
    public List<WDZJBorrowListDataCustomize> selectBorrowList(Map<String, Object> paraMap){
        return wdzjCustomizeMapper.selectBorrowList(paraMap);
    }

    /**
     * 统计标的总记录数
     * @param paraMap
     * @return
     */
    @Override
    public int countBorrowList(Map<String, Object> paraMap){
        return wdzjCustomizeMapper.countBorrowList(paraMap);
    }

    /**
     * 当日放款总金额
     */
    @Override
    public String selectBorrowAmountSum(Map<String, Object> paraMap){
        return wdzjCustomizeMapper.sumBorrowAmount(paraMap);
    }

    /**
     * 当日提前还款列表
     */
    @Override
    public List<WDZJPreapysListCustomize> selectPreapysList(Map<String, Object> paraMap) {
        return wdzjCustomizeMapper.selectPreapysList(paraMap);
    }

    /**
     * 当日提前还款数
     */
    @Override
    public int countPreapysList(Map<String, Object> paraMap) {
        return wdzjCustomizeMapper.countPreapysList(paraMap);
    }
}
