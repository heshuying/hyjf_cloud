package com.hyjf.cs.user.bean;

/**
 * @author lisheng
 * @version FclcPostBean, v0.1 2018/11/15 16:42
 */

public class FclcPostBean {
    /**
     * 来源
     */
    private String from;

    /**
     * 登陆票据
     */
    private String ticket;

    /**
     * 目标url
     */
    private String target_url;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getTarget_url() {
        return target_url;
    }

    public void setTarget_url(String target_url) {
        this.target_url = target_url;
    }

}
