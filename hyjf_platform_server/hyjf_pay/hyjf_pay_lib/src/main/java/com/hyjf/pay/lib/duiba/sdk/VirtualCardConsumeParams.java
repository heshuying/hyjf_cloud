package com.hyjf.pay.lib.duiba.sdk;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 扣虚拟卡请求参数
 * Created by xiaoxuda on 2017/9/7.
 */
public class VirtualCardConsumeParams implements Serializable{
    private static final long serialVersionUID = -2237464347809728045L;

    private String appKey;
    private Date timestamp;
    private Long count;
    private String orderNum = "";
    private String description = "";
    private String type = "";
    private String uid = "";
    private boolean waitAudit = false;
    private String ip = "";
    private String params = "";
    private String transfer = "";

    public Map<String, String> toRequestMap(String appSecret) {
        HashMap map = new HashMap();
        map.put("count", "" + this.count);
        map.put("description", this.description);
        map.put("uid", this.uid);
        map.put("appKey", this.appKey);
        map.put("waitAudit", String.valueOf(this.waitAudit));
        map.put("appSecret", appSecret);
        map.put("timestamp",  System.currentTimeMillis()+"");
        map.put("orderNum", this.orderNum);
        map.put("type", this.type);
        map.put("ip", this.ip);
        map.put("params", this.params);
        this.putIfNotEmpty(map, "transfer", this.transfer);
        String sign = SignTool.sign(map);
        map.remove("appSecret");
        map.put("sign", sign);
        return map;
    }

    private void putIfNotEmpty(Map<String, String> map, String key, String value) {
        if(value != null && value.length() != 0) {
            map.put(key, value);
        }
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getWaitAudit() {
        return waitAudit;
    }

    public void setWaitAudit(boolean waitAudit) {
        this.waitAudit = waitAudit;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getTransfer() {
        return transfer;
    }

    public void setTransfer(String transfer) {
        this.transfer = transfer;
    }
}
