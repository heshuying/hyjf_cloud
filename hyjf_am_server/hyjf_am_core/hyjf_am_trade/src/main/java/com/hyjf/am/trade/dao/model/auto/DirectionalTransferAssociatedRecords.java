package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class DirectionalTransferAssociatedRecords implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 转出账户用户名
     *
     * @mbggenerated
     */
    private String turnOutUsername;

    /**
     * 转出账户用户编号
     *
     * @mbggenerated
     */
    private Integer turnOutUserId;

    /**
     * 转出账户用户手机号
     *
     * @mbggenerated
     */
    private String turnOutMobile;

    /**
     * 转出账户用户汇付客户号
     *
     * @mbggenerated
     */
    private Long turnOutChinapnrUsrcustid;

    /**
     * 转入账户用户名
     *
     * @mbggenerated
     */
    private String shiftToUsername;

    /**
     * 转入账户用户编号
     *
     * @mbggenerated
     */
    private Integer shiftToUserId;

    /**
     * 转入账户用户手机号
     *
     * @mbggenerated
     */
    private String shiftToMobile;

    /**
     * 转入账户用户汇付客户号
     *
     * @mbggenerated
     */
    private Long shiftToChinapnrUsrcustid;

    /**
     * 关联状态(0初始，1成功，2失败)
     *
     * @mbggenerated
     */
    private Integer associatedState;

    /**
     * 关联时间
     *
     * @mbggenerated
     */
    private Date associatedTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTurnOutUsername() {
        return turnOutUsername;
    }

    public void setTurnOutUsername(String turnOutUsername) {
        this.turnOutUsername = turnOutUsername == null ? null : turnOutUsername.trim();
    }

    public Integer getTurnOutUserId() {
        return turnOutUserId;
    }

    public void setTurnOutUserId(Integer turnOutUserId) {
        this.turnOutUserId = turnOutUserId;
    }

    public String getTurnOutMobile() {
        return turnOutMobile;
    }

    public void setTurnOutMobile(String turnOutMobile) {
        this.turnOutMobile = turnOutMobile == null ? null : turnOutMobile.trim();
    }

    public Long getTurnOutChinapnrUsrcustid() {
        return turnOutChinapnrUsrcustid;
    }

    public void setTurnOutChinapnrUsrcustid(Long turnOutChinapnrUsrcustid) {
        this.turnOutChinapnrUsrcustid = turnOutChinapnrUsrcustid;
    }

    public String getShiftToUsername() {
        return shiftToUsername;
    }

    public void setShiftToUsername(String shiftToUsername) {
        this.shiftToUsername = shiftToUsername == null ? null : shiftToUsername.trim();
    }

    public Integer getShiftToUserId() {
        return shiftToUserId;
    }

    public void setShiftToUserId(Integer shiftToUserId) {
        this.shiftToUserId = shiftToUserId;
    }

    public String getShiftToMobile() {
        return shiftToMobile;
    }

    public void setShiftToMobile(String shiftToMobile) {
        this.shiftToMobile = shiftToMobile == null ? null : shiftToMobile.trim();
    }

    public Long getShiftToChinapnrUsrcustid() {
        return shiftToChinapnrUsrcustid;
    }

    public void setShiftToChinapnrUsrcustid(Long shiftToChinapnrUsrcustid) {
        this.shiftToChinapnrUsrcustid = shiftToChinapnrUsrcustid;
    }

    public Integer getAssociatedState() {
        return associatedState;
    }

    public void setAssociatedState(Integer associatedState) {
        this.associatedState = associatedState;
    }

    public Date getAssociatedTime() {
        return associatedTime;
    }

    public void setAssociatedTime(Date associatedTime) {
        this.associatedTime = associatedTime;
    }
}