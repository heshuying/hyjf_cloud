package com.hyjf.am.bean.fdd;

import java.io.Serializable;

/**
 * 娉曞ぇ澶т笅杞借劚鏁忓弬鏁扮被
 */
public class FddDessenesitizationBean implements Serializable{

    private static final long serialVersionUID = 3147045390130550149L;

    /**鏂囦欢淇濆瓨璺緞*/
    private  String savePath;
    /**鍚堝悓瀛樺偍ID*/
    private  String  agrementID;
    /**鍚堝悓绫诲瀷*/
    private  String transType;
    /**ftp鏈嶅姟鍣ㄥ瓨鍌ㄥ湴鍧�*/
    private String  ftpPath;
    /**鍚堝悓涓嬭浇鍦板潃*/
    private String downloadUrl;
    /**鍚堝悓缂栧彿*/
    private String ordid;
    /**鍒ゆ柇鎶曡祫浜�/鎵挎帴浜烘槸鍚︿负浼佷笟鐢ㄦ埛*/
    private boolean isTenderCompany;
    /**鍑鸿浜烘槸鍚︿负浼佷笟鎴�*/
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
