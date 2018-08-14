/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */

package com.hyjf.am.vo.message;

import java.util.Map;

/**
 * app消息实体类
 * @author xiasq
 * @version AppMsMessage, v0.1 2018/6/22 14:08
 */
public class AppMsMessage extends HyjfMessage {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -4172518682626049718L;

    private String mobile;

    private String serviceType;

    private String tplCode;

    private Integer msgId;
    
    public AppMsMessage() {
        super();
    }
    
    public AppMsMessage(Integer userId, Map<String, String> replaceStrs, String mobile, String serviceType, String tplCode) {
        super(userId, replaceStrs);
        this.mobile = mobile;
        this.serviceType = serviceType;
        this.tplCode = tplCode;
    }

    public AppMsMessage(String serviceType, Integer msgId) {
        super();
        this.serviceType = serviceType;
        this.msgId = msgId;
    }
 
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getTplCode() {
        return tplCode;
    }

    public void setTplCode(String tplCode) {
        this.tplCode = tplCode;
    }


	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

    @Override
    public String toString() {
        return "AppMsMessage{" +
                "mobile='" + mobile + '\'' +
                ", serviceType='" + serviceType + '\'' +
                ", tplCode='" + tplCode + '\'' +
                ", msgId=" + msgId +
                '}';
    }

}
