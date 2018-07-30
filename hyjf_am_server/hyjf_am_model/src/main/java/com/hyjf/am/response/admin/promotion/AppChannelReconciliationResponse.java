package com.hyjf.am.response.admin.promotion;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 16:43
 * @Description: AppChannelReconciliationResponse
 */
public class AppChannelReconciliationResponse<T> {
    public static final String SUCCESS = "000";
    public static final String SUCCESS_DESC = "成功";
    public static final String ERROR = "-1";
    public static final String ERROR_DESC = "异常";
    public static final String FAIL = "1";
    public static final String FAIL_DESC = "失败";
    public static final String NO_PERMISSION = "2";
    public static final String NO_PERMISSION_DESC = "无权限";

    private String status = SUCCESS;
    private String statusDesc = SUCCESS_DESC;
    private List<T> resultList;
    private T data;

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

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
