package com.hyjf.am.vo.trade.cert;

import java.io.Serializable;

public class CertBorrowVO implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 是否是债转标的 1：债转2：原始债权
     *
     * @mbggenerated
     */
    private Integer creditFlg;

    /**
     * 是否上送产品配置信息
     *
     * @mbggenerated
     */
    private Integer isConfig;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getCreditFlg() {
        return creditFlg;
    }

    public void setCreditFlg(Integer creditFlg) {
        this.creditFlg = creditFlg;
    }

    public Integer getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(Integer isConfig) {
        this.isConfig = isConfig;
    }
}