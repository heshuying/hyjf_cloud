package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author : huanghui
 */
public class WebUserRepayTransferCustomize implements Serializable {

    // 请求处理是否成功
    private boolean status = false;

    /** 出让人ID */
    private Integer creditUserId;

    /** 出让人用户名 */
    private String creditUserName;

    /** 承接人用户名 */
    private String undertakerUserName;

    /** 承接日期 */
    private Date assignOrderDate;
    private String assignOrderDateStr;

    /** 承接金额 */
    private BigDecimal assignCapital;

    /** 承接金额 格式化后的*/
    private String assignCapitalString;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getUndertakerUserName() {
        return undertakerUserName;
    }

    public void setUndertakerUserName(String undertakerUserName) {
        this.undertakerUserName = undertakerUserName;
    }

    public Date getAssignOrderDate() {
        return assignOrderDate;
    }

    public void setAssignOrderDate(Date assignOrderDate) {
        this.assignOrderDate = assignOrderDate;
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignCapitalString() {
        return assignCapitalString;
    }

    public void setAssignCapitalString(String assignCapitalString) {
        this.assignCapitalString = assignCapitalString;
    }

    public String getAssignOrderDateStr() {
        return assignOrderDateStr;
    }

    public void setAssignOrderDateStr(String assignOrderDateStr) {
        this.assignOrderDateStr = assignOrderDateStr;
    }
}
