/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * 销户记录RequstBean
 *
 * @author liuyang
 * @version BankCancellationAccountRequestBean, v0.1 2019/3/31 13:16
 */
public class BankCancellationAccountRequestBean extends BasePage{

    // 销户开始时间
    @ApiModelProperty(value = "销户开始时间")
    public String cancellationTimeStart;

    // 销户结束时间
    @ApiModelProperty(value = "销户结束时间")
    public String cancellationTimeEnd;

    // 用户名
    @ApiModelProperty(value = "用户名")
    public String username;

    // 真是姓名
    @ApiModelProperty(value = "真是姓名")
    public String truename;
    // 手机号
    @ApiModelProperty(value = "手机号")
    public String mobile;

    // 身份证号
    @ApiModelProperty(value = "身份证号")
    private String idcard;

    public String getCancellationTimeStart() {
        return cancellationTimeStart;
    }

    public void setCancellationTimeStart(String cancellationTimeStart) {
        this.cancellationTimeStart = cancellationTimeStart;
    }

    public String getCancellationTimeEnd() {
        return cancellationTimeEnd;
    }

    public void setCancellationTimeEnd(String cancellationTimeEnd) {
        this.cancellationTimeEnd = cancellationTimeEnd;
    }


    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }
}
