/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 *
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by :
 */

package com.hyjf.cs.trade.bean;

import java.io.Serializable;


public class WechatHomeProjectList implements Serializable {

    /**
     *序列化id
     */
    private static final long serialVersionUID = 5748630051215873837L;
    // 项目id
    private String borrowNid;
    // 项目标题
    private String borrowName;
    // 项目类型
    private String borrowType;
    // 项目年华收益率
    private String borrowApr;
    // 项目期限
    private String borrowPeriod;
    // 项目期限计量单位
    private String borrowPeriodType;
    // 项目状态
    private String status;
    // 定时发标时间
    private String onTime;

    //剩余可投金额
    private String accountWait;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowPeriodType() {
        return borrowPeriodType;
    }

    public void setBorrowPeriodType(String borrowPeriodType) {
        this.borrowPeriodType = borrowPeriodType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getAccountWait() {
        return accountWait;
    }

    public void setAccountWait(String accountWait) {
        this.accountWait = accountWait;
    }


}
