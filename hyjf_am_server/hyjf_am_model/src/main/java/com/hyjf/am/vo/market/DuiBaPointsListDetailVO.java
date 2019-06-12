/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author wangjun
 * @version DuiBaPointsListDetailVO, v0.1 2019/6/11 11:36
 */
public class DuiBaPointsListDetailVO extends BaseVO {
    @ApiModelProperty(value = "0：普通明细，1：总计")
    public int isTotal;

    @ApiModelProperty(value = "时间")
    public String time;

    @ApiModelProperty(value = "月份总获取积分")
    public String pointsGetTotal;

    @ApiModelProperty(value = "月份总使用积分")
    public String pointsUseTotal;

    @ApiModelProperty(value = "积分业务名称")
    public String businessName;

    @ApiModelProperty(value = "0:获取 1:使用")
    public int type;

    @ApiModelProperty(value = "积分")
    public String pointsDetail;

    /** 年 */
    public String year;
    /** 月 */
    public String month;

    public int getIsTotal() {
        return isTotal;
    }

    public void setIsTotal(int isTotal) {
        this.isTotal = isTotal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPointsGetTotal() {
        return pointsGetTotal;
    }

    public void setPointsGetTotal(String pointsGetTotal) {
        this.pointsGetTotal = pointsGetTotal;
    }

    public String getPointsUseTotal() {
        return pointsUseTotal;
    }

    public void setPointsUseTotal(String pointsUseTotal) {
        this.pointsUseTotal = pointsUseTotal;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPointsDetail() {
        return pointsDetail;
    }

    public void setPointsDetail(String pointsDetail) {
        this.pointsDetail = pointsDetail;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
