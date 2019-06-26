package com.hyjf.am.vo.trade.cert;

import java.io.Serializable;

public class CertProductVO implements Serializable {
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
     * 是否是智投 1：散标,2：智投
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
        this.productNid = productNid;
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