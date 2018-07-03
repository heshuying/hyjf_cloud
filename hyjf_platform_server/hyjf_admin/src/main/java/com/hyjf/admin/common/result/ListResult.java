/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.common.result;

import java.util.List;

/**
 * @author liubin
 * @version ListResult, v0.1 2018/7/2 19:42
 */
public class ListResult<T> {

    private List<T> list;

    private Long count;

    public static <T> ListResult<T> build(List<T> list,Long count) {
        ListResult<T> result = new ListResult<>();
        result.setCount(count);
        result.setList(list);
        return result;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ListResult [list=" + list + ", count=" + count + "]";
    }
}
