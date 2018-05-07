package com.hyjf.cs.message.client;

import com.hyjf.am.vo.config.*;

import java.util.List;

/**
 * @author xiasq
 * @version AmConfigClient, v0.1 2018/5/4 10:00
 */
public interface AmConfigClient {
    /**
     * 查询短信模板
     * @param tplCode
     * @return
     */
    SmsTemplateVO findSmsTemplateByCode(String tplCode);

    /**
     * 查询短信模板
     * @param tplCode
     * @return
     */
    SmsNoticeConfigVO findSmsNoticeByCode(String tplCode);

    /**
     * 查询邮件模板
     * @param mailCode
     * @return
     */
    SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

    /**
     * 查询app消息模板
     * @param tplCode
     * @return
     */
    MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode);

    /**
     * 查询邮件配置
     * @return
     */
    SiteSettingVO findSiteSetting();
}
