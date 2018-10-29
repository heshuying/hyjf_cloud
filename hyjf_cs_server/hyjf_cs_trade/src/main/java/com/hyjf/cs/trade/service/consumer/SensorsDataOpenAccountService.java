/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.cs.common.service.BaseService;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;

import java.io.IOException;

/**
 * 神策数据统计:用户开户相关Service
 *
 * @author liuyang
 * @version SensorsDataOpenAccountService, v0.1 2018/10/24 10:48
 */
public interface SensorsDataOpenAccountService extends BaseService {

    /**
     * 发送神策数据
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException;
}
