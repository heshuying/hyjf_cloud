package com.hyjf.am.message.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author xiasq
 * @version SmsLogWithBLOBs, v0.1 2018/5/4 9:32
 */

@Document(collection = "t_sms_log")
public class SmsLogWithBLOBs extends SmsLog implements Serializable {

    private String mobile;

    private String content;

    private static final long serialVersionUID = 1L;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
