/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.message.processer;

/**
 * 信息处理器
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月17日
 * @see 下午2:03:05
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
