package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author by xiehuili on 2018/7/17.
 */
public class OperationLogRequestBean extends BasePage implements Serializable {

    @ApiModelProperty(value = " 资产来源")
    String instCodeSrch;
    @ApiModelProperty(value = "产品类型")
    String assetTypeSrch;
    @ApiModelProperty(value = "期限")
    String borrowPeriodSrch;
    @ApiModelProperty(value = "修改类型")
    String modifyTypeSrch;
    @ApiModelProperty(value = "操作人")
    String userNameSrch;
    @ApiModelProperty(value = "操作时间")
    String recieveTimeStartSrch;
    @ApiModelProperty(value = " 操作时间")
    String recieveTimeEndSrch;
    @ApiModelProperty(value = "状态名称")
    String statusName;
    @ApiModelProperty(value = "机构编号名称")
    private String instName;
    @ApiModelProperty(value = "机构产品类型名称")
    private String assetTypeName;
    @ApiModelProperty(value = "操作人")
    private String name;
    @ApiModelProperty(value = "操作时间字符串")
    private String createTimeString;

    private Integer id;

    private String instCode;

    private Integer assetType;

    private Integer borrowPeriod;
    private String borrowPeriods;

    private String borrowStyle;

    private BigDecimal borrowApr;

    private String serviceFee;

    private String manageFee;

    private String revenueDiffRate;

    private String lateInterestRate;

    private Integer lateFreeDays;

    private Integer status;

    private Integer modifyType;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private Integer updateTime;

    private Boolean delFlg;

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getAssetTypeSrch() {
        return assetTypeSrch;
    }

    public void setAssetTypeSrch(String assetTypeSrch) {
        this.assetTypeSrch = assetTypeSrch;
    }

    public String getBorrowPeriodSrch() {
        return borrowPeriodSrch;
    }

    public void setBorrowPeriodSrch(String borrowPeriodSrch) {
        this.borrowPeriodSrch = borrowPeriodSrch;
    }

    public String getModifyTypeSrch() {
        return modifyTypeSrch;
    }

    public void setModifyTypeSrch(String modifyTypeSrch) {
        this.modifyTypeSrch = modifyTypeSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getRecieveTimeStartSrch() {
        return recieveTimeStartSrch;
    }

    public void setRecieveTimeStartSrch(String recieveTimeStartSrch) {
        this.recieveTimeStartSrch = recieveTimeStartSrch;
    }

    public String getRecieveTimeEndSrch() {
        return recieveTimeEndSrch;
    }

    public void setRecieveTimeEndSrch(String recieveTimeEndSrch) {
        this.recieveTimeEndSrch = recieveTimeEndSrch;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowPeriods() {
        return borrowPeriods;
    }

    public void setBorrowPeriods(String borrowPeriods) {
        this.borrowPeriods = borrowPeriods;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getRevenueDiffRate() {
        return revenueDiffRate;
    }

    public void setRevenueDiffRate(String revenueDiffRate) {
        this.revenueDiffRate = revenueDiffRate;
    }

    public String getLateInterestRate() {
        return lateInterestRate;
    }

    public void setLateInterestRate(String lateInterestRate) {
        this.lateInterestRate = lateInterestRate;
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getModifyType() {
        return modifyType;
    }

    public void setModifyType(Integer modifyType) {
        this.modifyType = modifyType;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTimeString() {
        return createTimeString;
    }

    public void setCreateTimeString(String createTimeString) {
        this.createTimeString = createTimeString;
    }

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;
    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

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


}
