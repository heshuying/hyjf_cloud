package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

public class BorrowCreditRequest extends BaseRequest {

    //用户名
    private String userName;
    // 债转编号
    private String creditNid;
    // 项目编号
    private String bidNid;
    // 债转状态
    private String creditStatus;
    // 添加时间 开始
    private String timeStart;
    // 添加时间 结束
    private String timeEnd;

    /**
     * 标注，调用接口是初次调用，还是点击搜索按钮(1:是  0:否)
     */
    private String isSearch;

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
}
