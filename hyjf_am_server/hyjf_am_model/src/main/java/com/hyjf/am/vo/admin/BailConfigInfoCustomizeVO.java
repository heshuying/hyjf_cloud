/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version BailConfigCustomizeVO, v0.1 2018/9/26 15:44
 */
public class BailConfigInfoCustomizeVO extends BailConfigCustomizeVO implements Serializable {

    /**
     * 等额本息 NCL:新增授信  LCL:在贷授信 RCT:回滚方式
     */
    @ApiModelProperty(value = "等额本息-新增授信")
    private Integer monthNCL;
    @ApiModelProperty(value = "等额本息-在贷授信")
    private Integer monthLCL;
    @ApiModelProperty(value = "等额本息-回滚方式")
    private Integer monthRCT;
    @ApiModelProperty(value = "等额本息-删除标识")
    private Integer monthDEL;
    /**
     * 按月计息，到期还本还息
     */
    @ApiModelProperty(value = "按月计息，到期还本还息-新增授信")
    private Integer endNCL;
    @ApiModelProperty(value = "按月计息，到期还本还息-在贷授信")
    private Integer endLCL;
    @ApiModelProperty(value = "按月计息，到期还本还息-回滚方式")
    private Integer endRCT;
    @ApiModelProperty(value = "按月计息，到期还本还息-删除标识")
    private Integer endDEL;
    /**
     * 先息后本
     */
    @ApiModelProperty(value = "先息后本-新增授信")
    private Integer endmonthNCL;
    @ApiModelProperty(value = "先息后本-在贷授信")
    private Integer endmonthLCL;
    @ApiModelProperty(value = "先息后本-回滚方式")
    private Integer endmonthRCT;
    @ApiModelProperty(value = "先息后本-删除标识")
    private Integer endmonthDEL;
    /**
     * 按天计息，到期还本息
     */
    @ApiModelProperty(value = "按天计息，到期还本息-新增授信")
    private Integer enddayNCL;
    @ApiModelProperty(value = "按天计息，到期还本息-在贷授信")
    private Integer enddayLCL;
    @ApiModelProperty(value = "按天计息，到期还本息-回滚方式")
    private Integer enddayRCT;
    @ApiModelProperty(value = "按天计息，到期还本息-删除标识")
    private Integer enddayDEL;
    /**
     * 等额本金
     */
    @ApiModelProperty(value = "等额本金-新增授信")
    private Integer principalNCL;
    @ApiModelProperty(value = "等额本金-在贷授信")
    private Integer principalLCL;
    @ApiModelProperty(value = "等额本金-回滚方式")
    private Integer principalRCT;
    @ApiModelProperty(value = "等额本金-删除标识")
    private Integer principalDEL;

    /**
     * 按季还款
     */
    @ApiModelProperty(value = "按季还款-新增授信")
    private Integer seasonNCL;
    @ApiModelProperty(value = "按季还款-在贷授信")
    private Integer seasonLCL;
    @ApiModelProperty(value = "按季还款-回滚方式")
    private Integer seasonRCT;
    @ApiModelProperty(value = "按季还款-删除标识")
    private Integer seasonDEL;

    /**
     * 按月付息到期还本
     */
    @ApiModelProperty(value = "按月付息到期还本-新增授信")
    private Integer endmonthsNCL;
    @ApiModelProperty(value = "按月付息到期还本-在贷授信")
    private Integer endmonthsLCL;
    @ApiModelProperty(value = "按月付息到期还本-回滚方式")
    private Integer endmonthsRCT;
    @ApiModelProperty(value = "按月付息到期还本-删除标识")
    private Integer endmonthsDEL;


    public Integer getMonthNCL() {
        return monthNCL;
    }

    public void setMonthNCL(Integer monthNCL) {
        this.monthNCL = monthNCL;
    }

    public Integer getMonthLCL() {
        return monthLCL;
    }

    public void setMonthLCL(Integer monthLCL) {
        this.monthLCL = monthLCL;
    }

    public Integer getMonthRCT() {
        return monthRCT;
    }

    public void setMonthRCT(Integer monthRCT) {
        this.monthRCT = monthRCT;
    }

    public Integer getMonthDEL() {
        return monthDEL;
    }

    public void setMonthDEL(Integer monthDEL) {
        this.monthDEL = monthDEL;
    }

    public Integer getEndNCL() {
        return endNCL;
    }

    public void setEndNCL(Integer endNCL) {
        this.endNCL = endNCL;
    }

    public Integer getEndLCL() {
        return endLCL;
    }

    public void setEndLCL(Integer endLCL) {
        this.endLCL = endLCL;
    }

    public Integer getEndRCT() {
        return endRCT;
    }

    public void setEndRCT(Integer endRCT) {
        this.endRCT = endRCT;
    }

    public Integer getEndDEL() {
        return endDEL;
    }

    public void setEndDEL(Integer endDEL) {
        this.endDEL = endDEL;
    }

    public Integer getEndmonthNCL() {
        return endmonthNCL;
    }

    public void setEndmonthNCL(Integer endmonthNCL) {
        this.endmonthNCL = endmonthNCL;
    }

    public Integer getEndmonthLCL() {
        return endmonthLCL;
    }

    public void setEndmonthLCL(Integer endmonthLCL) {
        this.endmonthLCL = endmonthLCL;
    }

    public Integer getEndmonthRCT() {
        return endmonthRCT;
    }

    public void setEndmonthRCT(Integer endmonthRCT) {
        this.endmonthRCT = endmonthRCT;
    }

    public Integer getEndmonthDEL() {
        return endmonthDEL;
    }

    public void setEndmonthDEL(Integer endmonthDEL) {
        this.endmonthDEL = endmonthDEL;
    }

    public Integer getEnddayNCL() {
        return enddayNCL;
    }

    public void setEnddayNCL(Integer enddayNCL) {
        this.enddayNCL = enddayNCL;
    }

    public Integer getEnddayLCL() {
        return enddayLCL;
    }

    public void setEnddayLCL(Integer enddayLCL) {
        this.enddayLCL = enddayLCL;
    }

    public Integer getEnddayRCT() {
        return enddayRCT;
    }

    public void setEnddayRCT(Integer enddayRCT) {
        this.enddayRCT = enddayRCT;
    }

    public Integer getEnddayDEL() {
        return enddayDEL;
    }

    public void setEnddayDEL(Integer enddayDEL) {
        this.enddayDEL = enddayDEL;
    }

    public Integer getPrincipalNCL() {
        return principalNCL;
    }

    public void setPrincipalNCL(Integer principalNCL) {
        this.principalNCL = principalNCL;
    }

    public Integer getPrincipalLCL() {
        return principalLCL;
    }

    public void setPrincipalLCL(Integer principalLCL) {
        this.principalLCL = principalLCL;
    }

    public Integer getPrincipalRCT() {
        return principalRCT;
    }

    public void setPrincipalRCT(Integer principalRCT) {
        this.principalRCT = principalRCT;
    }

    public Integer getPrincipalDEL() {
        return principalDEL;
    }

    public void setPrincipalDEL(Integer principalDEL) {
        this.principalDEL = principalDEL;
    }

    public Integer getSeasonNCL() {
        return seasonNCL;
    }

    public void setSeasonNCL(Integer seasonNCL) {
        this.seasonNCL = seasonNCL;
    }

    public Integer getSeasonLCL() {
        return seasonLCL;
    }

    public void setSeasonLCL(Integer seasonLCL) {
        this.seasonLCL = seasonLCL;
    }

    public Integer getSeasonRCT() {
        return seasonRCT;
    }

    public void setSeasonRCT(Integer seasonRCT) {
        this.seasonRCT = seasonRCT;
    }

    public Integer getSeasonDEL() {
        return seasonDEL;
    }

    public void setSeasonDEL(Integer seasonDEL) {
        this.seasonDEL = seasonDEL;
    }

    public Integer getEndmonthsNCL() {
        return endmonthsNCL;
    }

    public void setEndmonthsNCL(Integer endmonthsNCL) {
        this.endmonthsNCL = endmonthsNCL;
    }

    public Integer getEndmonthsLCL() {
        return endmonthsLCL;
    }

    public void setEndmonthsLCL(Integer endmonthsLCL) {
        this.endmonthsLCL = endmonthsLCL;
    }

    public Integer getEndmonthsRCT() {
        return endmonthsRCT;
    }

    public void setEndmonthsRCT(Integer endmonthsRCT) {
        this.endmonthsRCT = endmonthsRCT;
    }

    public Integer getEndmonthsDEL() {
        return endmonthsDEL;
    }

    public void setEndmonthsDEL(Integer endmonthsDEL) {
        this.endmonthsDEL = endmonthsDEL;
    }

    public String getMonthLLL() {
        StringBuffer sb = new StringBuffer();
        if (null != this.monthNCL && this.monthNCL == 1) {
            sb.append("新增授信+");
        }
        if (null != this.monthLCL && this.monthLCL == 1) {
            sb.append("在贷授信+");
        }
        if (null != this.monthRCT && this.monthRCT == 0) {
            sb.append("到期回滚");
        } else if (null != this.monthRCT && this.monthRCT == 1) {
            sb.append("分期回滚");
        } else if (null != this.monthRCT && this.monthRCT == 2) {
            sb.append("不回滚");
        }
        return sb.toString();
    }

    public String getEndLLL() {
        StringBuffer sb = new StringBuffer();
        if (null != this.endNCL && this.endNCL == 1) {
            sb.append("新增授信+");
        }
        if (null != this.endLCL && this.endLCL == 1) {
            sb.append("在贷授信+");
        }
        if (null != this.endRCT && this.endRCT == 0) {
            sb.append("到期回滚");
        } else if (null != this.endRCT && this.endRCT == 1) {
            sb.append("分期回滚");
        } else if (null != this.endRCT && this.endRCT == 2) {
            sb.append("不回滚");
        }
        return sb.toString();
    }

    public String getEndmonthLLL() {
        StringBuffer sb = new StringBuffer();
        if (null != this.endmonthNCL && this.endmonthNCL == 1) {
            sb.append("新增授信+");
        }
        if (null != this.endmonthNCL && this.endmonthLCL == 1) {
            sb.append("在贷授信+");
        }
        if (null != this.endmonthRCT && this.endmonthRCT == 0) {
            sb.append("到期回滚");
        } else if (null != this.endmonthRCT && this.endmonthRCT == 1) {
            sb.append("分期回滚");
        } else if (null != this.endmonthRCT && this.endmonthRCT == 2) {
            sb.append("不回滚");
        }
        return sb.toString();
    }

    public String getEnddayLLL() {
        StringBuffer sb = new StringBuffer();
        if (null != this.enddayNCL && this.enddayNCL == 1) {
            sb.append("新增授信+");
        }
        if (null != this.enddayLCL && this.enddayLCL == 1) {
            sb.append("在贷授信+");
        }
        if (null != this.enddayRCT && this.enddayRCT == 0) {
            sb.append("到期回滚");
        } else if (null != this.enddayRCT && this.enddayRCT == 1) {
            sb.append("分期回滚");
        } else if (null != this.enddayRCT && this.enddayRCT == 2) {
            sb.append("不回滚");
        }
        return sb.toString();
    }

    public String getPrincipalLLL() {
        StringBuffer sb = new StringBuffer();
        if (null != this.principalNCL && this.principalNCL == 1) {
            sb.append("新增授信+");
        }
        if (null != this.principalLCL && this.principalLCL == 1) {
            sb.append("在贷授信+");
        }
        if (null != this.principalRCT && this.principalRCT == 0) {
            sb.append("到期回滚");
        } else if (null != this.principalRCT && this.principalRCT == 1) {
            sb.append("分期回滚");
        } else if (null != this.principalRCT && this.principalRCT == 2) {
            sb.append("不回滚");
        }
        return sb.toString();
    }
}
