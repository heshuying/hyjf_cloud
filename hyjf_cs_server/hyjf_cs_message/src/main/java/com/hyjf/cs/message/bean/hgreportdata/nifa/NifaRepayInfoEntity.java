/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.hgreportdata.nifa;

import com.hyjf.cs.message.bean.hgreportdata.BaseHgDataReportEntity;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合规数据上报 NIFA 还款信息上报
 * @author liubin
 * @version BaseHgDataReportEntity, v0.1 2018/6/27 10:06
 */
@Document(collection = "ht_nifa_repay_info")
public class NifaRepayInfoEntity extends BaseHgDataReportEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    // 标的编号
    private String borrowNid;

    // 还款期数
    private String repayPeriod;

    // 还款金额
    private BigDecimal repayAccount;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    @Override
    public String toString() {
        return "{" +
                "borrowNid='" + borrowNid + '\'' +
                ", repayPeriod='" + repayPeriod + '\'' +
                ", repayAccount=" + repayAccount +
                '}';
    }
}
