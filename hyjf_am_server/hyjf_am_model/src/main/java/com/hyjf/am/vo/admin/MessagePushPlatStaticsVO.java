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
    private String id;

    private String tagId;

    private String tagName;

    private String staDate;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getStaDate() {
        return staDate;
    }

    public void setStaDate(String staDate) {
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
}
