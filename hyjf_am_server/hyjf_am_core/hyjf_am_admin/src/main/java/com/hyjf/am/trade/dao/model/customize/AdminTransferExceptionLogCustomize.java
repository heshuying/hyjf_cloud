package com.hyjf.am.trade.dao.model.customize;

import com.hyjf.am.trade.dao.model.auto.TransferExceptionLog;

public class AdminTransferExceptionLogCustomize extends TransferExceptionLog {

    private static final long serialVersionUID = 1L;

    //用户名
    private String username;

    //交易类型
    private String tradeType;

    //交易状态
    private String tradeStatus;

    //添加时间
    private String createTimeView;

    private Integer updatetime;

    public Integer getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Integer updatetime) {
        this.updatetime = updatetime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getCreateTimeView() {
        return createTimeView;
    }

    public void setCreateTimeView(String createTimeView) {
        this.createTimeView = createTimeView;
    }
}
