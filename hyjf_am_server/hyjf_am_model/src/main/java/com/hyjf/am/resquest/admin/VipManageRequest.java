/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yaoyong
 * @version VipManageRequest, v0.1 2018/7/2 15:13
 */
public class VipManageRequest extends BasePage {
    @ApiModelProperty(value = "注册开始时间")
    public String regTimeStart;
    @ApiModelProperty(value = "注册结束时间")
    public String regTimeEnd;
    @ApiModelProperty(value = "用户名")
    public String userName;
    @ApiModelProperty(value = "姓名")
    public String realName;
    @ApiModelProperty(value = "手机号")
    public String mobile;
    @ApiModelProperty(value = "推荐人")
    public String recommendName;
    @ApiModelProperty(value = "部门")
    public String combotreeListSrch;
    @ApiModelProperty(value = "用户角色")
    public String userRole;
    @ApiModelProperty(value = "用户属性")
    public String userProperty;
    @ApiModelProperty(value = "用户状态")
    public String userStatus;
    @ApiModelProperty(value = "是否51用户")
    public String is51;

    public String ids;

    public int limit;

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getRegTimeStart() {
        return regTimeStart;
    }

    public void setRegTimeStart(String regTimeStart) {
        this.regTimeStart = regTimeStart;
    }

    public String getRegTimeEnd() {
        return regTimeEnd;
    }

    public void setRegTimeEnd(String regTimeEnd) {
        this.regTimeEnd = regTimeEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getIs51() {
        return is51;
    }

    public void setIs51(String is51) {
        this.is51 = is51;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
