package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version BorrowRepayAgreementRequest, v0.1 2018/8/10 15:05
 * @Author: Zha Daojian
 */
public class BorrowRepayAgreementRequest extends BasePage {

    @ApiModelProperty(value = "借款编号(批量生成垫付债转协议时使用,'借款编号_期数'组合JSONArray)")
    private String ids;
    @ApiModelProperty(value = "借款编号")
    private String borrowNidSrch;

    @ApiModelProperty(value = "垫付时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "垫付时间结束")
    private String timeEndSrch;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }
}
