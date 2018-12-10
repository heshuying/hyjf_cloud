/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yaoyong
 * @version UserOperationLogEntity, v0.1 2018/10/8 16:52
 *
 */
public class UserOperationLogEntityVO extends BaseVO implements Serializable {
    /**
     * 活动类型：1.登录 2登出 3.开户 4.出借确认 5.转让确认 6.修改交易密码 7.修改登录密码 8.绑定邮箱 9.修改邮箱 10.绑定银行卡 11.解绑银行卡 12.风险测评
     */
    @ApiModelProperty(value = "活动类型：1.登录 2登出 3.开户 4.出借确认 5.转让确认 6.修改交易密码 7.修改登录密码 8.绑定邮箱 9.修改邮箱 10.绑定银行卡 11.解绑银行卡 12.风险测评")
    private Integer operationType;

    /**
     *
     */
    @ApiModelProperty(value = "用户角色 : 1.出借人 2.借款人 3.担保机构")
    private String userRole;
    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "操作平台 : 0.PC 1.wechat 2.Andriod 3.IOS")
    private Integer platform;

    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "ip地址")
    private String ip;
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

    private static final long serialVersionUID = 1L;

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(Date operationTime) {
        this.operationTime = operationTime;
    }
}
