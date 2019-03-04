/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataWithdrawService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 神策数据统计:用户提现相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataWithdrawServiceImpl, v0.1 2018/10/22 16:33
 */
@Service
public class SensorsDataWithdrawServiceImpl extends BaseServiceImpl implements SensorsDataWithdrawService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 发送神策数据统计
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

        // 提现订单号
        String withdrawOrderId = sensorsDataBean.getOrderId();
        if (StringUtils.isBlank(withdrawOrderId)) {
            logger.error("提现订单号为空");
            return;
        }

        // 根据提现订单号查询提现订单
        AccountWithdrawVO accountwithdraw = this.amTradeClient.getAccountWithdrawByOrderId(withdrawOrderId);
        if (accountwithdraw == null) {
            logger.error("根据提现订单号查询提现订单失败,提现订单号:[" + accountwithdraw);
            return;
        }

        // 用户ID
        Integer userId = accountwithdraw.getUserId();
        Map<String, Object> properties = new HashMap<String, Object>();
        // 提现金额
        properties.put("withdraw_amount", accountwithdraw.getTotal());
        // 提现手续费
        properties.put("fee_amount", new BigDecimal(accountwithdraw.getFee()));
        // 实际到账金额
        properties.put("arrive_money", accountwithdraw.getCredited());
        // 根据用户ID 查询用户银行卡信息
        BankCardVO bankCard = this.amUserClient.getBankCardByUserId(userId);
        if (bankCard == null) {
            properties.put("bank_name", "");
            properties.put("bank_card_number", "");
        } else {
            // 银行名称
            properties.put("bank_name", StringUtils.isBlank(bankCard.getBank()) ? "" : bankCard.getBank());
            // 银行卡号
            // properties.put("bank_card_number", StringUtils.isBlank(bankCard.getCardNo()) ? "" : bankCard.getCardNo());
            properties.put("bank_card_number", "");
        }
        // 提现金额大于 50001,设置提现方式
        if (accountwithdraw.getTotal().compareTo(new BigDecimal(50001)) >= 0) {
            properties.put("withdraw_type", "大额提现");
        } else {
            properties.put("withdraw_type", "普通提现");
        }
        // 提现成功时间
        properties.put("success_time", GetDate.getDateTimeMyTimeInMillis(accountwithdraw.getCreateTime()));
        // 是否成功
        properties.put("success", true);
        // 是否是首次
        properties.put("$is_first_time", this.isFirstWithdarw(userId));
        // 错误信息
        properties.put("error_message", "");
        // 平台类型
        if (accountwithdraw.getClient() == 0) {
            properties.put("PlatformType", "PC");
        } else if (accountwithdraw.getClient() == 1) {
            properties.put("PlatformType", "wechat");
        } else if (accountwithdraw.getClient() == 2) {
            properties.put("PlatformType", "Android");
        } else if (accountwithdraw.getClient() == 3) {
            properties.put("PlatformType", "iOS");
        }
        // 调用神策track事件
        sa.track(String.valueOf(userId), true, "withdraw_result", properties);
        sa.shutdown();
    }

    /**
     * 根据用户ID查询用户提现记录
     *
     * @param userId
     * @return
     */
    private boolean isFirstWithdarw(Integer userId) {
        List<AccountWithdrawVO> list = this.amTradeClient.selectAccountWithdrawByUserId(userId);
        // 如果充值记录中只有一条 说明是首次提现
        if (list != null && list.size() == 1) {
            return true;
        }
        return false;
    }
}
