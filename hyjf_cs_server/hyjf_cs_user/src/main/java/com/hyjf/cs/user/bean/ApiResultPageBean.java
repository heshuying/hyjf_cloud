/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2017
 * Company: HYJF Corporation
 * @author: lb
 * @version: 1.0
 * Created at: 2017年9月14日 下午5:23:36
 * Modification History:
 * Modified by : 
 */
	
package com.hyjf.cs.user.bean;

import java.io.Serializable;

/**
 * @author liubin
 * 授权提示页面返回提示信息
 */
public class ApiResultPageBean implements Serializable{
	private static final long serialVersionUID = 3215431135432432222L;

    //返回状态
    private String status;
    private String statusDesc;
    private String retUrl;
    private String butMes;
    
    public String getButMes() {
        return butMes;
    }
    public void setButMes(String butMes) {
        this.butMes = butMes;
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
    public String getRetUrl() {
        return retUrl;
    }
    public void setRetUrl(String retUrl) {
        this.retUrl = retUrl;
    }
    
}

	