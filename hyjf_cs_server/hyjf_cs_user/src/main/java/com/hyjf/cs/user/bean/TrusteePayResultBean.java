package com.hyjf.cs.user.bean;

public class TrusteePayResultBean extends BaseMapBean {

    // 请求处理是否成功  0成功 1失败
    private int status = 0;
    // 返回信息
    private String returnMsg;

    private String auto_inves_status;
    private String auto_credit_status;
    private String auto_withdraw_status;
    private String auto_consume_status;
    private String update_time;
    private String smsseq;
    private String callBackAction;

    public String getCallBackAction() {
        return callBackAction;
    }

    public void setCallBackAction(String callBackAction) {
        this.callBackAction = callBackAction;
    }

    public String getSmsseq() {
        return smsseq;
    }

    public void setSmsseq(String smsseq) {
        this.smsseq = smsseq;
    }

    public String getAuto_inves_status() {
        return auto_inves_status;
    }

    public void setAuto_inves_status(String auto_inves_status) {
        this.auto_inves_status = auto_inves_status;
    }

    public String getAuto_credit_status() {
        return auto_credit_status;
    }

    public void setAuto_credit_status(String auto_credit_status) {
        this.auto_credit_status = auto_credit_status;
    }

    public String getAuto_withdraw_status() {
        return auto_withdraw_status;
    }

    public void setAuto_withdraw_status(String auto_withdraw_status) {
        this.auto_withdraw_status = auto_withdraw_status;
    }

    public String getAuto_consume_status() {
        return auto_consume_status;
    }

    public void setAuto_consume_status(String auto_consume_status) {
        this.auto_consume_status = auto_consume_status;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }


}
