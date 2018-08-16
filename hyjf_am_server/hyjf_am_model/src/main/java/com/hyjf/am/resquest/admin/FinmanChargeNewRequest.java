package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author xiehuili on 2018/8/13.
 * @Version 1.0
 */
public class FinmanChargeNewRequest extends BasePage {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 8853785949572253967L;
//
//    /** 管理费列表list */
//    private List<BorrowFinmanNewChargeCustomize> recordList;

    @ApiModelProperty(value = "类型 ")
    private String manChargeTypeSear;

    @ApiModelProperty(value = "资产来源 ")
    String instCodeSrch;

    @ApiModelProperty(value = "产品类型 ")
    String assetTypeSrch;

    @ApiModelProperty(value = "期限")
    private String manChargeTimeSear;

    @ApiModelProperty(value = "项目类型 ")
    private String projectTypeSear;

    @ApiModelProperty(value = "状态")
    private String statusSear;
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

    private String manChargeCd;

    private String manChargeType;

    private Integer manChargeTime;

    private String manChargeTimeType;

    private String projectType;

    private String instCode;

    private Integer assetType;

    private String autoBorrowApr;

    private Integer chargeMode;

    private String chargeRate;

    private String manChargeRate;

    private String returnRate;

    private String lateInterest;

    private BigDecimal serviceFeeTotal;

    private Integer lateFreeDays;

    private Integer autoRepay;

    private Integer repayerType;

    private Integer status;

    private String remark;

    private Integer createTime;

    private Integer updateTime;


    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        return paginatorPage == 0 ? 1 : paginatorPage;
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

    public String getManChargeTypeSear() {
        return manChargeTypeSear;
    }

    public void setManChargeTypeSear(String manChargeTypeSear) {
        this.manChargeTypeSear = manChargeTypeSear;
    }

    public String getManChargeTimeSear() {
        return manChargeTimeSear;
    }

    public void setManChargeTimeSear(String manChargeTimeSear) {
        this.manChargeTimeSear = manChargeTimeSear;
    }

    public String getProjectTypeSear() {
        return projectTypeSear;
    }

    public void setProjectTypeSear(String projectTypeSear) {
        this.projectTypeSear = projectTypeSear;
    }

    public String getStatusSear() {
        return statusSear;
    }

    public void setStatusSear(String statusSear) {
        this.statusSear = statusSear;
    }

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

    public String getManChargeCd() {
        return manChargeCd;
    }

    public void setManChargeCd(String manChargeCd) {
        this.manChargeCd = manChargeCd;
    }

    public String getManChargeType() {
        return manChargeType;
    }

    public void setManChargeType(String manChargeType) {
        this.manChargeType = manChargeType;
    }

    public Integer getManChargeTime() {
        return manChargeTime;
    }

    public void setManChargeTime(Integer manChargeTime) {
        this.manChargeTime = manChargeTime;
    }

    public String getManChargeTimeType() {
        return manChargeTimeType;
    }

    public void setManChargeTimeType(String manChargeTimeType) {
        this.manChargeTimeType = manChargeTimeType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
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

    public String getAutoBorrowApr() {
        return autoBorrowApr;
    }

    public void setAutoBorrowApr(String autoBorrowApr) {
        this.autoBorrowApr = autoBorrowApr;
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate;
    }

    public String getManChargeRate() {
        return manChargeRate;
    }

    public void setManChargeRate(String manChargeRate) {
        this.manChargeRate = manChargeRate;
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate;
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest;
    }

    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public Integer getAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(Integer autoRepay) {
        this.autoRepay = autoRepay;
    }

    public Integer getRepayerType() {
        return repayerType;
    }

    public void setRepayerType(Integer repayerType) {
        this.repayerType = repayerType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
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
