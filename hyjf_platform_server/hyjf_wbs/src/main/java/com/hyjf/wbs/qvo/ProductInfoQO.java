package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Auther: wxd
 * @Date: 2019-04-26 10:15
 * @Description:产品信息
 */
public class ProductInfoQO implements Serializable {
    @ApiModelProperty(value = "产品名称")
    protected String productName;
    @ApiModelProperty(value = "产品编号")
    protected String productNo;
    @ApiModelProperty(value = "线上产品类型")
    protected Integer productType=2;
    @ApiModelProperty(value = "产品来源")
    protected Integer assetId = 1;
    @ApiModelProperty(value = "产品状态")
    protected Integer productStatus;
    @ApiModelProperty(value = "产品发布状态")
    protected Integer publishStatus=1;
    @ApiModelProperty(value = "pc站外跳转链接")
    protected String linkUrl;
    @ApiModelProperty(value = "h5站外跳转链接")
    protected String h5linkUrl;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public Integer getPublishStatus() {
        return publishStatus;
    }

    public void setPublishStatus(Integer publishStatus) {
        this.publishStatus = publishStatus;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getH5linkUrl() {
        return h5linkUrl;
    }

    public void setH5linkUrl(String h5linkUrl) {
        this.h5linkUrl = h5linkUrl;
    }
}
