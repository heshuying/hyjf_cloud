/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.result;

import com.hyjf.cs.common.util.Page;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liubin
 * @version ListResult, v0.1 2018/7/2 19:42
 */
public class ListResult<T> {

    @ApiModelProperty(value = "列表数据")
    private List<T> list;
    @ApiModelProperty(value = "数据")
    private T data;
    @ApiModelProperty(value = "列表count")
    private int count;
    @ApiModelProperty(value = "page")
    private Page page;

    public static <T> ListResult<T> build(List<T> list,int count) {
        ListResult<T> result = new ListResult<>();
        result.setCount(count);
        result.setList(list);
        return result;
    }

    public static <T> ListResult<T> build2(List<T> list, int count, T data) {
        ListResult<T> result = new ListResult<>();
        result.setCount(count);
        result.setList(list);
        result.setData(data);
        return result;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "ListResult{" +
                "list=" + list +
                ", data=" + data +
                ", count=" + count +
                ", page=" + page +
                '}';
    }
}
