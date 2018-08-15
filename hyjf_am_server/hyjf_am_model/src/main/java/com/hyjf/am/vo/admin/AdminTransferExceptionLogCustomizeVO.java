/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import java.io.Serializable;

/**
 * @author jun
 * @version AdminTransferExceptionLogCustomizeVO, v0.1 2018/7/10 10:38
 */
public class AdminTransferExceptionLogCustomizeVO extends TransferExceptionLogVO implements Serializable {


    private static final long serialVersionUID = 1L;

    //用户名
    private String username;

    //交易类型
    private String tradeType;

    //交易状态
    private String tradeStatus;

    //添加时间
    private String addTimeView;

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

    public String getAddTimeView() {
        return addTimeView;
    }

    public void setAddTimeView(String addTimeView) {
        this.addTimeView = addTimeView;
    }

}
