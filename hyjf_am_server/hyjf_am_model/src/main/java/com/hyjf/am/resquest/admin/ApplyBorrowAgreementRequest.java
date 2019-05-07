package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.common.paginator.Paginator;
import io.swagger.annotations.ApiModelProperty;

/**
 * @version ApplyAgreementRequest, v0.1 2018/8/9 17:05
 * @Author: Zha Daojian
 */
public class ApplyBorrowAgreementRequest extends BasePage {

    /**
     * 检索条件 项目编号
     */
    @ApiModelProperty(value = "项目编号")
    private String borrowNid;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请人")
    private String applyUserName;

    /**
     * 申请人
     */
    @ApiModelProperty(value = "申请状态（0 初始；1成功）")
    private String status;

    /**
     * 检索条件 申请时间开始
     */
    @ApiModelProperty(value = "申请时间开始")
    private String timeStart;

    /**
     * 检索条件 申请时间结束
     */
    @ApiModelProperty(value = "申请时间结束")
    private String timeEnd;

    public Integer limit;

    public Integer limitStart;

    public Integer limitEnd;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
}
