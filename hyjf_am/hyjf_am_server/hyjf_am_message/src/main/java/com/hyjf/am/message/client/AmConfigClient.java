package com.hyjf.am.message.client;

import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.am.vo.config.SmsMailTemplateVO;
import com.hyjf.am.vo.config.SmsNoticeConfigVO;
import com.hyjf.am.vo.config.SmsTemplateVO;

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

    SmsNoticeConfigVO findSmsNoticeByCode(String tplCode);

    SmsMailTemplateVO findSmsMailTemplateByCode(String mailCode);

    MessagePushTemplateVO findMessagePushTemplateByCode(String tplCode);
}
