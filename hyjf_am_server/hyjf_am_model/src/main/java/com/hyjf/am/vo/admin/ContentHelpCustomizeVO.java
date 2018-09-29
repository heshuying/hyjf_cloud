package com.hyjf.am.vo.admin;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 15:41
 * @Description: ContentHelpCustomizeVO
 */
public class ContentHelpCustomizeVO extends ContentHelpVO implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 分类名称
     */
    private String catetile;

    /**
     * 旧分类ID
     */
    private Integer cateID;

    /**
     * 区间查询添加时间开始时间
     */
    private Integer post_time_begin;

    /**
     * 区间查询添加时间结束时间
     */
    private Integer post_time_end;

    /**
     * 格式化时间
     */
    private String add_time;

    private String isZhiChi;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    //特殊返回页面要生成的ID
    private String  itemId;
    //特殊返回页面要生成的分类ID
    private String  typeId;

    public String getCatetile() {
        return catetile;
    }

    public void setCatetile(String catetile) {
        this.catetile = catetile;
    }

    public Integer getCateID() {
        return cateID;
    }

    public void setCateID(Integer cateID) {
        this.cateID = cateID;
    }

    public Integer getPost_time_begin() {
        return post_time_begin;
    }

    public void setPost_time_begin(Integer post_time_begin) {
        this.post_time_begin = post_time_begin;
    }

    public Integer getPost_time_end() {
        return post_time_end;
    }

    public void setPost_time_end(Integer post_time_end) {
        this.post_time_end = post_time_end;
    }

    public String getAdd_time() {
        return add_time;
    }

    public void setAdd_time(String add_time) {
        this.add_time = add_time;
    }

    public String getIsZhiChi() {
        return isZhiChi;
    }

    public void setIsZhiChi(String isZhiChi) {
        this.isZhiChi = isZhiChi;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
}
