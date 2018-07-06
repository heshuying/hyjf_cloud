/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.user.bean;

import com.hyjf.common.util.ApiSignUtil;

import java.io.Serializable;

/**
 * 接口返回数据的基类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月19日
 * @see 2:23:01
 */
public class BaseResultBean implements Serializable {
	
	public static final String STATUS_SUCCESS = "0";
	
	public static final String STATUS_FAIL = "1";
	
	public static final String STATUS_DESC_SUCCESS = "成功";
	
	public static final String STATUS_DESC_FAIL = "失败";

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -3589570872364671096L;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatusForResponse(String status) {
        this.status = status;
        String chkvalue = ApiSignUtil.encryptByRSA(status);
        setChkValue(chkvalue);
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
    
    /** 返回数据 **/
    private Object data;

    private String status;
    
    private String chkValue;

    public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	

	public String getChkValue() {
		return chkValue;
	}

	public void setChkValue(String chkValue) {
		this.chkValue = chkValue;
	}

	private String statusDesc;

	@Override
	public String toString() {
		return "BaseResultBean [data=" + data + ", status=" + status + ", statusDesc=" + statusDesc + "]";
	}

}
