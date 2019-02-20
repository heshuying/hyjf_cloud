/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author fq
 * @version SmsCodeRequestBean, v0.1 2018/8/15 10:07
 */
public class SmsCodeRequestBean extends BaseRequest implements Serializable {
    /**
     * serialVersionUID
     */

    private static final long serialVersionUID = 387630498860089653L;

    /**
     * 用户手机号码，以英文逗号间隔
     */
    private String user_phones;

    /**
     * 短信内容
     */
    private String message;

    /**
     * 是否开户 0 未开 1 已开 3 所有用户
     */
    private Integer open_account;

    /**
     * 注册时间日期段---开始时间
     */
    private String re_time_begin;

    /**
     * 注册时间日期段---结束时间
     */
    private String re_time_end;

    /**
     * 出借日期段开始时间
     */
    private String add_time_begin;

    /**
     * 出借日期段结束时间
     */
    private String add_time_end;

    /**
     * 累计出借金额
     */
    private String add_money_count;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 发送人姓名
     */
    private String sender;

    /**
     * 短信通道
     */
    private String channelType;
    /**
     * 发送类型
     */
    private String sendType;
    /**
     * 定时发送时间
     */
    private String on_time;

    /**
     * 短信内容是否显示
     */
    private Integer isDisplay;

    @ApiModelProperty(value = "短信类型 single:单发 crowd:群发")
    private String messageType;

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender
     *            the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     *            the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_phones() {
        return user_phones;
    }

    public void setUser_phones(String user_phones) {
        this.user_phones = user_phones;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getOpen_account() {
        return open_account;
    }

    public void setOpen_account(Integer open_account) {
        this.open_account = open_account;
    }

    /**
     * @return the re_time_begin
     */
    public String getRe_time_begin() {
        return re_time_begin;
    }

    /**
     * @param re_time_begin
     *            the re_time_begin to set
     */
    public void setRe_time_begin(String re_time_begin) {
        this.re_time_begin = re_time_begin;
    }

    /**
     * @return the re_time_end
     */
    public String getRe_time_end() {
        return re_time_end;
    }

    /**
     * @param re_time_end
     *            the re_time_end to set
     */
    public void setRe_time_end(String re_time_end) {
        this.re_time_end = re_time_end;
    }

    /**
     * @return the add_time_begin
     */
    public String getAdd_time_begin() {
        return add_time_begin;
    }

    /**
     * @param add_time_begin
     *            the add_time_begin to set
     */
    public void setAdd_time_begin(String add_time_begin) {
        this.add_time_begin = add_time_begin;
    }

    /**
     * @return the add_time_end
     */
    public String getAdd_time_end() {
        return add_time_end;
    }

    /**
     * @param add_time_end
     *            the add_time_end to set
     */
    public void setAdd_time_end(String add_time_end) {
        this.add_time_end = add_time_end;
    }

    public String getAdd_money_count() {
        return add_money_count;
    }

    public void setAdd_money_count(String add_money_count) {
        this.add_money_count = add_money_count;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getOn_time() {
        return on_time;
    }

    public void setOn_time(String on_time) {
        this.on_time = on_time;
    }

    public Integer getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(Integer isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
