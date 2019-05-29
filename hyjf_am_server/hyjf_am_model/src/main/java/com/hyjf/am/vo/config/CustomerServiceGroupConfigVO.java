/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * 电销数据-客组配置VO
 *
 * @author liuyang
 * @version CustomerServiceGroupConfigVO, v0.1 2019/5/29 10:30
 */
public class CustomerServiceGroupConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -143241833828005340L;
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 客组名称
     *
     * @mbggenerated
     */
    private String groupName;

    /**
     * 第三方用户唯一凭证
     *
     * @mbggenerated
     */
    private String serviceUserCode;

    /**
     * 第三方用户账户编号
     *
     * @mbggenerated
     */
    private String serviceUserNo;

    /**
     * 第三方用户唯一凭证密钥
     *
     * @mbggenerated
     */
    private String serviceUserKey;

    /**
     * 启用状态 1.启用2.禁用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
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
