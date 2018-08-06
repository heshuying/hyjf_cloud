package com.hyjf.cs.trade.bean.app;

import com.hyjf.am.vo.app.AppTradeListCustomizeVO;

import java.util.List;

/**
 * @author pangchengchao
 * @version AppTradeDetailBean, v0.1 2018/8/6 14:30
 */
public class AppTradeDetailBean {
    private String status;
    private String statusDesc;
    private String request;
    private Integer tradeTotal;
    private List<AppTradeListCustomizeVO> userTrades;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getTradeTotal() {
        return tradeTotal;
    }

    public void setTradeTotal(Integer tradeTotal) {
        this.tradeTotal = tradeTotal;
    }

    public List<AppTradeListCustomizeVO> getUserTrades() {
        return userTrades;
    }

    public void setUserTrades(List<AppTradeListCustomizeVO> userTrades) {
        this.userTrades = userTrades;
    }
}
