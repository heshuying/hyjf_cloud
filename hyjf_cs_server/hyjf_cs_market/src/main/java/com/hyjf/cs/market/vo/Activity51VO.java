package com.hyjf.cs.market.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * @author xiasq
 * @version Activity51VO, v0.1 2019/4/22 14:05
 */
@ApiModel(value = "查询当前累计出借金额返回参数")
public class Activity51VO {

    @ApiModelProperty("当前累计出借金额")
    private BigDecimal sumAmount;
    @ApiModelProperty("当前进度， 例如 31.10%")
    private String progressRate;
    @ApiModelProperty("当前档位， 达到3000万返回1，依次2,3,4")
    private int progressGrade;

    public Activity51VO(BigDecimal sumAmount, String progressRate, int progressGrade) {
        this.sumAmount = sumAmount;
        this.progressRate = progressRate;
        this.progressGrade = progressGrade;
    }

    public BigDecimal getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(BigDecimal sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getProgressRate() {
        return progressRate;
    }

    public void setProgressRate(String progressRate) {
        this.progressRate = progressRate;
    }

    public int getProgressGrade() {
        return progressGrade;
    }

    public void setProgressGrade(int progressGrade) {
        this.progressGrade = progressGrade;
    }
}
