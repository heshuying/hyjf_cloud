package com.hyjf.am.resquest.trade;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author xiasq
 * @version SumTenderAmountRequest, v0.1 2019/5/5 11:18
 */
public class SumTenderAmountRequest  implements Serializable {

    private static final long serialVersionUID = 1L;
    private final DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int userId;
    private Date startDate;
    private Date endDate;
    private Integer client;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "SumTenderAmountRequest{" +
                "userId=" + userId +
                ", startDate=" + sdf.format(startDate) +
                ", endDate=" + sdf.format(endDate) +
                ", client=" + client +
                '}';
    }
}
