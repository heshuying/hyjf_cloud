/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.BaseVO;

import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordListVo, v0.1 2018/7/5 14:27
 */
public class AssociatedRecordListVo extends BaseVO {
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
        this.turnOutUsername = turnOutUsername;
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
        this.turnOutMobile = turnOutMobile;
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
        this.shiftToUsername = shiftToUsername;
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
        this.shiftToMobile = shiftToMobile;
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
