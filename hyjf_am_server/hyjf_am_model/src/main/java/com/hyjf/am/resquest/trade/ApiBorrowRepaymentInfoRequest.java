package com.hyjf.am.resquest.trade;

import io.swagger.annotations.ApiModelProperty;

/**
 * @version ApiBorrowRepaymentInfoRequestBean, v0.1 2018/8/28 9:17
 * @Author: Zha Daojian
 */
public class ApiBorrowRepaymentInfoRequest {

    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "借款编号 检索条件")
    private String borrowNid;

    @ApiModelProperty(value = "资产编号 检索条件")
    private String assetId;

    @ApiModelProperty(value = "机构编号 检索条件")
    private String instCode;

    @ApiModelProperty(value = "应还日期开始 检索条件")
    private String recoverTimeStart;

    @ApiModelProperty(value = "应还日期结束 检索条件")
    private String recoverTimeEnd;

    @ApiModelProperty(value = "检索条件 limitStart")
    private Integer limitStart;

    @ApiModelProperty(value = "检索条件 limitEnd")
    private Integer limitEnd;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getRecoverTimeStart() {
        return recoverTimeStart;
    }

    public void setRecoverTimeStart(String recoverTimeStart) {
        this.recoverTimeStart = recoverTimeStart;
    }

    public String getRecoverTimeEnd() {
        return recoverTimeEnd;
    }

    public void setRecoverTimeEnd(String recoverTimeEnd) {
        this.recoverTimeEnd = recoverTimeEnd;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
