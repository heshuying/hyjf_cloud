package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhBailConfigLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 修改字段
     *
     * @mbggenerated
     */
    private String modifyColumn;

    /**
     * 修改前值
     *
     * @mbggenerated
     */
    private String beforeValue;

    /**
     * 修改后值
     *
     * @mbggenerated
     */
    private String afterValue;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getModifyColumn() {
        return modifyColumn;
    }

    public void setModifyColumn(String modifyColumn) {
        this.modifyColumn = modifyColumn == null ? null : modifyColumn.trim();
    }

    public String getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(String beforeValue) {
        this.beforeValue = beforeValue == null ? null : beforeValue.trim();
    }

    public String getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(String afterValue) {
        this.afterValue = afterValue == null ? null : afterValue.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}