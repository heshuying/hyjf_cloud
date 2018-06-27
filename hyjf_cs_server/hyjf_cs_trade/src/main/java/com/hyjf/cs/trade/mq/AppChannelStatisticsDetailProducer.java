/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * 渠道统计mq producer
 * @author jijun
 * @since 20180625
 */
@Component
public class AppChannelStatisticsDetailProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MassageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
