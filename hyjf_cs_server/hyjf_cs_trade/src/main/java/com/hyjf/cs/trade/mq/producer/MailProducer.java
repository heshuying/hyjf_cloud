/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.producer;

import com.hyjf.common.constants.MessageConstant;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.base.Producer;
import com.hyjf.cs.trade.mq.base.ProducerFieldsWrapper;

/**
 * @author fuqiang
 * @version MailProducer, v0.1 2018/5/8 17:44
 */
@Component
public class MailProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.MAIL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }

    /**
     * 信息采集，返回1为成功
     * @param message
     * @return
     */
    public int gather(MailMessage message) {
        if (null != message) {
            return Integer.parseInt(RedisUtils.lpush(MessageConstant.MAILQUENEM, JSON.toJSONString(message)) + "");
        } else {
            return 0;
        }
    }
}
