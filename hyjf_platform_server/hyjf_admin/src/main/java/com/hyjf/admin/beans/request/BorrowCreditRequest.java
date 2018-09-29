package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

public class BorrowCreditRequest extends BaseRequest {

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "债转编号")
    private String creditNid;

    @ApiModelProperty(value = "项目编号")
    private String bidNid;

    @ApiModelProperty(value = "债转状态")
    private String creditStatus;

    @ApiModelProperty(value = "添加时间 开始")
    private String timeStart;

    @ApiModelProperty(value = "添加时间 结束")
    private String timeEnd;

    private String client;

    /**
     * 标注，调用接口是初次调用，还是点击搜索按钮(1:是  0:否)
     */
    private String isSearch;

    @ApiModelProperty(value = "是否是导出 1:是;0:否")
    private String isExport;

    public String getIsSearch() {
        return isSearch;
    }

    public void setIsSearch(String isSearch) {
        this.isSearch = isSearch;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getIsExport() {
        return isExport;
    }

    public void setIsExport(String isExport) {
        this.isExport = isExport;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
