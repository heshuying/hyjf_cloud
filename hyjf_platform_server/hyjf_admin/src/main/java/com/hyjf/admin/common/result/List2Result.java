/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.result;

import com.hyjf.admin.utils.Page;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author liubin
 * @version List2Result, v0.1 2018/7/2 19:42
 */
public class List2Result<T, V> {

    @ApiModelProperty(value = "列表数据")
    private List<T> list;
    @ApiModelProperty(value = "列表count")
    private int count;
    @ApiModelProperty(value = "page")
    private Page page;
    @ApiModelProperty(value = "列表总计")
    private T sum;
    @ApiModelProperty(value = "其他数据")
    private V data;

    public static <T, V> List2Result<T, V> build(List<T> list, int count) {
        List2Result<T, V> result = new List2Result<>();
        result.setCount(count);
        result.setList(list);
        return result;
    }

    public static <T, V> List2Result<T, V> build(List<T> list, int count, V data) {
        List2Result<T, V> result = new List2Result<>();
        result.setCount(count);
        result.setList(list);
        result.setData(data);
        return result;
    }

    public static <T, V> List2Result<T, V> build(List<T> list, int count, T sum, V data) {
        List2Result<T, V> result = new List2Result<>();
        result.setCount(count);
        result.setList(list);
        result.setSum(sum);
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

    public V getData() {
        return data;
    }

    public void setData(V data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public T getSum() {
        return sum;
    }

    public void setSum(T sum) {
        this.sum = sum;
    }

    @Override
    public String toString() {
        return "List2Result{" +
                "list=" + list +
                ", count=" + count +
                ", page=" + page +
                ", sum=" + sum +
                ", data=" + data +
                '}';
    }
}
