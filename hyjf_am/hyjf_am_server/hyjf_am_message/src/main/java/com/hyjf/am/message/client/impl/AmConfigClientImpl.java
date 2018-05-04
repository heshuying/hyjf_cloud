package com.hyjf.am.message.client.impl;

import com.hyjf.am.message.client.AmConfigClient;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import com.hyjf.am.vo.config.SmsTemplateVO;
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
        return null;
    }

    @Override
    public SmsNoticeConfigVO findSmsNoticeByCode(String tplCode) {
        return null;
    }

    @Override
    public SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode) {
        return null;
    }

    @Override
    public MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode) {
        return null;
    }
}
