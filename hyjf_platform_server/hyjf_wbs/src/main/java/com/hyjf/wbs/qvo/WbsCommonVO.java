/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.qvo;

/**
 * 接口通用返回类
 * @author cui
 * @version WbsCommonVO, v0.1 2019/4/17 11:02
 */
public class WbsCommonVO {

    public static final String SUCCESS = "0";
    public static final String SUCCESS_MSG = "成功";

    private  String code;
    private String msg;
    private Object data;

    public WbsCommonVO() {
        this.code=SUCCESS;
        this.msg=SUCCESS_MSG;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
