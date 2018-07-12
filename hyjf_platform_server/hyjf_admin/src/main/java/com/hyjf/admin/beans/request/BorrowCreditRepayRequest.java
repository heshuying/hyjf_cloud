package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BorrowCreditRepayRequest extends BaseRequest implements Serializable {

    /* 承接人用户名称 */
    @ApiModelProperty(value = "承接人")
    private String userName;

    /* 出让人名称 */
    @ApiModelProperty(value = "出让人")
    private String creditUserName;

    /* 债转标号 */
    @ApiModelProperty(value = "债转编号")
    private String creditNid;

    /* 原标标号 */
    @ApiModelProperty(value = "项目编号")
    private String bidNid;

    /* 认购单号 */
    @ApiModelProperty(value = "订单号")
    private String assignNid;

    /* 还款状态 */
    @ApiModelProperty(value = "还款状态")
    private String status;

    //下次还款时间
    @ApiModelProperty(value = "下次还款时间开始")
    private String assignRepayNextTimeStart;

    //下次还款时间
    @ApiModelProperty(value = "下次还款时间结束")
    private String assignRepayNextTimeEnd;

    // 债权承接时间
    @ApiModelProperty(value = "债权承接时间开始")
    private String addTimeStart;

    // 债权承接时间
    @ApiModelProperty(value = "债权承接时间结束")
    private String addTimeEnd;

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignRepayNextTimeStart() {
        return assignRepayNextTimeStart;
    }

    public void setAssignRepayNextTimeStart(String assignRepayNextTimeStart) {
        this.assignRepayNextTimeStart = assignRepayNextTimeStart;
    }

    public String getAssignRepayNextTimeEnd() {
        return assignRepayNextTimeEnd;
    }

    public void setAssignRepayNextTimeEnd(String assignRepayNextTimeEnd) {
        this.assignRepayNextTimeEnd = assignRepayNextTimeEnd;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
