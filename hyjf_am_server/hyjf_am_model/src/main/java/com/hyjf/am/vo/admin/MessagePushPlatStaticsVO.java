/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

/**
 * @author fq
 * @version MessagePushPlatStaticsVO, v0.1 2018/8/14 16:13
 */
public class MessagePushPlatStaticsVO extends BaseVO {
    private Integer id;

    private Integer tagId;

    private Integer staDate;

    private Integer destinationCount;

    private Integer sendCount;

    private Integer readCount;

    private Integer iosDestinationCount;

    private Integer iosSendCount;

    private Integer iosReadCount;

    private Integer androidDestinationCount;

    private Integer androidSendCount;

    private Integer androidReadCount;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getStaDate() {
        return staDate;
    }

    public void setStaDate(Integer staDate) {
        this.staDate = staDate;
    }

    public Integer getDestinationCount() {
        return destinationCount;
    }

    public void setDestinationCount(Integer destinationCount) {
        this.destinationCount = destinationCount;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }

    public Integer getIosDestinationCount() {
        return iosDestinationCount;
    }

    public void setIosDestinationCount(Integer iosDestinationCount) {
        this.iosDestinationCount = iosDestinationCount;
    }

    public Integer getIosSendCount() {
        return iosSendCount;
    }

    public void setIosSendCount(Integer iosSendCount) {
        this.iosSendCount = iosSendCount;
    }

    public Integer getIosReadCount() {
        return iosReadCount;
    }

    public void setIosReadCount(Integer iosReadCount) {
        this.iosReadCount = iosReadCount;
    }

    public Integer getAndroidDestinationCount() {
        return androidDestinationCount;
    }

    public void setAndroidDestinationCount(Integer androidDestinationCount) {
        this.androidDestinationCount = androidDestinationCount;
    }

    public Integer getAndroidSendCount() {
        return androidSendCount;
    }

    public void setAndroidSendCount(Integer androidSendCount) {
        this.androidSendCount = androidSendCount;
    }

    public Integer getAndroidReadCount() {
        return androidReadCount;
    }

    public void setAndroidReadCount(Integer androidReadCount) {
        this.androidReadCount = androidReadCount;
    }
}
