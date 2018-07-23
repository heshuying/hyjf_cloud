/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author NXL
 * @version LoanCoverUserRequest, v0.1 2018/6/26 17:25
 */
public class LoanCoverUserCustomizeVO extends BaseVO implements Serializable{

    private Integer id;

    @ApiModelProperty(value = "姓名或企业名称")
    private String name;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "证件类型:0:身份证,1:企业证件号码")
    private Integer idType;

    @ApiModelProperty(value = "证件号码")
    private String idNo;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "状态:success:成功,error:失败")
    private String status;

    @ApiModelProperty(value = "认证状态")
    private String code;

    @ApiModelProperty(value = "客户编号")
    private String customerId;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建用户id")
    private Integer createUserId;

    @ApiModelProperty(value = "创建用户名")
    private String createUserName;

    @ApiModelProperty(value = "更新用户id")
    private Integer updateUserId;

    @ApiModelProperty(value = "更新用户名")
    private String updateUserName;

    private Date createTime;

    private Date updateTime;
    //显示用 添加时间和修改时间
    @ApiModelProperty(value = "添加时间")
    private String strCreateTime;
    @ApiModelProperty(value = "更新时间")
    private String strUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo == null ? null : idNo.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }



    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getStrCreateTime() {
        return strCreateTime;
    }

    public void setStrCreateTime(String strCreateTime) {
        this.strCreateTime = strCreateTime;
    }

    public String getStrUpdateTime() {
        return strUpdateTime;
    }

    public void setStrUpdateTime(String strUpdateTime) {
        this.strUpdateTime = strUpdateTime;
    }
}
