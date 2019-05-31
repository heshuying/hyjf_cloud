package com.hyjf.pay.entity;

import java.io.Serializable;
import java.util.Map;

public class DuiBaSendLog implements Serializable {

    private String orderNum;

    private String msgType;

    private String remark;

    private String createTime;

    private String logOrdId;

    private Integer logUserId;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
    }

    public Integer getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Integer logUserId) {
        this.logUserId = logUserId;
    }
}