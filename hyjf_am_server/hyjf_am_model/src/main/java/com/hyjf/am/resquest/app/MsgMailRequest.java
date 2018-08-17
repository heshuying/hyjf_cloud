package com.hyjf.am.resquest.app;

/**
 * @author lisheng
 * @version MsgMailRequest, v0.1 2018/8/13 18:00
 */

public class MsgMailRequest {
    //短信开关
    private String smsOpenStatus;

    //邮件开关
    private String emailOpenStatus;

    public String getSmsOpenStatus() {
        return smsOpenStatus;
    }

    public void setSmsOpenStatus(String smsOpenStatus) {
        this.smsOpenStatus = smsOpenStatus;
    }

    public String getEmailOpenStatus() {
        return emailOpenStatus;
    }

    public void setEmailOpenStatus(String emailOpenStatus) {
        this.emailOpenStatus = emailOpenStatus;
    }
}
