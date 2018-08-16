/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

/**
 * @author fq
 * @version SmsOntimeVO, v0.1 2018/8/14 18:53
 */
public class SmsOntimeVO extends BaseVO {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;
    private Integer id;

    private String channelType;

    private Integer starttime;

    private Integer endtime;

    private Integer status;

    private Integer openAccount;

    private BigDecimal addMoneyCount;

    private String addTimeBegin;

    private String addTimeEnd;

    private String reTimeBegin;

    private String reTimeEnd;

    private String ip;

    private String remark;

    private Integer createUserId;

    private String createUserName;

    private Integer createTime;

    private int limitStart = -1;

    private int limitEnd = -1;

    private String mobile;

    private String content;

    private String postString;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getStarttime() {
        return starttime;
    }

    public void setStarttime(Integer starttime) {
        this.starttime = starttime;
    }

    public Integer getEndtime() {
        return endtime;
    }

    public void setEndtime(Integer endtime) {
        this.endtime = endtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public BigDecimal getAddMoneyCount() {
        return addMoneyCount;
    }

    public void setAddMoneyCount(BigDecimal addMoneyCount) {
        this.addMoneyCount = addMoneyCount;
    }

    public String getAddTimeBegin() {
        return addTimeBegin;
    }

    public void setAddTimeBegin(String addTimeBegin) {
        this.addTimeBegin = addTimeBegin;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public String getReTimeBegin() {
        return reTimeBegin;
    }

    public void setReTimeBegin(String reTimeBegin) {
        this.reTimeBegin = reTimeBegin;
    }

    public String getReTimeEnd() {
        return reTimeEnd;
    }

    public void setReTimeEnd(String reTimeEnd) {
        this.reTimeEnd = reTimeEnd;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPostString() {
        return postString;
    }

    public void setPostString(String postString) {
        this.postString = postString;
    }
}
