package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CertErrLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * mongo里面的订单号
     *
     * @mbggenerated
     */
    private String logOrdId;

    /**
     * 接口类型  见 CertCallConstant 请求类型 
     *
     * @mbggenerated
     */
    private Integer infType;

    /**
     * 上传时间
     *
     * @mbggenerated
     */
    private Integer sendTime;

    /**
     * 上报结果 0初始，1成功，9失败 99无响应
     *
     * @mbggenerated
     */
    private Integer sendStatus;

    /**
     * 上送次数 最多三次
     *
     * @mbggenerated
     */
    private Integer sendCount;

    /**
     * 上报返回状态
     *
     * @mbggenerated
     */
    private String resultCode;

    /**
     * 上报返回描述
     *
     * @mbggenerated
     */
    private String resultMsg;

    /**
     * 查询结果 0初始，1成功，9失败 99无响应
     *
     * @mbggenerated
     */
    private Integer queryResult;

    /**
     * 查询结果返回描述
     *
     * @mbggenerated
     */
    private String queryMsg;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
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