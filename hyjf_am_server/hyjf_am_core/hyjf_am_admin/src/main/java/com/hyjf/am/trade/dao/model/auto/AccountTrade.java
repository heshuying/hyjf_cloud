package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class AccountTrade implements Serializable {
    private Short id;

    /**
     * 交易类型 1收入 2支出  3冻结 4解冻
     *
     * @mbggenerated
     */
    private Integer typeId;

    /**
     * 名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 标识名
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 值
     *
     * @mbggenerated
     */
    private String value;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Short order;

    /**
     * 账户余额操作(ADD:余额增加,SUB:余额减少,UNCHANGED:余额不变)
     *
     * @mbggenerated
     */
    private String operation;

    private static final long serialVersionUID = 1L;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
        this.order = order;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }
}