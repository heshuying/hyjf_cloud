package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.trade.repay.BankRepayOrgFreezeLogVO;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author hesy
 * @version BankRepayFreezeOrgCheckRequestBean, v0.1 2018/10/19 16:11
 */
public class BankRepayFreezeOrgProcessRequestBean extends BankRepayOrgFreezeLogVO {
    @ApiModelProperty(value = "冻结状态 0-正常 1-已撤销")
    private String state;
    @ApiModelProperty(value = "银行返回码")
    private String retCode;
    @ApiModelProperty(value = "添加时间int类型")
    private Integer createTimeInt;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Integer getCreateTimeInt() {
        return createTimeInt;
    }

    public void setCreateTimeInt(Integer createTimeInt) {
        this.createTimeInt = createTimeInt;
    }
}
