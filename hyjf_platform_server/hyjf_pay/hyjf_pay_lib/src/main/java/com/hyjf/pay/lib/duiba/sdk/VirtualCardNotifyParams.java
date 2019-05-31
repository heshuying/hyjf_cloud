package com.hyjf.pay.lib.duiba.sdk;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 扣虚拟卡通知接口
 * Created by xiaoxuda on 2017/9/7.
 */
public class VirtualCardNotifyParams implements Serializable {
    private static final long serialVersionUID = -8937031393324335215L;

    private boolean success;
    private String bizId = "";
    private String errorMessage = "";
    private String orderNum = "";
    private Date timestamp = new Date();
    private String appKey;
    private String uid = "";

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBizId() {
        return this.bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Map<String, String> toRequestMap(String appSecret) {
        HashMap map = new HashMap();
        map.put("success", String.valueOf(this.success));
        map.put("errorMessage", this.getString(this.errorMessage));
        map.put("bizId", this.getString(this.bizId));
        map.put("appKey", this.getString(this.appKey));
        map.put("appSecret", this.getString(appSecret));
        map.put("timestamp", this.getString(Long.valueOf(this.timestamp.getTime())));
        map.put("uid", this.getString(this.uid));
        map.put("orderNum", this.getString(this.orderNum));
        String sign = SignTool.sign(map);
        map.remove("appSecret");
        map.put("sign", sign);
        return map;
    }

    private String getString(Object o) {
        return o == null?"":o.toString();
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
