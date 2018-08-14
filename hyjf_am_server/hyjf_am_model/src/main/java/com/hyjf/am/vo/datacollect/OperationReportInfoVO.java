package com.hyjf.am.vo.datacollect;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tanyy
 * @version OperationReportInfoVO, v0.1 2018/7/23 16:38
 */
public class OperationReportInfoVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = 1L;

    public String title;//标题
    public BigDecimal sumAccount;//总金额
    public String dealMonth;//交易月份
    public Integer dealSum;//成交笔数
    public Integer userId;//用户id
    public String userName;//用户名

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getSumAccount() {
        return sumAccount;
    }

    public void setSumAccount(BigDecimal sumAccount) {
        this.sumAccount = sumAccount;
    }

    public String getDealMonth() {
        return dealMonth;
    }

    public void setDealMonth(String dealMonth) {
        this.dealMonth = dealMonth;
    }

    public Integer getDealSum() {
        return dealSum;
    }

    public void setDealSum(Integer dealSum) {
        this.dealSum = dealSum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
