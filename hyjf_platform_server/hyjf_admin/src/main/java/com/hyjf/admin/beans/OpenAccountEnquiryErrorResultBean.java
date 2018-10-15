package com.hyjf.admin.beans;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * ajax用户按照手机号和身份证号查询开户掉单校验
 * @version OpenAccountEnquiryDefineResultBean, v0.1 2018/8/20 15:42
 * @Author: Zha Daojian
 */
public class OpenAccountEnquiryErrorResultBean implements Serializable {

    @ApiModelProperty(value = "结果说明，status为y是为空")
    private String info;
    @ApiModelProperty(value = "返回结果,成功")
    private String status;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
