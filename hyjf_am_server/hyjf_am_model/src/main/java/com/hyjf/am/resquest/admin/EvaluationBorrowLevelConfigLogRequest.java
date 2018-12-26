/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author liuyang
 * @version EvaluationBorrowLevelConfigLogRequest, v0.1 2018/12/25 17:22
 */
public class EvaluationBorrowLevelConfigLogRequest extends BasePage implements Serializable {


    @ApiModelProperty(value = "操作人")
    private String updateUserSrch;

    @ApiModelProperty(value = "开始时间")
    private String startTimeSrch;

    @ApiModelProperty(value = "结束时间")
    private String endTimeSrch;

    public int limit;

    public int limitStart;

    public int limitEnd;

    public String getUpdateUserSrch() {
        return updateUserSrch;
    }

    public void setUpdateUserSrch(String updateUserSrch) {
        this.updateUserSrch = updateUserSrch;
    }

    public String getStartTimeSrch() {
        return startTimeSrch;
    }

    public void setStartTimeSrch(String startTimeSrch) {
        this.startTimeSrch = startTimeSrch;
    }

    public String getEndTimeSrch() {
        return endTimeSrch;
    }

    public void setEndTimeSrch(String endTimeSrch) {
        this.endTimeSrch = endTimeSrch;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
