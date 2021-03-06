/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.DuibaPointsModifyVO;
import com.hyjf.am.vo.admin.DuibaPointsVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsRequest, v0.1 2019/5/29 10:46
 */
public class DuibaPointsRequest extends BasePage implements Serializable {

    @ApiModelProperty(value = "用户名查询")
    private String userNameSrch;

    @ApiModelProperty(value = "姓名查询")
    private String trueNameSrch;

    @ApiModelProperty(value = "调整人查询")
    private String modifyNameSrch;

    @ApiModelProperty(value = "积分业务名称查询")
    private Integer businessNameSrch;

    @ApiModelProperty(value = "类型查询")
    private Integer typeSrch;

    @ApiModelProperty(value = "状态查询")
    private Integer statusSrch;

    @ApiModelProperty(value = "发生时间开始查询")
    private String startDateSrch;

    @ApiModelProperty(value = "发生时间结束查询")
    private String endDateSrch;

    @ApiModelProperty(value = "调整积分数")
    private Integer modifyPoints;

    @ApiModelProperty(value = "调整类型 0调增 1调减")
    private Integer modifyType;

    @ApiModelProperty(value = "批量调整用户积分")
    private List<Integer> userIdList;

    private Integer userId;

    @ApiModelProperty(value = "调整原因")
    private String reason;

    @ApiModelProperty(value = "审核不通过原因")
    private String refuseReason;

    @ApiModelProperty(value = "审核结果 1通过 2不通过")
    private Integer auditStatus;

    @ApiModelProperty(value = "当前审批订单号")
    private String orderId;

    @ApiModelProperty(value = "导出类型0：积分调整明细 1：积分调整审核")
    private Integer exportType;

    @ApiModelProperty(value = "积分明细列表相关")
    private List<DuibaPointsVO> recordList;

    @ApiModelProperty(value = "积分调整明细列表相关")
    private List<DuibaPointsModifyVO> recordModifyList;

    /**
     * 修改人账号
     */
    private String modifyName;

    private String modifyId;

    /*
     * 页码
     */
    protected int limitStart = -1;
    /*
     * 页码
     */
    protected int limitEnd = -1;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
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

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getTrueNameSrch() {
        return trueNameSrch;
    }

    public void setTrueNameSrch(String trueNameSrch) {
        this.trueNameSrch = trueNameSrch;
    }

    public List<DuibaPointsVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<DuibaPointsVO> recordList) {
        this.recordList = recordList;
    }

    public String getModifyNameSrch() {
        return modifyNameSrch;
    }

    public void setModifyNameSrch(String modifyNameSrch) {
        this.modifyNameSrch = modifyNameSrch;
    }

    public Integer getBusinessNameSrch() {
        return businessNameSrch;
    }

    public void setBusinessNameSrch(Integer businessNameSrch) {
        this.businessNameSrch = businessNameSrch;
    }

    public Integer getTypeSrch() {
        return typeSrch;
    }

    public void setTypeSrch(Integer typeSrch) {
        this.typeSrch = typeSrch;
    }

    public Integer getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(Integer statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getStartDateSrch() {
        return startDateSrch;
    }

    public void setStartDateSrch(String startDateSrch) {
        this.startDateSrch = startDateSrch;
    }

    public String getEndDateSrch() {
        return endDateSrch;
    }

    public void setEndDateSrch(String endDateSrch) {
        this.endDateSrch = endDateSrch;
    }

    public List<DuibaPointsModifyVO> getRecordModifyList() {
        return recordModifyList;
    }

    public void setRecordModifyList(List<DuibaPointsModifyVO> recordModifyList) {
        this.recordModifyList = recordModifyList;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getModifyPoints() {
        return modifyPoints;
    }

    public void setModifyPoints(Integer modifyPoints) {
        this.modifyPoints = modifyPoints;
    }

    public Integer getModifyType() {
        return modifyType;
    }

    public void setModifyType(Integer modifyType) {
        this.modifyType = modifyType;
    }

    public String getModifyName() {
        return modifyName;
    }

    public void setModifyName(String modifyName) {
        this.modifyName = modifyName;
    }

    public String getModifyId() {
        return modifyId;
    }

    public void setModifyId(String modifyId) {
        this.modifyId = modifyId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }
}
