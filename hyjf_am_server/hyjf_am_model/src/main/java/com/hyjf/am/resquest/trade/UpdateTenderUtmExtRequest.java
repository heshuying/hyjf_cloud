/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import java.util.Date;

/**
 * @author cui
 * @version UpdateTenderUtmExtRequest, v0.1 2019/6/17 16:51
 */
public class UpdateTenderUtmExtRequest extends UpdateTenderUtmRequest {

    /**
     * 一级分部
     *
     * @mbggenerated
     */
    private Integer topDeptId;

    private String topDeptName;

    /**
     * 二级分部
     *
     * @mbggenerated
     */
    private Integer secondDeptId;
    private String secondDeptName;

    /**
     * 三级分部id
     *
     * @mbggenerated
     */
    private Integer thirdDeptId;
    private String thirdDeptName;

    /**
     * 操作人
     *
     * @mbggenerated
     */
    private Integer operator;

    /**
     * 操作时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    public Integer getTopDeptId() {
        return topDeptId;
    }

    public void setTopDeptId(Integer topDeptId) {
        this.topDeptId = topDeptId;
    }

    public Integer getSecondDeptId() {
        return secondDeptId;
    }

    public void setSecondDeptId(Integer secondDeptId) {
        this.secondDeptId = secondDeptId;
    }

    public Integer getThirdDeptId() {
        return thirdDeptId;
    }

    public void setThirdDeptId(Integer thirdDeptId) {
        this.thirdDeptId = thirdDeptId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTopDeptName() {
        return topDeptName;
    }

    public void setTopDeptName(String topDeptName) {
        this.topDeptName = topDeptName;
    }

    public String getSecondDeptName() {
        return secondDeptName;
    }

    public void setSecondDeptName(String secondDeptName) {
        this.secondDeptName = secondDeptName;
    }

    public String getThirdDeptName() {
        return thirdDeptName;
    }

    public void setThirdDeptName(String thirdDeptName) {
        this.thirdDeptName = thirdDeptName;
    }
}
