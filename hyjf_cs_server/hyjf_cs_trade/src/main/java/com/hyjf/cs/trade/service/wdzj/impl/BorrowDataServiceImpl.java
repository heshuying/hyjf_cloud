package com.hyjf.cs.trade.service.wdzj.impl;

import com.hyjf.am.vo.wdzj.BorrowListCustomizeVO;
import com.hyjf.am.vo.wdzj.PreapysListCustomizeVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 网贷之家标的列表
 * @author hesy
 * @version BorrowDataServiceImpl, v0.1 2018/7/16 15:03
 */
@Service
public class BorrowDataServiceImpl extends BaseTradeServiceImpl implements com.hyjf.cs.trade.service.wdzj.BorrowDataService {
    @Autowired
    AmTradeClient amTradeClient;

    @Override
    public List<BorrowListCustomizeVO> selectBorrowList(Map<String, Object> requestBean) {
        return amTradeClient.selectBorrowList(requestBean);
    }

    @Override
    public Integer countBorrowList(Map<String, Object> requestBean) {
        return amTradeClient.countBorrowList(requestBean);
    }

    @Override
    public String sumBorrowAmount(Map<String, Object> requestBean) {
        return amTradeClient.sumBorrowAmount(requestBean);
    }

    @Override
    public List<PreapysListCustomizeVO> selectPreapysList(Map<String, Object> requestBean) {
        return amTradeClient.selectPreapysList(requestBean);
    }

    @Override
    public Integer countPreapysList(Map<String, Object> requestBean) {
        return amTradeClient.countPreapysList(requestBean);
    }
}
