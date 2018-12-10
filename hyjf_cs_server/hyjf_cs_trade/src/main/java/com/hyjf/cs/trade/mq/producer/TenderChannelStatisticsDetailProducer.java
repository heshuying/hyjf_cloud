/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.producer;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.base.Producer;
import com.hyjf.cs.trade.mq.base.ProducerFieldsWrapper;
import org.springframework.stereotype.Component;

/**
 * @Description 出借更新渠道统计等信息
 * @Author sunss
 * @Date 2018/9/5 9:41
 */
@Component
public class TenderChannelStatisticsDetailProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.TENDER_CHANNEL_STATISTICS_DETAIL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
