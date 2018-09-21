package com.hyjf.am.response;

/**
 * @author lisheng
 * @version WrbResponse, v0.1 2018/9/19 11:39
 */

public class WrbResponse {
    public static final String SUCCESS_RETCODE = "0";
    public static final String SUCCESS_RETMESSAGE = "成功";
    public static final String FAIL_RETCODE = "99";
    public static final String FAIL_RETMESSAGE = "请求参数错误或缺失";

    // 返回状态码
    protected String retcode;
    // 返回信息
    protected String retmsg;

    public WrbResponse() {
        this.retcode = SUCCESS_RETCODE;
        this.retmsg = SUCCESS_RETMESSAGE;
    }

    public WrbResponse(String retcode, String retmsg) {
        this.retcode = retcode;
        this.retmsg = retmsg;
    }

    public String getRetcode() {
        return retcode;
    }

    public void setRetcode(String retcode) {
        this.retcode = retcode;
    }

    public String getRetmsg() {
        return retmsg;
    }

    public void setRetmsg(String retmsg) {
        this.retmsg = retmsg;
    }

}
