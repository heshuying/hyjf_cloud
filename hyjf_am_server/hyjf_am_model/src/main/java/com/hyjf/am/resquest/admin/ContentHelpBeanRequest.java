package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;
import com.hyjf.am.vo.admin.ContentHelpVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/24 11:20
 * @Description: ContentHelpBeanRequest
 */
public class ContentHelpBeanRequest extends ContentHelpVO {
    private Integer delType;

    /**
     * 给文章替换的新的id
     */
    private Integer newid;
    /**
     * 给文章替换的新的pid
     */
    private Integer newpid;

    // 区间查询添加时间开始时间
    private String post_time_begin;

    // 区间查询添加时间结束时间
    private String post_time_end;

    private List<CategoryVO> recordList;

    private List<ContentHelpCustomizeVO> helpList;

    //当前页码
    private int currPage;
    //当前页条数
    private int pageSize;

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getDelType() {
        return delType;
    }

    public void setDelType(Integer delType) {
        this.delType = delType;
    }

    public Integer getNewid() {
        return newid;
    }

    public void setNewid(Integer newid) {
        this.newid = newid;
    }

    public Integer getNewpid() {
        return newpid;
    }

    public void setNewpid(Integer newpid) {
        this.newpid = newpid;
    }

    public String getPost_time_begin() {
        return post_time_begin;
    }

    public void setPost_time_begin(String post_time_begin) {
        this.post_time_begin = post_time_begin;
    }

    public String getPost_time_end() {
        return post_time_end;
    }

    public void setPost_time_end(String post_time_end) {
        this.post_time_end = post_time_end;
    }

    public List<CategoryVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<CategoryVO> recordList) {
        this.recordList = recordList;
    }

    public List<ContentHelpCustomizeVO> getHelpList() {
        return helpList;
    }

    public void setHelpList(List<ContentHelpCustomizeVO> helpList) {
        this.helpList = helpList;
    }
}
