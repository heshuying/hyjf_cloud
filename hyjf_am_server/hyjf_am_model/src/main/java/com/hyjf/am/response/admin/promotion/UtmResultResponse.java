package com.hyjf.am.response.admin.promotion;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/14 14:43
 * @Description: UtmResultResponse
 */
public class UtmResultResponse<T>{
    public static final String SUCCESS = "000";
    public static final String SUCCESS_DESC = "成功";
    public static final String ERROR = "-1";
    public static final String ERROR_DESC = "异常";
    public static final String FAIL = "1";
    public static final String NOUSER = "99";
    public static final String FAIL_DESC = "失败";
    public static final String NO_PERMISSION = "2";
    public static final String NO_PERMISSION_DESC = "无权限";

    private String status = SUCCESS;
    private String statusDesc = SUCCESS_DESC;

    private List<T> resultList;
    private String url;
    private String utmReferrer;
    private T data;
    private Integer total;
    private List<T> utmPlatList;

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUtmReferrer() {
        return utmReferrer;
    }

    public void setUtmReferrer(String utmReferrer) {
        this.utmReferrer = utmReferrer;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<T> getUtmPlatList() {
        return utmPlatList;
    }

    public void setUtmPlatList(List<T> utmPlatList) {
        this.utmPlatList = utmPlatList;
    }
}
