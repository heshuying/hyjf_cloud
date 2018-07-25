package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.admin.CategoryVO;
import com.hyjf.am.vo.admin.ContentHelpCustomizeVO;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:36
 * @Description: CategoryBeanRequest
 */
public class CategoryBeanRequest extends BasePage {
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

    private Integer delType;

    //页面赋值用
    private String ids;

    private Integer id;

    private String title;

    private Integer pid;

    private String code;

    private String group;

    private Integer sort;

    private Integer hide;

    private String tip;

    private Integer level;
    
    //是否只显示二级菜单 false:只显示二级菜单
    private boolean isShow;

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

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getHide() {
        return hide;
    }

    public void setHide(Integer hide) {
        this.hide = hide;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public boolean getIsShow() {
        return isShow;
    }

    public void setIsShow(boolean show) {
        isShow = show;
    }

    public Integer getDelType() {
        return delType;
    }

    public void setDelType(Integer delType) {
        this.delType = delType;
    }
}
