/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

/**
 * @author PC-LIUSHOUYI
 * @version RedPacketSmsNoticeBatchService, v0.1 2018/6/21 16:11
 */
public interface RedPacketSmsNoticeBatchService extends BaseTradeService {
    /**
     * 查询红包余额并发送短信
     */
    void queryAndSend();
}
