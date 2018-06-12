package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class HjhAssetRiskInfoWithBLOBs extends HjhAssetRiskInfo implements Serializable {
    private String amazonInfo;

    private String ebayInfo;

    private String jingdongInfo;

    private String taobaoInfo;

    private String tianmaoInfo;

    private static final long serialVersionUID = 1L;

    public String getAmazonInfo() {
        return amazonInfo;
    }

    public void setAmazonInfo(String amazonInfo) {
        this.amazonInfo = amazonInfo == null ? null : amazonInfo.trim();
    }

    public String getEbayInfo() {
        return ebayInfo;
    }

    public void setEbayInfo(String ebayInfo) {
        this.ebayInfo = ebayInfo == null ? null : ebayInfo.trim();
    }

    public String getJingdongInfo() {
        return jingdongInfo;
    }

    public void setJingdongInfo(String jingdongInfo) {
        this.jingdongInfo = jingdongInfo == null ? null : jingdongInfo.trim();
    }

    public String getTaobaoInfo() {
        return taobaoInfo;
    }

    public void setTaobaoInfo(String taobaoInfo) {
        this.taobaoInfo = taobaoInfo == null ? null : taobaoInfo.trim();
    }

    public String getTianmaoInfo() {
        return tianmaoInfo;
    }

    public void setTianmaoInfo(String tianmaoInfo) {
        this.tianmaoInfo = tianmaoInfo == null ? null : tianmaoInfo.trim();
    }
}