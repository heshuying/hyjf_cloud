/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单渠道修改-查询订单信息
 * @author cui
 * @version BorrowInvestCustomizeExtVO, v0.1 2019/6/14 17:17
 */
public class BorrowInvestCustomizeExtVO{

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    //出借时间格式化
    private String createTimeStr;


    /**
     * 投资人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 投资人用户属性
     *
     * @mbggenerated
     */
    private Integer tenderUserAttribute;

    /**
     * 推荐人用户属性
     *
     * @mbggenerated
     */
    private Integer inviteUserAttribute;


    /**
     * 一级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteRegionId;

    /**
     * 一级部门名称(投资时)
     *
     * @mbggenerated
     */
    private String inviteRegionName;

    /**
     * 二级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteBranchId;

    /**
     * 二级部门名称(投资时)
     *
     * @mbggenerated
     */
    private String inviteBranchName;

    /**
     * 三级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteDepartmentId;

    /**
     * 三级部门名称(投资时)
     *
     * @mbggenerated
     */
    private String inviteDepartmentName;



    //出借时渠道
    private String utmName;

    //当前渠道
    private String utmNameNow;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTenderUserAttribute() {
        return tenderUserAttribute;
    }

    public void setTenderUserAttribute(Integer tenderUserAttribute) {
        this.tenderUserAttribute = tenderUserAttribute;
    }

    public Integer getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(Integer inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public Integer getInviteRegionId() {
        return inviteRegionId;
    }

    public void setInviteRegionId(Integer inviteRegionId) {
        this.inviteRegionId = inviteRegionId;
    }

    public String getInviteRegionName() {
        return inviteRegionName;
    }

    public void setInviteRegionName(String inviteRegionName) {
        this.inviteRegionName = inviteRegionName;
    }

    public Integer getInviteBranchId() {
        return inviteBranchId;
    }

    public void setInviteBranchId(Integer inviteBranchId) {
        this.inviteBranchId = inviteBranchId;
    }

    public String getInviteBranchName() {
        return inviteBranchName;
    }

    public void setInviteBranchName(String inviteBranchName) {
        this.inviteBranchName = inviteBranchName;
    }

    public Integer getInviteDepartmentId() {
        return inviteDepartmentId;
    }

    public void setInviteDepartmentId(Integer inviteDepartmentId) {
        this.inviteDepartmentId = inviteDepartmentId;
    }

    public String getInviteDepartmentName() {
        return inviteDepartmentName;
    }

    public void setInviteDepartmentName(String inviteDepartmentName) {
        this.inviteDepartmentName = inviteDepartmentName;
    }

    public String getUtmNameNow() {
        return utmNameNow;
    }

    public void setUtmNameNow(String utmNameNow) {
        this.utmNameNow = utmNameNow;
    }

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }
}
