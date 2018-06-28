package com.hyjf.am.trade.controller.demo;

import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import org.apache.rocketmq.client.exception.MQClientException;

/**
 * @author xiasq
 * @version ProducerTransactionMessageService, v0.1 2018/6/28 15:18
 */
public interface ProducerTransactionMessageService {
    /**
     * 保存
     * @param message
     */
    void save(ProducerTransactionMessage message);

    /**
     * 根据特定条件查询
     * @param topic
     * @param keys
     * @param tag
     * @return
     */
    ProducerTransactionMessage findByCondition(String topic, String keys, String tag);

    /**
     * 事务回查
     */
    void callBackQuery() throws MQClientException, InterruptedException;
}
