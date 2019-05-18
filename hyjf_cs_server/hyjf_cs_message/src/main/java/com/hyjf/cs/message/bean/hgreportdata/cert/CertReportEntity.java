/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.hgreportdata.cert;

import com.alibaba.fastjson.JSONArray;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * //合规数据上报 CERT  国家互联网应急中心    CERT 用户信息上报
 *
 * @author sss
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
@Document(collection = "ht_cert_report_send")
public class  CertReportEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 版本号
     */
    private String version;
    /**
     * 公共方法设置  请求地址
     */
    private String url;

    /**
     * 批次号
     */
    private String batchNum;
    /**
     * 公共方法设置   工具包 checkCode 方法
     */
    private String checkCode;
    /**
     * 公共方法设置   本批次发送的总数据条数，一个批次最多传 3000 条
     */
    private String totalNum;
    /**
     * 公共方法设置   接口数据类型；0：调试数据；1 正式数据（接口联调阶段传 0，正式  推数据阶段传 1）
     */
    private String dataType;
    /**
     * 公共方法设置   发送时间
     */
    private String sentTime;
    /**
     * 公共方法设置   平台编码
     */
    private String sourceCode;

    /**
     * 公共方法设置   获取当前系统时间戳
     */
    private String timestamp;
    /**
     * 公共方法设置   随机数
     */
    private String nonce;
    /**
     * 公共方法设置
     */
    private String apiKey;
    /**
     * 公共方法设置   订单号
     */
    private String logOrdId;


    // ---- 主要关注的
    /**
     * 请求对象
     */
    private JSONArray dataList;
    /**
     * 返回值
     */
    private String retMess;
    /**
     * 上报结果 0初始，1成功，9失败 99请求失败
     */
    private String reportStatus;
    /**
     * 描述
     */
    private String remark;
    /**
     * 接口类型  1--用户接口，传值样例1
     */
    private String infType;

    /**
     * 为了方便查询用的  可以不设置 可以设置 userid  或者borrownid 等等
     */
    private String logParm;

    /**
     * userId
     */
    private String userId;

    /**
     * 交易范围数  生成批次号使用 如果推送 2017-01-01 到 2017-01-07 七天的数据，则 num 为 7。
     */
    private String dateNum;

    /**
     * 交易开始时间
     */
    private String tradeDate;

    public CertReportEntity() {
        super();
    }

    /**
     * 构造方法  不带logParm
     *
     * @param remark
     * @param infType
     * @param dataList
     */
    public CertReportEntity(String remark, String infType, JSONArray dataList) {
        super();
        this.dataList = dataList;
        this.remark = remark;
        this.infType = infType;
    }

    /**
     * 构造方法  带logParm
     *
     * @param remark
     * @param infType
     * @param logParm
     * @param dataList
     */
    public CertReportEntity(String remark, String infType, String logParm, JSONArray dataList) {
        super();
        this.dataList = dataList;
        this.remark = remark;
        this.infType = infType;
        this.logParm = logParm;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONArray getDataList() {
        return dataList;
    }

    public void setDataList(JSONArray dataList) {
        this.dataList = dataList;
    }

    public String getBatchNum() {
        return batchNum;
    }

    public void setBatchNum(String batchNum) {
        this.batchNum = batchNum;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSentTime() {
        return sentTime;
    }

    public void setSentTime(String sentTime) {
        this.sentTime = sentTime;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getInfType() {
        return infType;
    }

    public void setInfType(String infType) {
        this.infType = infType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getRetMess() {
        return retMess;
    }

    public void setRetMess(String retMess) {
        this.retMess = retMess;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getLogParm() {
        return logParm;
    }

    public void setLogParm(String logParm) {
        this.logParm = logParm;
    }

    public String getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(String reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getLogOrdId() {
        return logOrdId;
    }

    public void setLogOrdId(String logOrdId) {
        this.logOrdId = logOrdId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDateNum() {
        return dateNum;
    }

    public void setDateNum(String dateNum) {
        this.dateNum = dateNum;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }
}
