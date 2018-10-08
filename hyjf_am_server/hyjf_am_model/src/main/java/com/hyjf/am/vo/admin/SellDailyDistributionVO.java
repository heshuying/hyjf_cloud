package com.hyjf.am.vo.admin;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author lisheng
 * @version SellDailyDistributionVO, v0.1 2018/10/8 11:26
 */

public class SellDailyDistributionVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String businessName;

    private String email;

    private Integer timePoint;

    private String time;

    private Integer status;

    private Date createTime;

    private String createName;

    private Date updateTime;

    private String updateName;

    private String addtime;

    private String formatUpdatetime;

    private List<String> emails;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Integer timePoint) {
        this.timePoint = timePoint;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getFormatUpdatetime() {
        return formatUpdatetime;
    }

    public void setFormatUpdatetime(String formatUpdatetime) {
        this.formatUpdatetime = formatUpdatetime;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
