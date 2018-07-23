/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 * @version UserRecommendCustomizeShowVO, v0.1 2018/6/21 21:38
 */
public class UserRecommendCustomizeShowVO extends BaseVO implements Serializable {
    /** 用户id */
    @ApiModelProperty(value = "用户id")
    private String userId;
    /** 用户名 */
    @ApiModelProperty(value = "用户名")
    public String userName;
    /** 推荐人 */
    @ApiModelProperty(value = "用户id")
    public String recommendName;
    /** 说明 */
    @ApiModelProperty(value = "说明")
    public String remark;
    /** ip */
    @ApiModelProperty(value = "ip")
    public String ip;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "姓名")
    private String trueName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }
}
