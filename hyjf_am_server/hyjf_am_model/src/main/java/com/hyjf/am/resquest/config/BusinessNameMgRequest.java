package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:10
 * @Version 1.0
 */
public class BusinessNameMgRequest  extends BasePage implements Serializable {
    public static final long serialVersionUID = 5454155825314635342L;

    @ApiModelProperty(value = "业务ID")
    private Integer id;

    @ApiModelProperty(value = "业务名称")
    private String bsname;

    @ApiModelProperty(value = "业务主管")
    private String bscharge;

    @ApiModelProperty(value = "邮件地址")
    private String mail;

    @ApiModelProperty(value = "用户姓名")
    private String username;

    @ApiModelProperty(value = "业务状态 1:启用; 2:禁用")
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBsname() {
        return bsname;
    }

    public void setBsname(String bsname) {
        this.bsname = bsname;
    }

    public String getBscharge() {
        return bscharge;
    }

    public void setBscharge(String bscharge) {
        this.bscharge = bscharge;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
