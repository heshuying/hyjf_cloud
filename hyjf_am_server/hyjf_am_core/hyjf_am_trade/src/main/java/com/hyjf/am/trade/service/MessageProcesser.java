/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service;

import com.hyjf.am.market.dao.model.auto.bean.Message;

/**
 * @author ${yaoy}
 * @version MessageProcesser, v0.1 2018/6/12 18:35
 */
public interface MessageProcesser {

    /**
     *
     * 发送信息
     * @author renxingchen
     * @param message
     * @return
     */
    public boolean send(Message message);

    /**
     *
     * 信息采集，返回1为成功
     * @author renxingchen
     * @param message
     * @return
     */
    public Integer gather(Message message);

}
