package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author nxl
 * @version UserPortraitResponse, v0.1 2018/6/28 14:27
 */
public class UserPortraitRequestBean extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}