package com.hyjf.am.vo.app.recharge;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/24
 */
@ApiModel(value = "限额说明", description = "限额说明")
public class AppRechargeLimitVO implements Serializable {
    private static final long serialVersionUID = -2323359532266579146L;
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    @ApiModelProperty(value = "银行logo", example = "http://micro.file.hyjf.com/...")
    private String bankIcon;
    @ApiModelProperty(value = "每笔限额", example = "XX万元/不限")
    private String single;
    @ApiModelProperty(value = "每日限额", example = "XX万元/不限")
    private String daily;
    @ApiModelProperty(value = "每月限额", example = "XX万元/不限")
    private String monthly;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankIcon() {
        return bankIcon;
    }

    public void setBankIcon(String bankIcon) {
        this.bankIcon = bankIcon;
    }

    public String getSingle() {
        return single;
    }

    public void setSingle(String single) {
        this.single = single;
    }

    public String getDaily() {
        return daily;
    }

    public void setDaily(String daily) {
        this.daily = daily;
    }

    public String getMonthly() {
        return monthly;
    }

    public void setMonthly(String monthly) {
        this.monthly = monthly;
    }
}
