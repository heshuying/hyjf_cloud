package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class TenderUtmChangeLog implements Serializable {
    private Integer id;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 渠道来源
     *
     * @mbggenerated
     */
    private Integer tenderUtmId;

    /**
     * 一级分部
     *
     * @mbggenerated
     */
    private Integer topDeptId;

    /**
     * 二级分部
     *
     * @mbggenerated
     */
    private Integer secondDeptId;

    /**
     * 三级分部id
     *
     * @mbggenerated
     */
    private Integer thirdDeptId;

    /**
     * 操作人
     *
     * @mbggenerated
     */
    private Integer operator;

    /**
     * 操作时间
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

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public Integer getTenderUtmId() {
        return tenderUtmId;
    }

    public void setTenderUtmId(Integer tenderUtmId) {
        this.tenderUtmId = tenderUtmId;
    }

    public Integer getTopDeptId() {
        return topDeptId;
    }

    public void setTopDeptId(Integer topDeptId) {
        this.topDeptId = topDeptId;
    }

    public Integer getSecondDeptId() {
        return secondDeptId;
    }

    public void setSecondDeptId(Integer secondDeptId) {
        this.secondDeptId = secondDeptId;
    }

    public Integer getThirdDeptId() {
        return thirdDeptId;
    }

    public void setThirdDeptId(Integer thirdDeptId) {
        this.thirdDeptId = thirdDeptId;
    }

    public Integer getOperator() {
        return operator;
    }

    public void setOperator(Integer operator) {
        this.operator = operator;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}