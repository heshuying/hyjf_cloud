package com.hyjf.cs.user.controller.wechat.base;

import java.io.Serializable;
public class BaseRequestBean implements Serializable {

    private static final long serialVersionUID = -4492942282166034094L;

    // 当前第几页
    private Integer page;
    // 每页显示多少条
    private Integer pageSize=15;
    // 排序
    private String order;
    // 根据sign获取用户信息
    private String sign;
    // 版本
    private String version;
    
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getPageSize() {
        return pageSize;
    }
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    public String getOrder() {
        return order;
    }
    public void setOrder(String order) {
        this.order = order;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
}
