package com.hyjf.cs.trade.bean.fdd;

import java.io.Serializable;

/**
 * 法大大下载脱敏参数类
 */
public class FddDessenesitizationBean implements Serializable{

    private static final long serialVersionUID = 3147045390130550149L;

    /**文件保存路径*/
    private  String savePath;
    /**合同存储ID*/
    private  String  agrementID;
    /**合同类型*/
    private  String transType;
    /**ftp服务器存储地址*/
    private String  ftpPath;
    /**合同下载地址*/
    private String downloadUrl;
    /**合同编号*/
    private String ordid;
    /**判断投资人/承接人是否为企业用户*/
    private boolean isTenderCompany;
    /**出让人是否为企业户*/
    private boolean isCreditCompany;


    public boolean isCreditCompany() {
        return isCreditCompany;
    }

    public void setCreditCompany(boolean creditCompany) {
        isCreditCompany = creditCompany;
    }

    public boolean isTenderCompany() {
        return isTenderCompany;
    }

    public void setTenderCompany(boolean tenderCompany) {
        isTenderCompany = tenderCompany;
    }

    public String getOrdid() {
        return ordid;
    }

    public void setOrdid(String ordid) {
        this.ordid = ordid;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getAgrementID() {
        return agrementID;
    }

    public void setAgrementID(String agrementID) {
        this.agrementID = agrementID;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
