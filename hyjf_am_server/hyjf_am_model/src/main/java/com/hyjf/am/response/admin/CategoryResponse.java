package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:48
 * @Description: CategoryResponse
 */
public class CategoryResponse<T> extends Response {
    //查询总条数
    private Integer count;

    //返回数据
    private T data;

    //返回List
    private List<T> recordList;

    //返回父级List
    private List<T> parentList;

    //返回子级List
    private List<T> childList;

    //问题列表数据List
    private List<T> helpList;

    //菜单级别
    private String categoryLevel;

    //flag：新增或修改或删除结果标识
    private Integer flag;

    //flag：新增或修改或删除结果标识
    private boolean success;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<T> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<T> recordList) {
        this.recordList = recordList;
    }

    public List<T> getParentList() {
        return parentList;
    }

    public void setParentList(List<T> parentList) {
        this.parentList = parentList;
    }

    public List<T> getChildList() {
        return childList;
    }

    public void setChildList(List<T> childList) {
        this.childList = childList;
    }

    public String getCategoryLevel() {
        return categoryLevel;
    }

    public void setCategoryLevel(String categoryLevel) {
        this.categoryLevel = categoryLevel;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public List<T> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<T> helpList) {
        this.helpList = helpList;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
