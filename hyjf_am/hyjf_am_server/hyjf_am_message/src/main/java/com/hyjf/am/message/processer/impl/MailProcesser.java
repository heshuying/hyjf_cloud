/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.message.processer.impl;

import java.io.File;

import org.springframework.stereotype.Component;

import com.hyjf.am.message.processer.MailMessage;
import com.hyjf.am.message.processer.Message;
import com.hyjf.am.message.processer.MessageDefine;
import com.hyjf.am.message.processer.MessageProcesser;
import com.hyjf.am.message.util.MailUtil;

/**
 * 邮件处理器
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月17日
 * @see 下午2:09:05
 */
@Component(value = "mailProcesser")
public class MailProcesser implements MessageProcesser {

    @Override
    public boolean send(Message message) {
        if (null != message) {
            MailMessage mailMessage = (MailMessage) message;
            switch (mailMessage.getServiceType()) {
            case MessageDefine.MAILSENDFORUSER:// 给指定用户发送邮件
                MailUtil.sendMail(mailMessage.getUserId(), mailMessage.getSubject(), mailMessage.getBody(),
                        mailMessage.getFileNames());
                break;
            case MessageDefine.MAILSENDFORMAILINGADDRESS:// 给指定用户发送邮件
                MailUtil.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(), mailMessage.getMailKbn(),
                        mailMessage.getReplaceStrs(), mailMessage.getFileNames());
                break;
            case MessageDefine.MAILSENDFORMAILINGADDRESSMSG:// 批量给邮件地址发送邮件信息
                MailUtil.sendMail(mailMessage.getToMailArray(), mailMessage.getSubject(), mailMessage.getBody(),
                        mailMessage.getFileNames());
                break;

            default:
                break;
            }
            if (null != mailMessage.getFileNames()) {
                if (mailMessage.getFileNames().length > 1) {
                    File f = new File(mailMessage.getFileNames()[0] + mailMessage.getFileNames()[1]);
                    if (f.exists()) {
                        f.delete();
                    }
                    File dir = new File(mailMessage.getFileNames()[0]);
                    if (dir.exists()) {
                        dir.delete();
                    }
                } else if (mailMessage.getFileNames().length == 1) {
                    File dir = new File(mailMessage.getFileNames()[0]);
                    if (dir.exists()) {
                        dir.delete();
                    }
                }
            }
        }
        return true;
    }

    @Override
    public Integer gather(Message message) {
        if (null != message) {
            MailMessage mailMessage = (MailMessage) message;
            return 0;
            //return Integer.parseInt(RedisUtils.lpush(MessageDefine.MAILQUENEM, JSON.toJSONString(mailMessage)) + "");
        } else {
            return 0;
        }
    }

}
