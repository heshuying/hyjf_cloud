/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 * 
 * _ooOoo_
 * o8888888o
 * 88" . "88
 * (| -_- |)
 * O\ = /O
 * ____/`---'\____
 * .' \\| |// `.
 * / \\||| : |||// \
 * / _||||| -:- |||||- \
 * | | \\\ - /// | |
 * | \_| ''\---/'' | |
 * \ .-\__ `-` ___/-. /
 * ___`. .' /--.--\ `. . __
 * ."" '< `.___\_<|>_/___.' >'"".
 * | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * \ \ `-. \_ __\ /__ _/ .-` / /
 * ======`-.____`-.___\_____/___.-`____.-'======
 * `=---='
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * 佛祖保佑 永无BUG
 */

package com.hyjf.am.message.processer;

import java.util.Map;

/**
 * app消息实体类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年8月17日
 * @see 上午11:15:04
 */
public class AppMsMessage extends Message {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -4172518682626049718L;

    
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
	
	
    private String mobile;

    private String serviceType;// 业务类型

    private String tplCode;
    
    private Integer msgId;//消息id

}
