package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version BankOpenAccountLogRequest, v0.1 2018/8/20 14:52
 * @Author: Zha Daojian
 */
public class BankOpenAccountLogRequest extends BaseVO {



    @ApiModelProperty(value = "身份证")
    public String idcard;

    @ApiModelProperty(value = "用户手机号")
    public String mobile;

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
