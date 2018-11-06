/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataAuthService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 神策数据采集:授权相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataAuthServiceImpl, v0.1 2018/10/23 17:40
 */
@Service
public class SensorsDataAuthServiceImpl extends BaseServiceImpl implements SensorsDataAuthService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;


    /**
     * 发送神策数据
     *
     * @param sensorsDataBean
     * @throws IOException
     * @throws InvalidArgumentException
     */
    @Override
    public void sendSensorsData(SensorsDataBean sensorsDataBean) throws IOException, InvalidArgumentException {

        // log文件存放位置
        String logFilePath = systemConfig.getSensorsDataLogPath();
        // 初始化神策SDK
        SensorsAnalytics sa = new SensorsAnalytics(new SensorsAnalytics.ConcurrentLoggingConsumer(logFilePath + "sensorsData.log"));

        // 用户ID
        Integer userId = sensorsDataBean.getUserId();
        if (userId == null || userId <= 0) {
            logger.error("神策数据统计:授权相关,用户ID错误");
            return;
        }

        // 根据用户ID查询用户信息
        UserVO users = this.amUserClient.findUserById(userId);
        if (users == null) {
            logger.error("神策数据统计:授权相关,用户不存在");
            return;
        }

        // 授权类型
        String authType = sensorsDataBean.getAuthType();
        if (StringUtils.isBlank(authType)) {
            logger.error("神策数据统计:授权相关,授权订单号为空");
            return;
        }

        // 根据用户查询用户授权记录
        HjhUserAuthVO hjhUserAuth = this.amTradeClient.getUserAuthByUserId(userId);
        if (hjhUserAuth == null) {
            logger.error("神策数据统计:授权相关,授权订单号为空");
            return;
        }
        Map<String, Object> properties = new HashMap<String, Object>();
        if (!"paymentAuth".equals(authType)) {
            properties.put("auth_name", "智投授权");
            if ("autoBid".equals(authType)) {
                if (hjhUserAuth.getAutoBidTime() != null) {
                    properties.put("auth_time", GetDate.getDateTimeMyTime(hjhUserAuth.getAutoBidTime()));
                }
            } else if ("autoCredit".equals(authType)) {
                if (hjhUserAuth.getAutoCreditTime() != null) {
                    properties.put("auth_time", GetDate.getDateTimeMyTime(hjhUserAuth.getAutoCreditTime()));
                }
            } else {
                if (hjhUserAuth.getAutoCreditTime() != null) {
                    properties.put("auth_time", GetDate.getDateTimeMyTime(hjhUserAuth.getAutoCreditTime()));
                }
            }
            // 调用神策track事件:智投授权结果
            sa.track(String.valueOf(userId), true, "plan_auth_result", properties);
            sa.shutdown();
        } else {
            properties.put("auth_name", "服务费授权");
            if (hjhUserAuth.getAutoPaymentTime() != null) {
                properties.put("auth_time", GetDate.getDateTimeMyTime(hjhUserAuth.getAutoPaymentTime()));
            }
            // 调用神策track事件:服务费授权
            sa.track(String.valueOf(userId), true, "fee_auth_result", properties);
            sa.shutdown();
        }
    }
}
