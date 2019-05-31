package com.hyjf.pay.lib.duiba.sdk;

import java.io.Serializable;

/**
 * 扣虚拟卡结果
 * Created by xiaoxuda on 2017/9/7.
 */
public class VirtualCardConsumeResult implements Serializable {
    private static final long serialVersionUID = -7247877424274706219L;

    private static final String SUCCESS = "ok";
    private static final String FAIL = "fail";

    /**
     * 扣虚拟卡结果
     */
    private String status;
    /**
     * 错误信息
     */
    private String errorMessage = "";
    /**
     * 开发者订单号
     */
    private String bizId = "";
    /**
     * 虚拟卡剩余数量
     */
    private Long count = Long.valueOf(-1L);

    public VirtualCardConsumeResult(){}

    public VirtualCardConsumeResult(boolean success) {
        if (success) {
            this.status = SUCCESS;
        } else {
            this.status = FAIL;
        }
    }

    public boolean isSuccess(){
        return SUCCESS.equalsIgnoreCase(this.status);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getBizId() {
        return this.bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String toString() {
        return "{\'status\':\'" + this.status + "\',\'errorMessage\':\'" + this.errorMessage + "\',\'bizId\':\'" + this.bizId + "\',\'count\':\'" + this.count + "\'}";
    }
}
