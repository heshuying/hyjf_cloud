package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author by xiehuili on 2018/7/6.
 */
@ApiModel(value="保证金配置对象",description="保证金配置对象")
public class AdminInstConfigListRequest extends BasePage {
    @ApiModelProperty(value = "根据ids查询和删除")
    private String ids;
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "机构编号")
    private String instCode;
    @ApiModelProperty(value = "机构名称")
    private String instName;
    @ApiModelProperty(value = "机构类别")
    private Integer instType;
    @ApiModelProperty(value = "担保公司名称")
    private String cooperativeAgency;
    @ApiModelProperty(value = "额度上限")
    private BigDecimal capitalToplimit;
    @ApiModelProperty(value = "提现手续费")
    private BigDecimal commissionFee;
    @ApiModelProperty(value = "等额本息保证金的回滚方式")
    private Integer repayCapitalType;
    @ApiModelProperty(value = "备注说明")
    private String remark;

    private Integer userId;
    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public Integer getInstType() {
        return instType;
    }

    public void setInstType(Integer instType) {
        this.instType = instType;
    }

    public BigDecimal getCapitalToplimit() {
        return capitalToplimit;
    }

    public void setCapitalToplimit(BigDecimal capitalToplimit) {
        this.capitalToplimit = capitalToplimit;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public Integer getRepayCapitalType() {
        return repayCapitalType;
    }

    public void setRepayCapitalType(Integer repayCapitalType) {
        this.repayCapitalType = repayCapitalType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCooperativeAgency() {
        return cooperativeAgency;
    }

    public void setCooperativeAgency(String cooperativeAgency) {
        this.cooperativeAgency = cooperativeAgency;
    }
}
