package com.hyjf.am.message.processer.impl;

import com.hyjf.am.message.processer.AppMsMessage;
import com.hyjf.am.message.processer.Message;
import com.hyjf.am.message.processer.MessageDefine;
import com.hyjf.am.message.processer.MessageProcesser;
import com.hyjf.am.message.util.MsgPushUtil;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * 此处为类说明
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年8月17日
 * @see 上午11:11:47
 */
@Component(value = "appMsProcesser")
public class AppMsProcesser implements MessageProcesser {

    @Override
    public boolean send(Message message) {
        int result = 1;
        if (null != message) {
            AppMsMessage appMsMessage = (AppMsMessage) message;
            switch (appMsMessage.getServiceType()) {
            case MessageDefine.APPMSSENDFORMOBILE:// 根据电话号码和模版给指定手机号推送app消息
                result =
                        MsgPushUtil.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
                                appMsMessage.getMobile());
                break;
            case MessageDefine.APPMSSENDFORUSER: // 根据用户编号和模版号给某电话推送app消息
                result =
                        MsgPushUtil.sendMessages(appMsMessage.getTplCode(), appMsMessage.getReplaceStrs(),
                                appMsMessage.getUserId());
                break;
                
            case MessageDefine.APPMSSENDFORMSG:
            	 result =
                 MsgPushUtil.sendMessages(appMsMessage.getMsgId());
            	 break;
            	 
            default:
                break;
            }
        }
        if (result == 1) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer gather(Message message) {
        if (null != message) {
            AppMsMessage appMsMessage = (AppMsMessage) message;
            //todo 使用mq
            return 0;
            //return Integer.parseInt(RedisUtils.lpush(MessageDefine.APPMSQUENEM, JSON.toJSONString(appMsMessage)) + "");
        } else {
            return 0;
        }
    }

}
