package com.hyjf.am.response.config;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/27 09:09
 * @Description: ShareNewsResponse
 */
public class ShareNewsResponse<T> {
    private T data;

    private List<T> resultList;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }
}
