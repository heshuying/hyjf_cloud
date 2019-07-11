package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 承接信息请求bean
 * @author zhangyk
 * @date 2018/7/12 19:02
 */
public class BorrowCreditTenderRequest extends BaseRequest implements Serializable {

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

    // 债权承接时间
    @ApiModelProperty(value = "债权承接时间开始")
    private String addTimeStart;

    // 债权承接时间
    @ApiModelProperty(value = "债权承接时间结束")
    private String addTimeEnd;

    // 发起平台
    @ApiModelProperty(value = "发起平台")
    private String client;

    @ApiModelProperty(value = "是否具有组织架构查看权限")
    private String isOrganizationView;

    @ApiModelProperty(value = "债权结束状态 S-成功;F-失败;A-初始:W-未开始")
    private String stateSrch;

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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getIsOrganizationView() {
        return isOrganizationView;
    }

    public void setIsOrganizationView(String isOrganizationView) {
        this.isOrganizationView = isOrganizationView;
    }

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }
}
