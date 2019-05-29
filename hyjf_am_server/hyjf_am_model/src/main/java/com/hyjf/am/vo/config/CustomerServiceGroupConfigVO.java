/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * 电销数据-客组配置VO
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigVO, v0.1 2019/5/29 10:30
 */
@ApiModel(value="客组配置",description="电销数据-客组配置")
public class CustomerServiceGroupConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -143241833828005340L;

    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "客组名称")
    private String groupName;

    @ApiModelProperty(value = "第三方用户唯一凭证")
    private String serviceUserCode;

    @ApiModelProperty(value = "第三方用户账户编号")
    private String serviceUserNo;

    @ApiModelProperty(value = "第三方用户唯一凭证密钥")
    private String serviceUserKey;

    @ApiModelProperty(value = "启用状态 1.启用2.禁用")
    private Integer status;

    @ApiModelProperty(value = "创建人")
    private Integer createUserId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getServiceUserCode() {
        return serviceUserCode;
    }

    public void setServiceUserCode(String serviceUserCode) {
        this.serviceUserCode = serviceUserCode == null ? null : serviceUserCode.trim();
    }

    public String getServiceUserNo() {
        return serviceUserNo;
    }

    public void setServiceUserNo(String serviceUserNo) {
        this.serviceUserNo = serviceUserNo == null ? null : serviceUserNo.trim();
    }

    public String getServiceUserKey() {
        return serviceUserKey;
    }

    public void setServiceUserKey(String serviceUserKey) {
        this.serviceUserKey = serviceUserKey == null ? null : serviceUserKey.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
