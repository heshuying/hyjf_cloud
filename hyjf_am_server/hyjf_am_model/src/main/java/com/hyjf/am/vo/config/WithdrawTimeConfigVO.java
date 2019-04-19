/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.config;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * 提现时间配置VO
 *
 * @author liuyang
 * @version WithdrawTimeConfigVO, v0.1 2019/4/19 9:36
 */
public class WithdrawTimeConfigVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -7680141357973731735L;
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 年份
     *
     * @mbggenerated
     */
    private String year;

    /**
     * 假日名称
     *
     * @mbggenerated
     */
    private String holidayName;

    /**
     * 假日开始时间
     *
     * @mbggenerated
     */
    private Date startDate;

    /**
     * 假日结束时间
     *
     * @mbggenerated
     */
    private Date endDate;

    /**
     * 假日类型 1补休 2假期
     *
     * @mbggenerated
     */
    private Integer holidayType;

    /**
     * 0删除 1可用
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getHolidayType() {
        return holidayType;
    }

    public void setHolidayType(Integer holidayType) {
        this.holidayType = holidayType;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
