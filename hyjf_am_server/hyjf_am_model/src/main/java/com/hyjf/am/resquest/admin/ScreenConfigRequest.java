package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * Created by future on 2019/3/18.
 */
public class ScreenConfigRequest extends BasePage implements Serializable {

    /**
     * 任务时间,精确到月  yyyy-mm
     *
     * @mbggenerated
     */
    private String taskTime;

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }
}
