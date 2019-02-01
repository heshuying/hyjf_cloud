package com.hyjf.am.vo.hgreportdata.cert;

import java.io.Serializable;
import java.util.Date;

public class CertLogVO implements Serializable {
    private Integer id;

    private String mqContent;

    private String logOrdId;

    private Integer infType;

    private Integer sendTime;

    private Integer sendStatus;

    private String resultCode;

    private String resultMsg;

    private Integer queryResult;

    private String queryMsg;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMqContent() {
        return mqContent;
    }

    public void setMqContent(String mqContent) {
        this.mqContent = mqContent == null ? null : mqContent.trim();
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId == null ? null : logOrdId.trim();
    }

    public Integer getInfType() {
        return infType;
    }

    public void setInfType(Integer infType) {
        this.infType = infType;
    }

    public Integer getSendTime() {
        return sendTime;
    }

    public void setSendTime(Integer sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode == null ? null : resultCode.trim();
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg == null ? null : resultMsg.trim();
    }

    public Integer getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(Integer queryResult) {
        this.queryResult = queryResult;
    }

    public String getQueryMsg() {
        return queryMsg;
    }

    public void setQueryMsg(String queryMsg) {
        this.queryMsg = queryMsg == null ? null : queryMsg.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}