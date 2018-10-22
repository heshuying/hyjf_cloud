/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import java.io.Serializable;
import java.util.Map;


/**
 * 神策数据采集Bean
 *
 * @author liuyang
 * @version SensorsDataBean, v0.1 2018/7/11 10:41
 */
public class SensorsDataBean implements Serializable {

    private static final long serialVersionUID = 4243928297959602371L;

    // 预置属性
    private Map<String,Object> presetProps;

    // 事件类型
    private String eventCode;

    // 入口
    private String entrance;

    // 订单号
    private String orderId;

    // 债转编号
    private String creditNid;

    // 用户ID
    private Integer userId;

    // 平台类型
    private String platformType;

    // 授权类型
    private String authType;

    public Map<String, Object> getPresetProps() {
        return presetProps;
    }

    public void setPresetProps(Map<String, Object> presetProps) {
        this.presetProps = presetProps;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
