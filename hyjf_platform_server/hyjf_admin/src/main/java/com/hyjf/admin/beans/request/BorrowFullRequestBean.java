/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wangjun
 * @version BorrowFullRequestBean, v0.1 2018/7/6 9:39
 */
public class BorrowFullRequestBean extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "借款编号(所有接口需要传borrowNid时都在这个字段赋值)")
    private String borrowNidSrch;

    @ApiModelProperty(value = "复审查询用:用户名")
    private String usernameSrch;

    @ApiModelProperty(value = "复审查询用:满标时间-开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "复审查询用:满标时间-结束")
    private String timeEndSrch;

    @ApiModelProperty(value = "复审提交用:复审备注")
    private String reverifyRemark;

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getUsernameSrch() {
        return usernameSrch;
    }

    public void setUsernameSrch(String usernameSrch) {
        this.usernameSrch = usernameSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark;
    }
}
