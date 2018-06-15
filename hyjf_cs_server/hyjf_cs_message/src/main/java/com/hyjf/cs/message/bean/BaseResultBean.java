/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean;

import java.io.Serializable;

import com.hyjf.common.util.ApiSignUtil;

/**
 * 接口返回数据的基类
 * @author fuqiang
 * @version BaseResultBean, v0.1 2018/6/11 17:54
 */
public class BaseResultBean implements Serializable {
    private static final long serialVersionUID = 4849134832742546555L;

    public static final String STATUS_SUCCESS = "0";

    public static final String STATUS_FAIL = "1";

    public static final String STATUS_DESC_SUCCESS = "成功";

    public static final String STATUS_DESC_FAIL = "失败";

    /**
     * 此处为属性说明
     */
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
