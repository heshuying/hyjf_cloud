/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author: zdj
 * @version: AccountExceptionVO, v0.1 2018/7/11 15:10
 */
@ApiModel(value = "账户异常返回值")
public class AccountExceptionVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "客户号")
    private Long customId;
    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "平台可用金额")
    private BigDecimal balancePlat;
    @ApiModelProperty(value = "平台冻结金额")
    private BigDecimal frostPlat;
    @ApiModelProperty(value = "汇付可用金额")
    private BigDecimal balanceHuifu;
    @ApiModelProperty(value = "汇付冻结金额")
    private BigDecimal frostHuifu;
    @ApiModelProperty(value = "创建时间")
    private Integer createTime;
    @ApiModelProperty(value = "角色")
    private String role;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getCustomId() {
        return customId;
    }

    public void setCustomId(Long customId) {
        this.customId = customId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getBalancePlat() {
        return balancePlat;
    }

    public void setBalancePlat(BigDecimal balancePlat) {
        this.balancePlat = balancePlat;
    }

    public BigDecimal getFrostPlat() {
        return frostPlat;
    }

    public void setFrostPlat(BigDecimal frostPlat) {
        this.frostPlat = frostPlat;
    }

    public BigDecimal getBalanceHuifu() {
        return balanceHuifu;
    }

    public void setBalanceHuifu(BigDecimal balanceHuifu) {
        this.balanceHuifu = balanceHuifu;
    }

    public BigDecimal getFrostHuifu() {
        return frostHuifu;
    }

    public void setFrostHuifu(BigDecimal frostHuifu) {
        this.frostHuifu = frostHuifu;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
