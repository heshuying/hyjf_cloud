/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author tany
 * @version UserLargeScreenRequest, v0.1 2019/3/18 16:21
 */
public class UserLargeScreenRequest extends BasePage implements Serializable {

    String taskTime;

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
