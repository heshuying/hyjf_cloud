package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

public class CustomerTaskConfigRequest extends BasePage implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务时间,精确到月 yyyy-mm
     *
     * @mbggenerated
     */
    private String taskTime;

    /**
     * 坐席分组 1:新客组,2:老客组
     *
     * @mbggenerated
     */
    private Integer customerGroup;

    /**
     * 坐席姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 是否有效 1:有效,2:无效
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 添加/编辑重复检验 标志 1:oper,2:task
     */
    private Integer flag;

    /**
     * 开始行
     * @return
     */
    private int limitStart;

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public Integer getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(Integer customerGroup) {
        this.customerGroup = customerGroup;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public CustomerTaskConfigRequest(String customerName) {
        this.customerName = customerName;
    }

    public CustomerTaskConfigRequest() {
    }
}
