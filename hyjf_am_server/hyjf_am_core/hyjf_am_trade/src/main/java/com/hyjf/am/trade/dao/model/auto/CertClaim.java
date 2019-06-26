package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class CertClaim implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 债权编码编号
     *
     * @mbggenerated
     */
    private String claimNid;

    /**
     * 是否是债转标的 1：承接债权,2：原始债权
     *
     * @mbggenerated
     */
    private Integer creditFlg;

    /**
     * 是否是智投 0：散标,1：智投
     *
     * @mbggenerated
     */
    private Integer isPlan;

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

    public String getClaimNid() {
        return claimNid;
    }

    public void setClaimNid(String claimNid) {
        this.claimNid = claimNid == null ? null : claimNid.trim();
    }

    public Integer getCreditFlg() {
        return creditFlg;
    }

    public void setCreditFlg(Integer creditFlg) {
        this.creditFlg = creditFlg;
    }

    public Integer getIsPlan() {
        return isPlan;
    }

    public void setIsPlan(Integer isPlan) {
        this.isPlan = isPlan;
    }

    public Integer getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(Integer isConfig) {
        this.isConfig = isConfig;
    }
}