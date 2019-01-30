/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nxl
 * @version CertLogRequestBean, v0.1 2018/7/6 15:35
 */
public class CertLogRequestBean extends BasePage implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * mq内容
     *
     * @mbggenerated
     */
    private String mqContent;

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
        this.mqContent = mqContent;
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
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
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
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
        this.queryMsg = queryMsg;
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
