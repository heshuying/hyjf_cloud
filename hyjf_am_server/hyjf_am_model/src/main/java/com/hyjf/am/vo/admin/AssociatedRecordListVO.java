/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author: sunpeikai
 * @version: AssociatedRecordListVO, v0.1 2018/7/5 14:27
 */
@ApiModel(value = "关联记录返回值")
public class AssociatedRecordListVO extends BaseVO {
    @ApiModelProperty(value = "主键ID")
    private Integer id;
    @ApiModelProperty(value = "转出账户")
    private String turnOutUsername;
    @ApiModelProperty(value = "转出账户id")
    private Integer turnOutUserId;
    @ApiModelProperty(value = "转出账户手机")
    private String turnOutMobile;
    @ApiModelProperty(value = "转出账户客户号")
    private Long turnOutChinapnrUsrcustid;
    @ApiModelProperty(value = "转入账户")
    private String shiftToUsername;
    @ApiModelProperty(value = "转入账户id")
    private Integer shiftToUserId;
    @ApiModelProperty(value = "转入账户手机")
    private String shiftToMobile;
    @ApiModelProperty(value = "转入账户客户号")
    private Long shiftToChinapnrUsrcustid;
    @ApiModelProperty(value = "关联状态")
    private Integer associatedState;
    @ApiModelProperty(value = "关联时间")
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
