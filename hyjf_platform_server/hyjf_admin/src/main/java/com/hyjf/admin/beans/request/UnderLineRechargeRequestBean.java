package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class UnderLineRechargeRequestBean extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "当前ID")
    private Integer id;

    @ApiModelProperty(value = "交易类型")
    private String code;

    @ApiModelProperty(value = "添加者ID")
    private Integer createUserId;

    @ApiModelProperty(value = "添加者用户名")
    private String createUserName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
