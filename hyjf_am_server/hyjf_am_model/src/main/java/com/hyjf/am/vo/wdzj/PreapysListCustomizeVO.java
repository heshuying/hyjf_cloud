package com.hyjf.am.vo.wdzj;

/**
 * @author hesy
 * @version PreapysListCustomizeVO, v0.1 2018/7/16 12:18
 */
public class PreapysListCustomizeVO {
    String projectId;
    int deadline;
    String deadlineUnit;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public String getDeadlineUnit() {
        return deadlineUnit;
    }

    public void setDeadlineUnit(String deadlineUnit) {
        this.deadlineUnit = deadlineUnit;
    }
}
