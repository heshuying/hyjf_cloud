/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service;

import com.hyjf.cs.trade.bean.MQBorrow;

/**
 * @author fuqiang
 * @version AutoPreAuditService, v0.1 2018/6/14 16:34
 */
public interface AutoPreAuditService extends BaseTradeService{
    /**
     * 发送自动关联计划消息
     * @param mqBorrow
     * @param hyjfBorrowIssueGroup
     */
    void sendToMQ(MQBorrow mqBorrow, String hyjfBorrowIssueGroup);
}
