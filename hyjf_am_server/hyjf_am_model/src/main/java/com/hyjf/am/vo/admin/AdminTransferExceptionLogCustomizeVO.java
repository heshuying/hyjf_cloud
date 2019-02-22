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

    public String getCreateTimeView() {
        return createTimeView;
    }

    public void setCreateTimeView(String createTimeView) {
        this.createTimeView = createTimeView;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }



}
