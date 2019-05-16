package com.hyjf.cs.market.vo;

import com.hyjf.cs.common.bean.result.WeChatResult;

/**
 * @author xiasq
 * @version ShareVO, v0.1 2018/9/26 13:52
 */
public class ShareVO extends WeChatResult {

    private String appId;
    private String timestamp;
    private String nonceStr;
    private String jsapi_ticket;
    private String signature;
    private String url;
    private Integer reffer;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getReffer() {
        return reffer;
    }

    public void setReffer(Integer reffer) {
        this.reffer = reffer;
    }
}
