package com.hyjf.am.resquest.hgreportdata.cert;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author pangchengchao
 * @version CertRequest, v0.1 2019/1/24 17:14
 */
public class CertRequest implements Serializable {
    private String minId;
    private String maxId;

    private List<String> tradeList;
    private String borrowNid;
    private String nid;
    private Date repayYestime;
    private String transferId;
    private String realTenderId;
    private String couponTenderId;
    private String repayOrdid;
    private String investOrderId;
    private String trade;
    private Integer period;

    private Integer limitStart;
    private Integer limitEnd;

    public String getMinId() {
        return minId;
    }

    public void setMinId(String minId) {
        this.minId = minId;
    }

    public String getMaxId() {
        return maxId;
    }

    public void setMaxId(String maxId) {
        this.maxId = maxId;
    }

    public List<String> getTradeList() {
        return tradeList;
    }

    public void setTradeList(List<String> tradeList) {
        this.tradeList = tradeList;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public Date getRepayYestime() {
        return repayYestime;
    }

    public void setRepayYestime(Date repayYestime) {
        this.repayYestime = repayYestime;
    }

    public String getCouponTenderId() {
        return couponTenderId;
    }

    public void setCouponTenderId(String couponTenderId) {
        this.couponTenderId = couponTenderId;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getRepayOrdid() {
        return repayOrdid;
    }

    public void setRepayOrdid(String repayOrdid) {
        this.repayOrdid = repayOrdid;
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getRealTenderId() {
        return realTenderId;
    }

    public void setRealTenderId(String realTenderId) {
        this.realTenderId = realTenderId;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
}
