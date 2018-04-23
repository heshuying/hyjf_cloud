/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.message.processer.impl;

import com.hyjf.am.message.processer.Message;
import com.hyjf.am.message.processer.MessageDefine;
import com.hyjf.am.message.processer.MessageProcesser;
import com.hyjf.am.message.processer.SmsMessage;
import com.hyjf.common.web.RedisUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * 短信处理器
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月17日
 * @see 下午2:08:41
 */
@Component(value = "smsProcesser")
public class SmsProcesser implements MessageProcesser {

    @Override
    public boolean send(Message message) {
        int result = 1;
        if (null != message) {
            SmsMessage smsMessage = (SmsMessage) message;
            switch (smsMessage.getServiceType()) {
            case MessageDefine.SMSSENDFORMANAGER:// 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
                result =
                        SmsUtil.sendMessages(smsMessage.getTplCode(), smsMessage.getReplaceStrs(),
                                smsMessage.getSender(), smsMessage.getChannelType());
                break;
            case MessageDefine.SMSSENDFORMOBILE: // 根据电话号码和模版号给某电话发短信
                result =
                        SmsUtil.sendMessages(smsMessage.getMobile(), smsMessage.getTplCode(),
                                smsMessage.getReplaceStrs(), smsMessage.getChannelType());
                break;
            case MessageDefine.SMSSENDFORUSER:// 根据用户ID和模版号给某用户发短信
                result =
                        SmsUtil.sendMessages(smsMessage.getUserId(), smsMessage.getTplCode(),
                                smsMessage.getReplaceStrs(), smsMessage.getChannelType());
                break;
            case MessageDefine.SMSSENDFORUSERSNOTPL:// 根据电话号码和消息内容给某电话发短信
                try {
                    result =
                            SmsUtil.sendMessage(smsMessage.getMobile(), smsMessage.getMessage(),
                                    smsMessage.getServiceType(), null, smsMessage.getChannelType());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
            }
        }
        System.err.println("发送短信------------------------------------" + result);
        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer gather(Message message) {
        if (null != message) {
            SmsMessage smsMessage = (SmsMessage) message;
            System.err.println("sms gather++++++++++++++++++++++++++" + JSON.toJSONString(smsMessage));
            return Integer.parseInt(RedisUtils.lpush(MessageDefine.SMSQUENEM, JSON.toJSONString(smsMessage)) + "");
        } else {
            return 0;
        }
    }

}
