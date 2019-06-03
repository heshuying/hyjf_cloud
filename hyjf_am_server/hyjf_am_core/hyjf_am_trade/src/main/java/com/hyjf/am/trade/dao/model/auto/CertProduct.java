package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class CertProduct implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 产品编号
     *
     * @mbggenerated
     */
    private String productNid;

    /**
     * 是否是智投 0：散标,1：智投
     *
     * @mbggenerated
     */
    private Integer isPlan;

    /**
     * 是否上送产品信息
     *
     * @mbggenerated
     */
    private Integer isProduct;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductNid() {
        return productNid;
    }

    public void setProductNid(String productNid) {
        this.productNid = productNid == null ? null : productNid.trim();
    }

    public Integer getIsPlan() {
        return isPlan;
    }

    public void setIsPlan(Integer isPlan) {
        this.isPlan = isPlan;
    }

    public Integer getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(Integer isProduct) {
        this.isProduct = isProduct;
    }
}