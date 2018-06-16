/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetCode;
import com.hyjf.cs.trade.bean.MQBorrow;
import com.hyjf.cs.trade.mq.AutoPreAuditProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.service.AutoPreAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version AutoPreAuditServiceImpl, v0.1 2018/6/14 16:35
 */
@Service
public class AutoPreAuditServiceImpl extends BaseTradeServiceImpl implements AutoPreAuditService {

    private Logger _log = LoggerFactory.getLogger(AutoPreAuditServiceImpl.class);

    @Autowired
    private AutoPreAuditProducer autoPreAuditProducer;

    @Override
    public void sendToMQ(MQBorrow mqBorrow, String hyjfBorrowIssueGroup) {
        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        if (mqBorrow.getBorrowNid() != null) {
            params.put("borrowNid", mqBorrow.getBorrowNid());
        }else if (mqBorrow.getCreditNid() != null) {
            params.put("creditNid", mqBorrow.getCreditNid());
        }
        try {
            autoPreAuditProducer.messageSend(new Producer.MassageContent(MQConstant.HYJF_BORROW_ISSUE_TOPIC, params));
        } catch (MQException e) {
            _log.error("发送自动关联计划消息失败...");
        }
    }
}
