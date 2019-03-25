package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.admin.NewYearActivityVO;
import com.hyjf.am.vo.admin.NewYearActivityRewardVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author xiehuili on 2019/3/25.
 */
@ApiModel(value="春节活动",description="春节活动")
public class NewYearNineteenRequestBean  implements Serializable {

    private Integer id;
    /**
     * 账户户名 检索条件
     */
    @ApiModelProperty(value ="账户户名 检索条件")
    private String username;
    /**
     * 姓名 检索条件
     */
    @ApiModelProperty(value ="姓名 检索条件")
    private String truename;

    /**
     * 状态 检索条件
     */
    @ApiModelProperty(value ="状态 检索条件")
    private Integer status;

    /**
     * 排序 检索条件
     */
    @ApiModelProperty(value ="排序 检索条件")
    private String sort;
    /**
     * 排序列
     */
    @ApiModelProperty(value ="排序列 检索条件")
    private String col;

    /**
     * 时间排序 检索条件
     */
    @ApiModelProperty(value ="时间排序 检索条件")
    private String sortTwo;
    /**
     * 排序列
     */
    @ApiModelProperty(value ="排序列 检索条件")
    private String colTwo;

    public List<NewYearActivityVO> recordList;

    public List<NewYearActivityRewardVO> recordRewardList;

    /** 翻页开始 */
    protected int limitStart = -1;
    /** 翻页结束 */
    protected int limitEnd = -1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getSortTwo() {
        return sortTwo;
    }

    public void setSortTwo(String sortTwo) {
        this.sortTwo = sortTwo;
    }

    public String getColTwo() {
        return colTwo;
    }

    public void setColTwo(String colTwo) {
        this.colTwo = colTwo;
    }

    public List<NewYearActivityVO> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<NewYearActivityVO> recordList) {
        this.recordList = recordList;
    }

    public List<NewYearActivityRewardVO> getRecordRewardList() {
        return recordRewardList;
    }

    public void setRecordRewardList(List<NewYearActivityRewardVO> recordRewardList) {
        this.recordRewardList = recordRewardList;
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
}
