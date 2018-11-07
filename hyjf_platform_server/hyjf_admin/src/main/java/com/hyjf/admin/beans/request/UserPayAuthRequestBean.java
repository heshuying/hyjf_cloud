/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author NXL
 * @version UserMemberParamVO, v0.1 2018/6/19 17:41
 *          会员中心->服务费授权(请求参数）
 */
public class UserPayAuthRequestBean extends BasePage {
    @ApiModelProperty(value = "授权时间开始")
    public String authTimeStart;
    @ApiModelProperty(value = "授权时间结束")
    public String authTimeEnd;
    @ApiModelProperty(value = "用户名")
    public String userName;
    @ApiModelProperty(value = "授权状态")
    public String authType;
    @ApiModelProperty(value = "电子账号")
    public String bankCode;
    @ApiModelProperty(value = "签约到期日开始")
    public String signTimeStart;
    @ApiModelProperty(value = "签约到期日结束")
    public String signTimeEnd;

    public String getAuthTimeStart() {
        return authTimeStart;
    }

    public void setAuthTimeStart(String authTimeStart) {
        this.authTimeStart = authTimeStart;
    }

    public String getAuthTimeEnd() {
        return authTimeEnd;
    }

    public void setAuthTimeEnd(String authTimeEnd) {
        this.authTimeEnd = authTimeEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSignTimeStart() {
        return signTimeStart;
    }

    public void setSignTimeStart(String signTimeStart) {
        this.signTimeStart = signTimeStart;
    }

    public String getSignTimeEnd() {
        return signTimeEnd;
    }

    public void setSignTimeEnd(String signTimeEnd) {
        this.signTimeEnd = signTimeEnd;
    }
}
