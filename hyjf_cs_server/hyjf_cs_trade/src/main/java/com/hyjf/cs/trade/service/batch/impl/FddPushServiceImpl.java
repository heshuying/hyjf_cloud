package com.hyjf.cs.trade.service.batch.impl;

import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.service.batch.FddPushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Auther:yangchangwei
 * @Date:2018/12/25
 * @Description:
 */
@Service
public class FddPushServiceImpl implements FddPushService{

    private static final Logger _log = LoggerFactory.getLogger(FddPushServiceImpl.class);

    @Autowired
    private AmTradeClient amTradeClient;

    /**
     * 开始推送法大大协议
     */
    @Override
    public void updateFddPush(BorrowApicronVO borrowApicronVO) {

        amTradeClient.updateFddPush(borrowApicronVO);

    }

    /**
     * 获取需要推送的任务标的
     * @return
     */
    @Override
    public List<BorrowApicronVO> getFddPushBorrowList() {

        List<BorrowApicronVO> list =  amTradeClient.getFddPushBorrowList();

        return list;
    }
}
