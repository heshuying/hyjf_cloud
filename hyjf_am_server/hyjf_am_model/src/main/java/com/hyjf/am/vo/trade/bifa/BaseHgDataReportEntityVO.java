/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.bifa;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * 合规数据上报 mongodb Entity基类
 * @author liubin
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
public class BaseHgDataReportEntityVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键
    private String id;

    // 上报结果 0初始，1成功，9失败
    private String reportStatus;

    // 消息内容
    private String message;

    // routingKey
    private String routingKey;

    // 错误码
    private String errCode;

    // 错误描述
    private String errDesc;

    // 业务发生时间YYYY-MM-DD记录该条记录原数据操作时间
    private String historyData;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;

    /**
     * 平台编号
     */
    private String source_code;
    /**
     * 原产品编号
     */
    private String source_product_code;

    public String getSource_code() {
        return source_code;
    }

    public void setSource_code(String source_code) {
        this.source_code = source_code;
    }

    public String getSource_product_code() {
        return source_product_code;
    }

    public void setSource_product_code(String source_product_code) {
        this.source_product_code = source_product_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrDesc() {
        return errDesc;
    }

    public void setErrDesc(String errDesc) {
        this.errDesc = errDesc;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getHistoryData() {
        return historyData;
    }

    public void setHistoryData(String historyData) {
        this.historyData = historyData;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
