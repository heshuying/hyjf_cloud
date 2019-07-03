package com.hyjf.wbs.qvo.csuser;

import java.io.Serializable;


public class BaseResultBean implements Serializable {

    private static final long serialVersionUID = -2899752944266497051L;

    private String status;
    private String statusDesc;
    private String request;
    //是否最后一页0不是最后一页  1为最后一页
    private Integer endPage;


    private Integer currentPage;//当前页
    private Integer pageSize;//每页显示条数
    // 角色是否允许出借
    private Boolean isAllowedTender;
    // 缴费授权状态
    private Integer paymentAuthStatus;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }
    public Boolean getIsAllowedTender() {
        return isAllowedTender;
    }

    public void setIsAllowedTender(Boolean isAllowedTender) {
        this.isAllowedTender = isAllowedTender;
    }

    public Integer getPaymentAuthStatus() {
        return paymentAuthStatus;
    }

    public void setPaymentAuthStatus(Integer paymentAuthStatus) {
        this.paymentAuthStatus = paymentAuthStatus;
    }

    public BaseResultBean() {
        this.status = ResultEnum.SUCCESS.getStatus();
        this.statusDesc = ResultEnum.SUCCESS.getStatusDesc();
    }

    public BaseResultBean(String status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    public BaseResultBean(ResultEnum item) {
        this.status = item.getStatus();
        this.statusDesc = item.getStatusDesc();
    }

    public BaseResultBean(String request) {
        super();
        this.status = ResultEnum.FAIL.getStatus();
        this.setRequest(request);
    }

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

    // 设置枚举
    public BaseResultBean setEnum(ResultEnum item) {
        this.status = item.getStatus();
        this.statusDesc = item.getStatusDesc();
        return this;
    }

    /**
     * request
     *
     * @return the request
     */

    public String getRequest() {
        return request;

    }

    /**
     * @param request
     *            the request to set
     */

    public void setRequest(String request) {
        this.request = request;

    }
}
