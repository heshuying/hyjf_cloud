package com.hyjf.am.trade.service.front.batch;

import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiasq
 * @version ProducerTransactionMessageService, v0.1 2018/6/28 15:18
 */
public interface ProducerTransactionMessageService {
    /**
     * 保存
     * @param message
     */
    void insert(ProducerTransactionMessage message);

    /**
     * 更新
     * @param message
     */
    void update(ProducerTransactionMessage message);

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
    void callBackQuery() throws MQClientException, InterruptedException, RemotingException, MQBrokerException;
}