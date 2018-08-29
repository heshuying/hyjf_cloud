/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: sunpeikai
 * @version: DirectionalTransferAssociatedLog, v0.1 2018/8/29 19:30
 */
@Document(collection = "ht_directional_transfer_associated_log")
public class DirectionalTransferAssociatedLog implements Serializable {

    private Integer id;

    private String turnOutUsername;

    private Integer turnOutUserId;

    private String turnOutMobile;

    private Long turnOutChinapnrUsrcustid;

    private String shiftToUsername;

    private Integer shiftToUserId;

    private String shiftToMobile;

    private Long shiftToChinapnrUsrcustid;

    private Integer associatedState;

    private Date associatedTime;

    private String remark;

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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
