/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataOpenAccountService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 神策数据统计:用户开户相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataOpenAccountServiceImpl, v0.1 2018/10/24 11:09
 */
@Service
public class SensorsDataOpenAccountServiceImpl extends BaseServiceImpl implements SensorsDataOpenAccountService {


    @Autowired
    private AmUserClient amUserClient;

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
            logger.error("神策数据统计:用户开户相关,用户ID错误");
            return;
        }
        // 根据用户ID 查询用户
        UserVO users = this.amUserClient.findUserById(userId);
        if (users == null) {
            logger.info("神策数据统计:用户开户相关,根据用户ID查询用户信息失败");
            return;
        }


        // 根据用户ID查询用户详情信息
        UserInfoVO usersInfo = this.amUserClient.getUserInfo(userId);
        if (usersInfo == null) {
            logger.info("神策数据统计:用户开户相关,根据用户ID查询用户详情信息失败");
            return;
        }


        // 根据用户ID查询开户信息
        BankOpenAccountVO bankOpenAccount = this.amUserClient.selectBankAccountById(userId);
        if (bankOpenAccount == null) {
            logger.info("神策数据统计:用户开户相关,根据用户ID查询用户开户信息失败");
            return;
        }


        Map<String, Object> properties = new HashMap<String, Object>();
        // 平台类型
        if (users.getBankAccountEsb() == 0) {
            properties.put("PlatformType", "PC");
        } else if (users.getBankAccountEsb() == 1) {
            properties.put("PlatformType", "wechat");
        } else if (users.getBankAccountEsb() == 2) {
            properties.put("PlatformType", "Android");
        } else if (users.getBankAccountEsb() == 3) {
            properties.put("PlatformType", "iOS");
        }
        // 根据用户ID查询用户银行卡信息
        BankCardVO bankCard = this.amUserClient.getBankCardByUserId(userId);
        if (bankCard == null) {
            logger.info("未获取到用户银行卡信息:用户ID:[" + userId + "].");
            properties.put("bank_name", "");
        } else {
            properties.put("bank_name", StringUtils.isNotBlank(bankCard.getBank()) ? bankCard.getBank() : "");
        }
        // 身份证号
        properties.put("id_card", StringUtils.isNotBlank(usersInfo.getIdcard()) ? usersInfo.getIdcard() : "");
        // 电子账户号
        properties.put("account_id", StringUtils.isNotBlank(bankOpenAccount.getAccount()) ? bankOpenAccount.getAccount() : "");
        // 姓名
        properties.put("true_name", StringUtils.isNotBlank(usersInfo.getTruename()) ? usersInfo.getTruename() : "");
        // 开户时间
        properties.put("open_time", bankOpenAccount.getCreateTime());
        // 调用神策track事件
        sa.track(String.valueOf(userId), true, "open_success", properties);
        sa.shutdown();
        // 如果redis里存在的话,将redis里的值删除
        if (RedisUtils.exists("SENSORS_DATA_OPEN_ACCOUNT:" + userId)) {
            RedisUtils.del("SENSORS_DATA_OPEN_ACCOUNT:" + userId);
        }
    }
}
