package com.hyjf.wbs.qvo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @Auther: wxd
 * @Date: 2019-04-16 14:19
 * @Description:
 */
@ApiModel("产品信息")
public class ProductInfoQO implements Serializable {
    @ApiModelProperty(value = "资产端产品名称")
    private String productName;
    @ApiModelProperty(value = "资产端产品编号")
    private String productNo;
    @ApiModelProperty(value = "线上产品类型，默认1")
    private Integer productSort=1;
    @ApiModelProperty(value = "产品来源，默认1")
    private Integer assetId=1;
    @ApiModelProperty(value = "产品状态")
    private Integer productStatus;
    @ApiModelProperty(value = "产品发布状态默认1")
    private Integer publishStatus=1;
    @ApiModelProperty(value = "pc站外跳转链接")
    private String linkUrl;
    @ApiModelProperty(value = "h5站外跳转链接")
    private String H5linkUrl;

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

    public Integer getProductSort() {
        return productSort;
    }

    public void setProductSort(Integer productSort) {
        this.productSort = productSort;
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
        return H5linkUrl;
    }

    public void setH5linkUrl(String h5linkUrl) {
        H5linkUrl = h5linkUrl;
    }
}
