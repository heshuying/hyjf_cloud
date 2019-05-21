package com.hyjf.am.vo.trade.cert;

import java.io.Serializable;

public class CertClaimVO implements Serializable {
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
    private String claimNid;

    /**
     * 是否是债转标的 1：未转让,2：未还款
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

    public Integer getIsConfig() {
        return isConfig;
    }

    public void setIsConfig(Integer isConfig) {
        this.isConfig = isConfig;
    }
}