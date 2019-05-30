/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;

/**
 * @author PC-LIUSHOUYI
 * @version DuibaPointsUserVO, v0.1 2019/5/29 16:50
 */
public class DuibaPointsUserVO implements Serializable {
    private String userId;
    private String userName;
    private String trueName;
    private Integer pointsTotal;
    private Integer pointsCurrent;

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

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public Integer getPointsTotal() {
        return pointsTotal;
    }

    public void setPointsTotal(Integer pointsTotal) {
        this.pointsTotal = pointsTotal;
    }

    public Integer getPointsCurrent() {
        return pointsCurrent;
    }

    public void setPointsCurrent(Integer pointsCurrent) {
        this.pointsCurrent = pointsCurrent;
    }
}
