/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 返回给前端的VO
 * @author jun
 * @version AdminTransferExceptionLogCustomizeVO, v0.1 2018/7/10 10:38
 */
public class AdminTransferExceptionLogAPIVO extends TransferExceptionLogVO implements Serializable {


    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "交易状态")
    private String tradeStatus;

    @ApiModelProperty(value = "添加时间")
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
