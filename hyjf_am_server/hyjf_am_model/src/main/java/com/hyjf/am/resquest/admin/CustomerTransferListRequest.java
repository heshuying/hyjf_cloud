/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: CustomerTransferListRequest, v0.1 2018/7/6 14:39
 */
@ApiModel(value = "用户转账请求参数")
public class CustomerTransferListRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "订单号(检索用)")
    private String orderIdSrch;
    @ApiModelProperty(value = "交易类型(检索用)")
    private String transferTypeSrch;
    @ApiModelProperty(value = "转出账户(检索用)")
    private String outUserNameSrch;
    @ApiModelProperty(value = "对账标识(检索用)")
    private String reconciliationIdSrch;
    @ApiModelProperty(value = "转账状态(检索用)")
    private String statusSrch;
    @ApiModelProperty(value = "转账时间开始值(检索用)")
    private String transferTimeStart;
    @ApiModelProperty(value = "转账时间结束值(检索用)")
    private String transferTimeEnd;
    @ApiModelProperty(value = "操作时间开始值")
    private String opreateTimeStart;
    @ApiModelProperty(value = "操作时间结束值")
    private String opreateTimeEnd;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    public String getOrderIdSrch() {
        return orderIdSrch;
    }

    public void setOrderIdSrch(String orderIdSrch) {
        this.orderIdSrch = orderIdSrch;
    }

    public String getTransferTypeSrch() {
        return transferTypeSrch;
    }

    public void setTransferTypeSrch(String transferTypeSrch) {
        this.transferTypeSrch = transferTypeSrch;
    }

    public String getOutUserNameSrch() {
        return outUserNameSrch;
    }

    public void setOutUserNameSrch(String outUserNameSrch) {
        this.outUserNameSrch = outUserNameSrch;
    }

    public String getReconciliationIdSrch() {
        return reconciliationIdSrch;
    }

    public void setReconciliationIdSrch(String reconciliationIdSrch) {
        this.reconciliationIdSrch = reconciliationIdSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getTransferTimeStart() {
        return transferTimeStart;
    }

    public void setTransferTimeStart(String transferTimeStart) {
        this.transferTimeStart = transferTimeStart;
    }

    public String getTransferTimeEnd() {
        return transferTimeEnd;
    }

    public void setTransferTimeEnd(String transferTimeEnd) {
        this.transferTimeEnd = transferTimeEnd;
    }

    public String getOpreateTimeStart() {
        return opreateTimeStart;
    }

    public void setOpreateTimeStart(String opreateTimeStart) {
        this.opreateTimeStart = opreateTimeStart;
    }

    public String getOpreateTimeEnd() {
        return opreateTimeEnd;
    }

    public void setOpreateTimeEnd(String opreateTimeEnd) {
        this.opreateTimeEnd = opreateTimeEnd;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
