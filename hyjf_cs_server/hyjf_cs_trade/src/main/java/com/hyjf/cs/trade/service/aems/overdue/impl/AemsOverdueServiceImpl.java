package com.hyjf.cs.trade.service.aems.overdue.impl;

import com.hyjf.cs.trade.bean.AemsOverdueRequestBean;
import com.hyjf.cs.trade.bean.AemsOverdueResultBean;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.aems.overdue.AemsOverdueService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehuili on 2019/3/12.
 */
@Service
public class AemsOverdueServiceImpl  extends BaseTradeServiceImpl implements AemsOverdueService {

    @Autowired
    private AmTradeClient amTradeClient;


    /**
     * 查询逾期相关数据
     * @param requestBean
     * @return
     */
    @Override
    public AemsOverdueResultBean selectRepayOverdue(AemsOverdueRequestBean requestBean){
        return amTradeClient.selectRepayOverdue(requestBean);
    }
}
