package com.hyjf.am.message.client.impl;

import com.hyjf.am.message.client.AmConfigClient;
import com.hyjf.am.vo.config.*;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AmConfigClientImpl, v0.1 2018/5/4 10:00
 */
@Service
public class AmConfigClientImpl implements AmConfigClient {

    /**
     * 查询短信模板
     * @param tplCode
     * @return
     */
    @Override
    public SmsTemplateVO findSmsTemplateByCode(String tplCode) {
        //todo xiashuqing
        return null;
    }

    /**
     * 查询短信模板
     * @param tplCode
     * @return
     */
    @Override
    public SmsNoticeConfigVO findSmsNoticeByCode(String tplCode) {
        //todo xiashuqing
        return null;
    }

    /**
     * 查询邮件模板
     * @param mailCode
     * @return
     */
    @Override
    public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {
        //todo xiashuqing
        return null;
    }

    /**
     * 查询app消息模板
     * @param tplCode
     * @return
     */
    @Override
    public MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode) {
        //todo xiashuqing
        return null;
    }

    /**
     * 查询邮件配置
     * @return
     */
    @Override
    public SiteSettingVO findSiteSetting() {
        //todo xiashuqing
        return null;
    }
}
