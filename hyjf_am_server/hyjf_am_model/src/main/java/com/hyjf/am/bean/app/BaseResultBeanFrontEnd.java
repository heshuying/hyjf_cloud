/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.bean.app;

import java.io.Serializable;

/**
 * @author libin
 * @version BaseResultBeanFrontEnd.java, v0.1 2018年7月25日 下午2:34:35
 * APP协议用，区别于BaseResultBean,为了保持和原代码返回的一致性
 */
public class BaseResultBeanFrontEnd implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
    public static final String SUCCESS = "000";
	public static final String SUCCESS_MSG = "成功";
	public static final String FAIL = "99";
	public static final String FAIL_MSG = "失败";

	private String status;
	private String statusDesc;

	public BaseResultBeanFrontEnd() {
		this.status = SUCCESS;
		this.statusDesc = SUCCESS_MSG;
	}

	public BaseResultBeanFrontEnd(String status, String statusDesc) {
		this.status = status;
		this.statusDesc = statusDesc;
	}

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }
}
