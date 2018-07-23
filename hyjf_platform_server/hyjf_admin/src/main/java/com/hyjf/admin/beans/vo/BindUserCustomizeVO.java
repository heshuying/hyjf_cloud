package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author NXL
 * @version UserBankOpenAccountVO, v0.1 2018/6/22 09:52
 * 第三方平台用户绑定
 */
public class BindUserCustomizeVO extends BaseVO implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "用户ID")
    private Integer userId;

    @ApiModelProperty(value = "绑定唯一id（第三方提供）")
    private Long bindUniqueId;

    @ApiModelProperty(value = "绑定用户第三方平台编号")
    private Integer bindPlatformId;

    @ApiModelProperty(value = "删除标识")
    private Boolean delFlg;

    @ApiModelProperty(value = "创建用户")
    private Integer createUserId;
    @ApiModelProperty(value = "添加时间")
    private Date createTime;

    @ApiModelProperty(value = "更新用户")
    private Integer updateUserId;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

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

    public Long getBindUniqueId() {
        return bindUniqueId;
    }

    public void setBindUniqueId(Long bindUniqueId) {
        this.bindUniqueId = bindUniqueId;
    }

    public Integer getBindPlatformId() {
        return bindPlatformId;
    }

    public void setBindPlatformId(Integer bindPlatformId) {
        this.bindPlatformId = bindPlatformId;
    }

    public Boolean getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}