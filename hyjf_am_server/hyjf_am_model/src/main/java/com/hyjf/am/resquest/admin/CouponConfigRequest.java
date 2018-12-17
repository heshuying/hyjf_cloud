/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author yaoyong
 * @version CouponConfigRequest, v0.1 2018/7/5 11:09
 */
public class CouponConfigRequest extends BasePage {
    private String id;

    @ApiModelProperty(value = "优惠券编号")
    private String couponCode;

    @ApiModelProperty(value = "优惠券名称")
    private String couponName;

    @ApiModelProperty(value = "优惠券额度")
    private BigDecimal couponQuota;

    @ApiModelProperty(value = "发行数量")
    private Integer couponQuantity;

    @ApiModelProperty(value = "收益期限")
    private Short couponProfitTime;

    @ApiModelProperty(value = "优惠券有效期类别")
    private Integer expirationType;

    @ApiModelProperty(value = "优惠券有效期截止日")
    private String expirationDate;

    @ApiModelProperty(value = "优惠券有效期时长")
    private Integer expirationLength;

    @ApiModelProperty(value = "固定期限时长")
    private Short expirationLengthDay;

    @ApiModelProperty(value = "是否与本金共用")
    private Integer addFlag;

    @ApiModelProperty(value = "优惠券使用平台")
    private String couponSystem;

    @ApiModelProperty(value = "优惠券类型")
    private Integer couponType;

    @ApiModelProperty(value = "优惠券的使用项目类别")
    private String projectType;

    @ApiModelProperty(value = "项目期限类别")
    private Integer projectExpirationType;

    @ApiModelProperty(value = "项目期限时长")
    private Integer projectExpirationLength;

    @ApiModelProperty(value = "项目期限最短时长")
    private Integer projectExpirationLengthMin;

    @ApiModelProperty(value = "项目期限最长时长")
    private Integer projectExpirationLengthMax;

    @ApiModelProperty(value = "投资金额类别")
    private Integer tenderQuotaType;

    @ApiModelProperty(value = "投资金额")
    private Integer tenderQuota;

    @ApiModelProperty(value = "投资金额最小额度")
    private Integer tenderQuotaMin;

    @ApiModelProperty(value = "投资金额最大额度")
    private Integer tenderQuotaMax;

    @ApiModelProperty(value = "优惠券描述")
    private String content;

    @ApiModelProperty(value = "优惠券状态")
    private Integer status;

    @ApiModelProperty(value = "审核内容")
    private String auditContent;

    @ApiModelProperty(value = "审核人")
    private String auditUser;

    @ApiModelProperty(value = "审核时间")
    private Integer auditTime;

    @ApiModelProperty(value = "收益还款时间配置")
    private Integer repayTimeConfig;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private int exportCount;
    /**
     * 检索条件 时间开始
     */
    @ApiModelProperty(value = "发行开始时间")
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    @ApiModelProperty(value = "发行结束时间")
    private String timeEndSrch;

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    //默认为true ,获取全部数据，为false时，获取部分数据
    private boolean limitFlg = false;

    public boolean isLimitFlg() {
        return limitFlg;
    }

    public void setLimitFlg(boolean limitFlg) {
        this.limitFlg = limitFlg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public Integer getCouponType() {
        return couponType;
    }

    public void setCouponType(Integer couponType) {
        this.couponType = couponType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Integer getCouponQuantity() {
        return couponQuantity;
    }

    public void setCouponQuantity(Integer couponQuantity) {
        this.couponQuantity = couponQuantity;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(BigDecimal couponQuota) {
        this.couponQuota = couponQuota;
    }

    public Short getCouponProfitTime() {
        return couponProfitTime;
    }

    public void setCouponProfitTime(Short couponProfitTime) {
        this.couponProfitTime = couponProfitTime;
    }

    public Integer getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(Integer expirationType) {
        this.expirationType = expirationType;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getExpirationLength() {
        return expirationLength;
    }

    public void setExpirationLength(Integer expirationLength) {
        this.expirationLength = expirationLength;
    }

    public Short getExpirationLengthDay() {
        return expirationLengthDay;
    }

    public void setExpirationLengthDay(Short expirationLengthDay) {
        this.expirationLengthDay = expirationLengthDay;
    }

    public Integer getAddFlag() {
        return addFlag;
    }

    public void setAddFlag(Integer addFlag) {
        this.addFlag = addFlag;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public Integer getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(Integer projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public Integer getProjectExpirationLength() {
        return projectExpirationLength;
    }

    public void setProjectExpirationLength(Integer projectExpirationLength) {
        this.projectExpirationLength = projectExpirationLength;
    }

    public Integer getProjectExpirationLengthMin() {
        return projectExpirationLengthMin;
    }

    public void setProjectExpirationLengthMin(Integer projectExpirationLengthMin) {
        this.projectExpirationLengthMin = projectExpirationLengthMin;
    }

    public Integer getProjectExpirationLengthMax() {
        return projectExpirationLengthMax;
    }

    public void setProjectExpirationLengthMax(Integer projectExpirationLengthMax) {
        this.projectExpirationLengthMax = projectExpirationLengthMax;
    }

    public Integer getTenderQuotaType() {
        return tenderQuotaType;
    }

    public void setTenderQuotaType(Integer tenderQuotaType) {
        this.tenderQuotaType = tenderQuotaType;
    }

    public Integer getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(Integer tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public Integer getTenderQuotaMin() {
        return tenderQuotaMin;
    }

    public void setTenderQuotaMin(Integer tenderQuotaMin) {
        this.tenderQuotaMin = tenderQuotaMin;
    }

    public Integer getTenderQuotaMax() {
        return tenderQuotaMax;
    }

    public void setTenderQuotaMax(Integer tenderQuotaMax) {
        this.tenderQuotaMax = tenderQuotaMax;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getRepayTimeConfig() {
        return repayTimeConfig;
    }

    public void setRepayTimeConfig(Integer repayTimeConfig) {
        this.repayTimeConfig = repayTimeConfig;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int getExportCount() {
        return exportCount;
    }

    public void setExportCount(int exportCount) {
        this.exportCount = exportCount;
    }
}
