package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;

public class NmUser implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 汇盈账号
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String trueName;

    /**
     * 部门
     *
     * @mbggenerated
     */
    private Integer debtId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName == null ? null : trueName.trim();
    }

    public Integer getDebtId() {
        return debtId;
    }

    public void setDebtId(Integer debtId) {
        this.debtId = debtId;
    }
}