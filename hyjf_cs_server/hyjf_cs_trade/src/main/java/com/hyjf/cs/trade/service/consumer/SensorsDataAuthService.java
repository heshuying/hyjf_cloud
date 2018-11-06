/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.cs.common.service.BaseService;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import zipkin2.Call;

import java.io.IOException;

/**
 * 神策数据采集:授权相关Service
 *
 * @author liuyang
 * @version SensorsDataAuthService, v0.1 2018/10/23 17:30
 */
public interface SensorsDataAuthService extends BaseService {

    /**
     * 发送神策数据
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException;
}
