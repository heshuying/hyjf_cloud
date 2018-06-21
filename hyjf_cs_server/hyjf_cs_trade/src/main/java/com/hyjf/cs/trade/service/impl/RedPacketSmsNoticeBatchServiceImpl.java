/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.mq.SmsProducer;
import com.hyjf.cs.trade.service.RedPacketSmsNoticeBatchService;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author PC-LIUSHOUYI
 * @version RedPacketSmsNoticeBatchServiceImpl, v0.1 2018/6/21 16:12
 */
@Service
public class RedPacketSmsNoticeBatchServiceImpl extends BaseTradeServiceImpl implements RedPacketSmsNoticeBatchService {
    private static Logger logger = LoggerFactory.getLogger(RedPacketSmsNoticeBatchServiceImpl.class);

    @Autowired
    SmsProducer smsProducer;

    /** 交易渠道 */
    @Value("${hyjf.bank.merrp.account}")
    private String merrpAccount;

    /**
     * 查询红包账户余额并发送短信通知
     */
    @Override
    public void queryAndSend() {

        if(StringUtils.isEmpty(merrpAccount)){
            logger.error("【红包账户余额短信提醒】红包账户电子账号没有配置");
            return;
        }

        // todo 获取红包账户余额
        BigDecimal balance = this.getBankBalancePay(0, merrpAccount);
        if(balance == null){
            logger.error("【红包账户余额短信提醒】调用即信余额查询接口失败");
            return;
        }

        logger.info("【红包账户余额短信提醒】当前账户余额：" + balance);
        sendMessage(balance);
    }

    /**
     * 发送短信
     *
     * @param balance
     */
    public void sendMessage(BigDecimal balance) {
        try {
            // 替换参数
            Map<String, String> messageStrMap = new HashMap<String, String>();
            messageStrMap.put("val_amount", balance.toString());
            // 发送短信通知
            SmsMessage smsMessage = new SmsMessage(null, messageStrMap, null, null, MessageConstant.SMSSENDFORMANAGER, null, CustomConstants.PARAM_RED_PACKET,
                    CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                smsProducer.messageSend(
                        new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, JSON.toJSONBytes(smsMessage)));
            } catch (MQException e) {
                logger.error("短信发送失败...", e);
            }
        } catch (Exception e) {
            CheckUtil.check(false, MsgEnum.ERR_IP_VISIT_TOO_MANNY);
        }
    }
}
