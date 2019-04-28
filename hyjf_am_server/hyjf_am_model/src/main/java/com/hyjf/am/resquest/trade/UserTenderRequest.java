package com.hyjf.am.resquest.trade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiasq
 * @version UserTenderRequest, v0.1 2019/4/28 14:10
 */
public class UserTenderRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Integer userId;

    private Date startDate;

    private Date endDate;

    public UserTenderRequest() {
    }

    public UserTenderRequest(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public UserTenderRequest(Integer userId, Date startDate, Date endDate) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }


    @Override
    public String toString() {
        return "UserTenderRequest{" +
                "userId=" + userId == null ? "" : userId +
                ", startDate=" + sdf.format(startDate) +
                ", endDate=" + sdf.format(endDate) +
                '}';
    }
}
