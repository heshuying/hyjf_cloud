/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.consumer.impl;

import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.service.BaseServiceImpl;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.consumer.SensorsDataRechargeService;
import com.sensorsdata.analytics.javasdk.SensorsAnalytics;
import com.sensorsdata.analytics.javasdk.exceptions.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 神策数据统计:用户充值相关Service实现类
 *
 * @author liuyang
 * @version SensorsDataRechargeServiceImpl, v0.1 2018/10/22 15:06
 */
@Service
public class SensorsDataRechargeServiceImpl extends BaseServiceImpl implements SensorsDataRechargeService {

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 发送神策埋点数据
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

        // 充值订单号
        String rechageOrderId = sensorsDataBean.getOrderId();
        if(StringUtils.isEmpty(rechageOrderId)){
            logger.error("获取充值订单号为空.");
            return;
        }

        // 根据充值订单号查询充值记录表
        AccountRechargeVO accountRechargeVO =  this.amTradeClient.selectByOrderId(rechageOrderId);
        if(accountRechargeVO == null){
            logger.error("根据充值订单号查询用户充值记录不存在,充值订单号:[" + rechageOrderId + "].");
            return;
        }
        Map<String, Object> properties = new HashMap<String, Object>();

        // 充值金额
        properties.put("recharge_amount", accountRechargeVO.getMoney());
        // 充值时间
        properties.put("recharge_time", GetDate.getDateTimeMyTimeInMillis(accountRechargeVO.getCreateTime()));
        // 查询用户银行卡信息
        Integer userId = accountRechargeVO.getUserId();
        // 根据用户ID查询用户银行卡信息
        BankCardVO bankCard = this.amUserClient.getBankCardByUserId(userId);
        if (bankCard == null) {
            properties.put("bank_name", "");
            properties.put("bank_card_number", "");
        } else {
            properties.put("bank_name", org.apache.commons.lang.StringUtils.isBlank(bankCard.getBank()) ? "" : bankCard.getBank());
            properties.put("bank_card_number", org.apache.commons.lang.StringUtils.isBlank(bankCard.getCardNo()) ? "" : bankCard.getCardNo());
        }
        // 充值类型
        properties.put("recharge_type", "快捷充值");

        // 是否是首次充值
        properties.put("$is_first_time", this.isFirstRecharge(userId));
        // 是否成功
        properties.put("success", true);
        // 错误信息
        properties.put("error_message", "");

        // 平台类型
        if (accountRechargeVO.getClient() == 0) {
            properties.put("PlatformType", "PC");
        } else if (accountRechargeVO.getClient() == 1) {
            properties.put("PlatformType", "wechat");
        } else if (accountRechargeVO.getClient() == 2) {
            properties.put("PlatformType", "Android");
        } else if (accountRechargeVO.getClient() == 3) {
            properties.put("PlatformType", "iOS");
        }
        // 调用神策track事件
        sa.track(String.valueOf(userId), true, "recharge_result", properties);
        sa.shutdown();


    }

    /**
     * 根据用户ID查询是否是首次充值
     * @param userId
     * @return
     */
    private boolean isFirstRecharge(Integer userId) {
        List<AccountRechargeVO> list = this.amTradeClient.selectRechargeListByUserId(userId);
        // 如果充值记录中只有一条 说明是首次充值
        if (list != null && list.size() == 1) {
            return true;
        }
        return false;
    }
}
